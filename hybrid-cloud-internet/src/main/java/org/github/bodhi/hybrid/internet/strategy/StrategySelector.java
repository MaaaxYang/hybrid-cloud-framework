package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.appointment.Relay;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.DefaultHttpClient;
import org.github.bodhi.hybrid.internet.enums.RelayTiming;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.DefaultHttpClient;
import org.github.bodhi.hybrid.internet.enums.RelayTiming;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.norms.exception.BestsignException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:21
 **/
public class StrategySelector {

    private static final Object lock = new Object();

    private volatile static Map<String,IStrategy> cache = new ConcurrentHashMap<>();

    private Client client;

    public StrategySelector() {

    }

    public IStrategy select(StrategyParameter parameter){
        if (client==null){
            synchronized (StrategySelector.class){
                if (client==null){
                    client = ClientHolder.getClientMap().get(DefaultHttpClient.class);
                }
            }
        }

        if (parameter==null){
            throw new BestsignException("strategy parameter can't null");
        }

        Relay relay = parameter.getMethod().getAnnotation(Relay.class);
        // normal strategy
        if (relay==null){
            if (parameter.getTarget()==null){
                throw new BestsignException(parameter.getMethod().getDeclaringClass().getCanonicalName()+ "not found implementation class");
            }
            return get(parameter,NormalStrategy.class);
        }

        parameter.setResponseType(relay.responseType());

        // skip strategy
        RelayTiming timing = relay.relayTiming();
        if (timing.equals(RelayTiming.SKIP)){
            return get(parameter,SkipStrategy.class);
        }

        if (parameter.getTarget()==null){
            throw new BestsignException(parameter.getMethod().getDeclaringClass().getCanonicalName()+ " not found implementation class");
        }

        // before strategy
        if (timing.equals(RelayTiming.BEFORE)){
            return get(parameter,BeforeStrategy.class);
        }

        // after strategy
        if (timing.equals(RelayTiming.AFTER)){
            return get(parameter,AfterStrategy.class);
        }

        throw new BestsignException("not found a strategy");
    }


    private IStrategy get(StrategyParameter parameter,Class<? extends IStrategy> clazz){
        String key = parameter.getClassLoader().hashCode()+parameter.getMethod().getName();
        IStrategy strategy = cache.get(key);
        if (strategy==null){
            synchronized (lock){
                strategy = cache.get(key);
                if (strategy==null){
                    try{
                        Constructor constructor = clazz.getConstructor(Client.class);
                        strategy = (IStrategy) constructor.newInstance(client);
                    }catch (Exception e){
                        throw new BestsignException("strategy instance error",e);
                    }
                    cache.put(key,strategy);
                }
            }
        }
        return strategy;
    }
}
