package com.awesome.haozhexu.gpsgenerator;

public class JavaCaller {

    public static void call(DemoClass.AbsClass cls) {
        System.out.println(cls.getInt());


    }

    public static void main(String[] args) {
        DemoClass demo = new DemoClass();

        call(demo.new AbsClass() {
            @Override
            public int getInt() {
                return super.getInt();
            }
        });
    }
}
