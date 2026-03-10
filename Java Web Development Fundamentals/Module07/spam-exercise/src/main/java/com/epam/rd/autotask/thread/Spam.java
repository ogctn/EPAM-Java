package com.epam.rd.autotask.thread;

import java.util.Scanner;

/**
 * Runs threads, each of which prints its own message 
 * with a given time interval while the thread is not interrupted
 */
public class Spam {

    private final String[] messages;
    private final long[] intervals;
    private final Thread[] threads;

    public Spam(String[] messages, long[] intervals) {
        if (messages == null || intervals == null ||
                messages.length < 2 || (messages.length != intervals.length))
            throw (new IllegalArgumentException());

        this.messages = messages;
        this.intervals = intervals;
        this.threads = new Thread[messages.length];
    }

    /**
     *  Runs all threads in the pool
     */
    public void start() {
        for (int i = 0; i < messages.length; i++) {
            threads[i] = new Thread(new Worker(messages[i], intervals[i]));
            threads[i].start();
        }
    }

    /** 
     * Interrupts all threads in the pool
     */
    public void stop() {

        for (Thread thread : threads) {
            if (thread != null)
                thread.interrupt();
        }

        for (Thread thread : threads) {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * This class is used to spam a message with a given time interval.
     * The message must be printed to the console until the thread is interrupted.
     */
    static class Worker implements Runnable {
        private final String message;
        private final long interval;

        public Worker(String message, long interval) {
            this.message = message;
            this.interval = interval;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(message);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

    /**
     * 1) Creates a Spam object.<br/>
     * 2) Starts the threads.<br/>
     * 3) Reads Enter from System.in<br/>
     * 4) Interrupts the threads.<br/>
     */
    public static void send() {
        String[] messages = {"@@@", "bbbbbbb"};
        long[] intervals = {142, 420};

        Spam spam = new Spam(messages, intervals);
        spam.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        spam.stop();
    }
}
