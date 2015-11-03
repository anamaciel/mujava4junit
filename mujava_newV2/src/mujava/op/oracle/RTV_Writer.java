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

import java.io.PrintWriter;

import mujava.op.util.OracleMutantCodeWriter;
import openjava.ptree.Literal;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;

/**
 * <p>Output and log ATV oracle mutants to files </p>
 * @author Yu-Seung Ma
 * @version 1.0
  */

public class RTV_Writer extends OracleMutantCodeWriter
{

   MethodCall original;
   MethodCall mutant;
   Literal mutantBoolean;

   public RTV_Writer( String file_name, PrintWriter out ) 
   {
      super(file_name,out);
   }
   
   /**
    * Set original source code and mutated code
    * @param exp1
    * @param exp2
    */
   public void setMutant(MethodCall exp1, MethodCall exp2)
   {
	   original = exp1;
	   this.mutant = exp2;
   }
   

  
   
   /**
    * Log mutated line
    */
   public void visit( MethodCall p ) throws ParseTreeException
   {
	   
	   if(mutant != null){
	      if (isSameObject(p, original))
	      {
	         super.visit(mutant);
	         // -----------------------------------------------------------
	         mutated_line = line_num;
	         String log_str = p.toFlattenString()+ "  =>  " + mutant.toFlattenString();
	         writeLog(removeNewline(log_str));
	         // -------------------------------------------------------------
	      }
	      else
	      {
	         super.visit(p);
	      }
	   }
	   else{
		   if (isSameObject(p, original))
		      {
		         super.visit(mutantBoolean);
		         // -----------------------------------------------------------
		         mutated_line = line_num;
		         String log_str = p.toFlattenString()+ "  =>  " + mutantBoolean.toFlattenString();
		         writeLog(removeNewline(log_str));
		         // -------------------------------------------------------------
		      }
		      else
		      {
		         super.visit(p);
		      }
	   }
   }
}
