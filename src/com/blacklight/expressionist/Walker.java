package com.blacklight.expressionist;

import com.blacklight.expressionist.exp.NumberValueExpression;

public class Walker {
	final String source; // String being parsed
	int prev; // previous char position
	int next; // next char position
	Token tok; // current token
	float nval; // numeric value for number
	String sval; // String value for ident
	boolean literalNotification = false; //Notification for text appearing in expression

	/** Constructs an instance, using s as the input */
	public Walker(String s) {
		source = s;
		prev = 0;
		next = 0;
	}

	void getNumber() {
		while (next < source.length()) {
			char c = source.charAt(next);
			if (c == '.' || Character.isDigit(c))
				next++;
			else
				break;
		}
		tok = Token.NUMBER;
		String s = source.substring(prev, next);
		nval = Float.parseFloat(s);
	}

	void getIdent() {
		while (next < source.length()) {
			char c = source.charAt(next);
			if (Character.isLetterOrDigit(c))
				next++;
			else
				break;
		}
		tok = Token.IDENT;
		sval = source.substring(prev, next);
	}

	void getText() {
		while (next < source.length()) {
			char c = source.charAt(next);
			if (Token.valueOf(c) != Token.DOUBLE_QUOTE)
				next++;
			else
				break;
		}
		tok = Token.IDENT;
		sval = source.substring(prev, next);
	}

	/** Returns next token after reading a sufficient number of chars */
	public Token nextToken() {
		nval = 0;
		sval = "";
		char c;
		do {
			if (next >= source.length())
				return (tok = Token.EOF);
			c = source.charAt(next++); // read next char
		} while (Character.isWhitespace(c));
		prev = next - 1;
		
		if(isLiteralNotification()) {
			getText();
		} else if (Character.isLetter(c))
			getIdent();
		else if (Character.isDigit(c))
			getNumber();
		else {
			if (next < source.length()) {
				String oper = c+""+ source.charAt(next);
				if( Token.getToken(oper) != null) {
					tok = Token.getToken(oper);
					prev = next;
					next++;
				} else
					tok = Token.getToken(""+c);
			} else tok = Token.getToken(""+c);
		} // tok = c;
		return tok;
	}

	/** String representation of the current token */
	public String toString() {
		String s = tok.toString();
		if (tok == Token.NUMBER)
			return NumberValueExpression.numToStr(nval);
		if (tok == Token.IDENT)
			return sval;
		return s;
	}

	/** Prints all (remaining) tokens to System.out */
	public void listTokens() {
		while (tok != Token.EOF) {
			nextToken();
			System.out.println(toString());
		}
	}
	
	public boolean isLiteralNotification() {
		return literalNotification;
	}

	public void setLiteralNofication(boolean literalNotification) {
		this.literalNotification = literalNotification;
	}

	public String val() {
		return sval != null ? sval : nval+"";
	}
}