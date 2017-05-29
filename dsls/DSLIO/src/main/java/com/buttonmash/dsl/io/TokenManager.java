package com.buttonmash.dsl.io;

import com.buttonmash.dsl.crosswalk.generated.DSLIOSymbols;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.util.HashMap;

public class TokenManager {

    private final HashMap<String, Integer> nameToIndex;
    private final ComplexSymbolFactory symbolFactory;

    public TokenManager() {
        symbolFactory=new ComplexSymbolFactory();

        nameToIndex = new HashMap<>();

        int index = 0;
        for (String it : DSLIOSymbols.terminalNames) {
            nameToIndex.put(it, index);

            index++;
        }
    }

    public Symbol getSymbol(Token<LanguageDefinitions> token) throws Exception {

        if (token == null) {
            return null;
        }
            Integer idx = null;
        String symbolText = null;
        if (LanguageDefinitions.LOGIC_KEYWORD.equals(token.getType())) {
            symbolText = token.getText();
            idx = getSymFromString(symbolText);
        }

        if (idx == null) {
            symbolText = token.getType().name();
            idx = getSymFromString(symbolText);
        }

        if (idx == null) {
            idx = -1;/*Some unknown magic slipped in*/
        }

        Symbol symbol = symbolFactory.newSymbol(token.toString(), idx, token);
        return symbol;
    }



    public int getSymFromString(String param) {
        Integer idx = nameToIndex.get(param);

        if (idx == null)
            return -1;
        return idx;
    }
}
