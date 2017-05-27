package com.buttonmash.dsl.io

import com.buttonmash.dsl.io.generated.DSLLexer

import static com.buttonmash.dsl.io.LanguageDefinitions.*;

import static org.testng.Assert.*

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

        println "Tokens "


        tokens.each {
            Token t ->
                println t
        }


        if(expected instanceof List){
            expected  = expected as List
            assertEquals( tokens.size(), expected.size() )

            def iter = expected.iterator()
            tokens.each {
                def nxt = iter.next()
                if(nxt) {
                    def (type, value) = nxt

                    assertEquals(it.type, type)
                }
            }
        }
    }

    @DataProvider(name = "parserDataprovider")
    public Object[][] parserDataprovider(){
        [
            [/Fixed Width Positional Simple/,
             /[1,10->A]/,
             [[IO_START, 0], [LITERAL,1], [NOP, null],[LITERAL,10], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,0], null]
            ],
            [/Fixed Width Positional 1/,
             /[ 1 ,10 -> A ]/,
             [[IO_START, 0], [LITERAL,1], [NOP, null],[LITERAL,10], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,0], null]
            ],
            [/Fixed Width Positional 1/,
             " [ 1 , 10  -> A ] ",
             [[IO_START, 0], [LITERAL,1], [NOP, null],[LITERAL,10], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,0], null]
            ],
        ] as Object[][]
    }

}
