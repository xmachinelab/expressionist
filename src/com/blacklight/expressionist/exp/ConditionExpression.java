package com.blacklight.expressionist.exp;

import com.blacklight.expressionist.Token;

public class ConditionExpression implements Expression {
	
	Expression left;
	Token relation;
	Expression right;

	/**
	 * Constructs a binary operation from two Expressions and an operator.
	 */
	public ConditionExpression(Expression le, Token op, Expression re) {
		left = le;
		relation = op;
		right = re;
	}

	public float fValue() {
		return Float.NaN;
	}
	public boolean bValue() {
		if (relation == Token.AND)
			return left.bValue() && right.bValue();
		if (relation == Token.OR)
			return left.bValue() || right.bValue();
		if (relation == Token.EQUALS)
			return (left.bValue() == right.bValue()) || (left.fValue() == right.fValue()) || (left.sValue().equals(right.sValue()));
		if (relation == Token.NOT_EQUALS)
			return !((left.bValue() == right.bValue()) || (left.fValue() == right.fValue()) || (left.sValue().equals(right.sValue())));
		if (relation == Token.LE)
			return left.fValue() <= right.fValue();
		if (relation == Token.GE)
			return left.fValue() >= right.fValue();
		if (relation == Token.LT)
				return left.fValue() < right.fValue();
		if (relation == Token.GT)
			return left.fValue() > right.fValue();
		return Boolean.FALSE;
	}

	public String toPostfix() {
		return left.toPostfix() + right.toPostfix() + ' ' + relation;
	}

	int precedence() {
		if (relation == Token.PLUS || relation == Token.MINUS)
			return 10;
		if (relation == Token.STAR || relation == Token.SLASH)
			return 20;
		throw new IllegalArgumentException("operation " + relation);
	}

	public String toString() {
		return "" + left + relation + right;
	}

	String toString(Expression e, boolean atRight) {
		String s = e.toString();
		if (!(e instanceof ConditionExpression))
			return s;
		int prec = this.precedence();
		int p = ((ConditionExpression) e).precedence();
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
		return relation.name() + "\n" + addBlanks(left) + addBlanks(right);
	}

	@Override
	public String sValue() {
		return "" + left.bValue() + relation + right.bValue();
	}
}