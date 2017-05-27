package com.buttonmash.dsl.io;

public interface IDSLLexer {
    Token getNext() throws java.io.IOException;
}
