package com.example.lostandfound.dto;

public class SuccessOrFailure {
    private boolean success = true;
    private String message;
    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public SuccessOrFailure setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SuccessOrFailure setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public SuccessOrFailure setResult(Object result) {
        this.result = result;
        return this;
    }
}
