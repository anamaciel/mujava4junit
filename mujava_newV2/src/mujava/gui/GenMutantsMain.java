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


package mujava.gui;

import javax.swing.*;
import java.awt.event.*;
import mujava.MutationSystem;

/**
 * <p>GUI program (main interface) for generating mutants </p>
 * @author Yu-Seung Ma
 * @version 1.0
  */

public class GenMutantsMain extends JFrame 
{
   private static final long serialVersionUID = 102L;

   JTabbedPane mutantTabbedPane = new JTabbedPane();

   /** Panel for generating mutants. */
   MutantsGenPanel genPanel;
   
   /** Panel for generating oracle mutants. */
   MutantsOracleGenPanel genPanelOracle;

   /** Panel for viewing details of class mutants. */
   ClassMutantsViewerPanel cvPanel;

   /** Panel for viewing details of traditional mutants.  */
   TraditionalMutantsViewerPanel tvPanel;
   
   /** Panel for viewing details of signature mutants. */
   SignatureMutantsViewerPanel sgPanel;
   
   /** Panel for viewing details of signature mutants. */
   AnnotationMutantsViewerPanel anPanel;


   public GenMutantsMain() 
   {
      try 
      {
    	  System.out.println("gui principal");
         jbInit();
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }
   
   public GenMutantsMain(boolean oracle) 
   {
      try 
      {
    	  if(!oracle){
    		  jbInit();
    	  }else{
    		  jbInitOracle();
    		  System.out.println("oraculos");
    	  }
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }

   /** <p> Main program for generating mutants (no parameter required for run).</p>
    *  <p>- supporting functions: 
    *       (1) selection of Java source files to apply,
    *       (2) selection of mutation operators to apply </p> 
 * @throws Exception 
    */
   public static void main (String[] args) throws Exception 
   { 
	   System.out.println("The main method starts");
	      MutationSystem.setJMutationStructure();
	      MutationSystem.recordInheritanceRelation();
	      GenMutantsMain main;
	      if(args.length==1 && args[0].equals("-oracle")){
	    	  System.out.println("executar GUI de or�culo");
	    	  main = new GenMutantsMain(true);
	    	  main.pack();
	          main.setVisible(true);
	      }else{
	    	  main = new GenMutantsMain();
	    	  main.pack();
	          main.setVisible(true);
	      }  
   } 

   /** <p> Initialize GenMutantsMain </p> */
   private void jbInit() throws Exception 
   {
      genPanel = new MutantsGenPanel(this);
      cvPanel = new ClassMutantsViewerPanel();
      tvPanel = new TraditionalMutantsViewerPanel();

      mutantTabbedPane.add("Mutants Generator", genPanel);
      mutantTabbedPane.add("Traditional Mutants Viewer", tvPanel);
      mutantTabbedPane.add("Class Mutants Viewer", cvPanel);
      this.getContentPane().add(mutantTabbedPane);

      this.addWindowListener( new java.awt.event.WindowAdapter()
      {
         public void windowClosing(WindowEvent e)
         {
            this_windowClosing(e);
         }
      } );
   }
   
   /** <p> Initialize GenMutantsMainOracles </p> */
   private void jbInitOracle() throws Exception 
   {
      genPanelOracle = new MutantsOracleGenPanel(this);
      sgPanel = new SignatureMutantsViewerPanel();
      anPanel = new AnnotationMutantsViewerPanel();

      mutantTabbedPane.add("Mutants Generator", genPanelOracle);
      mutantTabbedPane.add("Signature Mutants Viewer", sgPanel);
      mutantTabbedPane.add("Annotation Mutants Viewer", anPanel);
      this.getContentPane().add(mutantTabbedPane);

      this.addWindowListener( new java.awt.event.WindowAdapter()
      {
         public void windowClosing(WindowEvent e)
         {
            this_windowClosing(e);
         }
      } );
   }

   void this_windowClosing (WindowEvent e)
   {
      System.exit(0);
   }
}
