package com.github.dsl.io.lex;

public interface IDSLLexer {
    Token getNext() throws java.io.IOException;
}
