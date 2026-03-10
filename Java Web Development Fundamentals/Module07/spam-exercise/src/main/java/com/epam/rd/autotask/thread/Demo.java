package com.epam.rd.autotask.thread;

import java.io.InputStream;

public class Demo {
    public static void main(String[] args) {

        InputStream oldInStream = System.in;

        try {
            System.setIn(new EnterKeyInputStream(1337));
            Thread t = new Thread(Spam::send);
            t.start();
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("main interruption");
        } finally {
            System.setIn(oldInStream);
        }
    }

}
