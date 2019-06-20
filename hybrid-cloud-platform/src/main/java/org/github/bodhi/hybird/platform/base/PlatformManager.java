package org.github.bodhi.hybird.platform.base;

import org.github.bodhi.hybird.platform.factory.BestsignThreadFactory;
import org.github.bodhi.hybird.platform.loader.ClassLoaderFactory;
import org.github.bodhi.hybird.platform.loader.DefaultClassloader;
import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.config.BestsignConfig;
import org.github.bodhi.hybrid.context.config.RefreshConfig;
import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.github.bodhi.hybrid.context.factory.ApplicationContextFactory;
import org.github.bodhi.hybrid.context.listeners.events.ApplicationRefreshedEvent;
import org.github.bodhi.hybrid.context.serializers.SerializerHolder;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.github.bodhi.hybrid.norms.event.BestsignEventPublisher;
import org.github.bodhi.hybrid.norms.event.BestsignListener;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 15:27
 **/
public class PlatformManager {

    private final static Logger logger = LoggerFactory.getLogger(PlatformManager.class);

    private static volatile ApplicationContext context = null;

    private static ApplicationContextFactory applicationContextFactory;

    private static ClassLoaderFactory classLoaderFactory;

    private static BestsignEventPublisher publisher;

    private static ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(1);

    public static void init(BestsignConfig config,ThreadPoolConfig threadPoolConfig) {

        logger.info("bestsign platform init ...");
        PlatformStatus.STATE = PlatformStatus.Status.INIT;

        // base path check
        if (config.getBasePath() != null && config.getBasePath().length() > 0) {

            try {
                Path dirPath = Paths.get(config.getBasePath());
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                PathMenu.BASEPATH.setPath(config.getBasePath());
                List<String> pathList = new ArrayList<>();
                for(PathMenu menu : PathMenu.values()){
                    pathList.add(menu.getPath());
                }
                for(String pathString : pathList){
                    Path path = Paths.get(pathString);
                    if (!Files.exists(path)){
                        Files.createDirectories(path);
                    }
                }

            } catch (IOException e) {
                throw new BestsignException(e);
            }
        }

        // class loader
        classLoaderFactory = new ClassLoaderFactory();

        ClassLoader loader;
        if (config.isDebug()){
            loader = new DefaultClassloader(Thread.currentThread().getContextClassLoader());
        }else{
            loader = classLoaderFactory.create(config.getClassPathConfig());
        }

        // executor configuration
        ExecutorService executor = null;
        if (threadPoolConfig!=null && threadPoolConfig.isEnalbe()){
            executor = new ThreadPoolExecutor(
                    threadPoolConfig.getCorePoolSize(),
                    threadPoolConfig.getMaximumPoolSize(),
                    threadPoolConfig.getKeepAliveTime(),
                    threadPoolConfig.getTimeUnit(),
                    new LinkedBlockingQueue<Runnable>(1024),
                    new BestsignThreadFactory(loader),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }


        // event publisher
        publisher = new BestsignEventPublisher();
        publisher.setExecutor(executor);



        // register listener
        List<BestsignListener> listeners = ServiceLoadUtils.loadAllInstance(BestsignListener.class, loader);
        publisher.register(listeners);


        // serializer configuration
        Serializer serializer = SerializerHolder.getInstance().getSerializer();


        // create context factory
        applicationContextFactory = new ApplicationContextFactory(
                config,
                loader,
                executor,
                publisher,
                serializer
        );
    }

    public static <T extends ApplicationContext> ApplicationContext startApplication(Class<T> clazz){

        logger.info("bestsign platform start application ...");
        PlatformStatus.STATE = PlatformStatus.Status.START;

        logger.info("bestsign platform used context : {}",clazz.getCanonicalName());
        ApplicationContext newContext = applicationContextFactory.create(clazz);

        newContext.start();

        newContext.running();

        context = newContext;

        logger.info("bestsign platform started");

        return newContext;
    }


    public static void shutdownApplicaiton(boolean delay){

        logger.info("bestsign platform shutdown application ...");
        PlatformStatus.STATE = PlatformStatus.Status.CLOSING;

        if (delay){
            scheduled.schedule(new Runnable() {
                @Override
                public void run() {
                    context.close();
                    PlatformStatus.STATE = PlatformStatus.Status.CLOSED;
                }
            },5,TimeUnit.SECONDS);
        }else{
            context.close();
            PlatformStatus.STATE = PlatformStatus.Status.CLOSED;
        }

    }


    public static <T extends ApplicationContext> ApplicationContext refreshApplication(RefreshConfig config, Class<T> clazz){

        logger.info("bestsign platform refresh application ...");
        PlatformStatus.STATE = PlatformStatus.Status.REFRESHING;

        classLoaderFactory = new ClassLoaderFactory();
        ClassLoader loader = classLoaderFactory.create(config.getClassPathConfig());

        applicationContextFactory.setLoader(loader);

        ApplicationContext newContext = applicationContextFactory.create(clazz);
        newContext.start();
        newContext.running();

        final ApplicationContext oldContext = context;

        context = newContext;

        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                oldContext.close();
                System.gc();
            }
        },10,TimeUnit.SECONDS);

        logger.info("platform push context refreshed event");
        publisher.push(new ApplicationRefreshedEvent((AbstractApplicationContext) context));

        PlatformStatus.STATE = PlatformStatus.Status.REFRESHED;

        logger.info("bestsign platform refresh application end");
        logger.info("-----------------------------------------------------------");
        logger.info("bestsign application refreshed , current version {}",config.getVersion());
        logger.info("-----------------------------------------------------------");

        return newContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
