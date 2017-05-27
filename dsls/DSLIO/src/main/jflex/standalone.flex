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

%debug


%unicode


%{

   private List<Token> stack = new LinkedList<>();

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
CommentHeader = @

IMPERATIVE = #\!
COMMENT = #

Identifier = [:jletter:] [:jletterdigit:]*

 DecIntegerLiteral = 0 | [1-9][0-9]*

%state USEFUL

%%

<YYINITIAL> {
    {IOSeparator}           {yybegin(USEFUL); return token(LanguageDefinitions.IO_START); }
    {LogicSeparator}        {yybegin(USEFUL);  return token(LanguageDefinitions.LOGIC_START); }


    {AnySpace}+             {/*WHITE SPACE YAHI!*/}
}

<USEFUL> {
    {IOSeparatorArrow}      { return token(LanguageDefinitions.IOSeparatorArrow);}

    {IOSeparatorEnd}        { yybegin(YYINITIAL); return token(LanguageDefinitions.IO_END);}
    {LogicSeparatorEnd}     { yybegin(YYINITIAL); return token(LanguageDefinitions.LOGIC_END);}


    "," {return token(LanguageDefinitions.NOP);}

    {Identifier} {return token(LanguageDefinitions.IDENTITY);}

    {DecIntegerLiteral}            { return token(LanguageDefinitions.LITERAL); }

}

[^] { return token(null);}