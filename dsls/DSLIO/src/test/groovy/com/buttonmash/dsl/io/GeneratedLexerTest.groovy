package com.buttonmash.dsl.io


import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import com.buttonmash.dsl.io.generated.*

import static com.buttonmash.dsl.io.LanguageDefinitions.*
import static org.testng.Assert.*;

public class GeneratedLexerTest {

    @Test(dataProvider = "parserDataprovider")
    public void test(testName, dsl, expected) {

        def dslStringReader = new StringReader(dsl);

        def lexer = new DSLLexer(dslStringReader)

        def tokens = []

        def next = Token.StartToken;
        while (next != null) {
            next = lexer.next
            if (next != null)
                tokens << next
        }

        try {
            if (expected instanceof List) {
                expected = expected as List

                def iter = expected.iterator()
                tokens.eachWithIndex { Token it , idx->
                    def nxt = iter.next()
                    if (nxt) {
                        def (type, value) = nxt

                        assertEquals(it.type, type, "#${idx} Type")

                        if (value != null) {
                            assertEquals(it.text, value, "#${idx} Value")
                        }
                    }
                }
            }
            assertEquals(tokens.size(), expected.size(), 'should have the same size')
        }catch(Throwable t){
            fail("${testName}\n::DSL::${dsl}::DSL::\nActual:${tokens}\nExpected:${expected}", t)
        }
    }

    @DataProvider(name = "parserDataprovider")
    public Object[][] parserDataprovider() {
        def result = [
                [/Fixed Width Positional/,
                 /[1,10->A]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]]
                ],
                [/Fixed Width Positional 2/,
                 /[ 1 ,10 -> A B C ]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A B C'], [IO_END, null]]
                ],
                [/Fixed Width Positional 3/,
                 " [ 1 , 10  ->\nA 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]]
                ],
                [/Fixed Width Positional 4/,
                 " [ 1 , 10  ->\nA 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]]
                ],

                [/Fixed Width Length/,
                 " [1234->Alp123]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alp123'], [IO_END, null]]
                ],
                [/Fixed Width Length 2/,
                 "[ 1234 -> Alpha 1 Bravo ]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alpha 1 Bravo'], [IO_END, null]]
                ],
                [/Fixed Width Length 3/,
                 " [ 1234  ->\nA ] ",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]]
                ],


                [/Logic Simple/,
                 " (SET A TO 'B') ",
                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'A'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "'B'"], [LOGIC_END, null]]
                ],
                [/Logic Simple 2/,
                 " (  SET  A B C  TO 123 )",
                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'A B C'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "123"], [LOGIC_END, null]]
                ],
                [/Logic Simple 3/,
                 " (SET\t Alpha\nTO 'B C D'\t) ",
                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'Alpha'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "'B C D'"], [LOGIC_END, null]]
                ],

                [/Method Header/,
                 "@@MethodName",
                 [[METHOD_HEADER, null], [IDENTITY, 'MethodName']]
                ],
                [/Method Header 2/,
                 "@@Method Name\n",
                 [[METHOD_HEADER, null], [IDENTITY, 'Method Name']]
                ],
                [/Method Header 3/,
                 "@@\tMethod Name \n",
                 [[METHOD_HEADER, null], [IDENTITY, 'Method Name']]
                ],

                [/Method Header Alone/,
                 "@@\nFluff, Ignore Me\n",
                 [[METHOD_HEADER, null]]
                ],

                [/Retained Comment/,
                 "#Fluff,= Save Me\n",
                 [[COMMENT, '#Fluff,= Save Me']]
                ],

                [/Imperative/,
                 "#!Fluff=Save Me\n",
                 [[IMPERATIVE, null],[IDENTITY,'Fluff'],[IOSeparatorArrow, '='],[IDENTITY,'Save Me']]
                ],

                [/Imperative/,
                 "#! Fluff = Save Me \n",
                 [[IMPERATIVE, null],[IDENTITY,'Fluff'],[IOSeparatorArrow, '='],[IDENTITY,'Save Me']]
                ],

                [/Option/,
                 "@Fluff=Save Me\n",
                 [[OPTION, null],[IDENTITY,'Fluff'],[IOSeparatorArrow, '='],[IDENTITY,'Save Me']]
                ],
        ]


        //x1
        addCrossTests(result)

        result as Object[][]
    }

    private void addCrossTests(List<List<Object>> result) {
    //Cross Tests
        def next = []
        result.each { a ->
            result.each { b ->
                def list = []
                list.addAll(a[2])
                list.addAll(b[2])

                next << ["${a[0]} x ${b[0]}", "${a[1]}\n${b[1]}", list]

            }
        }

        //huck em in
        result.addAll(next)
    }

}
