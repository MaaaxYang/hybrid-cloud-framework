package org.github.bodhi.hybrid.norms.event;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-10 09:43
 **/
public class BestsignEventPublisher {

    private List<BestsignListener> listeners;

    private ExecutorService executor;

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public BestsignEventPublisher() {
        executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors()<<2,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024)
                );

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (executor!=null && !executor.isShutdown()){
                    executor.shutdown();
                }
            }
        }));
    }

    public void register(BestsignListener listener){
        if (listeners==null){
            synchronized (BestsignEventPublisher.class){
                if (listeners == null){
                    listeners = new LinkedList<>();
                }
            }
        }
        if (listener!=null){
            listeners.add(listener);
        }
    }

    public void register(List<BestsignListener> listeners){
        if(listeners!=null&&listeners.size()>0){
            for(BestsignListener listener:listeners){
                register(listener);
            }
        }
    }

    public <T extends BestsignEvent> void push(T event){
        if (listeners!=null && listeners.size()>0){
            for(BestsignListener listener:listeners){
                for(Method method:listener.getClass().getDeclaredMethods()){
                    for(Type type: method.getGenericParameterTypes()){
                        if (type.equals(event.getClass())){
                            listener.onBestsignEvent(event);
                        }
                    }
                }
            }
        }
    }

    public <T extends BestsignEvent> void pushAysnc(final T event){
        if (executor!=null){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    push(event);
                }
            });
        }else{
            push(event);
        }
    }
}
