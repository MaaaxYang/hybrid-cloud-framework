package org.github.bodhi.hybrid.internet.client;

import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import okhttp3.Response;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 09:28
 **/
public interface Client {

    <T> T execute(ClientRequest request, Class<T> responseClass) throws BestsignException;

    Response execute(ClientRequest request) throws BestsignException;
}
