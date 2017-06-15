package com.blacklight.expressionist.exp;

public interface Expression {
	/** Returns float value of this Expression */
	float fValue();

	/** Returns String value of this Expression */
	String sValue();

	/** String representation of this Expression */
	String toString();

	/** Converts this Expression to postfix (RPN) */
	String toPostfix();

	/** Converts this Expression to a tree */
	String toTree();
}