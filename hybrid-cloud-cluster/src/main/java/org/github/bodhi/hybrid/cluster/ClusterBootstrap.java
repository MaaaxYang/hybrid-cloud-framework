package org.github.bodhi.hybrid.cluster;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-20 18:14
 **/
public class ClusterBootstrap {

    public static void start() throws UnknownHostException {
        ClusterProperties properties = new ClusterProperties();
        properties.setMembers(Arrays.asList("127.0.0.1:10099"));

        InetAddress address = InetAddress.getLocalHost();
        String host = address.getHostAddress();

        // local
        ServerNode local = new ServerNode(host);
        local.setNodeId(UUID.randomUUID().toString());
        local.setEpoch(new AtomicInteger(0));
        local.setVersion(new AtomicInteger(0));

        // members
        List<ServerNode> members = new ArrayList<>();
        Iterator<String> iterator = properties.getMembers().iterator();
        while (iterator.hasNext()){
            members.add(new ServerNode(iterator.next()));
        }

        MemberMenu.setLocal(local);
        MemberMenu.setMembers(members);

        // 原则，有节点 上线/下线 才更新epoch，同一个epoch内发生的事件 version +

        // 第一轮
        // 启动时 当前服务节点状态 start状态
        // 遍历member节点，推送当前node，接收到这个节点的服务端，要epoch+1，version 归零

        // 获取到member节点 epoch version，刷新本地记录

        // 当前服务节点状态变更为 同步状态

        // 第二轮
        // 随机挑选几个节点 向member节点 发送当前节点 信息
        // 接受member节点返回的 节点list，
        // 先存入一个单独的list 等到本轮结束后再存入本地member list

        // 如果推送过来的节点 记录信息  与本地的一致则不处理 直接返回一个空的list
        // 如果不一致（epoch、version）不同，则需要返回最新的list
    }

}
