package com.buttonmash.dsl.crosswalk;

public enum LogicKeywords {
    //NO-OPs
    NOP, DO, WITH,

    NULL,

    /**
     * Reference to the current node.
     */
    LINE,


    WHEN, THEN, OTHERWISE,

    STARTS, ENDS, CONTAINS, IS, NOT,

    /**
     * Defines a series of WHEN X IS B and THEN SET Y TO A rules
     */
    CROSSWALK,


    SET, TO, ON,

    SPLIT, CONCAT,

    CONVERTDATE,

    ANY,
    BLANK,
    AFTER, BEFORE,

    /**
     * CONTROL FLOW Stop, return nothing.
     *
     * SKIP
     */
    SKIP,


    /**
     * CONTROL FLOW Error at Record level, return an Error Record.
     *
     * ERROR 'OOPS MESSAGE'
     */
    ERROR,


    UNKNOWN;

    public static LogicKeywords whichKeyword(String text) {
        try{
            return valueOf(text);
        }catch(Throwable t){
            return null;
        }
    }

}
