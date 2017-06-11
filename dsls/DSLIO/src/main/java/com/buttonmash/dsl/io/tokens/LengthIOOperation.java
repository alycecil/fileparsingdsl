package com.buttonmash.dsl.io.tokens;

import com.buttonmash.dsl.io.Token;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LengthIOOperation extends IOOperation {
    Token length;

    public LengthIOOperation(Token length, Token storeTo, Token ... storeAsDirectives) {
        super(length, storeTo);

        this.length = length;

        this.storeTo = storeTo;
        this.storeAsDirectives = storeAsDirectives;
    }


    public Token getLength() {
        return length;
    }
}
