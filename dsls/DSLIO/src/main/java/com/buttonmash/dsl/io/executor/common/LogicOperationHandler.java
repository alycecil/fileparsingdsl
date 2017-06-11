package com.buttonmash.dsl.io.executor.common;

import com.buttonmash.dsl.io.tokens.LogicOperation;
import com.buttonmash.dsl.io.tokens.Operation;

import java.util.Map;

public class LogicOperationHandler implements OperationHandler {

    @Override
    public Class<? extends Operation>[] listensTo() {
        Class<? extends Operation>[] result = new Class[]{LogicOperation.class};
        return result;
    }

    @Override
    public OperationResult handle(Operation operation, ReaderLine line, Map<String, Object> obj) {
        ///TODO Implement these as separate handlers in common package as needed got dsl.crosswalk
        throw new UnsupportedOperationException("TODO");
    }
}
