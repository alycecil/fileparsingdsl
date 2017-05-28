package com.buttonmash.dsl.io;

import java.text.MessageFormat;

public class Token<T> {

    public static final Token<Void> StartToken = new Token<Void>(null, null, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);


    T type;
    String text;
    int line;
    int charBegin;
    int charEnd;

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

    @Override
    public String toString() {
        return MessageFormat.format("[{0}, ''{1}'']", type, text);
        //return MessageFormat.format("Token'{'type={0}, text=''{1}'', line={2}, charBegin={3}, charEnd={4}'}'", type, text, line, charBegin, charEnd);
    }
}

