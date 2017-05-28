/**
*
* GENERATED CODE FROM standalone.flex
*
* PLEASE MODIFY THE FLEX FILE AS MAVEN COMPILE WILL WHIPE THEM AWAY AND YOU WILL BE SO SAD
*
*/

package com.buttonmash.dsl.io.generated;

import com.buttonmash.dsl.io.*;


%%

%public

%line
%char

%class DSLLexer
%implements IDSLLexer


%function getNext
%type Token

%unicode

%debug

%{

  private <T> Token token(T type) {
      return new Token(type, yytext(),yyline,yychar,yychar+yylength());
  }
%}


LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t]
AnySpace = {LineTerminator} | {WhiteSpace} | [\f]

LogicSeparator = \(
LogicSeparatorEnd = \)


IOSeparator = \[
IOSeparatorArrow = =>|->
IOSeparatorEnd = \]

MethodHeader = @@
OptionHeader = @

IMPERATIVE = #\!
COMMENT = #

Word = [:jletter:][:jletterdigit:]*

 DecIntegerLiteral = 0 | [1-9][0-9]*


Identifier = {Word}({WhiteSpace}|{Word}|{DecIntegerLiteral})*
Literal = {DecIntegerLiteral}|'{Identifier}*'

%state USEFUL, IO, LOGIC

%%

<YYINITIAL> {
    {IOSeparator}                   {yybegin(IO); return token(LanguageDefinitions.IO_START); }
    {LogicSeparator}                {yybegin(LOGIC);  return token(LanguageDefinitions.LOGIC_START); }

    {MethodHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}

    {OptionHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}

}

<IO> {
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {IOSeparatorEnd}                {yybegin(YYINITIAL); return token(LanguageDefinitions.IO_END);}

    ","                             {return token(LanguageDefinitions.NOP);}



    {Literal}                       {return token(LanguageDefinitions.LITERAL); }
    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}
}

<LOGIC> {
    {LogicSeparatorEnd}             {yybegin(YYINITIAL); return token(LanguageDefinitions.LOGIC_END);}

    {Literal}                       {return token(LanguageDefinitions.LITERAL); }

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
	"NOP"                          {
        if(LogicKeywords.whichKeyword(yytext())!=null){
            return token(LanguageDefinitions.LOGIC_KEYWORD);
        }
    }


    {AnySpace}+             {/*WHITE SPACE YAHI!*/}

    [^] {/**other*/return token(LanguageDefinitions.IDENTITY);}
}

<USEFUL> {
    "="                             |
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {Literal}                       {return token(LanguageDefinitions.LITERAL); }
    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}

    {LineTerminator}                {/*EOL*/ yybegin(YYINITIAL);}
}

{AnySpace}+             {/*WHITE SPACE YAHI!*/}

[^] { return token(null);}