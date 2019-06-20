package org.github.bodhi.hybrid.norms.exception;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 13:54
 **/
public enum  ExceptionLevel {
    IGNORE(9,"P9-忽略/正常的"),
    NORMAL(3,"P3-普通错误"),
    ENDURE(2,"P2-可容忍的"),
    SERIOUS(1,"P1-严重"),
    PROFOUND(0,"P0-极度严重")
    ;

    ExceptionLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    private int level;

    private String description;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

