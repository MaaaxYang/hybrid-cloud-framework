package org.github.bodhi.hybrid.context.config;


import org.github.bodhi.hybrid.context.enums.HybridMode;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-05 20:13
 **/
public class BestsignConfig extends FrameworkConfig{

    private String clientId = "1";

    private String clientSecret = "bestsign";

    private String signType = "RSA256";

    private String privateKey = "";

    private String storageDefaultDriver = "";

    private HybridMode mode = HybridMode.PRIVATE;

    /**---------------------------------------------------------------------------------**/


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public HybridMode getMode() {
        return mode;
    }

    public void setMode(HybridMode mode) {
        this.mode = mode;
    }

    public String getStorageDefaultDriver() {
        return storageDefaultDriver;
    }

    public void setStorageDefaultDriver(String storageDefaultDriver) {
        this.storageDefaultDriver = storageDefaultDriver;
    }


}
