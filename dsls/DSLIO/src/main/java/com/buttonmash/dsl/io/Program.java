package com.buttonmash.dsl.io;

import com.buttonmash.dsl.io.tokens.Operation;

import java.util.List;

public class Program {
    List<Operation> operations;

    public Program(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Program{" +
                "operations:" + operations +
                '}';
    }
}
