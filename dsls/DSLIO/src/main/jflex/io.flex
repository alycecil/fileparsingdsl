/**
*
* GENERATED CODE FROM io.flex
*
* PLEASE MODIFY THE FLEX FILE AS MAVEN COMPILE WILL WHIPE THEM AWAY AND YOU WILL BE SO SAD
*
*/

package com.buttonmash.dsl.io.generated;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

import com.buttonmash.dsl.io.*;


%%

%public

%line
%char

%class DSLLexer
%implements IDSLLexer, Scanner


%function getNext
%type Token

%unicode

%{
  private Token lastToken;
  private StringBuilder sb = new StringBuilder();
  TokenManager tokenManager = new TokenManager();

  public DSLLexer(java.io.Reader in, ComplexSymbolFactory sf){
  	this(in);
  }

  private synchronized Token<LanguageDefinitions> token(LanguageDefinitions type) {
      Token t = new Token<LanguageDefinitions>(type, yytext(),yyline,yychar,yychar+yylength());
      lastToken = t;
      return t;
  }

  @Override
  public Symbol next_token() throws Exception{
      return tokenManager.getSymbol(getNext());
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
COMMENT = #[^(\!|\r|\n|\r\n)][^(\r|\n|\r\n)]+

Word = [:jletter:][:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*


Identifier = {Word}(({WhiteSpace})*({Word}|{DecIntegerLiteral}))*
Literal = {DecIntegerLiteral}|'{Identifier}*'

%state USEFUL, IO, LOGIC, TYPE

%%

<YYINITIAL> {
    {IOSeparator}                   {yybegin(IO); return token(LanguageDefinitions.IO_START); }
    {LogicSeparator}                {yybegin(LOGIC); return token(LanguageDefinitions.LOGIC_START); }


    {MethodHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}
    {OptionHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.OPTION);}
    {IMPERATIVE}                    {yybegin(USEFUL); return token(LanguageDefinitions.IMPERATIVE);}
    {COMMENT}                       {return token(LanguageDefinitions.COMMENT);}

    [^]                             {/* wtf is this / WHITE SPACE YAHI!*/}
}

<IO> {
    {LogicSeparator}                {yybegin(TYPE);  return token(LanguageDefinitions.LOGIC_START); }
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {IOSeparatorEnd}                {yybegin(YYINITIAL); return token(LanguageDefinitions.IO_END);}

    ","                             {return token(LanguageDefinitions.NOP);}

    {Literal}                       {return token(LanguageDefinitions.LITERAL); }
    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}

    {AnySpace}+                     {/*WHITE SPACE*/}
}

<TYPE> {
    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}
    {Literal}                       {return token(LanguageDefinitions.LITERAL); }

    {LogicSeparatorEnd}             {yybegin(IO); return token(LanguageDefinitions.LOGIC_END);}
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
	"NOP"                          {if(LogicKeywords.whichKeyword(yytext())!=null){return token(LanguageDefinitions.LOGIC_KEYWORD);}}


    {Word}                             {
 if(lastToken.getType() == LanguageDefinitions.IDENTITY){
    String current = sb.toString();
    sb.append(yytext());
    lastToken.setText(sb.toString());
 }else{
    sb = new StringBuilder(yytext());
    return token(LanguageDefinitions.IDENTITY);
}
}

    {AnySpace}+                     { if(lastToken.getType() == LanguageDefinitions.IDENTITY){
sb.append(yytext());
    }
/*WHITE SPACE*/}


}

<USEFUL> {
    "="                             |
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {Literal}                       {return token(LanguageDefinitions.LITERAL); }
    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}

    {LineTerminator}                {/*EOL*/ yybegin(YYINITIAL);}

    {WhiteSpace}                    {/*whitespace*/}
}

[^] { /**Ooops?*/ return token(null);}