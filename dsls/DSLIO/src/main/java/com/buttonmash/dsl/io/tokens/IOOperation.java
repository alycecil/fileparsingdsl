package com.buttonmash.dsl.io.tokens;

import com.buttonmash.dsl.io.Token;

import java.util.Arrays;
import java.util.List;

public class IOOperation implements Operation {
    List<Token> args;

    public IOOperation(Token ... arg0) {
        args = Arrays.asList(arg0);
    }

    @Override
    public String toString() {
        return "IOOperation{" +
                "args=" + args +
                '}';
    }
}
