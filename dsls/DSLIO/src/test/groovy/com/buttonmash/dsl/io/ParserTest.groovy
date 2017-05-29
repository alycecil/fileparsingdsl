package com.buttonmash.dsl.io

import com.buttonmash.dsl.crosswalk.generated.DSLIOParser
import com.buttonmash.dsl.io.generated.DSLLexer
import com.buttonmash.dsl.io.tokens.IOOperation
import com.buttonmash.dsl.io.tokens.Operation
import java_cup.runtime.ComplexSymbolFactory
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import javax.xml.stream.XMLStreamWriter;

import java_cup.runtime.XMLElement;
import java_cup.runtime.ScannerBuffer

import javax.xml.stream.XMLOutputFactory;

import static com.buttonmash.dsl.io.LanguageDefinitions.*
import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertNotNull
import static org.testng.Assert.fail

class ParserTest {

    @Test(groups = 'Lexer',  dataProvider = 'parserDataprovider')
    public void parserTest(testName, String dsl, expectedScan, expectedLexed) {
        def actual = null
        try {

            def factory = new ComplexSymbolFactory()
            ScannerBuffer lexer = new ScannerBuffer(new DSLLexer(new StringReader(dsl), factory))
            DSLIOParser mine = new DSLIOParser(lexer)

            actual = (Program)mine.parse().value

            assertNotNull(actual, 'We did something right?')

            def ops = actual.operations.iterator()

            if(expectedLexed instanceof List){
                expectedLexed.each {

                    Operation underTest = ops.next()

                    validateOperationType(it, underTest)

                }
            }else{
                fail('We did no testing poor show')
            }

        }catch (Throwable t) {
            fail("${testName}\n::DSL::${dsl}::DSL::\nActual:${actual}\nExpected:${expectedLexed}", t)
        }
    }

    private void validateOperationType(it, Operation underTest) {
        Class expectedTokenType;
        if (it instanceof List) {
            expectedTokenType = it.first()
        } else {
            expectedTokenType = it
        }

        if (expectedTokenType != null) {
            assertEquals underTest.getClass(), expectedTokenType, 'Operation Type'

        } else {
            fail('We did not provide an expected...')
        }
    }


    @DataProvider(name = "parserDataprovider")
    public static Object[][] parserDataprovider() {
        def result = [
                [/Fixed Width Positional/,
                 /[1,10->A]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A'], ] ]
                ],
                [/Fixed Width Positional 2/,
                 /[ 1 ,10 -> A B C ]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A B C'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A B C'], ] ]
                ],
                [/Fixed Width Positional 3/,
                 " [ 1 , 10  ->\nA 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A 1 100'], ] ]
                ],
                [/Fixed Width "Legal, no comma whatever" 4/,
                 " [ 1  10  -> A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A'], ] ]
                ],
                [/Fixed Width "Legal, (poor)" 4/,
                 " [ 1  10  A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A'], ] ]
                ],

                [/Fixed Width "Legal, (commas are so good)" 4/,
                 " [ 1  , 10  , A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1'], [LITERAL, '10'],[IDENTITY, 'A'], ] ]
                ],


                [/Fixed Width Length/,
                 " [1234->Alp123]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alp123'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1234'], [IDENTITY, 'Alp123'], ] ]
                ],
                [/Fixed Width Length 2/,
                 "[ 1234 -> Alpha 1 Bravo ]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alpha 1 Bravo'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1234'], [IDENTITY, 'Alpha 1 Bravo'], ] ]
                ],
                [/Fixed Width Length 3/,
                 " [ 1234  ->\nA ] ",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]],
                 [[IOOperation, [LITERAL, '1234'], [IDENTITY, 'A'], ] ]
                ],
//
//
//                [/Logic Simple/,
//                 " (SET A TO 'B') ",
//                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'A'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "'B'"], [LOGIC_END, null]]
//                ],
//                [/Logic Simple 2/,
//                 " (  SET  A B C  TO 123 )",
//                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'A B C'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "123"], [LOGIC_END, null]]
//                ],
//                [/Logic Simple 3/,
//                 " (SET\t Alpha\nTO 'B C D'\t) ",
//                 [[LOGIC_START, null], [LOGIC_KEYWORD, 'SET'], [IDENTITY, 'Alpha'], [LOGIC_KEYWORD, 'TO'], [LITERAL, "'B C D'"], [LOGIC_END, null]]
//                ],
//
//                [/Method Header/,
//                 "@@MethodName",
//                 [[METHOD_HEADER, null], [IDENTITY, 'MethodName']]
//                ],
//                [/Method Header 2/,
//                 "@@Method Name\n",
//                 [[METHOD_HEADER, null], [IDENTITY, 'Method Name']]
//                ],
//                [/Method Header 3/,
//                 "@@\tMethod Name \n",
//                 [[METHOD_HEADER, null], [IDENTITY, 'Method Name']]
//                ],
//
//                [/Method Header Alone/,
//                 "@@\nFluff, Ignore Me\n",
//                 [[METHOD_HEADER, null]]
//                ],
//
//                [/Retained Comment/,
//                 "#Fluff,= Save Me\n",
//                 [[COMMENT, '#Fluff,= Save Me']]
//                ],
//
//                [/Imperative/,
//                 "#!Fluff=Save Me\n",
//                 [[IMPERATIVE, null], [IDENTITY, 'Fluff'], [IOSeparatorArrow, '='], [IDENTITY, 'Save Me']]
//                ],
//
//                [/Imperative/,
//                 "#! Fluff = Save Me \n",
//                 [[IMPERATIVE, null], [IDENTITY, 'Fluff'], [IOSeparatorArrow, '='], [IDENTITY, 'Save Me']]
//                ],
//
//                [/Option/,
//                 "@Fluff=Save Me\n",
//                 [[OPTION, null], [IDENTITY, 'Fluff'], [IOSeparatorArrow, '='], [IDENTITY, 'Save Me']]
//                ],
        ]
//x1
        //addCrossTests(result)

        result as Object[][]
    }
}
