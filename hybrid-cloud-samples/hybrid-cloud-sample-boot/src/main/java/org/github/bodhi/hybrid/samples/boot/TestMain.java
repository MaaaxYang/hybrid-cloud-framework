package org.github.bodhi.hybrid.samples.boot;

import org.github.bodhi.hybrid.internet.ClientRequest;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-20 17:07
 **/
public class TestMain {

    public static void main(String[] args) {

        ClientRequest clientRequest = ClientRequest.builder().host("123").build();
        System.out.println("");
    }

}
