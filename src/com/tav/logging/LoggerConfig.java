package com.company.logging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerConfig {
    static LogMode logMode;
    static LogLevel logLevel;
    static boolean appendLog;
    static BufferedWriter writer = null;
    static final String LOG_FILE = "logs/log_" + LocalDate.now() + ".txt";

    private static final String LOG_CONFIG_FILE = "resources/log_config.txt";
    private static final String LOG_MODE_PROPERTY = "log_mode";
    private static final String LOG_LEVEL_PROPERTY = "log_level";
    private static final String APPEND_LOG_PROPERTY = "append_log";

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOG_CONFIG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(LOG_MODE_PROPERTY)) {
                    logMode = LogMode.valueOf(extractPropertyValue(line));
                    System.out.println("log_mode configured to " + logMode);
                }

                if (line.contains(LOG_LEVEL_PROPERTY)) {
                    logLevel = LogLevel.valueOf(extractPropertyValue(line));
                    System.out.println("log_level configured to " + logLevel);
                }

                if (line.contains(APPEND_LOG_PROPERTY)) {
                    appendLog = Boolean.parseBoolean(extractPropertyValue(line));
                    System.out.println("append_log configured to " + appendLog);
                }
            }
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to configure logger");
            logMode = LogMode.CONSOLE;
        }
    }

    private static String extractPropertyValue(final String line) {
        int index = 0;
        char ch = line.charAt(index);
        while (ch != '=') {
            index++;
            ch = line.charAt(index);
        }
        index++;
        StringBuilder value = new StringBuilder();
        while (index < line.length()) {
            ch = line.charAt(index);
            value.append(ch);
            index++;
        }
        return value.toString();
    }
}
