# expressionist
Simple expression lang

Basic expression compiling and running including

<pre>
1. Number Expression (float) 

    Example 1:  2 
    Example 2:  35.34
    Example 3:  -56.8
   
2. String Expression
    
    Example 1: "test"
    Example 2: "testing needs"
  
3. Variable Expression
    
    Each expression itself has a name, which denotes a variable namee 
    
    Example 1: 
            v1 => 3   //assume variable declaration
            exp => v1 * 7  //v1 here used as variable 

4. Function Expression
    
    Example 1: 
            fx => 3 * (2 * 5)  // declaration of a function
            exp => fx()   // calling declared function fx

5. Complex Expression
    
    Example 1: (3+5) * (7*-2) / (2+2 * (57/19))
    Example 2: "test" + "for" + "expression string concetenation" + "2+3"
    Example 3:
                e1 => 2+3  // float 
                e2 => "test" // string
                e3 => e1 + e2 // gives result "5.0test"
    
    
</pre>
