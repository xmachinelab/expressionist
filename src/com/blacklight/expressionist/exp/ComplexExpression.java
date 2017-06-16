package com.blacklight.expressionist.exp;

import com.blacklight.expressionist.Token;

public class ComplexExpression implements Expression {
	
	Expression left;
	Token oper;
	Expression right;

	/**
	 * Constructs a binary operation from two Expressions and an operator.
	 */
	public ComplexExpression(Expression le, Token op, Expression re) {
		left = le;
		oper = op;
		right = re;
	}

	public boolean bValue() {return false;}
	public float fValue() {
		if (oper == Token.PLUS)
			return left.fValue() + right.fValue();
		if (oper == Token.MINUS)
			return left.fValue() - right.fValue();
		if (oper == Token.STAR)
			return left.fValue() * right.fValue();
		if (oper == Token.SLASH)
			return left.fValue() / right.fValue();
		return Float.NaN;
	}

	public String toPostfix() {
		return left.toPostfix() + right.toPostfix() + ' ' + oper;
	}

	int precedence() {
		if (oper == Token.PLUS || oper == Token.MINUS)
			return 10;
		if (oper == Token.STAR || oper == Token.SLASH)
			return 20;
		throw new IllegalArgumentException("operation " + oper);
	}

	public String toString() {
		return toString(left, false) + oper + toString(right, true);
	}

	String toString(Expression e, boolean atRight) {
		String s = e.toString();
		if (!(e instanceof ComplexExpression))
			return s;
		int prec = this.precedence();
		int p = ((ComplexExpression) e).precedence();
		if (prec < p || (prec == p && !atRight))
			return s;
		return Token.LEFT + s + Token.RIGHT;
	}

	static String addBlanks(Expression e) {
		String[] a = e.toTree().split("\n");
		String t = "";
		for (String s : a)
			t += "  " + s + "\n";
		return t;
	}

	public String toTree() {
		return oper.name() + "\n" + addBlanks(left) + addBlanks(right);
	}

	@Override
	public String sValue() {
		if (oper == Token.PLUS)
			return left.sValue() + right.sValue();
		return fValue() +"";
	}
}