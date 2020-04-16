package org.apache.ibatis.reflection;

/**
 * Created by yangyangha on 2018/8/30.
 */
public class Test_propertyNamer {

    private String  name;

    private int Age;

    private boolean isA;
    private boolean yes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
