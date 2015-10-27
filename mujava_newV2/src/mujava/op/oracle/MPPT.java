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

import mujava.op.util.SignatureMutantCodeWriter;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ExpressionList;
import openjava.ptree.Literal;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
/**
 * <p>Generate MPPT (Modify Primitive Parameter Type) mutants --
 *    Example: assertArrayEquals(long[], long[]) → assertArrayEquals(short[], short[])
 *    
 *    assertEquals
 *    assertArrayEquals
 *    assertNotEquals
 *    
 * </p>
 * @author Ana Maciel
 * @version 1.0
 */

public class MPPT extends JUnit_OP
{

	public MPPT(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	{
		super( file_env, comp_unit );
	}


	public void visit( MethodCall p ) throws ParseTreeException
	{

		ExpressionList arguments = p.getArguments();
		//System.out.println(p.getName());
		try {
			if (p.getName().equals("assertEquals")){
				
				OJClass varType = arguments.get(0).getType(getEnvironment());				
				ExpressionList mutantArgs = new ExpressionList();
				
				//System.out.println(varType.getName());
				
				if(varType.getName().contains("double")){
					if(arguments.size()==3 && (arguments.get(0).getType(getEnvironment()).getName().contains("String"))){

						mutantArgs.add(arguments.get(0));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(1)+""));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(2)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}else if(arguments.size()==2){

						mutantArgs.add(Literal.makeLiteralByte(arguments.get(0)+""));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(1)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}
				}else if(varType.getName().contains("byte")){
					if(arguments.size()==3 && (arguments.get(0).getType(getEnvironment()).getName().contains("String"))){

						mutantArgs.add(arguments.get(0));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(1)+""));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(2)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}else if(arguments.size()==2){

						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(0)+""));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(1)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}
				}
			}else if (p.getName().equals("assertArrayEquals")){
				
				OJClass varType = arguments.get(0).getType(getEnvironment());				
				ExpressionList mutantArgs = new ExpressionList();
				
				//System.out.println(varType.getName());
				
				if(varType.getName().contains("double")){
					if(arguments.size()==3 && (arguments.get(0).getType(getEnvironment()).getName().contains("String"))){

						mutantArgs.add(arguments.get(0));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(1)+""));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(2)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}else if(arguments.size()==2){

						mutantArgs.add(Literal.makeLiteralByte(arguments.get(0)+""));
						mutantArgs.add(Literal.makeLiteralByte(arguments.get(1)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}
				}else if(varType.getName().contains("byte")){
					if(arguments.size()==3 && (arguments.get(0).getType(getEnvironment()).getName().contains("String"))){

						mutantArgs.add(arguments.get(0));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(1)+""));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(2)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}else if(arguments.size()==2){

						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(0)+""));
						mutantArgs.add(Literal.makeLiteralDouble(arguments.get(1)+""));
						MethodCall mutant = new MethodCall(p.getReferenceExpr(), p.getName(), mutantArgs);
						//System.out.println(p);
						//System.out.println(mutant);
						outputToFile(p, mutant);

					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Write MPPTO mutants to files
	 * @param original_field
	 * @param mutant
	 */
	public void outputToFile(MethodCall original_field, MethodCall mutant)
	{
		if (comp_unit == null) 
			return;

		String f_name;
		num++;
		f_name = getSourceName("MPPT");
		String mutant_dir = getMuantID("MPPT");

		try 
		{
			PrintWriter out = getPrintWriter(f_name);
			System.out.println("f_name: " + f_name);
			ICfTV_Writer writer = new ICfTV_Writer(mutant_dir, out);
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
