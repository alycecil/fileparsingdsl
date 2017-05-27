package com.buttonmash.dsl.io.lex;

public interface IDSLLexer {
    Token getNext() throws java.io.IOException;
}
