package com.buttonmash.dsl.io.executor.common;

public class OperationResult extends Throwable /* for that stack trace */{
    public static final OperationResult SUCCESS = new OperationResult(OperationResultTypes.SUCCESS);
    OperationResultTypes type;

    public OperationResult(OperationResultTypes type) {
        this.type = type;
    }

    public OperationResult(String msg, OperationResultTypes type) {
        super(msg);
        this.type = type;
    }
}