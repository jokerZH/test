package com.didi.test;

import sun.misc.Service;

import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        Thread thread1, thread2;

        thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Class clazz = Class.forName("com.mysql.jdbc.Driver", true, Thread.currentThread().getContextClassLoader());
                    System.out.println(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.setName("thread-0");
        thread1.setDaemon(true);
        thread1.start();


        thread2 = new Thread(new Runnable() {
            public void run() {
                Iterator ps = Service.providers(java.sql.Driver.class);
                try {
                    while (ps.hasNext()) {
                        System.out.println(ps.next());
                    }
                } catch (Throwable t) {

                }
            }
        });
        thread2.setName("thread-1");
        thread2.setDaemon(true);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hello world");
    }
}
