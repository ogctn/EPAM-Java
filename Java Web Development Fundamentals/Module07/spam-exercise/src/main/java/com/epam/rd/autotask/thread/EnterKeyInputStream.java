package com.epam.rd.autotask.thread;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("java:S4929")
public class EnterKeyInputStream extends InputStream {

    private final long interval;
    private final byte[] separatorBytes;
    private int index = 0;

    public EnterKeyInputStream(long interval) {
        this.interval = interval;
        this.separatorBytes = System.lineSeparator().getBytes();
    }

    @Override
    public int read() throws IOException {
        if (index >= separatorBytes.length)
            return (-1);

        if (index == 0) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return (-1);
            }
        }
        return (separatorBytes[index++]);
    }

}
