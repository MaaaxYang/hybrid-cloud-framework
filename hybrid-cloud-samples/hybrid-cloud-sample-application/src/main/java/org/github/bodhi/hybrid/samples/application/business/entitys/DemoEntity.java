package org.github.bodhi.hybrid.samples.application.business.entitys;

import org.github.bodhi.hybrid.internet.appointment.Header;
import org.github.bodhi.hybrid.internet.appointment.Param;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:40
 **/
public class DemoEntity {


    @Param("name")
    private String name;

    @Header("age")
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
