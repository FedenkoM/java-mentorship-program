package org.spring.data.access.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
