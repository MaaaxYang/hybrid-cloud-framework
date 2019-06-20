package org.github.bodhi.hybrid.cluster;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-20 18:16
 **/
public class ServerNode {

    private String nodeId;

    private String address;

    private AtomicInteger epoch;

    private AtomicInteger version;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AtomicInteger getEpoch() {
        return epoch;
    }

    public void setEpoch(AtomicInteger epoch) {
        this.epoch = epoch;
    }

    public AtomicInteger getVersion() {
        return version;
    }

    public void setVersion(AtomicInteger version) {
        this.version = version;
    }

    public ServerNode(String address) {
        this.address = address;
    }
}
