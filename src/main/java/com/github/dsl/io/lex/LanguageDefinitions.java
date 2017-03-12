package com.github.dsl.io.lex;

public enum LanguageDefinitions {
    IMPERATIVE("#!"),
	COMMENT("#"),

	METHOD_HEADER("@@"),
	OPTION("@"),

	LOGIC_START("("),
	LOGIC_END(")"),

	IO_START("["),
	IO_END("]"),

	IDENTITY,
	LITERAL,

	LOGIC_KEYWORD,

	NOP;


	LanguageDefinitions(){
		this.keyWord = this.name();
	}

	LanguageDefinitions(String keyWord){
		this.keyWord = keyWord;
	}


	final String keyWord;

	public String getKeyWord() {
		return keyWord;
	}
}