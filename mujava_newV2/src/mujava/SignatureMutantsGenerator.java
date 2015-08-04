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
import mujava.op.oracle.ATV;
import mujava.op.oracle.RBA;
import mujava.op.util.CodeChangeLog;
import mujava.util.Debug;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ParseTreeException;

/**
 * 
 *            
 * <p> Currently available signature mutation operators:    
 *          (1) ASM: Adding String Message
 *          (2) RSM: Remove String Message
 *          (3) MPPT: Modify Primitive Parameter Type
 *          (4) MPPTO: Modify Primitive Parameter Type to Object
 *          (5) ATV: Adding Threshold Value
 *          (6) RTV: Removing Threshold Value
 *          (7) ICtTV: Increment Constant to Threshold Value
 *          (8) DCfTV: Decrement Constant from Threshold Value
 *          (9) MSM: Modify String Message
 *          (10) RBA: Replace Boolean Assertion
 *          (11) RNA: Replace Null Assertion
 *          (12) RSA: Replace Same Assertion
 *          (13) RFM: Remove fail() Method

 * </p>        
 * @author Ana Maciel
 * @version 1.0
 */

public class SignatureMutantsGenerator  extends MutantsGenerator
{

	String[] signatureOp;

	public SignatureMutantsGenerator(File f) 
	{
		super(f);
		signatureOp = MutationSystem.sg_operators;
	}

	public SignatureMutantsGenerator(File f, boolean debug) 
	{
		super (f, debug);
		signatureOp = MutationSystem.sg_operators;
	}

	public SignatureMutantsGenerator(File f, String[] tOP) 
	{
		super(f);
		signatureOp = tOP;
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

		if (signatureOp != null && signatureOp.length > 0)
		{
			Debug.println("* Generating signature oracle mutants");
			MutationSystem.clearPreviousTraditionalMutants();

			MutationSystem.MUTANT_PATH = MutationSystem.SIGNATURE_MUTANT_PATH;

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
	 *      ASM, RSM, MPPT, MPPTO, ATV, RTV, ICtTV
	 *      DCfTV, MSM, RBA, RNA, RSA, RFM
	 *      
	 * @param cdecls
	 */
	void genTraditionalMutants(ClassDeclarationList cdecls)
	{

		for (int j=0; j<cdecls.size(); ++j)
		{
			ClassDeclaration cdecl = cdecls.get(j);
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
						System.out.println("SIGNATURE GENERATION");
						System.out.println("MutationSystem.MUTANT_PATH: " + MutationSystem.MUTANT_PATH);
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

					/*if (hasOperator (signatureOp, "ASM") )
					{
						Debug.println("  Applying ASM ... ... ");
						AOR_FLAG = true;
						mutant_op = new AORB(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "RSM") )
					{
						Debug.println("  Applying RSM ... ... ");
						mutant_op = new AORS(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "MPPT") ) 
					{
						Debug.println("  Applying MPPT ... ... ");
						mutant_op = new AODU(file_env, cdecl, comp_unit);
						((AODU)mutant_op).setAORflag(AOR_FLAG);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "MPPTO") )
					{
						Debug.println("  Applying MPPTO	 ... ... ");
						mutant_op = new AODS(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}*/

					if (hasOperator (signatureOp, "ATV") )
					{
						System.out.println("  Applying ATV ... ... ");
						Debug.println("  Applying ATV ... ... ");
						mutant_op = new ATV(file_env,cdecl,comp_unit);	
						comp_unit.accept(mutant_op);
					}

					/*if (hasOperator (signatureOp, "RTV") )
					{
						Debug.println("  Applying RTV ... ... ");
						mutant_op = new AOIS(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "ICtTV") )
					{
						Debug.println("  Applying ICtTV ... ... ");
						mutant_op = new ROR(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "DCfTV") )
					{
						Debug.println("  Applying DCfTV ... ... ");
						mutant_op = new COR(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "MSM") ) 
					{
						Debug.println("  Applying MSM ... ... ");
						mutant_op = new COD(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}*/

					if (hasOperator (signatureOp, "RBA") )
					{
						System.out.println("  Applying RBA ... ... ");
						Debug.println("  Applying RBA ... ... ");
						mutant_op = new RBA(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					/*if (hasOperator (signatureOp, "RNA") )
					{
						Debug.println("  Applying RNA ... ... ");
						mutant_op = new SOR(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "RSA") )
					{
						Debug.println("  Applying RSA ... ... ");
						mutant_op = new LOR(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}

					if (hasOperator (signatureOp, "RFM") )
					{
						Debug.println("  Applying RFM ... ... ");
						mutant_op = new LOI(file_env, cdecl, comp_unit);
						comp_unit.accept(mutant_op);
					}*/
					
				} catch (ParseTreeException e)
				{
					System.err.println( "Exception, during generating signature mutants for the JUnit class "
							+ MutationSystem.CLASS_NAME);
					e.printStackTrace();
				}
			}
		}
	}
}