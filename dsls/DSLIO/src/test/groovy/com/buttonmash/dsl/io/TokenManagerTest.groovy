package com.buttonmash.dsl.io

import com.buttonmash.dsl.crosswalk.generated.DSLIOSymbols
import com.buttonmash.dsl.io.generated.DSLLexer
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static com.buttonmash.dsl.io.LanguageDefinitions.*
import static org.testng.Assert.*

class TokenManagerTest {

    @Test(dataProvider = "getSymFromString")
    public void getSymFromString(testName, what, expected) {
        TokenManager tokenManager = new TokenManager()

        assertEquals tokenManager.getSymFromString(what as String), expected
    }


    @DataProvider(name = "getSymFromString")
    public Object[][] getSymFromStringDataProvider() {
        def result = [
                [/IO Start/,
                 IO_START,
                 DSLIOSymbols.IO_START,
                ],

                [/IO END/,
                 IO_END,
                 DSLIOSymbols.IO_END,
                ],

                [/Set/,
                 LogicKeywords.SET,
                 DSLIOSymbols.SET,
                ],
        ]

        result as Object[][]
    }



    @Test(dataProvider = "tokenManagerIDXDataprovider")
    public void test(testName, Token what, expected) {
        TokenManager tokenManager = new TokenManager()

        assertEquals tokenManager.getSymbol(what).sym, expected
    }


    @DataProvider(name = "tokenManagerIDXDataprovider")
    public Object[][] parserDataprovider() {
        def result = [
                [/IO Start/,
                 new Token(IO_START, "", 0, 1, 2),
                 DSLIOSymbols.IO_START,
                ],
                [/IO END/,
                 new Token(IO_END,"",0,1,2),
                 DSLIOSymbols.IO_END,
                ],
                [/Set/,
                 new Token(LOGIC_KEYWORD,LogicKeywords.SET.name(),0,1,2),
                 DSLIOSymbols.SET,
                ],
                [/Set/,
                 new Token(LOGIC_KEYWORD,LogicKeywords.SPLIT.name(),0,1,2),
                 DSLIOSymbols.SPLIT,
                ],
        ]

        result as Object[][]
    }

}
