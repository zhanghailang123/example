package com.zhl.example.test;

/**
 * @author hailang.zhang
 * @since 2023-02-17
 */
public class Test {

    public static void main(String[] args) {

        User zhangsan = new User();
        zhangsan.setName("zhangsan");

        Car car = new Car("宝马");
        User lisi = zhangsan;
        System.out.println(lisi);
        lisi.setName("lisi");
        lisi.setCar(car);
        System.out.println(lisi);

        lisi = new User();
        //测试引用的问题
        //think keep
        System.out.println(lisi);
        //前进
    }
}