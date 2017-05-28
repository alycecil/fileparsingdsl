package com.buttonmash.dsl.crosswalk;

public enum LanguageDefinitions {
    IMPERATIVE("#!"),
	COMMENT("#"),

	METHOD_HEADER("@@"),
	OPTION("@"),

	COLUMN_HEADER("||"),
	COLUMN("|"),

	LINE,

	INPUT(">"),
	OUTPUT("<"),

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