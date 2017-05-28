/**
*
* GENERATED CODE FROM crosswalk.flex
*
* PLEASE MODIFY THE FLEX FILE AS MAVEN COMPILE WILL WHIPE THEM AWAY AND YOU WILL BE SO SAD
*
*/

package com.buttonmash.dsl.crosswalk.generated;

import com.buttonmash.dsl.crosswalk.*;


%%

%public

%line
%char

%class DSLCrosswalkLexer
%implements IDSLLexer


%function getNext
%type Token

%unicode


%{
  private Token lastToken;
  private StringBuilder sb = new StringBuilder();

  private synchronized <T> Token<T> token(T type) {
      lastToken = new Token<T>(type, yytext(),yyline,yychar,yychar+yylength());
      return lastToken;
  }
%}


LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t] | [\f]

IOSeparatorArrow = =>|->

ColumnHeader = \|\|
ColumnValue = \|

MethodHeader = @@
OptionHeader = @

IMPERATIVE = #\!
COMMENT = #[^(\!|\r|\n|\r\n)][^(\r|\n|\r\n)]+

Word = [:jletter:][:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*


Identifier = {Word}(({WhiteSpace})*({Word}|{DecIntegerLiteral}))*
Literal = {DecIntegerLiteral}|'{Identifier}*'


%xstate USEFUL, INSIDE
%%

<YYINITIAL> {


    {ColumnHeader}                  {yybegin(INSIDE); return token(LanguageDefinitions.COLUMN_HEADER); }
    {ColumnValue}                   {yybegin(INSIDE); return token(LanguageDefinitions.COLUMN); }

    {MethodHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}
    {OptionHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.OPTION);}
    {IMPERATIVE}                    {yybegin(USEFUL); return token(LanguageDefinitions.IMPERATIVE);}

    {COMMENT}                       {return token(LanguageDefinitions.COMMENT);}

    {LineTerminator}                |
    {WhiteSpace}                    {/* NO OP */}
    [^]                             {/*Waste*/}
}

<INSIDE> {
   {LineTerminator}                 {yybegin(YYINITIAL); if(lastToken.getType() == LanguageDefinitions.IDENTITY) return token(LanguageDefinitions.LINE);}

   ">"                              {return token(LanguageDefinitions.INPUT);}
   "<"                              {return token(LanguageDefinitions.OUTPUT);}

    {ColumnHeader}                  {return token(LanguageDefinitions.COLUMN_HEADER); }
    {ColumnValue}                   {return token(LanguageDefinitions.COLUMN); }


    {COMMENT}                       {return token(LanguageDefinitions.COMMENT);}





	"DO"	|
	"WITH"	|
	"NULL"	|
	"LINE"	|
	"WHEN"	|
	"THEN"	|
	"OTHERWISE"	|
	"STARTS"	|
	"ENDS"	|
	"CONTAINS"	|
	"IS"	|
	"NOT"	|
	"CROSSWALK"	|
	"SET"	|
	"TO"	|
	"ON"	|
	"SPLIT"	|
	"CONCAT"	|
	"CONVERTDATE"	|
	"SKIP"	|
	"ERROR"	|
	"ANY"   |
	"BLANK" |
	"AFTER" |
	"BEFORE"|
	"NOP"                          {if(LogicKeywords.whichKeyword(yytext())!=null){return token(LanguageDefinitions.LOGIC_KEYWORD);}}

    {Literal}                          |
    {Word}                             {
         if(lastToken.getType() == LanguageDefinitions.IDENTITY){
            String current = sb.toString();
            sb.append(yytext());
            lastToken.setText(sb.toString());
         }else{
            sb = new StringBuilder(yytext());
            if(yytext() .matches("-?\\d+(\\.\\d+)?")){
                return token(LanguageDefinitions.LITERAL);
            }
            return token(LanguageDefinitions.IDENTITY);
         }
    }

    {WhiteSpace}+                     { if(lastToken.getType() == LanguageDefinitions.IDENTITY){
sb.append(yytext());
    }
/*WHITE SPACE*/}
}


<USEFUL> {
    "="                             |
    {IOSeparatorArrow}              {return token(LanguageDefinitions.NOP);}

    {Literal}                       |
    {Identifier}                    {return token(LanguageDefinitions.LITERAL);}

    {LineTerminator}                {/*EOL*/ yybegin(YYINITIAL);}

    {WhiteSpace}                    {/*whitespace*/}
}



[^] { /**Ooops?*/ return token(null);}