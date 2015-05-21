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

import openjava.mop.*;
import openjava.ptree.*;

import java.io.*;
import mujava.op.*;
import mujava.op.basic.*;
import mujava.op.util.*;
import mujava.util.Debug;
 /**
 * <p>Description: Generate all mutants </p>
 * @author Yu-Seung Ma
 * @version 1.0
  */ 

public class AllMutantsGenerator extends MutantsGenerator
{
   boolean existIHD = false;

   String[] classOp;
   String[] traditionalOp;

   public AllMutantsGenerator(File f) 
   {
      super(f);
      classOp = MutationSystem.cm_operators;
      traditionalOp = MutationSystem.tm_operators;
   }
   
   public AllMutantsGenerator(File f, boolean debug) 
   {
      super(f, debug); 
      classOp = MutationSystem.cm_operators;
      traditionalOp = MutationSystem.tm_operators;
   }

   public AllMutantsGenerator(File f, String[] cOP, String[] tOP) 
   {
      super(f);
      classOp = cOP;
      traditionalOp = tOP;
   }

   void genMutants()
   {
      if (comp_unit == null)
      {
         System.err.println(original_file + " is skipped.");
      }
      ClassDeclarationList cdecls = comp_unit.getClassDeclarations();
      if (cdecls == null || cdecls.size() == 0) 
         return;

      if (traditionalOp != null && traditionalOp.length > 0)
      {
         Debug.println("* Generating traditional mutants"); 
         MutationSystem.clearPreviousTraditionalMutants();
         MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH;
         CodeChangeLog.openLogFile();
         genTraditionalMutants(cdecls);
         CodeChangeLog.closeLogFile();
      }

      if (classOp != null && classOp.length > 0)
      {
	     Debug.println("* Generating class mutants");
         MutationSystem.clearPreviousClassMutants();
         MutationSystem.MUTANT_PATH = MutationSystem.CLASS_MUTANT_PATH;
         CodeChangeLog.openLogFile();
         genClassMutants(cdecls);
         CodeChangeLog.closeLogFile();
      }
   }


   void genClassMutants(ClassDeclarationList cdecls)
   {
      genClassMutants1(cdecls); 
      genClassMutants2(cdecls);
   }

   void genClassMutants2(ClassDeclarationList cdecls)
   {
      for (int j=0; j<cdecls.size(); ++j)
      {
    	 ClassDeclaration cdecl = cdecls.get(j);
         if (cdecl.getName().equals(MutationSystem.CLASS_NAME))
         {
    	    DeclAnalyzer mutant_op;

            
         }
      }
   }


   void genClassMutants1(ClassDeclarationList cdecls)
   {
      for (int j=0; j<cdecls.size(); ++j)
      {
         ClassDeclaration cdecl = cdecls.get(j);
         if (cdecl.getName().equals(MutationSystem.CLASS_NAME))
         {
            String qname = file_env.toQualifiedName(cdecl.getName());
            mujava.op.util.Mutator mutant_op;

              

               if (hasOperator(classOp, "IOR"))
               {
                  Debug.println("  Applying IOR ... ... ");
                  try
                  {
                     Class parent_class = Class.forName(qname).getSuperclass();
                     if (!(parent_class.getName().equals("java.lang.Object")))
                     {
                        String temp_str = parent_class.getName();
                        String result_str = "";
                        for (int k=0; k<temp_str.length(); k++)
                        {
                           char c = temp_str.charAt(k);
                           if (c == '.')
                           {
                              result_str = result_str + "/";
                           }
                           else
                           {
                              result_str = result_str + c;
                           }
                        }

                        File f = new File(MutationSystem.SRC_PATH, result_str + ".java");
                        if (f.exists())
                        {
                           CompilationUnit[] parent_comp_unit = new CompilationUnit[1];
                           FileEnvironment[] parent_file_env = new FileEnvironment[1];
                           this.generateParseTree(f, parent_comp_unit, parent_file_env);
                           this.initParseTree(parent_comp_unit, parent_file_env);
                           //mutant_op = new IOR(file_env, cdecl, comp_unit);
                           //((IOR)mutant_op).setParentEnv(parent_file_env[0], parent_comp_unit[0]);
                           //comp_unit.accept(mutant_op);
                        }
                     }
                  } catch (ClassNotFoundException e)
                  {
                     System.out.println(" Exception at generating IOR mutant. File : AllMutantsGenerator.java ");
                  } catch (NullPointerException e1)
                  {
                     System.out.print(" IOP  ^^; ");
                  }    
               }
         }
      }
   }

   /**
    * Compile mutants into bytecode
    */
   public void compileMutants()
   {
      if (traditionalOp != null && traditionalOp.length > 0)
      {
	     Debug.println("* Compiling traditional mutants into bytecode");
         MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH;
         super.compileMutants(); 
      }

      if (classOp != null && classOp.length > 0)
      {
	     Debug.println("* Compiling class mutants into bytecode");
         MutationSystem.MUTANT_PATH = MutationSystem.CLASS_MUTANT_PATH;
         super.compileMutants();
      }
   }

   void genTraditionalMutants(ClassDeclarationList cdecls)
   {
      for(int j=0; j<cdecls.size(); ++j)
      {
         ClassDeclaration cdecl = cdecls.get(j);

         if (cdecl.getName().equals(MutationSystem.CLASS_NAME))
         {
        	 
        	 //TINHA OPERADORES AQUI
            /*try
            {
               mujava.op.util.Mutator mutant_op;
               boolean AOR_FLAG = false;

              
            } catch (ParseTreeException e)
            {
               System.err.println( "Exception, during generating traditional mutants for the class "
                              + MutationSystem.CLASS_NAME);
               e.printStackTrace();
            }*/
         } 
      }
   }
}