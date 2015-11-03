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

package mujava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import mujava.op.basic.CreateDirForEachMethod;
import mujava.op.oracle.RIA;
import mujava.op.util.CodeChangeLog;
import mujava.util.Debug;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ParseTreeException;

/**
 * 
 *            
 * <p> Currently available signature mutation operators:    
 *          (1) MEC: Modify Expected Class
 *          (2) REC: Removing Expected Class
 *          (3) AEC: Adding Expected Class
 *          (4) ACtT: Increment Constant to Timeout
 *          (5) DCfT: Decrement Constant from Timeout
 *          (6) RTA: Removing Timeout
 *          (7) RIA: Remove Ignore Annotation
 *          
 * </p>        
 * @author Ana Maciel
 * @version 1.0
 */

public class AnnotationMutantsGenerator  extends MutantsGenerator
{

	String[] annotationOp;

	public AnnotationMutantsGenerator(File f) 
	{
		super(f);
		annotationOp = MutationSystem.an_operators;
	}

	public AnnotationMutantsGenerator(File f, boolean debug) 
	{
		super (f, debug);
		annotationOp = MutationSystem.an_operators;
	}

	public AnnotationMutantsGenerator(File f, String[] tOP) 
	{
		super(f);
		annotationOp = tOP;
	}

	/** 
	 * Verify if the target Java source and class files exist, 
	 * generate signature mutants
	 */
	void genMutants()
	{
		if (comp_unit == null)
		{
			System.err.println (original_file + " is skipped.");
		}

		ClassDeclarationList cdecls = comp_unit.getClassDeclarations();

		if (cdecls == null || cdecls.size() == 0) 
			return;

		if (annotationOp != null && annotationOp.length > 0)
		{
			Debug.println("* Generating annotation oracle mutants");
			MutationSystem.clearPreviousAnnotationMutants();

			MutationSystem.MUTANT_PATH = MutationSystem.ANNOTATION_MUTANT_PATH;

			CodeChangeLog.openLogFile();

			genTraditionalMutants(cdecls);

			CodeChangeLog.closeLogFile();
		}
	}

	/**
	 * Compile signature oracle mutants into bytecode 
	 * POR ENQUANTO NÃO COMPILA
	 */
	/*public void compileMutants()
	{
		if (traditionalOp != null && traditionalOp.length > 0)
		{
			try
			{
				Debug.println("* Compiling traditional mutants into bytecode");
				String original_tm_path = MutationSystem.TRADITIONAL_MUTANT_PATH;
				File f = new File(original_tm_path, "method_list");
				FileReader r = new FileReader(f);
				BufferedReader reader = new BufferedReader(r);
				String str = reader.readLine();

				while (str != null)
				{
					MutationSystem.MUTANT_PATH = original_tm_path + "/" + str;
					super.compileMutants();
					str = reader.readLine();
				}
				reader.close();
				MutationSystem.MUTANT_PATH = original_tm_path;
			} catch (Exception e)
			{
				e.printStackTrace();
				System.err.println("Error at compileMutants() in TraditionalMutantsGenerator.java");
			}
		}
	}*/

	/**
	 * Apply selected signature oracle mutation operators: 
	 *      MEC, REC, AEC, ACtV, DCtV, RTA, RIA 
	 *      
	 * @param cdecls
	 */
	void genTraditionalMutants(ClassDeclarationList cdecls)
	{

		for (int j=0; j<cdecls.size(); ++j)
		{
			ClassDeclaration cdecl = cdecls.get(j);
			//System.out.println(cdecl.getBody());
			//take care of the case for generics
			String tempName = cdecl.getName();
			//System.out.println("tempName: " + tempName);
			if(tempName.indexOf("<") != -1 && tempName.indexOf(">")!= -1)
				tempName = tempName.substring(0, tempName.indexOf("<")) + tempName.substring(tempName.lastIndexOf(">") + 1, tempName.length());
			//System.out.println("tempName: " + tempName);
			if (tempName.equals(MutationSystem.CLASS_NAME))
			{
				try
				{
					mujava.op.util.Mutator mutant_op;

					try
					{
						//generate a list of methods from the original java class
						System.out.println("ANNOTATION GENERATION");
						//System.out.println("MutationSystem.MUTANT_PATH: " + MutationSystem.MUTANT_PATH);
						File f = new File(MutationSystem.MUTANT_PATH, "method_list");
						FileOutputStream fout = new FileOutputStream(f);
						PrintWriter out = new PrintWriter(fout);

						mutant_op = new CreateDirForEachMethod(file_env, cdecl, comp_unit, out);

						comp_unit.accept(mutant_op);
						out.flush();  
						out.close();
					} catch (Exception e)
					{
						System.err.println("Error in writing method list");
						return;
					}

					if (hasOperator (annotationOp, "RIA") )
					{
						Debug.println("  Applying RIA ... ... ");
						mutant_op = new RIA(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					/*if (hasOperator (annotationOp, "RSM") )
					{
						Debug.println("  Applying RSM ... ... ");
						mutant_op = new RSM(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (annotationOp, "MPPT") ) 
					{
						Debug.println("  Applying MPPT ... ... ");
						mutant_op = new MPPT(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (annotationOp, "MPPTO") )
					{
						Debug.println("  Applying MPPTO	 ... ... ");
						mutant_op = new MPPTO(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (annotationOp, "ATV") )
					{
						//System.out.println("  Applying ATV ... ... ");
						Debug.println("  Applying ATV ... ... ");
						mutant_op = new ATV(file_env,cdecl,comp_unit);	
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (annotationOp, "RTV") )
					{
						Debug.println("  Applying RTV ... ... ");
						mutant_op = new RTV(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}
					
					if (hasOperator (annotationOp, "ICtTV") )
					{
						Debug.println("  Applying ICtTV ... ... ");
						mutant_op = new ICfTV(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}*/
				
					
				} catch (ParseTreeException e)
				{
					System.err.println( "Exception, during generating annotation mutants for the JUnit class "
							+ MutationSystem.CLASS_NAME);
					e.printStackTrace();
				}
			}
		}
	}
}