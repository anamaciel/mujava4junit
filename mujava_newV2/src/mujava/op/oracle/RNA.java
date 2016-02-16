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

import mujava.MutationSystem;
import mujava.op.oracle.util.AnnotationManager;
import mujava.op.util.OracleMutantCodeWriter;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ExpressionList;
import openjava.ptree.Literal;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.VariableDeclaration;

/**
 * <p>Generate RNA (Replace Null Assertion) mutants --
 *    Example: assertNull( Object)  → assertNotNull(NotNull= Object)
 *    
 *    assertNull
 *    assertNotNull
 *    
 * </p>
 * @author Ana Maciel
 * @version 1.0
  */

public class RNA extends JUnit_OP
{
	
	VariableDeclaration mutant;
	VariableDeclaration original;
	
	public RNA(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}

	
	
	/*public void visit( VariableDeclaration p ) throws ParseTreeException 
	{
		original = p;
		if(p.getInitializer().toString().equals("null")){
			
			ExpressionList teste = new ExpressionList();
			teste.add(Literal.makeLiteralNewType("new " +  p.getTypeSpecifier() + "()"));
			
			 mutant = p;
	         mutant.setTypeSpecifier(p.getTypeSpecifier());
	         mutant.setInitializer(teste.get(0));
	         
	         //System.out.println(mutant);
		}else{
			
			ExpressionList teste = new ExpressionList();
			teste.add(Literal.constantNull());
			
			 mutant = p;
	         mutant.setTypeSpecifier(p.getTypeSpecifier());
	         mutant.setInitializer(teste.get(0));
	         
	         //System.out.println(mutant);
		}
	}*/
	
	


	public void visit( MethodCall p ) throws ParseTreeException
	{

		if (p.getName().equals("assertNull") || p.getName().equals("assertNotNull"))
		{
			ExpressionList arguments = p.getArguments();
			try {
				//System.out.println("environment: " + getEnvironment());
				OJClass varType = arguments.get(0).getType(getEnvironment());

				if(p.getName().equals("assertNull")){
					p.setName(p.getName().replace("assertNull", "assertNotNull"));	


					if(arguments.size()==2){
						System.out.println("nome: " + p.getName());

						ExpressionList mutantArgs = new ExpressionList();				   

						mutantArgs.add(arguments.get(0));
						mutantArgs.add(arguments.get(1));

						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

						outputToFile(p, mutant);
					}else if(arguments.size()==1){
						ExpressionList mutantArgs = new ExpressionList();

						mutantArgs.add(arguments.get(0));

						//mutantArgs.add(new CastExpression(new OJClass(java_class, metainfo), expr));

						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

						outputToFile(p, mutant);
					}

				}else if(p.getName().equals("assertNotNull")){
					p.setName(p.getName().replace("assertNotNull", "assertNull"));	



						if(arguments.size()==2){
							System.out.println("nome: " + p.getName());

							ExpressionList mutantArgs = new ExpressionList();				   

							mutantArgs.add(arguments.get(0));
							mutantArgs.add(arguments.get(1));

							MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

							outputToFile(p, mutant, this.original, this.mutant );
						}else if(arguments.size()==1){
							ExpressionList mutantArgs = new ExpressionList();

							mutantArgs.add(arguments.get(0));

							//mutantArgs.add(new CastExpression(new OJClass(java_class, metainfo), expr));

							MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);

							//outputToFile(p, mutant);
							outputToFile(p, mutant, this.original, this.mutant );
						}
					
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
		f_name = getSourceName("RNA");
		String mutant_dir = getMuantID("RNA");

		try 
		{
			PrintWriter out = getPrintWriter(f_name);
			System.out.println("f_name: " + f_name);
			RNA_Writer writer = new RNA_Writer(mutant_dir, out);
			writer.setMutant(original_field, mutant);
			//System.out.println(currentMethodSignature);
			comp_unit.accept( writer );
			out.flush();  out.close();
			
			OracleMutantCodeWriter.writeAnnotations(f_name);
			
		} catch ( IOException e ) {
			System.err.println( "fails to create " + f_name );
		} catch ( ParseTreeException e ) {
			System.err.println( "errors during printing " + f_name );
			e.printStackTrace();
		}
	}
	
	/**
	 * Write RBA mutants to files
	 * @param original_field
	 * @param mutant
	 */
	public void outputToFile(MethodCall original_field, MethodCall mutant, VariableDeclaration original_var, VariableDeclaration mutation_var)
	{
		if (comp_unit == null) 
			return;

		String f_name;
		num++;
		f_name = getSourceName("RNA");
		String mutant_dir = getMuantID("RNA");

		try 
		{
			PrintWriter out = getPrintWriter(f_name);
			System.out.println("f_name: " + f_name);
			RNA_Writer writer = new RNA_Writer(mutant_dir, out);
			writer.setMutant(original_field, mutant, original_var, mutation_var);
			//System.out.println(currentMethodSignature);
			comp_unit.accept( writer );
			out.flush();  out.close();
			
			OracleMutantCodeWriter.writeAnnotations(f_name);
		} catch ( IOException e ) {
			System.err.println( "fails to create " + f_name );
		} catch ( ParseTreeException e ) {
			System.err.println( "errors during printing " + f_name );
			e.printStackTrace();
		}
	}



	/**
	 * Output PNC mutants to files
	 * @param original
	 * @param mutant
	 */
	/*public void outputToFile(AllocationExpression original, AllocationExpression mutant)
	{
		String f_name;
		num++;
		f_name = getSourceName(this);
		System.out.println("f_name 2 : " + f_name);
		System.out.println(mutant);
		String mutant_dir = getMuantID();

		try 
		{
			PrintWriter out = getPrintWriter(f_name);
			RNA_Writer writer = new RNA_Writer(  mutant_dir, out  );
			writer.setMutant(original, mutant);
			comp_unit.accept( writer );
			out.flush();  
			out.close();
		} catch ( IOException e ) {
			System.err.println( "fails to create " + f_name );
		} catch ( ParseTreeException e ) {
			System.err.println( "errors during printing " + f_name );
			e.printStackTrace();
		}
	}*/
}
