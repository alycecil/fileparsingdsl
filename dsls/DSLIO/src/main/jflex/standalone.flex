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

Identifier = [:jletter:] ({WhiteSpace}|[:jletterdigit:])*[:jletterdigit:]*

 DecIntegerLiteral = 0 | [1-9][0-9]*

Literal = {DecIntegerLiteral}|'{Identifier}*'

%state USEFUL, IO, LOGIC

%%

<YYINITIAL> {
    {IOSeparator}                   {yybegin(IO); return token(LanguageDefinitions.IO_START); }
    {LogicSeparator}                {yybegin(LOGIC);  return token(LanguageDefinitions.LOGIC_START); }

    {MethodHeader}                  {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}

    {OptionHeader}                 {yybegin(USEFUL); return token(LanguageDefinitions.METHOD_HEADER);}

}

<IO> {
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {IOSeparatorEnd}                {yybegin(YYINITIAL); return token(LanguageDefinitions.IO_END);}

    ","                             {return token(LanguageDefinitions.NOP);}



    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}
    {Literal}                       {return token(LanguageDefinitions.LITERAL); }
}

<LOGIC> {
    {LogicSeparatorEnd}             {yybegin(YYINITIAL); return token(LanguageDefinitions.LOGIC_END);}


    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}
    {Literal}                       { return token(LanguageDefinitions.LITERAL); }
}

<USEFUL> {
    "="                             |
    {IOSeparatorArrow}              {return token(LanguageDefinitions.IOSeparatorArrow);}

    {Identifier}                    {return token(LanguageDefinitions.IDENTITY);}
    {Literal}                       { return token(LanguageDefinitions.LITERAL); }

    {LineTerminator}                {/*EOL*/ yybegin(YYINITIAL);}
}

{AnySpace}+             {/*WHITE SPACE YAHI!*/}

[^] { return token(null);}