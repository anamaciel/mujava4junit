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


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import mujava.MutantsGenerator;
import mujava.MutationSystem;
import mujava.op.oracle.util.AnnotationManager;
import mujava.op.util.MutantCodeWriter;
import mujava.op.util.MutantCodeWriterOracle;
import mujava.op.util.SignatureMutantCodeWriter;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.mop.OJMethod;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ExpressionList;
import openjava.ptree.MethodCall;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.UnaryExpression;
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

	public RBA(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}
   

   
   public void visit( MethodCall p ) throws ParseTreeException
   {
	   try {
		   ExpressionList arguments = p.getArguments();		   

		   //System.out.println("RBA");
		   if (p.getName().equals("assertTrue") || (p.getName().equals("assertFalse")))
		   {
			   if(p.getName().equals("assertTrue")){
				   p.setName(p.getName().replace("assertTrue", "assertFalse"));
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
			   }else if(p.getName().equals("assertFalse")){
				   p.setName(p.getName().replace("assertFalse", "assertTrue"));	

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
			   }
		   }
	   } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
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
		 //System.out.println(out.toString());
		 RBA_Writer writer = new RBA_Writer(mutant_dir, out);
		 writer.setMutant(original_field, mutant);
		 //System.out.println(currentMethodSignature);
         writer.setMethodSignature(currentMethodSignature);
		 comp_unit.accept( writer );		 
		 out.flush();  out.close();
		 
		 SignatureMutantCodeWriter.writeAnnotations(f_name);
         
         
         
         //System.out.println("annotations: " + MutationSystem.annotations.size());
         
         //System.out.println("arquivoNovo: " + arquivoAnnotation);
         
         
         
      } catch ( IOException e ) {
		 System.err.println( "fails to create " + f_name );
      } catch ( ParseTreeException e ) {
		 System.err.println( "errors during printing " + f_name );
		 e.printStackTrace();
      }
   }
}
