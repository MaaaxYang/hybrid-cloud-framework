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

    private List<BestsignPackage> packages;

    public List<BestsignPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<BestsignPackage> packages) {
        this.packages = packages;
    }

    public static ClassPathConfig DEFAULT(){
        ClassPathConfig config = new ClassPathConfig();
        config.setPackages(new ArrayList<BestsignPackage>());
        return config;
    }
}
