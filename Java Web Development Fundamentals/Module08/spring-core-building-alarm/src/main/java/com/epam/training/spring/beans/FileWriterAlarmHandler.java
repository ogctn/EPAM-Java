package com.epam.training.spring.beans;

import com.epam.training.spring.model.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component("AlarmWriter")
public class FileWriterAlarmHandler implements AlarmHandler, Lifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterAlarmHandler.class);

    private final String alarmFilePath;
    private BufferedWriter writer;
    private boolean isRunning = false;

    public FileWriterAlarmHandler(@Value("${alarm.file.path}") String alarmFilePath) {
        this.alarmFilePath = alarmFilePath;
    }

    @Override
    public void handleAlarm(Alarm alarm) {
        if (isRunning()) {
            try {
                writer.write(String.format("Action: %s, Location: %s, Reason: %s%n", alarm.action(), alarm.location(), alarm.reason()));
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("Failed to write alarm", e);
            }
        }
    }

    @Override
    public void start() {
        try {
            File file = new File(alarmFilePath);
            if (file.getParentFile() != null && !file.getParentFile().exists())
                file.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(file, true));
            isRunning = true;
            LOGGER.info("AlarmWriter has been started");
        } catch (IOException e) {
            throw new RuntimeException("Could not open alarm file", e);
        }
    }

    @Override
    public void stop() {
        try {
            if (writer != null)
                writer.close();
            isRunning = false;
            LOGGER.info("AlarmWriter has been stopped");
        } catch (IOException e) {
            LOGGER.error("Error closing alarm file", e);
        }
    }

    @Override
    public boolean isRunning() {
        return (isRunning);
    }
}
