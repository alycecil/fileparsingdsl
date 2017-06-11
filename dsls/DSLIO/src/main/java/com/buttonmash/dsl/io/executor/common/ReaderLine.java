package com.buttonmash.dsl.io.executor.common;


//todo make this a child/parent to allow for non string based file formats (ie excel)
public class ReaderLine {

    final char[] chararray;
    final String line;

    ReaderLine(String line) {
        this.line = line;
        chararray = line.toCharArray();
    }

    public char[] getChararray() {
        return chararray;
    }

    public String getLine() {
        return line;
    }
}
