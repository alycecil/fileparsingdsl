package com.buttonmash.dsl.io.executor.common;

public enum OperationResultTypes {

    /**
     * this operation is done, no issues
     */
    SUCCESS,

    /**
     * request current line does nothing
     */
    SKIP,

    /**
     * Stop Program, Error
     */
    ERROR,

    /**
     * Stop Program, All done
     */
    EXIT,

}
