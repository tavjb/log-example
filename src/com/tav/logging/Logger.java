package com.company.logging;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.company.logging.LoggerConfig.writer;
import static com.company.logging.LoggerConfig.logMode;

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

    private void log(final String msg, final LogLevel logLevel)  {
        if (logLevel.getValue() > LoggerConfig.logLevel.getValue()) {
            return;
        }

        final String log = LocalDateTime.now() + " | source: " + this.source + " | level: " + logLevel + " | message: " + msg;
        try {
            if (logMode == LogMode.FILE) {
                writer.write(log + "\n");
                writer.flush();
            } else {
                final PrintStream printStream = logLevel == LogLevel.ERROR ? System.err : System.out;
                printStream.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
