package org.github.bodhi.hybrid.context.factory;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.config.BestsignConfig;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.github.bodhi.hybrid.norms.event.BestsignEventPublisher;
import org.github.bodhi.hybrid.norms.serializers.Serializer;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 16:14
 **/
public class ApplicationContextFactory {

    private BestsignConfig config;

    private ClassLoader loader;

    private ExecutorService executor;

    private BestsignEventPublisher publisher;

    private Serializer serializer;

    public ApplicationContextFactory(BestsignConfig config, ClassLoader loader, ExecutorService executor, BestsignEventPublisher publisher, Serializer serializer) {
        this.config = config;
        this.loader = loader;
        this.executor = executor;
        this.publisher = publisher;
        this.serializer = serializer;
    }

    public <T extends ApplicationContext> AbstractApplicationContext create(Class<T> clazz){

        try {

            AbstractApplicationContext context = (AbstractApplicationContext)clazz.getConstructor(BestsignConfig.class).newInstance(config);

            context.setClassloader(loader);
            context.setExecutor(executor);
            context.setPublisher(publisher);
            context.setSerializer(serializer);

            return context;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setLoader(ClassLoader loader) {
        this.loader = loader;
    }
}
