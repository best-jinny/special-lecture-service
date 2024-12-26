package com.hhplus.lecture.common.exceptions;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
