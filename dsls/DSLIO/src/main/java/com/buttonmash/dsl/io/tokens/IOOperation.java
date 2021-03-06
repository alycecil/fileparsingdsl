package com.buttonmash.dsl.io.tokens;

import com.buttonmash.dsl.io.Token;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IOOperation implements Operation {
    List<Token> args;

    Token[] storeAsDirectives;

    Token storeTo;

    public IOOperation(Token ... arg0) {
        args = Arrays.asList(arg0);
    }

    public List<Token> getArgs() {
        return Collections.unmodifiableList(args);
    }

    public Token[] getStoreAsDirectives() {
        return storeAsDirectives;
    }

    public Token getStoreTo() {
        return storeTo;
    }

    @Override
    public String toString() {
        return "IOOperation{" +
                "args=" + args +
                '}';
    }
}
