package org.github.bodhi.hybrid.context;

import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.context.interceptor.ApplicationInterceptor;
import org.github.bodhi.hybrid.context.listeners.events.ApplicationClosedEvent;
import org.github.bodhi.hybrid.context.listeners.events.ApplicationClosingEvent;
import org.github.bodhi.hybrid.context.listeners.events.ApplicationRunningEvent;
import org.github.bodhi.hybrid.context.listeners.events.ApplicationStartEvent;
import org.github.bodhi.hybrid.context.order.Orders;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.github.bodhi.hybrid.norms.BestsignApplication;
import org.github.bodhi.hybrid.norms.annotations.BestsignProvider;
import org.github.bodhi.hybrid.norms.bean.BodhiBean;
import org.github.bodhi.hybrid.norms.event.BestsignEventPublisher;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.github.bodhi.hybrid.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 09:19
 **/
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected final static Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    protected Map<String, BodhiBean> beanMap = new ConcurrentHashMap<>();

    protected List<BestsignApplication> applications;

    protected BodhiConfig config;

    protected ClassLoader loader;

    protected ExecutorService executor;

    protected BestsignEventPublisher publisher;

    protected Serializer serializer;

    public AbstractApplicationContext(BodhiConfig config) {
        this.config = config;
    }

    @Override
    public <T extends BodhiBean> T getBean(String canonicalName) {
        return (T)beanMap.get(canonicalName);
    }

    @Override
    public <T extends BodhiBean> T getBean(Class clazz) {
        BodhiBean bean = beanMap.get(clazz.getCanonicalName());
        if (bean==null){
            String alias = getAlias(clazz);
            if (!StringUtils.isNullOrEmpty(alias)){
                bean = beanMap.get(alias);
            }
        }
        return (T)bean;
    }

    @Override
    public void setClassloader(ClassLoader loader) {
        this.loader = loader;
    }

    @Override
    public ClassLoader getClassloader() {
        return this.loader;
    }

    @Override
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public ExecutorService getExecutor() {
        return this.executor;
    }

    @Override
    public <T,S> void setSerializer(Serializer<T,S> serializer) {
        this.serializer = serializer;
    }

    @Override
    public <T,S> Serializer<T,S> getSerializer() {
        return this.serializer;
    }

    @Override
    public void setPublisher(BestsignEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public BestsignEventPublisher getPublisher() {
        return this.publisher;
    }

    @Override
    public void start() {

        logger.info("application context start");

        // application load
        applications = ServiceLoadUtils.loadAllInstance(
                BestsignApplication.class,
                loader
        );


        // 加载拦截器
        logger.info("application context interceptors loading");
        List<ApplicationInterceptor> interceptors = ServiceLoadUtils.loadAllInstance(
                ApplicationInterceptor.class,
                loader
        );


        // 排序 数字越小优先级越高
        List<ApplicationInterceptor> sorted = Orders.listSort(interceptors);


        // 执行拦截器
        logger.info("application context interceptors execute");
        for (ApplicationInterceptor interceptor:sorted){
            logger.info("application context '{}' execute",interceptor.getClass().getCanonicalName());
            interceptor.execute(this);
        }

        // custom initialize
        logger.info("application context execute custom enhance");
        customize();

        if (publisher!=null){
            logger.info("application push started events");
            publisher.push(new ApplicationStartEvent(this));
        }
    }

    protected abstract void customize();

    @Override
    public void running() {
        
        if (publisher!=null){
            logger.info("application push running events");
            publisher.push(new ApplicationRunningEvent(this));
        }
        logger.info("application context running");
    }

    @Override
    public void close() {
        logger.info("application context closing");
        if (publisher!=null){
            logger.info("application push closing events");
            publisher.push(new ApplicationClosingEvent(this));
        }

        if (executor!=null){
            if (!executor.isShutdown()){
                executor.shutdown();
            }
        }

        if (publisher!=null){
            logger.info("application push closed events");
            publisher.push(new ApplicationClosedEvent(this));
        }
        logger.info("application context closed");
    }

    public Map<String, BodhiBean> getBeanMap() {
        return beanMap;
    }

    public BodhiConfig getConfig() {
        return config;
    }

    @Override
    public List<BestsignApplication> applicaitons() {
        return this.applications;
    }


    private String getAlias(Class clazz){
        if (clazz.isInterface()){
            Annotation annotation = clazz.getAnnotation(BestsignProvider.class);
            if (annotation!=null){
                BestsignProvider provider = (BestsignProvider)annotation;
                return provider.value();
            }
        }
        return null;
    }
}
