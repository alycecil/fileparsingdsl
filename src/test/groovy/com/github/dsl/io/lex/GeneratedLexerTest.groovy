package com.github.dsl.io.lex

import com.github.dsl.io.lex.generated.DSLLexer

import static org.testng.Assert.*
import static com.github.dsl.io.lex.LanguageDefinitions.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GeneratedLexerTest {

    @Test(dataProvider = "parserDataprovider")
    public void test(testName, dsl, expected){
        def dslStringReader = new StringReader(dsl);

        def lexer = new DSLLexer(dslStringReader)

        def tokens = []

        def next = Token.StartToken;
        while(next!=null){
            next = lexer.next
            tokens << next
        }



        tokens.each {
            Token t ->
                println t
        }

        fail('missing impl')
    }

    @DataProvider(name = "parserDataprovider")
    public Object[][] parserDataprovider(){
        [
            [/Fixed Width Positional Simple/,
             /[1,10->A]/,
            /* [IO_START, [IDENTITY,1], [IDENTITY,10], NOP, [IDENTITY,A], IO_END]*/ null
            ],
        ] as Object[][]
    }

}
