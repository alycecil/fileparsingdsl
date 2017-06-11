package com.buttonmash.dsl.io.executor.common;

import com.buttonmash.dsl.io.tokens.Operation;

import java.util.Map;

public interface OperationHandler {
    Class<? extends Operation>[] listensTo();

    OperationResult handle(Operation operation, ReaderLine line, Map<String, Object> obj);

}
