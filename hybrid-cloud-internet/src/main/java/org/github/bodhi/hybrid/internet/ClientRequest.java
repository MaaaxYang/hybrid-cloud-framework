package org.github.bodhi.hybrid.internet;


import org.github.bodhi.hybrid.internet.enums.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 09:29
 **/
public class ClientRequest {

    private String host;

    private String path;

    private HttpMethod httpMethod;

    private Map<String,String> params;

    private Map<String,String> headers;

    private byte[] body;

    private boolean skipFilter;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        if (headers==null){
            headers = new HashMap<>();
        }
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isSkipFilter() {
        return skipFilter;
    }

    public void setSkipFilter(boolean skipFilter) {
        this.skipFilter = skipFilter;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "configHost='" + host + '\'' +
                ", path='" + path + '\'' +
                ", httpMethod=" + httpMethod +
                ", params=" + params +
                ", headers=" + headers +
                ", body=" + Arrays.toString(body) +
                ", skipFilter=" + skipFilter +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        public Builder host(String host){
            this.host = host;
            return this;
        }

        public Builder path(String path){
            this.path = path;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder params(Map<String,String> params){
            this.params = params;
            return this;
        }

        public Builder headers(Map<String,String> headers){
            this.headers = headers;
            return this;
        }

        public Builder body(byte[] body){
            this.body = body;
            return this;
        }

        public Builder skipFilter(boolean skip){
            this.skipFilter = skip;
            return this;
        }

        public ClientRequest build(){
            ClientRequest request = new ClientRequest();
            request.setHost(this.host);
            request.setPath(this.path);
            request.setHttpMethod(this.httpMethod);
            request.setParams(this.params);
            request.setHeaders(this.headers);
            request.setBody(this.body);
            request.setSkipFilter(this.skipFilter);
            return request;
        }

        private String host;

        private String path;

        private HttpMethod httpMethod;

        private Map<String,String> params;

        private Map<String,String> headers;

        private byte[] body;

        private boolean skipFilter;

    }
}
