package groovy.com.buttonmash.dsl.crosswalk

import com.buttonmash.dsl.crosswalk.Token
import com.buttonmash.dsl.crosswalk.generated.DSLCrosswalkLexer
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static com.buttonmash.dsl.crosswalk.LanguageDefinitions.*
import static org.testng.Assert.assertEquals
import static org.testng.Assert.fail

public class GeneratedLexerTest {

    @Test(dataProvider = "parserDataprovider")
    public void test(testName, dsl, expected) {

        def dslStringReader = new StringReader(dsl);

        def lexer = new DSLCrosswalkLexer(dslStringReader)

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
                tokens.eachWithIndex { Token it, idx ->
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
        } catch (Throwable t) {
            fail("${testName}\n::DSL::${dsl}::DSL::\nActual:${tokens}\nExpected:${expected}", t)
        }
    }

    @DataProvider(name = "parserDataprovider")
    public Object[][] parserDataprovider() {
        def result = [


                [/Noise Column Header/,
                 "||MethodName\n",
                 [[COLUMN_HEADER, null], [IDENTITY, 'MethodName'], [LINE, null]]
                ],
                [/Input Header/,
                 "||>Input\n",
                 [[COLUMN_HEADER, null], [INPUT, INPUT.keyWord], [IDENTITY, 'Input'], [LINE, null]]
                ],
                [/Output Header/,
                 "||<OutPut\n",
                 [[COLUMN_HEADER, null], [OUTPUT, OUTPUT.keyWord], [IDENTITY, 'OutPut'], [LINE, null]]
                ],

                [/Header Heavy/,
                 "||a B || <Alpha 1 Bravo ||>A 1 2\n\n",
                 [[COLUMN_HEADER, null], [IDENTITY, 'a B'],
                  [COLUMN_HEADER, null], [OUTPUT, OUTPUT.keyWord], [IDENTITY, 'Alpha 1 Bravo'],
                  [COLUMN_HEADER, null], [INPUT, INPUT.keyWord], [IDENTITY, 'A 1 2'], [LINE, null]]
                ],

                [/Line Single/,
                 "|Hia\n",
                 [[COLUMN, null], [IDENTITY, 'Hia'], [LINE, null]]
                ],
                [/Line Double/,
                 "|X|Y\n\n",
                 [[COLUMN, null], [IDENTITY, 'X'],
                  [COLUMN, null], [IDENTITY, 'Y'], [LINE, null]]
                ],

                [/Line Heavy/,
                 "|a B | Alpha 1 Bravo |A 1 2\n",
                 [[COLUMN, null], [IDENTITY, 'a B'],
                  [COLUMN, null], [IDENTITY, 'Alpha 1 Bravo'],
                  [COLUMN, null], [IDENTITY, 'A 1 2'], [LINE, null]]
                ],




                [/Method Header/,
                 "@@MethodName",
                 [[METHOD_HEADER, null], [LITERAL, 'MethodName']]
                ],
                [/Method Header 2/,
                 "@@Method Name\n",
                 [[METHOD_HEADER, null], [LITERAL, 'Method Name']]
                ],
                [/Method Header 3/,
                 "@@\tMethod Name \n",
                 [[METHOD_HEADER, null], [LITERAL, 'Method Name']]
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
                 [[IMPERATIVE, null], [LITERAL, 'Fluff'], [NOP, '='], [LITERAL, 'Save Me']]
                ],

                [/Imperative/,
                 "#! Fluff = Save Me \n",
                 [[IMPERATIVE, null], [LITERAL, 'Fluff'], [NOP, '='], [LITERAL, 'Save Me']]
                ],

                [/Option/,
                 "@Fluff=Save Me\n",
                 [[OPTION, null], [LITERAL, 'Fluff'], [NOP, '='], [LITERAL, 'Save Me']]
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
