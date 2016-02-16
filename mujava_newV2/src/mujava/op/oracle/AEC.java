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
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS;

import mujava.MutantsGenerator;
import mujava.MutationSystem;
import mujava.op.oracle.util.AnnotationManager;
import mujava.op.util.MutantCodeWriter;
import mujava.op.util.MutantCodeWriterOracle;
import mujava.op.util.OracleMutantCodeWriter;
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
 * <p>Generate AEC (Adding Expected Class) mutants --
 *    Example: @Test → @Test = (expected = JavaNullExpection.class)
 *    
 *    @Test    
 *    
 *    
 * </p>
 * @author Ana Maciel
 * @version 1.0
  */

public class AEC extends JUnit_OP
{
	
	int cont=0;

	public AEC(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}
   
	
	public void visit( ClassDeclaration p ) throws ParseTreeException{
		System.out.println(p.getName());
		
		
		
		
		for (AnnotationManager annotation : MutationSystem.annotations) {
			AnnotationManager annotationOriginal = annotation;
		
			String ann = annotation.getAnnotation().trim();
			
			if(annotation.getAnnotation().trim().equals("@Test")){
											
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=IOException.class)");	
								
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=NullPointerException.class)");
				
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=IllegalArgumentException.class)");
				
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=ClassNotFoundException.class)");
							
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=ArrayIndexOutOfBoundsException.class)");
				
				outputToFile("AEC", annotation.getNumber(), "\t@Test(expected=ArithmeticException.class)");
				
			}else if(annotation.getAnnotation().contains("expected")){	
				String classe = ann.substring(ann.indexOf("expected"+1), ann.indexOf(")"));
				
				classe = classe.replace("=", "");
				classe = classe.replace(" ", "");
				
				//System.out.println(classe);
			}
			
		}
	
	}
   
   /**
    * Write AEC mutants to files
    * @param original_field
    * @param mutant
    */
   public void outputToFile(String ann, int number, String annotation)
   {
      if (comp_unit == null) 
    	 return;

      String f_name;
      num++;
      f_name = getSourceNameAnn("AEC", ann);
      String mutant_dir = getMuantID("AEC");

      try 
      {
		 PrintWriter out = getPrintWriter(f_name);
		 System.out.println("f_name: " + f_name);
		 //System.out.println(out.toString());
		 AEC_Writer writer = new AEC_Writer(mutant_dir, out);
		 //System.out.println(currentMethodSignature);
         writer.setMethodSignature(currentMethodSignature);
		 comp_unit.accept( writer );		 
		 out.flush();  out.close();
		 
		 OracleMutantCodeWriter.writeAnnotationsOperators(f_name, number, annotation);
         
         
         
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
