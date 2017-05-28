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
            if(next!=null)
                tokens << next
        }

        println "Tokens "


        tokens.each {
            Token t ->
                println t
        }


        if(expected instanceof List){
            expected  = expected as List

            def iter = expected.iterator()
            tokens.each {
                def nxt = iter.next()
                if(nxt) {
                    def (type, value) = nxt

                    assertEquals(it.type, type)

                    if(value!=null){

                    }
                }
            }
        }
        assertEquals( tokens.size(), expected.size() )
    }

    @DataProvider(name = "parserDataprovider")
    public Object[][] parserDataprovider(){
        [
            [/Fixed Width Positional Simple/,
             /[1,10->A]/,
             [[IO_START, null], [LITERAL,'1'], [NOP, null],[LITERAL,'10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,null]]
            ],
            [/Fixed Width Positional Simnple 2/,
             /[ 1 ,10 -> A ]/,
             [[IO_START, null], [LITERAL,'1'], [NOP, null],[LITERAL,'10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,null]]
            ],
            [/Fixed Width Positional Simple 3/,
             " [ 1 , 10  ->\nA ] ",
             [[IO_START, null], [LITERAL,'1'], [NOP, null],[LITERAL,'10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END,null]]
            ],


            [/Fixed Width Positional Simple 3/,
             " (SET A TO 'B') ",
             [[LOGIC_START, null], [LOGIC_KEYWORD,'SET'], [IDENTITY, 'A'], [LOGIC_KEYWORD,'TO'], [LITERAL, "'B'"], [LOGIC_END,null]]
            ],
        ] as Object[][]
    }

}
