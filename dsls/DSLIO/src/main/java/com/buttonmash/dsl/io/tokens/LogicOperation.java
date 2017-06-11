package com.buttonmash.dsl.io.tokens;

import com.buttonmash.dsl.io.Token;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LogicOperation implements Operation {
    List<Token> args;

    public LogicOperation(Token ... arg0) {
        args = Arrays.asList(arg0);
    }

    public List<Token> getArgs() {
        return Collections.unmodifiableList(args);
    }

    @Override
    public String toString() {
        return "IOOperation{" +
                "args=" + args +
                '}';
    }
}
