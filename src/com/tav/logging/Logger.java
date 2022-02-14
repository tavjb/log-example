package com.tav.logging;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.tav.logging.LoggerConfig.writer;

public class Logger {
    private static final Map<Class<?>, Logger> loggers = new HashMap<>();

    public static Logger getLogger(Class<?> sourceClass) {
        Logger logger = loggers.get(sourceClass);
        if (logger == null) {
            logger = new Logger(sourceClass);
            loggers.put(sourceClass, logger);
        }
        return logger;
    }

    private Logger(final Class<?> owner) {
        this.source = owner;
    }

    private final Class<?> source;

    public void info(String msg) {
        log(msg, LogLevel.INFO);
    }

    public void debug(String msg) {
        log(msg, LogLevel.DEBUG);
    }

    public void warn(String msg) {
        log(msg, LogLevel.WARNING);
    }

    public void error(String msg) {
        log(msg, LogLevel.ERROR);
    }

    private void log(String msg, LogLevel logLevel)  {
        final String log = LocalDateTime.now() + " | source: " + this.source + " | level: " + logLevel + " | message: " + msg;

        PrintStream printStream = logLevel == LogLevel.ERROR ? System.err : System.out;
        try {
            switch (LoggerConfig.logMode) {
                case FILE -> {
                    writer.write(log);
                    writer.flush();
                }

                default -> printStream.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
