header{ 
	import java.util.*;
}
//////////////////////////
// Analizador léxico   //
////////////////////////
class Analex extends Lexer;
options {
k=2;
	charVocabulary = '\3'..'\377';
}
tokens{
//loop
WHILE="while";
FOR="for";

//conditional 
IF="if";
ELSE = "else";

//types
VOID="void";
INT="int";
STRING = "String";

//break for loops
BREAK="break";

//special
CLASS ="class";
EXTENDS = "extends";
STATIC="static";
PACKAGE = "package";
IMPORT ="import";

//access specifiers
PUBLIC = "public";
PRIVATE = "private";
PROTECTED = "protected";

}

protected NUEVA_LINEA: '\n' {newline();};
BLANCO : (' '|'\t'|"\n"){$setType(Token.SKIP);};
protected DIGITO : '0'..'9';
NUMERO : (DIGITO)+('.'(DIGITO)+)?;
OPERADOR : '+'|'-'|'/'|'*';
PARENTESIS : '('|')' ;
SEPARADOR : ';' ;
protected LETTER: (('a'..'z')|('A'..'Z'));
CADENA: '"' (options {greedy = false;}:.)* '"';
MAS: '>';
MENOS: '<';
PARANT: '{'|'}';
EQUAL : '=';
COND :"<="|">="|"&&"|"||";
COMMA:',';
NOT:"!";
QUES: '?';
COL : ':';
IDENT: (LETTER)(LETTER)*(DIGITO)*;        //Name of an identifier can include both numbers and letters. But it must start with an alphabet.
KOL:'&';
DEQUAL:"==";	 
//QUOTE : '"';

COMMENT: "//" (options {greedy = false;}: . )* NUEVA_LINEA  {$setType(Token.SKIP);} 
		| "/*" (options {greedy = false;}: . )* "*/"{$setType(Token.SKIP);} 
				
				; 


/////////////////////////////
// Analizador sintáctico  //
///////////////////////////

class Anasint extends Parser;
{	
Stack breakStack = new Stack();
Hashtable variableTypes = new Hashtable(); //stores the type of variable i.e, int, String as a value and name of the variable as a key
Hashtable variables = new Hashtable();  //stores the value of variables
Hashtable classRelations = new Hashtable(); //stores the parent of the child class
List classes = new ArrayList(); //contains all the classes that have been defined
}

entrada : instrucciones EOF
		;

instrucciones : (instruccion)*
			  ;

///////////////////////////////////////////////////////////////////////////////////////
//following rule allows us to declare package, import package and define a new class /
/////////////////////////////////////////////////////////////////////////////////////
instruccion :	package_declaration
			|  	import_package
			|	class_definition 
			;

package_declaration : PACKAGE IDENT ";"  //current package
					;

import_package : IMPORT IDENT ";"       //import package
			   ;

///////////////////////////////////////////////////////////////////////////////////////////////////////
//Some general rules have defined below like return types, data types, parameters, access specifiers /
/////////////////////////////////////////////////////////////////////////////////////////////////////

access_specifiers : (PUBLIC|PRIVATE|PROTECTED)  
				  ;

data_type returns [String res = "";]  : INT{res = "int";}              //returns the type of variable
										|STRING     {res = "String";}
 		   								;
             //this is the datatype of variables and it cannot be void		
return_type : (INT|STRING|VOID) 		          //this is the return type of functions which can be void 
			;
///////////////////////////////////////////////////////////////////////			
//THE PARAMETER CAN BE 'VOID', EMPTY, OR OF THE FORM (int a, int b) //
//AS OF KNOW IT CANNOT BE OF THE FORM (int,int)                    //
////////////////////////////////////////////////////////////////////                 
			   			
parameter_func_def{String e1 = "";} : (data_type IDENT ",") => e1=data_type i:IDENT{variableTypes.put(i.getText(),e1);} "," parameter_func_def
					 | e1=data_type j:IDENT{variableTypes.put(j.getText(),e1);}
					 |VOID
					 |
					 ;	 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//CLASS DEFINITION : separate class_bloque has been created. thus in class we can only define variables and functions //
//Also THE BREAKSTACK HAS BEEN INITIALISED                                                                           //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class_definition : access_specifiers CLASS  j:IDENT (EXTENDS i:IDENT {classRelations.put(i.getText(),j.getText());}|)  "{"
						 {breakStack.push(0); classes.add(j.getText());} (class_bloque)* "}"  //define a new class
				 ;


/*
variables_with_commas_class : (IDENT COMMA) => IDENT COMMA variables_with_commas_class 
					  | IDENT 
	     			  ;
*/
			
///////////////////////////////////////  Variables   ///////////////////////////////////////////////
// One cannot declare variable more than once.                                                   //
//Scope Resolution has not been implemented. Hence Variable is allowed to be declared only once //
//with the same name                                                                           //
//////////////////////////////////////////////////////////////////////////////////////////////// 			
variables_with_commas[String e1] : (IDENT ",") => i:IDENT "," variables_with_commas[e1] 
							 	{variableTypes.put(i.getText(),e1);
							 			if(!variables.containsKey(i.getText())){
							 				if(e1=="String") variables.put(i.getText(),"");  
							 					else variables.put(i.getText(),0);
							 				}
					  					else System.err.println("Duplicate declaration of variable: " + i.getText() );}
					  			
					  			| f:IDENT {variableTypes.put(f.getText(),e1);
					  							if(!variables.containsKey(f.getText())){
					  								if(e1=="String") variables.put(f.getText(),""); 
					  									 else variables.put(f.getText(),0);
					  						 		}
					  							else System.err.println("Duplicate declaration of variable: " + f.getText());} 
	     			  			;
		  
		  
class_bloque :  (IDENT IDENT "=" "new")=>(object_creation)      //Alows to create an object of class 
				| (func_def_and_dec)
			 	;

///////////////////////////// OBJECT OF THE CLASS ////////////////////////////////////////////////////////////////////			  
//It creates the object of the class.                                                                             ///
//It also checks whether the class exists or not.                                                                ///
//It also check for inheritence                                                                                 ///
//one can call the constructor of child class while creating an object of the parent class but not vice versa. ///
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 
object_creation : i:IDENT IDENT "=" "new" j:IDENT "(" ")" ";"
			 {	if(classes.contains(i.getText())&&classes.contains(j.getText())){										
					if(classRelations.containsKey(i.getText())||i.getText().equals(j.getText())){
						if(classRelations.containsKey(i.getText())&&
								!(classRelations.get(i.getText()).equals(j.getText())|| i.getText().equals(j.getText())))
								         System.err.println("Cannot call the constructer of Parent class1");
						}
					else { System.err.println("Cannot call the constructer of Parent class2"); }
					}
				else if(!classes.contains(i.getText())){System.err.println("No such class exists: " + i.getText()); }
				
				else System.err.println("no such class exists: " + j.getText()); }							
									
				;
				
				
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//BLOQUE : its a general block which can contain all sort of instructions like loops, conditional (if, else), declaration of variables ///
//			 assignment of variables.                                                                                                  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//bloque_function has been defined separately because we cannot define a function inside another function //
///////////////////////////////////////////////////////////////////////////////////////////////////////////		
bloque_func : 	loop											// WHILE and FOR loop   	
			| 	conditional_if									// IF conditional statement  *******elseif has NOT been implemented			
			|	(conditionalexpr "?") => syntacticalPredicate ";"  	// syntactical predicate of type a>3?5:6		
			|	 (IDENT "=") => assignment ";"						// assignment to variables a=5;	
			|  	(IDENT "(")=>func_call ";"			
			| declaration ";"				// call to a function	
			;
			
///////////////////////////////////////////////////////////////////////////////////////////////////////////
//ONLY THE LOOP BLOCK CAN HAVE BREAK STATEMENT                                                          //
//IF ELSE STATEMENTS INSIDE LOOPS CAN CONTAIN BREAK STATEMENT HENCE THEY HAVE BEEN DEFINED SEPARATELY  //
////////////////////////////////////////////////////////////////////////////////////////////////////////		
bloque_loop : 		loop			 		
			|	conditional_if_loop														
			|	(conditionalexpr "?") => syntacticalPredicate ";"	
			|	 (IDENT "=") => assignment ";"						
			|  	(IDENT "(")=>func_call ";"							
			|   BREAK  {if(((Integer)breakStack.peek())==0){breakStack.pop(); breakStack.push(1);} 
						else {System.err.println("Unreachable code : Please check you break statement");};}";"
			;
			
////////////////////////////////////////////////////////////////////////////////////////////
//NOTE : declaration and assignment of variables at same time has not been implemented yet/
//////////////////////////////////////////////////////////////////////////////////////////		


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//NON DETERMINISM BETWEEN FUNCTION DEFINITION OF THE FORM ##  public static int main() { ## AND DECLARATION OF VARIABLES OF THE FORM //
// ## public static int a; ## HAS BEEN RESOLVED                                                                                     //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

func_def_and_dec : ((access_specifiers)* (STATIC)* return_type IDENT "(") => function_definition   
	    		| declaration ";" 		                                                     
				;


/////////////////////////////////////////////////////////////////////////
//function definition: OPTIONAL access specifier, static, parameters//// 
//					 : MUST HAVE return type                        ///
//////////////////////////////////////////////////////////////////////

				
function_definition	:(access_specifiers|) (STATIC|) return_type IDENT "("! (parameter_func_def) ")"!   "{" (bloque_func)* "}"
					;			

///////////////////////////////////   DECLARATION  ///////////////////////////////
//

declaration {String e1="";} :  (access_specifiers|) e1=data_type variables_with_commas[e1]           //IT ALLOWS MULTIPLE VARIABLES TO BE DEFINED TOGETHER 
			;
			
///////////////////////   ASSIGNMENT   /////////////////////////////////////////////////////////////////////////////
//Here the while assigning the value to a variable it first checks whether the variable has been defined or not////
//then it checks the type of variable and see if the value to be assigned belongs to the same type////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
assignment{int e1=0;} :  (IDENT "=" CADENA) => j:IDENT "=" c1:CADENA
   		   				{if(variableTypes.containsKey(j.getText())){
   		   					
   		   					if(variableTypes.get(j.getText())=="String") {variables.put(j.getText(),c1.getText()); 
   		   							System.out.println("Assignment Successfull: " + j.getText() +  " =  " + c1.getText() );}	
   		   					else System.err.println("Type mismatch::: Variable: "+ j.getText() +" is of type " + variableTypes.get(j.getText()));
   		   					
   		   					}
   		   				else System.err.println("Variable not defined : " + j.getText());
   		   				}
			|i:IDENT "=" e1=expr {if(variableTypes.containsKey(i.getText())){
									if(variableTypes.get(i.getText())=="int"){variables.put(i.getText(),e1);
										System.out.println("Assignment Successfull: " + i.getText() +  " = " + e1 );}																								
									else System.err.println("Type mismatch::: Variable: "+ i.getText() + " is of type " + variableTypes.get(i.getText()));
														}
														  		   							
   		   						else System.err.println("Variable not defined : " + i.getText());
   		   							}               //ASSIGNMENT OF VARIABLES LIKE a=34;   		   
   		   ;
						 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//AS OF KNOW IN THE LOOP STATEMENTS IT IS NECESSARY TO HAVE CURLY BRACES AFTER LIKE  while(..){ .... } AND for(..;..;..){ ..}  ///////
// FOR LOOP DOESN'T ALLOW USE OF ++ OR -- OPERRATORS                                                                     ////////////
//                                                                                                                       ///////////
//USE OF BREAK STATEMENT HAS BEEN IMPLEMENTED. IT CAN BE ONLY USED INSIDE LOOPS. GRAMMER IS CAPABLE OF DETECTING UNREACHABLE CODE// 
// DUE TO IMPROPER USE OF BREAK STATEMENT.                      / /                                                      /////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////						 
						 
						 
loop 		: 	WHILE "(" conditionalexpr ")" "{"  
				{if(breakStack.empty() || (Integer)breakStack.peek()==0){breakStack.push(0);} 
				else {	System.err.println("Unreachable code : Please check you break statement");}} 
		 		(bloque_loop)* "}" {breakStack.pop();}
		 	
		 	 | 	FOR "(" assignment ";" conditionalexpr ";" assignment ")" "{"  
	 			{if(breakStack.empty() || (Integer)breakStack.peek()==0){breakStack.push(0);} 
	 			else {	System.err.println("Unreachable code : Please check you break statement");}}
				(bloque_loop)* "}" {breakStack.pop();}	 
	 		;


//////////////////////////////////////////////
//SYNTACTICAL PREDICATE OF THE FORM a>4?5:5//
////////////////////////////////////////////

syntacticalPredicate{int e1=0,e2=0;} : conditionalexpr "?" e1=expr ":" e2=expr 
					; 

////////////////////////////////////////////////////////////////////////
//CONDITIONAL EXPRESSION RECOGNIZE  ==  , <=  ,  >= , &&  , || ,  >  //
//DOESNOT SUPPORT THE USE OF MULTIPLE OPERATORS AS a&&b||c          //
//-----------------------------------------------------------------//
//It also checks that only == operation is performed on Strings.  //
//If any other operation is performed it returns an error.       // 
//////////////////////////////////////////////////////////////////

conditionalexpr{int e1=0,e2=0;String s="";} :"!" e1=expr 
				|(IDENT DEQUAL CADENA) => i:IDENT DEQUAL s=expr_string
				{if(variableTypes.get(i.getText())!="String") System.err.println("Type Mismatch at variable " + i.getText()); }  
			    |e1=expr (DEQUAL|COND|MAS|MENOS) e2=expr	 
			    ;
			    
			    

////////////////////////////////////////////////////////////////////
//IT IS MANDATORY TO PUT CURLY BRACES IN IF AND ELSE STATEMENT   //
//////////////////////////////////////////////////////////////////
						
conditional_if : IF "(" (conditionalexpr) ")" "{" (bloque_func)* "}" (conditional_else| )  //As We cannot define function inside IF STATEMENT of a function
			   ;											

conditional_if_loop : IF "(" (conditionalexpr) ")" "{"   
					{if((Integer)breakStack.peek()==0){breakStack.push(0);}
	 				else {	System.err.println("Unreachable code : Please check you break statement");}} 
					(bloque_loop)* "}" {breakStack.pop();} (conditional_else_loop|)  //it can have break statement.
			   		;						//break statement cannot be without loop.				


conditional_else : ELSE "{" (bloque_func)* "}"
			   ;

conditional_else_loop : ELSE "{"   
						{if((Integer)breakStack.peek()==0){breakStack.push(0);}
	 						else {	System.err.println("Unreachable code : Please check you break statement");}}
			   		  (bloque_loop)* "}"{breakStack.pop();}
			   		  ;

////////////////////////////////////////////////////////////////
// MULTIPLE PARAMTERS ALLOWED WHILE CALLING FUNCTION         //
//////////////////////////////////////////////////////////////


func_call : IDENT "(" parameter ")" 
		;			

parameter : (IDENT ",") =>  IDENT "," parameter 
		| IDENT
		| 
		;
		
///////////////////
//EXPRESSIONS   //
/////////////////		
		
expr returns [int res=0] {int e1,e2;} : e1=exp_mult {res=e1;}(("+" e2=exp_mult {res=res+e2;})
										|("-" e2=exp_mult {res=res-e2;}))*
										;

exp_mult returns [int res=0] {int e1,e2;} : e1=exp_base {res=e1;} (("*" e2=exp_base {res=res*e2;})
											|("/" e2=exp_base {res=res/e2;}))*
											;

////////////////////////////////////////////////////////////////////////////////////////////////
//Here it is checking whether the identifier is int or not If it is String it return an error// 
//because Arithmetic Operations are not allowed on strings                                  //
//it also checks if the identifier has been defined or not. If NoT it returns an error.    //
////////////////////////////////////////////////////////////////////////////////////////////
exp_base returns [int res=0] {int e;} : n:NUMERO {res = new Integer(n.getText()).intValue();}
									| "(" e=expr ")" {res = e;}
									|i:IDENT {if(variables.containsKey(i.getText())){
												if(variableTypes.get(i.getText())=="int")
													res = ((Integer)(variables.get(i.getText()))).intValue();
	 												else if(variableTypes.get(i.getText())=="String")
	 													System.err.println("Cannot perform this function on type String");
	 											}	
	 	 											else System.err.println("undeclared identifier: " + i.getText());};
///////////////////////////////////////////////////////////////////////////////
//For DataType String: Arithmetic Operations cannot be performed on Strings //
//it returns the text value 	 	 								       //
////////////////////////////////////////////////////////////////////////////		
expr_string returns [String c1 = "";] : c:CADENA 
										{c1 = c.getText();};
	