package org.github.bodhi.hybrid.cluster;

import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-20 18:22
 **/
public class MemberMenu {

    private static ServerNode local;

    private static List<ServerNode> members;

    public static ServerNode getLocal() {
        return local;
    }

    public static void setLocal(ServerNode local) {
        MemberMenu.local = local;
    }

    public static List<ServerNode> getMembers() {
        return members;
    }

    public static void setMembers(List<ServerNode> members) {
        MemberMenu.members = members;
    }
}
