package com.blacklight.expressionist.exp;

public class BooleanValueExpression implements Expression  {
   
	boolean bool;
   /** Constructs a constant Expression. */   
   
   public BooleanValueExpression(boolean b) { bool = b; }
   
   public boolean bValue() {return bool;}

   public float fValue() { return Float.NaN; }

   public String sValue() { return "" + bool + ""; }
   
   public String toPostfix() { return ' '+toString(); }
   
   public String toString() { return bool+""; }
   /** Converts argument to String, removing ".0" 
    * at the end of an integer constant.
    */   
   public String toTree() { return toString() ; }
}