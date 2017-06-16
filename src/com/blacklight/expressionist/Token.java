package com.blacklight.expressionist;

public enum Token {

	LEFT("("), RIGHT(")"), EQUAL("="), PERIOD("."), PLUS("+"), 
	MINUS("-"), STAR("*"), SLASH("/"), COMMA(","), IDENT("ident"), 
	NUMBER("number"), EOF("eof"), DOUBLE_QUOTE("\""),
	AND("&&"), OR("||"), NOT_EQUALS("!="), EQUALS("=="),
	LE("<="), GE(">="), LT("<"), GT(">");
	
	final String expressionString;

	Token(String expressionString) {
		this.expressionString = expressionString;
	}

	public String toString() {
		return expressionString;
	}

	public static Token valueOf(char c) {
		for (Token t : values())
			if (t.expressionString.length() == 1 && t.expressionString.charAt(0) == c)
				return t;
		return Token.EOF;
	}

	public static Token getToken(String oper) {
		for (Token t : values())
			if (t.expressionString.equals(oper))
				return t;
		return null;
	}



}
