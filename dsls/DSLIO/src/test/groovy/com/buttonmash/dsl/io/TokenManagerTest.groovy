package com.buttonmash.dsl.io

import com.buttonmash.dsl.crosswalk.generated.DSLIOSymbols
import com.buttonmash.dsl.io.generated.DSLLexer
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static com.buttonmash.dsl.io.LanguageDefinitions.*
import static org.testng.Assert.*

class TokenManagerTest {

    @Test(dataProvider = "tokenManagerDataprovider")
    public void getSymFromString(testName, what, token, expected) {
        TokenManager tokenManager = new TokenManager()

        assertEquals tokenManager.getSymFromString(what as String), expected
    }


    @Test(dataProvider = "tokenManagerDataprovider")
    public void test(testName, what, Token token, expected) {
        TokenManager tokenManager = new TokenManager()

        assertEquals tokenManager.getSymbol(token).sym, expected
    }


    @DataProvider(name = "tokenManagerDataprovider")
    public Object[][] getSymFromStringDataProvider() {
        def result = [
                [/IO Start/,
                 IO_START,
                 new Token(IO_START, "", 0, 1, 2),
                 DSLIOSymbols.IO_START,
                ],

                [/IO END/,
                 IO_END,
                 new Token(IO_END,"",0,1,2),
                 DSLIOSymbols.IO_END,
                ],

                [/Set/,
                 LogicKeywords.SET,
                 new Token(LOGIC_KEYWORD,LogicKeywords.SET.name(),0,1,2),
                 DSLIOSymbols.SET,
                ],

                [/Split/,
                 LogicKeywords.SPLIT,
                 new Token(LOGIC_KEYWORD,LogicKeywords.SPLIT.name(),0,1,2),
                 DSLIOSymbols.SPLIT,
                ],
        ]

        result as Object[][]
    }
}
