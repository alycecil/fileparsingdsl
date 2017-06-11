package com.buttonmash.dsl.io

import com.buttonmash.dsl.io.tokens.IOOperation
import com.buttonmash.dsl.io.tokens.LengthIOOperation
import com.buttonmash.dsl.io.tokens.Operation
import com.buttonmash.dsl.io.tokens.PositionalIOOperation
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static com.buttonmash.dsl.io.LanguageDefinitions.*
import static org.testng.Assert.*

class ParserTest {

    @Test(groups = 'Lexer', dataProvider = 'parserDataprovider')
    public void parserTest(testName, String dsl, expectedScan, expectedLexed) {
        def actual = null
        try {

            actual = new DSLFactory().buildProgram(new StringReader(dsl))

            assertNotNull(actual, 'We did something right?')

            def ops = actual.operations.iterator()

            if (expectedLexed instanceof List && !expectedLexed.isEmpty()) {
                expectedLexed.eachWithIndex { expected , idx ->

                    assertTrue ops.hasNext(), 'A next was expected'
                    Operation underTest = ops.next()

                    assertNotNull underTest, "Under Test #${idx} may not be null"
                    validateOperationType(expected, underTest)
                }
            } else {
                fail('We did no testing poor show')
            }

        } catch (Throwable t) {
            fail("${testName}\n::DSL::${dsl}::DSL::\nActual:${actual}\nExpected:${expectedLexed}", t)
        }
    }

    private void validateOperationType(List expected, Operation underTest) {
        assertNotNull expected, 'We did not provide an expected...'
        def expectedType = expected.get(0)

        assertNotNull expectedType, 'We did not provide an expected type...'
        assertEquals underTest.getClass(), expectedType, 'Operation Type'

        def expectedArgs = (expected as List<List>).subList(1, expected.size())

        if (underTest instanceof IOOperation) {
            def args = underTest.getArgs().iterator()

            if (expectedArgs != null) {

                expectedArgs.eachWithIndex { def splitMe, idx ->
                    def (type, text) = splitMe

                    assertTrue args.hasNext()
                    def underTestArg = args.next()
                    assertEquals underTestArg.type, type, 'Argument Type'
                    assertEquals underTestArg.text, text, 'Argument Text'
                }
            }
        }else {
            fail("${underTest} is not handled expected args [${expectedArgs}]")
        }

    }


    @DataProvider(name = "parserDataprovider")
    public static Object[][] parserDataprovider() {
        def result = [
                [/Fixed Width Positional/,
                 /[1,10->A]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]],
                 [
                         [//operation 1
                                 PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A']
                         ]
                 ]
                ],
                [/Fixed Width Positional 2/,
                 /[ 1 ,10 -> A B C ]/,
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A B C'], [IO_END, null]],
                 [[PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A B C']]]
                ],
                [/Fixed Width Positional 3/,
                 " [ 1 , 10  ->\nA 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A 1 100']]]
                ],
                [/Fixed Width "Legal, no comma whatever" 4/,
                 " [ 1  10  -> A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A 1 100']]]
                ],
                [/Fixed Width "Legal, (poor)" 4/,
                 " [ 1  10  A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A 1 100']]]
                ],

                [/Fixed Width "Legal, (commas are so good)" 4/,
                 " [ 1  , 10  , A 1 100 ] ",
                 [[IO_START, null], [LITERAL, '1'], [NOP, null], [LITERAL, '10'], [IOSeparatorArrow, null], [IDENTITY, 'A 1 100'], [IO_END, null]],
                 [[PositionalIOOperation, [LITERAL, '1'], [LITERAL, '10'], [IDENTITY, 'A 1 100']]]
                ],


                [/Fixed Width Length/,
                 " [1234->Alp123]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alp123'], [IO_END, null]],
                 [[LengthIOOperation, [LITERAL, '1234'], [IDENTITY, 'Alp123']]]
                ],
                [/Fixed Width Length 2/,
                 "[ 1234 -> Alpha 1 Bravo ]",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'Alpha 1 Bravo'], [IO_END, null]],
                 [[LengthIOOperation, [LITERAL, '1234'], [IDENTITY, 'Alpha 1 Bravo']]]
                ],
                [/Fixed Width Length 3/,
                 " [ 1234  ->\nA ] ",
                 [[IO_START, null], [LITERAL, '1234'], [IOSeparatorArrow, null], [IDENTITY, 'A'], [IO_END, null]],
                 [[LengthIOOperation, [LITERAL, '1234'], [IDENTITY, 'A']]]
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
