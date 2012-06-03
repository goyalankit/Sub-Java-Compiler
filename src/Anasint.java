// $ANTLR : "grammer.g" -> "Anasint.java"$
 
	import java.util.*;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class Anasint extends antlr.LLkParser       implements AnalexTokenTypes
 {
	
Stack breakStack = new Stack();
Hashtable variableTypes = new Hashtable(); //stores the type of variable i.e, int, String as a value and name of the variable as a key
Hashtable variables = new Hashtable();  //stores the value of variables
Hashtable classRelations = new Hashtable(); //stores the parent of the child class
List classes = new ArrayList(); //contains all the classes that have been defined

protected Anasint(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public Anasint(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected Anasint(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public Anasint(TokenStream lexer) {
  this(lexer,1);
}

public Anasint(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void entrada() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			instrucciones();
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void instrucciones() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop6678:
			do {
				if (((LA(1) >= PACKAGE && LA(1) <= PROTECTED))) {
					instruccion();
				}
				else {
					break _loop6678;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void instruccion() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case PACKAGE:
			{
				package_declaration();
				break;
			}
			case IMPORT:
			{
				import_package();
				break;
			}
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			{
				class_definition();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void package_declaration() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(PACKAGE);
			match(IDENT);
			match(42);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void import_package() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(IMPORT);
			match(IDENT);
			match(42);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void class_definition() throws RecognitionException, TokenStreamException {
		
		Token  j = null;
		Token  i = null;
		
		try {      // for error handling
			access_specifiers();
			match(CLASS);
			j = LT(1);
			match(IDENT);
			{
			switch ( LA(1)) {
			case EXTENDS:
			{
				match(EXTENDS);
				i = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					classRelations.put(i.getText(),j.getText());
				}
				break;
			}
			case 44:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(44);
			if ( inputState.guessing==0 ) {
				breakStack.push(0); classes.add(j.getText());
			}
			{
			_loop6693:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					class_bloque();
				}
				else {
					break _loop6693;
				}
				
			} while (true);
			}
			match(45);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void access_specifiers() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PUBLIC:
			{
				match(PUBLIC);
				break;
			}
			case PRIVATE:
			{
				match(PRIVATE);
				break;
			}
			case PROTECTED:
			{
				match(PROTECTED);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
	}
	
	public final String  data_type() throws RecognitionException, TokenStreamException {
		String res = "";;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			{
				match(INT);
				if ( inputState.guessing==0 ) {
					res = "int";
				}
				break;
			}
			case STRING:
			{
				match(STRING);
				if ( inputState.guessing==0 ) {
					res = "String";
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		return res;
	}
	
	public final void return_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case INT:
			{
				match(INT);
				break;
			}
			case STRING:
			{
				match(STRING);
				break;
			}
			case VOID:
			{
				match(VOID);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_4);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void parameter_func_def() throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Token  j = null;
		String e1 = "";
		
		try {      // for error handling
			switch ( LA(1)) {
			case VOID:
			{
				match(VOID);
				break;
			}
			case 49:
			{
				break;
			}
			default:
				boolean synPredMatched6689 = false;
				if (((LA(1)==INT||LA(1)==STRING))) {
					int _m6689 = mark();
					synPredMatched6689 = true;
					inputState.guessing++;
					try {
						{
						data_type();
						match(IDENT);
						match(43);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched6689 = false;
					}
					rewind(_m6689);
inputState.guessing--;
				}
				if ( synPredMatched6689 ) {
					e1=data_type();
					i = LT(1);
					match(IDENT);
					if ( inputState.guessing==0 ) {
						variableTypes.put(i.getText(),e1);
					}
					match(43);
					parameter_func_def();
				}
				else if ((LA(1)==INT||LA(1)==STRING)) {
					e1=data_type();
					j = LT(1);
					match(IDENT);
					if ( inputState.guessing==0 ) {
						variableTypes.put(j.getText(),e1);
					}
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void class_bloque() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			{
				{
				object_creation();
				}
				break;
			}
			case VOID:
			case INT:
			case STRING:
			case STATIC:
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			{
				{
				func_def_and_dec();
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void variables_with_commas(
		String e1
	) throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Token  f = null;
		
		try {      // for error handling
			boolean synPredMatched6696 = false;
			if (((LA(1)==IDENT))) {
				int _m6696 = mark();
				synPredMatched6696 = true;
				inputState.guessing++;
				try {
					{
					match(IDENT);
					match(43);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched6696 = false;
				}
				rewind(_m6696);
inputState.guessing--;
			}
			if ( synPredMatched6696 ) {
				i = LT(1);
				match(IDENT);
				match(43);
				variables_with_commas(e1);
				if ( inputState.guessing==0 ) {
					variableTypes.put(i.getText(),e1);
												 			if(!variables.containsKey(i.getText())){
												 				if(e1=="String") variables.put(i.getText(),"");  
												 					else variables.put(i.getText(),0);
												 				}
										  					else System.err.println("Duplicate declaration of variable: " + i.getText() );
				}
			}
			else if ((LA(1)==IDENT)) {
				f = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					variableTypes.put(f.getText(),e1);
										  							if(!variables.containsKey(f.getText())){
										  								if(e1=="String") variables.put(f.getText(),""); 
										  									 else variables.put(f.getText(),0);
										  						 		}
										  							else System.err.println("Duplicate declaration of variable: " + f.getText());
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void object_creation() throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		Token  j = null;
		
		try {      // for error handling
			i = LT(1);
			match(IDENT);
			match(IDENT);
			match(46);
			match(LITERAL_new);
			j = LT(1);
			match(IDENT);
			match(48);
			match(49);
			match(42);
			if ( inputState.guessing==0 ) {
					if(classes.contains(i.getText())&&classes.contains(j.getText())){										
									if(classRelations.containsKey(i.getText())||i.getText().equals(j.getText())){
										if(classRelations.containsKey(i.getText())&&
												!(classRelations.get(i.getText()).equals(j.getText())|| i.getText().equals(j.getText())))
												         System.err.println("Cannot call the constructer of Parent class1");
										}
									else { System.err.println("Cannot call the constructer of Parent class2"); }
									}
								else if(!classes.contains(i.getText())){System.err.println("No such class exists: " + i.getText()); }
								
								else System.err.println("no such class exists: " + j.getText());
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void func_def_and_dec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			boolean synPredMatched6723 = false;
			if (((_tokenSet_8.member(LA(1))))) {
				int _m6723 = mark();
				synPredMatched6723 = true;
				inputState.guessing++;
				try {
					{
					{
					_loop6720:
					do {
						if (((LA(1) >= PUBLIC && LA(1) <= PROTECTED))) {
							access_specifiers();
						}
						else {
							break _loop6720;
						}
						
					} while (true);
					}
					{
					_loop6722:
					do {
						if ((LA(1)==STATIC)) {
							match(STATIC);
						}
						else {
							break _loop6722;
						}
						
					} while (true);
					}
					return_type();
					match(IDENT);
					match(48);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched6723 = false;
				}
				rewind(_m6723);
inputState.guessing--;
			}
			if ( synPredMatched6723 ) {
				function_definition();
			}
			else if ((_tokenSet_9.member(LA(1)))) {
				declaration();
				match(42);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void bloque_func() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case WHILE:
			case FOR:
			{
				loop();
				break;
			}
			case IF:
			{
				conditional_if();
				break;
			}
			case INT:
			case STRING:
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			{
				declaration();
				match(42);
				break;
			}
			default:
				boolean synPredMatched6705 = false;
				if (((_tokenSet_10.member(LA(1))))) {
					int _m6705 = mark();
					synPredMatched6705 = true;
					inputState.guessing++;
					try {
						{
						conditionalexpr();
						match(50);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched6705 = false;
					}
					rewind(_m6705);
inputState.guessing--;
				}
				if ( synPredMatched6705 ) {
					syntacticalPredicate();
					match(42);
				}
				else {
					boolean synPredMatched6707 = false;
					if (((LA(1)==IDENT))) {
						int _m6707 = mark();
						synPredMatched6707 = true;
						inputState.guessing++;
						try {
							{
							match(IDENT);
							match(46);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched6707 = false;
						}
						rewind(_m6707);
inputState.guessing--;
					}
					if ( synPredMatched6707 ) {
						assignment();
						match(42);
					}
					else {
						boolean synPredMatched6709 = false;
						if (((LA(1)==IDENT))) {
							int _m6709 = mark();
							synPredMatched6709 = true;
							inputState.guessing++;
							try {
								{
								match(IDENT);
								match(48);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched6709 = false;
							}
							rewind(_m6709);
inputState.guessing--;
						}
						if ( synPredMatched6709 ) {
							func_call();
							match(42);
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}}
				}
				catch (RecognitionException ex) {
					if (inputState.guessing==0) {
						reportError(ex);
						recover(ex,_tokenSet_11);
					} else {
					  throw ex;
					}
				}
			}
			
	public final void loop() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case WHILE:
			{
				match(WHILE);
				match(48);
				conditionalexpr();
				match(49);
				match(44);
				if ( inputState.guessing==0 ) {
					if(breakStack.empty() || (Integer)breakStack.peek()==0){breakStack.push(0);} 
									else {	System.err.println("Unreachable code : Please check you break statement");}
				}
				{
				_loop6737:
				do {
					if ((_tokenSet_12.member(LA(1)))) {
						bloque_loop();
					}
					else {
						break _loop6737;
					}
					
				} while (true);
				}
				match(45);
				if ( inputState.guessing==0 ) {
					breakStack.pop();
				}
				break;
			}
			case FOR:
			{
				match(FOR);
				match(48);
				assignment();
				match(42);
				conditionalexpr();
				match(42);
				assignment();
				match(49);
				match(44);
				if ( inputState.guessing==0 ) {
					if(breakStack.empty() || (Integer)breakStack.peek()==0){breakStack.push(0);} 
						 			else {	System.err.println("Unreachable code : Please check you break statement");}
				}
				{
				_loop6739:
				do {
					if ((_tokenSet_12.member(LA(1)))) {
						bloque_loop();
					}
					else {
						break _loop6739;
					}
					
				} while (true);
				}
				match(45);
				if ( inputState.guessing==0 ) {
					breakStack.pop();
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_13);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void conditional_if() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(IF);
			match(48);
			{
			conditionalexpr();
			}
			match(49);
			match(44);
			{
			_loop6748:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					bloque_func();
				}
				else {
					break _loop6748;
				}
				
			} while (true);
			}
			match(45);
			{
			switch ( LA(1)) {
			case ELSE:
			{
				conditional_else();
				break;
			}
			case WHILE:
			case FOR:
			case IF:
			case INT:
			case STRING:
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			case NUMERO:
			case IDENT:
			case 45:
			case 48:
			case 52:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void conditionalexpr() throws RecognitionException, TokenStreamException {
		
		Token  i = null;
		int e1=0,e2=0;String s="";
		
		try {      // for error handling
			if ((LA(1)==52)) {
				match(52);
				e1=expr();
			}
			else {
				boolean synPredMatched6743 = false;
				if (((LA(1)==IDENT))) {
					int _m6743 = mark();
					synPredMatched6743 = true;
					inputState.guessing++;
					try {
						{
						match(IDENT);
						match(DEQUAL);
						match(CADENA);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched6743 = false;
					}
					rewind(_m6743);
inputState.guessing--;
				}
				if ( synPredMatched6743 ) {
					i = LT(1);
					match(IDENT);
					match(DEQUAL);
					s=expr_string();
					if ( inputState.guessing==0 ) {
						if(variableTypes.get(i.getText())!="String") System.err.println("Type Mismatch at variable " + i.getText());
					}
				}
				else if ((LA(1)==NUMERO||LA(1)==IDENT||LA(1)==48)) {
					e1=expr();
					{
					switch ( LA(1)) {
					case DEQUAL:
					{
						match(DEQUAL);
						break;
					}
					case COND:
					{
						match(COND);
						break;
					}
					case MAS:
					{
						match(MAS);
						break;
					}
					case MENOS:
					{
						match(MENOS);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					e2=expr();
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_15);
				} else {
				  throw ex;
				}
			}
		}
		
	public final void syntacticalPredicate() throws RecognitionException, TokenStreamException {
		
		int e1=0,e2=0;
		
		try {      // for error handling
			conditionalexpr();
			match(50);
			e1=expr();
			match(51);
			e2=expr();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void assignment() throws RecognitionException, TokenStreamException {
		
		Token  j = null;
		Token  c1 = null;
		Token  i = null;
		int e1=0;
		
		try {      // for error handling
			boolean synPredMatched6734 = false;
			if (((LA(1)==IDENT))) {
				int _m6734 = mark();
				synPredMatched6734 = true;
				inputState.guessing++;
				try {
					{
					match(IDENT);
					match(46);
					match(CADENA);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched6734 = false;
				}
				rewind(_m6734);
inputState.guessing--;
			}
			if ( synPredMatched6734 ) {
				j = LT(1);
				match(IDENT);
				match(46);
				c1 = LT(1);
				match(CADENA);
				if ( inputState.guessing==0 ) {
					if(variableTypes.containsKey(j.getText())){
							   					
							   					if(variableTypes.get(j.getText())=="String") {variables.put(j.getText(),c1.getText()); 
							   							System.out.println("Assignment Successfull: " + j.getText() +  " =  " + c1.getText() );}	
							   					else System.err.println("Type mismatch::: Variable: "+ j.getText() +" is of type " + variableTypes.get(j.getText()));
							   					
							   					}
							   				else System.err.println("Variable not defined : " + j.getText());
							   				
				}
			}
			else if ((LA(1)==IDENT)) {
				i = LT(1);
				match(IDENT);
				match(46);
				e1=expr();
				if ( inputState.guessing==0 ) {
					if(variableTypes.containsKey(i.getText())){
														if(variableTypes.get(i.getText())=="int"){variables.put(i.getText(),e1);
															System.out.println("Assignment Successfull: " + i.getText() +  " = " + e1 );}																								
														else System.err.println("Type mismatch::: Variable: "+ i.getText() + " is of type " + variableTypes.get(i.getText()));
																			}
																			  		   							
							   						else System.err.println("Variable not defined : " + i.getText());
							   							
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_16);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void func_call() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(IDENT);
			match(48);
			parameter();
			match(49);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void declaration() throws RecognitionException, TokenStreamException {
		
		String e1="";
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			{
				access_specifiers();
				break;
			}
			case INT:
			case STRING:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			e1=data_type();
			variables_with_commas(e1);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void bloque_loop() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case WHILE:
			case FOR:
			{
				loop();
				break;
			}
			case IF:
			{
				conditional_if_loop();
				break;
			}
			case BREAK:
			{
				match(BREAK);
				if ( inputState.guessing==0 ) {
					if(((Integer)breakStack.peek())==0){breakStack.pop(); breakStack.push(1);} 
											else {System.err.println("Unreachable code : Please check you break statement");};
				}
				match(42);
				break;
			}
			default:
				boolean synPredMatched6712 = false;
				if (((_tokenSet_10.member(LA(1))))) {
					int _m6712 = mark();
					synPredMatched6712 = true;
					inputState.guessing++;
					try {
						{
						conditionalexpr();
						match(50);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched6712 = false;
					}
					rewind(_m6712);
inputState.guessing--;
				}
				if ( synPredMatched6712 ) {
					syntacticalPredicate();
					match(42);
				}
				else {
					boolean synPredMatched6714 = false;
					if (((LA(1)==IDENT))) {
						int _m6714 = mark();
						synPredMatched6714 = true;
						inputState.guessing++;
						try {
							{
							match(IDENT);
							match(46);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched6714 = false;
						}
						rewind(_m6714);
inputState.guessing--;
					}
					if ( synPredMatched6714 ) {
						assignment();
						match(42);
					}
					else {
						boolean synPredMatched6716 = false;
						if (((LA(1)==IDENT))) {
							int _m6716 = mark();
							synPredMatched6716 = true;
							inputState.guessing++;
							try {
								{
								match(IDENT);
								match(48);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched6716 = false;
							}
							rewind(_m6716);
inputState.guessing--;
						}
						if ( synPredMatched6716 ) {
							func_call();
							match(42);
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}}
				}
				catch (RecognitionException ex) {
					if (inputState.guessing==0) {
						reportError(ex);
						recover(ex,_tokenSet_17);
					} else {
					  throw ex;
					}
				}
			}
			
	public final void conditional_if_loop() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(IF);
			match(48);
			{
			conditionalexpr();
			}
			match(49);
			match(44);
			if ( inputState.guessing==0 ) {
				if((Integer)breakStack.peek()==0){breakStack.push(0);}
					 				else {	System.err.println("Unreachable code : Please check you break statement");}
			}
			{
			_loop6753:
			do {
				if ((_tokenSet_12.member(LA(1)))) {
					bloque_loop();
				}
				else {
					break _loop6753;
				}
				
			} while (true);
			}
			match(45);
			if ( inputState.guessing==0 ) {
				breakStack.pop();
			}
			{
			switch ( LA(1)) {
			case ELSE:
			{
				conditional_else_loop();
				break;
			}
			case WHILE:
			case FOR:
			case IF:
			case BREAK:
			case NUMERO:
			case IDENT:
			case 45:
			case 48:
			case 52:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void function_definition() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case PUBLIC:
			case PRIVATE:
			case PROTECTED:
			{
				access_specifiers();
				break;
			}
			case VOID:
			case INT:
			case STRING:
			case STATIC:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case STATIC:
			{
				match(STATIC);
				break;
			}
			case VOID:
			case INT:
			case STRING:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			return_type();
			match(IDENT);
			match(48);
			{
			parameter_func_def();
			}
			match(49);
			match(44);
			{
			_loop6729:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					bloque_func();
				}
				else {
					break _loop6729;
				}
				
			} while (true);
			}
			match(45);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
	}
	
	public final int  expr() throws RecognitionException, TokenStreamException {
		int res=0;
		
		int e1,e2;
		
		try {      // for error handling
			e1=exp_mult();
			if ( inputState.guessing==0 ) {
				res=e1;
			}
			{
			_loop6769:
			do {
				switch ( LA(1)) {
				case 53:
				{
					{
					match(53);
					e2=exp_mult();
					if ( inputState.guessing==0 ) {
						res=res+e2;
					}
					}
					break;
				}
				case 54:
				{
					{
					match(54);
					e2=exp_mult();
					if ( inputState.guessing==0 ) {
						res=res-e2;
					}
					}
					break;
				}
				default:
				{
					break _loop6769;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		return res;
	}
	
	public final String  expr_string() throws RecognitionException, TokenStreamException {
		String c1 = "";;
		
		Token  c = null;
		
		try {      // for error handling
			c = LT(1);
			match(CADENA);
			if ( inputState.guessing==0 ) {
				c1 = c.getText();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return c1;
	}
	
	public final void conditional_else() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(ELSE);
			match(44);
			{
			_loop6757:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					bloque_func();
				}
				else {
					break _loop6757;
				}
				
			} while (true);
			}
			match(45);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void conditional_else_loop() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(ELSE);
			match(44);
			if ( inputState.guessing==0 ) {
				if((Integer)breakStack.peek()==0){breakStack.push(0);}
					 						else {	System.err.println("Unreachable code : Please check you break statement");}
			}
			{
			_loop6760:
			do {
				if ((_tokenSet_12.member(LA(1)))) {
					bloque_loop();
				}
				else {
					break _loop6760;
				}
				
			} while (true);
			}
			match(45);
			if ( inputState.guessing==0 ) {
				breakStack.pop();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void parameter() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			boolean synPredMatched6764 = false;
			if (((LA(1)==IDENT))) {
				int _m6764 = mark();
				synPredMatched6764 = true;
				inputState.guessing++;
				try {
					{
					match(IDENT);
					match(43);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched6764 = false;
				}
				rewind(_m6764);
inputState.guessing--;
			}
			if ( synPredMatched6764 ) {
				match(IDENT);
				match(43);
				parameter();
			}
			else if ((LA(1)==IDENT)) {
				match(IDENT);
			}
			else if ((LA(1)==49)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			} else {
			  throw ex;
			}
		}
	}
	
	public final int  exp_mult() throws RecognitionException, TokenStreamException {
		int res=0;
		
		int e1,e2;
		
		try {      // for error handling
			e1=exp_base();
			if ( inputState.guessing==0 ) {
				res=e1;
			}
			{
			_loop6774:
			do {
				switch ( LA(1)) {
				case 55:
				{
					{
					match(55);
					e2=exp_base();
					if ( inputState.guessing==0 ) {
						res=res*e2;
					}
					}
					break;
				}
				case 56:
				{
					{
					match(56);
					e2=exp_base();
					if ( inputState.guessing==0 ) {
						res=res/e2;
					}
					}
					break;
				}
				default:
				{
					break _loop6774;
				}
				}
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return res;
	}
	
	public final int  exp_base() throws RecognitionException, TokenStreamException {
		int res=0;
		
		Token  n = null;
		Token  i = null;
		int e;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMERO:
			{
				n = LT(1);
				match(NUMERO);
				if ( inputState.guessing==0 ) {
					res = new Integer(n.getText()).intValue();
				}
				break;
			}
			case 48:
			{
				match(48);
				e=expr();
				match(49);
				if ( inputState.guessing==0 ) {
					res = e;
				}
				break;
			}
			case IDENT:
			{
				i = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					if(variables.containsKey(i.getText())){
																	if(variableTypes.get(i.getText())=="int")
																		res = ((Integer)(variables.get(i.getText()))).intValue();
						 												else if(variableTypes.get(i.getText())=="String")
						 													System.err.println("Cannot perform this function on type String");
						 											}	
						 	 											else System.err.println("undeclared identifier: " + i.getText());
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		return res;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"while\"",
		"\"for\"",
		"\"if\"",
		"\"else\"",
		"\"void\"",
		"\"int\"",
		"\"String\"",
		"\"break\"",
		"\"class\"",
		"\"extends\"",
		"\"static\"",
		"\"package\"",
		"\"import\"",
		"\"public\"",
		"\"private\"",
		"\"protected\"",
		"NUEVA_LINEA",
		"BLANCO",
		"DIGITO",
		"NUMERO",
		"OPERADOR",
		"PARENTESIS",
		"SEPARADOR",
		"LETTER",
		"CADENA",
		"MAS",
		"MENOS",
		"PARANT",
		"EQUAL",
		"COND",
		"COMMA",
		"NOT",
		"QUES",
		"COL",
		"IDENT",
		"KOL",
		"DEQUAL",
		"COMMENT",
		"\";\"",
		"\",\"",
		"\"{\"",
		"\"}\"",
		"\"=\"",
		"\"new\"",
		"\"(\"",
		"\")\"",
		"\"?\"",
		"\":\"",
		"\"!\"",
		"\"+\"",
		"\"-\"",
		"\"*\"",
		"\"/\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1015810L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 274878842624L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 22272L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 274877906944L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 562949953421312L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 35459250931456L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 4398046511104L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 935680L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 919040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 4785349490376704L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 4820533863384688L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 4785349490378864L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 4820533863386736L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 4785349491295856L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 1693247906775040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 567347999932416L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 4820533862467696L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 3946157432635392L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 30967755196858368L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 139054146253750272L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	
	}
