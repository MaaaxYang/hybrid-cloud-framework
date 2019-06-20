package org.github.bodhi.hybird.platform.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-14 12:22
 **/
public class DefaultClassloader extends URLClassLoader {

    public DefaultClassloader(URL[] urls) {
        super(urls);
    }

    public DefaultClassloader(){
        super(new URL[]{});
    }

    public DefaultClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public DefaultClassloader( ClassLoader parent) {
        super(new URL[]{}, parent);
    }
}
