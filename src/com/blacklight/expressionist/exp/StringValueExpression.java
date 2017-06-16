package com.blacklight.expressionist.exp;

public class StringValueExpression implements Expression  {
   
	String val;
   /** Constructs a constant Expression. */   
   
   public StringValueExpression(String nval) { val = nval; }
   
   public boolean bValue() {return false;}
   
   public float fValue() { return Float.NaN; }

   public String sValue() { return "" + val + ""; }
   
   public void setSvalue(String val){
	   this.val = val;
   }

   
   public String toPostfix() { return ' '+numToStr(val); }
   
   public String toString() { return numToStr(val); }
   /** Converts argument to String, removing ".0" 
    * at the end of an integer constant.
    */   
   public static String numToStr(String n) {
      String s = ""+n;
      if (s.endsWith(".0"))  //integers need no decimal point
         s = s.substring(0, s.length()-2);
      return s;
   }
   public String toTree() { return toString() ; }
}