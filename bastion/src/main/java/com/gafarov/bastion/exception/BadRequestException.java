package com.gafarov.bastion.exception;

public class BadRequestException extends ModelException{
    public BadRequestException(String message) {
        super(message);
    }
}
