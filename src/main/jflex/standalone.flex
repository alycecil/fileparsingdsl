/**
*
* GENERATED CODE FROM standalone.flex
*
* PLEASE MODIFY THE FLEX FILE AS MAVEN COMPILE WILL WHIPE THEM AWAY AND YOU WILL BE SO SAD
*
*/

package com.github.dsl.io.lex.generated;

import com.github.dsl.io.lex.*;


%%

%public

%line
%char

%class DSLLexer
%implements IDSLLexer


%function getNext
%type Token

%xstate IO_SEGMENT, LOGIC_SEGMENT

%state IO_SEGMENT_AFTER_ARROW, VALUE


%debug


%unicode


%{
  private <T> Token token(T type) {
      return new Token(type, yytext(),yyline,yychar,yychar+yylength());
  }
%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t]
AnySpace = {LineTerminator} | {WhiteSpace} | [\f]

EscapedCharacter = \\{InputCharacter}

LineComment = {WhiteSpace}* "#" .*

Identifier = [:jletterdigit:]+
IdentifierReference = \{ {Identifier} \}
Literal = \'{Identifier}\'

LogicSeparator = \(
LogicSeparatorEnd = \)


IOSeparator = \[
IOSeparatorArrow = =>
IOSeparatorEnd = \]

%%



<YYINITIAL> {
    {IOSeparator}           {
            yybegin(IO_SEGMENT);

            return token(LanguageDefinitions.IO_START); }

    {LogicSeparator}        { yybegin(LOGIC_SEGMENT);
            return token(LanguageDefinitions.LOGIC_START); }

    {AnySpace}+             |
    .+                      { /* Ignore the bulk text as thats not code */}
}



<IO_SEGMENT> {
    <IO_SEGMENT_AFTER_ARROW> {
        {IOSeparatorEnd}        { yybegin(YYINITIAL);
                return token(LanguageDefinitions.IO_END);}
    }

    {Literal}               { return token(LanguageDefinitions.LITERAL); }

    {IdentifierReference}   |
    {Identifier}            { return token(LanguageDefinitions.IDENTITY); }

    {IOSeparatorArrow}      { yybegin(IO_SEGMENT_AFTER_ARROW);
            return token(LanguageDefinitions.NOP);}

    .+                      { /* Unmatched Text in code block? */
        return token(LanguageDefinitions.NOP);}


}




<LOGIC_SEGMENT> {
    {LogicSeparatorEnd}     { yybegin(YYINITIAL);
            return token(LanguageDefinitions.LOGIC_END);}

    .+                      { /* Unmatched Text in code block? */
            return token(LanguageDefinitions.NOP);}
}





