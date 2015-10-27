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

import mujava.op.util.SignatureMutantCodeWriter;
import openjava.mop.*;
import openjava.ptree.*;
import openjava.syntax.*;
/**
 * <p>Generate ATV (Adding Threshold Value) mutants --
 *    Example: assertEquals(double, double) → assertEquals(double, double, double)
 *    
 *    assertEquals
 *    assertArrayEquals
 *    assertNotEquals
 *    
 * </p>
 * @author Ana Maciel
 * @version 1.0
 */

public class ATV extends JUnit_OP
{

	public ATV(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}


	public void visit( MethodCall p ) throws ParseTreeException
	{

		ExpressionList arguments = p.getArguments();
		//System.out.println(p.getName());

		if ((p.getName().equals("assertEquals"))||(p.getName().equals("assertNotEquals"))
				||(p.getName().equals("assertArrayEquals")))
		{
			try {
				//System.out.println("environment: " + getEnvironment());
				OJClass varType = arguments.get(1).getType(getEnvironment());
				//System.out.println(varType.getName());
				if((varType.getName().contains("double"))||(varType.getName().contains("float"))){				   
					ExpressionList mutantArgs = new ExpressionList();
					//System.out.println("qtde argumentos: " + arguments.size());
					
					if(arguments.size()==2){
						mutantArgs.add(arguments.get(0));
						mutantArgs.add(arguments.get(1));
						mutantArgs.add(Literal.makeLiteral(0.001));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);
					}
					if(arguments.size()==3 && (arguments.get(0).getType(getEnvironment()).getName().contains("String"))){
						//System.out.println("if 3 argumentos");
						
						mutantArgs.add(arguments.get(0));
						mutantArgs.add(arguments.get(1));
						mutantArgs.add(arguments.get(2));
						mutantArgs.add(Literal.makeLiteral(0.001));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);
					}
					
				}

			} catch (Exception e) {
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
		f_name = getSourceName("ATV");
		String mutant_dir = getMuantID("ATV");

		try 
		{
			PrintWriter out = getPrintWriter(f_name);
			System.out.println("f_name: " + f_name);
			ATV_Writer writer = new ATV_Writer(mutant_dir, out);
			writer.setMutant(original_field, mutant);
			//System.out.println(mutant);
			//System.out.println(currentMethodSignature);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept( writer );
			out.flush();  
			out.close();
			
			SignatureMutantCodeWriter.writeAnnotations(f_name);
		} catch ( IOException e ) {
			System.err.println( "fails to create " + f_name );
		} catch ( ParseTreeException e ) {
			System.err.println( "errors during printing " + f_name );
			e.printStackTrace();
		}
	}
}
