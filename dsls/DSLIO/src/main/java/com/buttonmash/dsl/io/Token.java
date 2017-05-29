package com.buttonmash.dsl.io;

import java_cup.runtime.Symbol;

import java.text.MessageFormat;

public class Token<T> {

    public static final Token<Void> StartToken = new Token<Void>(null, null, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);


    T type;
    String text;
    int line;
    int charBegin;
    int charEnd;
    Symbol symbol;

    public Token(T type, String text, int line, int charBegin, int charEnd) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.charBegin = charBegin;
        this.charEnd = charEnd;

    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCharBegin() {
        return charBegin;
    }

    public void setCharBegin(int charBegin) {
        this.charBegin = charBegin;
    }

    public int getCharEnd() {
        return charEnd;
    }

    public void setCharEnd(int charEnd) {
        this.charEnd = charEnd;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0}, {1}]", type, text);
    }
}

