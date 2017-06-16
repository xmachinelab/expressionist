package com.blacklight.expressionist;

import com.blacklight.expressionist.exp.BooleanValueExpression;
import com.blacklight.expressionist.exp.ComplexExpression;
import com.blacklight.expressionist.exp.ConditionExpression;
import com.blacklight.expressionist.exp.Expression;
import com.blacklight.expressionist.exp.FunctionExpression;
import com.blacklight.expressionist.exp.NumberValueExpression;
import com.blacklight.expressionist.exp.StringValueExpression;
import com.blacklight.expressionist.exp.VariableExpression;

public class Compiler {

	Walker lex;
	Token tok;

	public Compiler(String s) {
		lex = new Walker(s);
	}

	void match(Token k) {
		if (tok == k)
			tok = lex.nextToken();
		else
			expected(k.name(), tok, lex.val());
	}

	void expected(String s, Token t, String f) {
		error("\nExpected: " + s + " Found: " + t.name() + ": " + f);
	}

	void error(String msg) {
		throw new RuntimeException(msg);
	}

	/**
	 * Returns an Expression after parsing the input. May fail in case of a
	 * syntax error. Should be called once per instance.
	 */
	public Expression parse() {
		tok = lex.nextToken();
		Expression e = cond();
		match(Token.EOF);
		return e;
	}

	Expression cond() {
		Expression e = rel();
		Token t = tok;
		while (t == Token.AND || t == Token.OR) {
			match(t);
			e = new ConditionExpression(e, t, rel());
			t = tok;
		}
		return e;
	}

	Expression rel() {
		Expression e = expr();
		Token t = tok;
		while (t == Token.NOT_EQUALS || t == Token.EQUALS || t== Token.LE || t== Token.GE || t== Token.LT || t==Token.GT) {
			match(t);
			e = new ConditionExpression(e, t, expr());
			t = tok;
		}
		return e;
	}

	
	Expression expr() {
		Expression e = term();
		Token t = tok;
		while (t == Token.PLUS || t == Token.MINUS) {
			match(t);
			e = new ComplexExpression(e, t, term());
			t = tok;
		}
		return e;
	}

	Expression term() {
		Expression e = factor();
		Token t = tok;
		while (t == Token.STAR || t == Token.SLASH) {
			match(t);
			e = new ComplexExpression(e, t, factor());
			t = tok;
		}
		return e;
	}

	Expression factor() {
		if (tok == Token.MINUS) {
			match(Token.MINUS);
			Expression c = new NumberValueExpression(-1* lex.nval);
			match(Token.NUMBER);
			return c;
		}
		if (tok == Token.NUMBER) {
			Expression c = new NumberValueExpression(lex.nval);
			match(Token.NUMBER);
			return c;
		} 
		if (tok == Token.FALSE || tok == Token.TRUE) {
			Expression c = new BooleanValueExpression(Boolean.valueOf(lex.sval));
			match(tok);
			return c;
		} 
		if (tok == Token.LEFT) {
			match(Token.LEFT);
			Expression e = cond();
			match(Token.RIGHT);
			return e;
		} 
		if (tok == Token.IDENT) {
			Token t = tok;
			String f = lex.sval;
			String eStr = findExpression(f);
			match(Token.IDENT);
			if (tok == Token.LEFT) {
				if (eStr == null) {
					expected("Function NOT found\n Hint: if you use same name as a function, cannot handle this ", t, f);
					//expected("Function/Variable name should be different from the same function\n OR no function/variable found\n");
					return null;
				}

				match(Token.LEFT);
				match(Token.RIGHT);
				return new FunctionExpression(f, eStr);
			} else {
				if (eStr == null) {
					expected("Variable NOT found", t, f);
					//expected("Function/Variable name should be different from the same function\n OR no function/variable found\n");
					return null;
				}
				return new VariableExpression(f, eStr);
			}
		} 
		if (tok == Token.DOUBLE_QUOTE) {
			match(Token.DOUBLE_QUOTE);
			lex.setLiteralNofication(true);
			Expression s1 = new StringValueExpression(lex.sval);
			lex.setLiteralNofication(false);
			match(Token.IDENT);
			match(Token.DOUBLE_QUOTE);
			if (tok == Token.PLUS) {
				match(Token.PLUS);
				Expression s2 = cond();
				ComplexExpression e = new ComplexExpression(s1,Token.PLUS,s2);
				return e;
			}
			return s1;
		}
		expected("Expression Factor (One of number, variable, function etc.)", tok, lex.val());
		return null;
	}
	
	String findExpression(String ident){
		return Program.exprStringMap.get(ident);
	}

	/**
	 * Test code for this class. Makes a new instance by e = new
	 * Parser(s).parse() Then invokes all methods of Expression e
	 */
	public static void main(String[] args) {
		
		String sample0 = "(-1==-1)==true && false==true && (2>-1 || 3*5<14)";
		String sample1 = "(2*8)/2-(5-6+1)";
//		final static String sample = "-20/((3+5)*2)/(7--22)";
//		String sample2 = "\"hgfhgfhfh\"+\"asdas\" + fx() + fx()";
		String sample2 = "v1 + v1";
		String sample3 = "fx() + v1";

		
		String name = "v1";
		String s = "5";
		System.out.println(s);
		
		Expression e = new Compiler(s).parse();
		Program.exprStringMap.put(name, sample1);
		Program.exprMap.put(name, e);

		name = "cond";
		s = sample0;
		System.out.println(s);
		
		e = new Compiler(s).parse();
		Program.exprStringMap.put(name, sample0);
		Program.exprMap.put(name, e);
		System.out.println(e + " = " + e.bValue());


		name = "fx";
		s = "cond";
		System.out.println(s);
		
		e = new Compiler(s).parse();
		Program.exprStringMap.put(name, s);
		Program.exprMap.put(name, e);
		System.out.println(e + " = " + e.bValue());

		
		name = "fxs";
		s = sample3;
		System.out.println(s);
		
		e = new Compiler(s).parse();
		Program.exprStringMap.put(name, sample2);
		Program.exprMap.put(name, e);
		
		//System.out.println(e.toPostfix());
		System.out.println(e + " = " + e.fValue());
		System.out.println(e + " = " + e.sValue());
		
		float x = -20f/((3f+5f)*2f)/(7f-(-22f));
		System.out.println(x);
	}
}