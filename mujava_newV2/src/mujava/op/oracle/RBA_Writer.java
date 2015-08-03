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

import mujava.op.util.TraditionalMutantCodeWriter;
import openjava.ptree.*;

import java.io.*;

/**
 * <p>Output and log RBA oracle mutants to files </p>
 * @author Yu-Seung Ma
 * @version 1.0
  */

public class RBA_Writer extends TraditionalMutantCodeWriter
{
   Variable original_var;
   FieldAccess original_field;
   String mutant;
   MethodCall original;
   MethodCall mutant_;

   public RBA_Writer( String file_name, PrintWriter out ) 
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
	   this.mutant_ = exp2;
   }
   

   /**
    * Set original source code and mutated code
    * @param exp1
    * @param mutant
    */
   public void setMutant(Variable exp1, String mutant)
   {
      original_var = exp1; 
      this.mutant = mutant;
   }

   /**
    * Set original source code and mutated code
    * @param exp1
    * @param mutant
    */
   public void setMutant(FieldAccess exp1, String mutant)
   {
      original_field = exp1;
      this.mutant = mutant;
   }
   
    /**
    * Log mutated line
    */
   public void visit( Variable p ) throws ParseTreeException
   {
      if (isSameObject(p, original_var))
      {
         out.print(mutant);
         // -----------------------------------------------------------
         mutated_line = line_num;
         String log_str = p.toString() + " => " + mutant;
         writeLog(removeNewline(log_str));
         // -------------------------------------------------------------
      }
      else
      {
         super.visit(p);
      }
   }

   /**
    * Log mutated line
    */
   public void visit( FieldAccess p ) throws ParseTreeException
   {
      if (isSameObject(p, original_field))
      {
         out.print(mutant);
         // -----------------------------------------------------------
         mutated_line = line_num;
         String log_str = p.toString() + " => " + mutant;
         writeLog(removeNewline(log_str));
         // -------------------------------------------------------------
      }
      else
      {
         super.visit(p);
      }
   }
}
