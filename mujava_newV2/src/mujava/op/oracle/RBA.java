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


import openjava.mop.*;
import openjava.ptree.*;

import java.io.*;
import java.util.Vector;

/**
 * <p>Generate RBA (Replace Boolean Assertion) mutants --
 *    Example: assertFalse(expression)  → assertTrue (!expression)
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

   public void visit( UnaryExpression p ) throws ParseTreeException
   {
      // NO OPERATION
	   System.out.println(p);
   }

   public void visit( Variable p) throws ParseTreeException
   {
	   System.out.println(p);
   }

   public void visit( FieldAccess p ) throws ParseTreeException
   {
	   System.out.println(p);
   }

   public void visit( BinaryExpression p ) throws ParseTreeException 
   {
	   System.out.println(p);
   }

   public void visit( AssignmentExpression p ) throws ParseTreeException
   {
	   System.out.println(p);
   }
   
   
   
   public void visit( OJMethod p ) throws ParseTreeException
   {
     //System.out.println("métodos: " + p.getName());
     //System.out.println(p.getBody().get(1));
	  
     //StatementList comandos = p.getBody();
     Vector field_list = new Vector();
     System.out.println("entrei");
     
   }
   
   public void visit( MethodCall p ) throws ParseTreeException
   {
	   //System.out.println("métodos 2: " + p.getName());
	   ExpressionList arguments = p.getArguments();
	   ExpressionStatement expression = new ExpressionStatement("teste");
	   Object[] contents = p.getContents();
	   for (int i = 0; i < contents.length; i++) {
		   System.out.println("contents: " + i + " - " + contents[i]);		
	   }
	   System.out.println("qtd de argumentos: " + arguments.size());
	   for (int i = 0; i < arguments.size(); i++) {
		   System.out.println("argumentos: " + arguments.get(i));
		   
	   }
   }

   /**
    * Write RBA mutants to files
    * @param original_field
    * @param mutant
    */
   public void outputToFile(FieldAccess original_field, String mutant)
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
		 RBA_Writer writer = new RBA_Writer(mutant_dir, out);
		 writer.setMutant(original_field, mutant);
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

   /**
    * Write RBA mutants to files
    * @param original_var
    * @param mutant
    */
   public void outputToFile(Variable original_var, String mutant)
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
		 RBA_Writer writer = new RBA_Writer(mutant_dir, out);
		 writer.setMutant(original_var, mutant);
         writer.setMethodSignature(currentMethodSignature);
		 comp_unit.accept( writer );
		 out.flush();  
		 out.close();
      } catch ( IOException e ) {
		 System.err.println( "fails to create " + f_name );
      } catch ( ParseTreeException e ) {
		 System.err.println( "errors during printing " + f_name );
		 e.printStackTrace();
      }
   }
}
