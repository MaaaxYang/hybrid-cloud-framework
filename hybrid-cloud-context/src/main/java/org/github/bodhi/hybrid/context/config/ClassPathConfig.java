package org.github.bodhi.hybrid.context.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-08 09:24
 **/
public class ClassPathConfig {

    private List<BodhiPackage> packages;

    public List<BodhiPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<BodhiPackage> packages) {
        this.packages = packages;
    }

    public static ClassPathConfig DEFAULT(){
        ClassPathConfig config = new ClassPathConfig();
        config.setPackages(new ArrayList<BodhiPackage>());
        return config;
    }
}
