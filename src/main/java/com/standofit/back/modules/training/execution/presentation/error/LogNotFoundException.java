package com.standofit.back.modules.training.execution.presentation.error;

import java.util.UUID;

public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException(UUID logId) {
        super("Log not found: " + logId);
    }
}
