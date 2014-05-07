package com.hidetomo;

import java.util.function.Function;

/**
 * Java 8 Sample compare Anonymous class and Lambda
 */
public class Java8Sample {
    public static final void main( String [] args){
        // Anonymous Class
        HelloWorld helloWorldAnonymous = new HelloWorld() {
            @Override
            public String hello(String name) {
                return "Hello, " + name;
            }
        };
        System.out.println(helloWorldAnonymous.hello("Hidetomo"));
        // Lambda
        System.out.println(((HelloWorld)((name) -> "Hello, " + name)).hello("Srinath"));
    }

    @FunctionalInterface
    private interface HelloWorld{
        public String hello(String name);
    }
}
