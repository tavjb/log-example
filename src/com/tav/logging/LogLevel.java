package com.company.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogLevel {
    INFO(1),
    DEBUG(2),
    WARNING(3),
    ERROR(4);

    @Getter
    private final int value;
}
