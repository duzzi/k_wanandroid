package com.duzzi.kotlin.kotlin.learn.learn1;

public interface Flyer {

    void kind();

    //java8接口支持默认实现
    default public void fly() {
        System.out.println("this is java 8 default func");
    }
}
