package org.github.bodhi.hybrid.internet.listeners.events;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.listeners.events.StandardEvent;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.config.ClientConfig;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 14:45
 **/
public class PlatformStartedEvent extends StandardEvent {

    private ClientConfig clientConfig;

    public PlatformStartedEvent(AbstractApplicationContext context) {
        super(context);
    }

    public PlatformStartedEvent(AbstractApplicationContext context, ClientConfig clientConfig) {
        super(context);
        this.clientConfig = clientConfig;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }
}
