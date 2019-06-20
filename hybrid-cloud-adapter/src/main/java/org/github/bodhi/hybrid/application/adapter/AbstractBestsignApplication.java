package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.DefaultHttpClient;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.norms.BestsignApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 15:16
 **/
public abstract class AbstractBestsignApplication implements BestsignApplication {

    private final static Logger logger = LoggerFactory.getLogger(BestsignApplication.class);

    private static volatile Client client = null;

    private final static Object lock = new Object();

    public Client client() {
        if (client == null){
            synchronized (lock){
                if (client == null){
                    ClientHolder.getClientMap().get(DefaultHttpClient.class);
                }
            }
        }
        return client;
    }

}
