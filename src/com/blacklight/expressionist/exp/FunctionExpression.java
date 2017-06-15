package com.blacklight.expressionist.exp;

import com.blacklight.expressionist.Program;

public class FunctionExpression implements Expression  {
   String name; 
   String expString; 
   Expression exp;

	public FunctionExpression(String fn, String e) { 
      name = fn; 
      this.expString = e;
   }
   
	public float fValue() {
      try{
        return getExpression().fValue();
      } catch (Exception e) {
        return Float.NaN;
      }
   }

   public String sValue() {
	   try{
		   Expression expression = getExpression();
		return expression.sValue();
	   } catch (Exception e) {
		   return "Not a String";
	   }
   }
   
   Expression getExpression() {
	   return Program.exprMap.get(name);
   }
   
   public String toPostfix() { 
	   return exp.toPostfix()+" "+name; 
   }
   
   public String toString() { return name+"()"; }
   public String toTree() { 
	   return name+"\n"+ComplexExpression.addBlanks(exp);
   }

   public static void main(String[] args) {
   }
}