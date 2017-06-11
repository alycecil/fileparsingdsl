package com.buttonmash.dsl.io.executor.common;

import com.buttonmash.dsl.io.tokens.Operation;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NaiveIterator implements Iterator<Map<String, Object>> {
    List<Operation> programOperations;
    BufferedReader reader;
    Map<String, Object> next;

    List<OperationHandler> registeredHandlers;
    private IOException error;

    public NaiveIterator(Reader reader, List<Operation> programOperations, List<OperationHandler> registeredHandlers) {
        if (!(reader instanceof BufferedReader)) {
            reader = new BufferedReader(reader);
        }
        this.reader = (BufferedReader) reader;
        this.programOperations = programOperations;
        this.registeredHandlers = registeredHandlers;
    }

    @Override
    public boolean hasNext() {
        try {
            next = new HashMap<String, Object>();

            String line = reader.readLine();

            ReaderLine readerLine = new ReaderLine(line);

            for (Operation operation : programOperations) {
                for (OperationHandler handler : registeredHandlers) {
                    handle(operation, readerLine, handler);
                }
            }

            return true;
        } catch (IOException e) {
            error = e;
            return false;
        }
    }

    protected void handle(Operation operation, ReaderLine readerLine, OperationHandler handler) {
        OperationResult result = handler.handle(operation, readerLine, next);
        //todo handle result
    }

    @Override
    public Map<String, Object> next() {
        if (error != null) {
            Throwable error = this.error;
            this.error = null;
            throw new IOError(error);
        }


        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}