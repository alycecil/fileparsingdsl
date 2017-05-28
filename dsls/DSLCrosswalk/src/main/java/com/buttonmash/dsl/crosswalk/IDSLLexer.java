package com.buttonmash.dsl.crosswalk;

public interface IDSLLexer {
    Token getNext() throws java.io.IOException;
}
