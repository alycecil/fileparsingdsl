package com.buttonmash.dsl.io;

import com.buttonmash.dsl.crosswalk.generated.DSLIOParser;
import com.buttonmash.dsl.io.generated.DSLLexer;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;

import java.io.IOException;
import java.io.Reader;

public class DSLFactory {

    public Program buildProgram(Reader stream) throws IOException {
        try {
            ScannerBuffer lexer = new ScannerBuffer(new DSLLexer(stream));

            @SuppressWarnings("deprecation") DSLIOParser mine = new DSLIOParser(lexer);

            Program actual = (Program) mine.parse().value;


            return actual;
        } catch (Exception e) {
            throw new IOException("Unable to build from "+stream, e);
        }

    }


}
