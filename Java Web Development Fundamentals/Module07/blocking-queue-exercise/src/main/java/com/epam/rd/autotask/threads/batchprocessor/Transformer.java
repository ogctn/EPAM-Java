package com.epam.rd.autotask.threads.batchprocessor;

import java.util.concurrent.BlockingQueue;
import java.util.function.UnaryOperator;

public class Transformer extends Thread {

    private final UnaryOperator<String> op;
    private final BlockingQueue<String> sourceQueue;
    private final BlockingQueue<String> destinationQueue;

    public Transformer(UnaryOperator<String> op,
                       BlockingQueue<String> sourceQueue,
                       BlockingQueue<String> destinationQueue) {

        if (op == null || sourceQueue == null || destinationQueue == null)
            throw (new NullPointerException());

        this.op = op;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String message = sourceQueue.take();
                if ("".equals(message)) {
                    destinationQueue.put("");
                    break;
                }
                String res = op.apply(message);
                destinationQueue.put(res);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
