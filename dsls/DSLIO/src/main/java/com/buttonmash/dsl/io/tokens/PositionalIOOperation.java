package com.buttonmash.dsl.io.tokens;

import com.buttonmash.dsl.io.Token;

public class PositionalIOOperation extends IOOperation {
    Token start; Token end;

    public PositionalIOOperation(Token start, Token end, Token storeTo, Token ... storeAsDirectives) {
        super(start, end, storeTo);

        this.start = start;
        this.end = end;

        this.storeTo = storeTo;
        this.storeAsDirectives = storeAsDirectives;
    }

    public Token getStart() {
        return start;
    }

    public Token getEnd() {
        return end;
    }
}
