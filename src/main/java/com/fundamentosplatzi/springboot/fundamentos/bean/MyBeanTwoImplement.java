package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyBeanTwoImplement implements MyBean {
    @Override
    public void print() {
        System.out.println("Hola desde my implementacion 2 propia del bean 2");
    }
}
