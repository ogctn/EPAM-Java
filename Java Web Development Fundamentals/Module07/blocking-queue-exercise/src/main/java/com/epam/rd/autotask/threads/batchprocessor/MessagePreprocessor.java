package com.epam.rd.autotask.threads.batchprocessor;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.UnaryOperator;

/**
 *
 */
public class MessagePreprocessor {

    private final List<BlockingQueue<String>> queues;
    private final List<Transformer> threads;
    private Optional<Collection<? extends String>> finalResult;
    private final int initialSize;
    private boolean isCompleted;

    public MessagePreprocessor(Collection<String> initial, List<UnaryOperator<String>> functions) {
        if (initial == null || functions == null)
            throw (new NullPointerException());
        if (initial.isEmpty() || functions.isEmpty())
            throw (new IllegalArgumentException());

        this.queues = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.finalResult = Optional.empty();
        this.initialSize = initial.size();
        this.isCompleted = false;
        int halfCap = Math.max(1, initialSize / 2);

        queues.add(new ArrayBlockingQueue<>(initialSize));
        for (int i = 0; i < functions.size() - 1; i++)
            queues.add(new ArrayBlockingQueue<>(halfCap));
        queues.add(new ArrayBlockingQueue<>(initialSize));

        queues.get(0).addAll(initial);
        for (int i = 0; i < functions.size(); i++) {
            BlockingQueue<String> source = queues.get(i);
            BlockingQueue<String> dest = queues.get(i + 1);
            threads.add(new Transformer(functions.get(i), source, dest));
        }
    }

    List<BlockingQueue<String>> getState() {
        return (this.queues);
    }

    /**
     * Starts all operations in separate threads.
     */
    public void start() {
        for (Transformer t : threads) {
            t.start();
        }
    }

    /**
     * @return Optional.empty() if not all jobs have been processed
     * and processor isn't forcibly stopped, otherwise it stops all
     * running operations and returns a collection of processed jobs
     * @see MessagePreprocessor#stop()
     */
    public Optional<Collection<? extends String>> getResult() {
        if (finalResult.isPresent())
            return (finalResult);

        BlockingQueue<String> destQueue = queues.get(queues.size() - 1);
        if (isCompleted || destQueue.size() == initialSize) {
            stop();
            List<String> ret = new ArrayList<>(destQueue);
            finalResult = Optional.of(Collections.unmodifiableList(ret));
            return (finalResult);
        }
        return (Optional.empty());
    }


    /**
     * Stops all threads. The threads must interrupt immediately
     * after finishing current processing object.<br>
     * It must ensure that all threads are stopped.
     */
    public void stop() {
        this.isCompleted = true;
        for (Transformer t : threads) {
            t.interrupt();
        }
    }
}
