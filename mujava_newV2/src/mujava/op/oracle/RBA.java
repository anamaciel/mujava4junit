/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package mujava.op.oracle;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import openjava.mop.*;
import openjava.ptree.*;
import openjava.syntax.*;
/**
 * <p>Generate RBA (Replace Boolean Assertion) mutants --
 *    Example: assertFalse(expression)  → assertTrue (!expression)
 *    
 *    assertFalse
 *    assertTrue
 *    
 * </p>
 * @author Ana Maciel
 * @version 1.0
  */

public class RBA extends JUnit_OP
{
	boolean isPrePostEQ = true;

	public RBA(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}


   /*public void visit( Variable p) throws ParseTreeException
   {
	   System.out.println(p);
	  
   }*/

   
   
   public void visit( OJMethod p ) throws ParseTreeException
   {
     System.out.println("métodos: " + p.getName());
     //System.out.println(p.getBody().get(1));
	  
     //StatementList comandos = p.getBody();
     Vector field_list = new Vector();
     System.out.println("entrei");
     
   }
   
    
   /*private void uoi_boolean_MutantGen(Variable exp)
   {
      uoi_outputToFile(exp,UnaryExpression.NOT);
   }*/
   
   public void visit( MethodCall p ) throws ParseTreeException
   {



	   if (p.getName().equals("assertTrue"))
	   {
		   ExpressionList arguments = p.getArguments();
		   //System.out.println(p.getName());
		   try {
			   //System.out.println("environment: " + getEnvironment());
			   OJClass varType = arguments.get(0).getType(getEnvironment());

			   if(p.getName().equals("assertTrue")){
				   p.setName(p.getName().replace("assertTrue", "assertFalse"));
			   }else if(p.getName().equals("assertFalse")){
				   p.setName(p.getName().replace("assertFalse", "assertTrue"));	
			   }

			   if(arguments.size()==1){

				   ExpressionList mutantArgs = new ExpressionList();

				   //mutantArgs.add(arguments.get(0));
				   
				   mutantArgs.add(new UnaryExpression(arguments.get(0), UnaryExpression.NOT));

				   MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

				   outputToFile(p, mutant);
			   }else if(arguments.size()==2){

				   ExpressionList mutantArgs = new ExpressionList();

				   //mutantArgs.add(arguments.get(0));
				   //mutantArgs.add(arguments.get(1));

				   mutantArgs.add(arguments.get(0));
				   mutantArgs.add(new UnaryExpression(arguments.get(1), UnaryExpression.NOT));

				   MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

				   outputToFile(p, mutant);
			   }
		   } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }

   }

   /**
    * Write RBA mutants to files
    * @param original_field
    * @param mutant
    */
   public void outputToFile(MethodCall original_field, MethodCall mutant)
   {
      if (comp_unit == null) 
    	 return;

      String f_name;
      num++;
      f_name = getSourceName("RBA");
      String mutant_dir = getMuantID("RBA");

      try 
      {
		 PrintWriter out = getPrintWriter(f_name);
		 System.out.println("f_name: " + f_name);
		 RBA_Writer writer = new RBA_Writer(mutant_dir, out);
		 writer.setMutant(original_field, mutant);
		 //System.out.println(currentMethodSignature);
         writer.setMethodSignature(currentMethodSignature);
		 comp_unit.accept( writer );
		 out.flush();  out.close();
      } catch ( IOException e ) {
		 System.err.println( "fails to create " + f_name );
      } catch ( ParseTreeException e ) {
		 System.err.println( "errors during printing " + f_name );
		 e.printStackTrace();
      }
   }
}
