package com.skiply.student.registration.common.model.exception;

import jakarta.annotation.Nullable;

public class TransientFailure extends RuntimeException {

    private final String code;

    public TransientFailure(@Nullable String message) {
        super(message);
        this.code = null;
    }

    public TransientFailure(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
        this.code = null;
    }

    public TransientFailure(@Nullable String code, @Nullable String message) {
        super(message);
        this.code = code;
    }

    public TransientFailure(@Nullable String code, @Nullable String message, @Nullable Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
