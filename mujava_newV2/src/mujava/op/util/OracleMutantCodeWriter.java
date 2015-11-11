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


/**
 * <p>Description: </p>
 * @author Jeff Offutt and Yu-Seung Ma
 * @version 1.0
  */ 

package mujava.op.util;

import java.io.*;
import java.util.ArrayList;

import openjava.ptree.ParseTreeException;
import mujava.MutationSystem;
import mujava.op.oracle.util.AnnotationManager;


public class OracleMutantCodeWriter extends MutantCodeWriterOracle{
	
       String method_signature = null;

    public OracleMutantCodeWriter( PrintWriter out ) {
        super(out);
    }

    public OracleMutantCodeWriter( String mutant_dir, PrintWriter out ) {
        super(mutant_dir,out);
    }

    public void setMethodSignature(String str){
      method_signature = str;
    }

    protected void writeLog(String changed_content)
    {
      CodeChangeLog.writeLog(class_name+ MutationSystem.LOG_IDENTIFIER
	    + mutated_line+MutationSystem.LOG_IDENTIFIER
      + method_signature + MutationSystem.LOG_IDENTIFIER
      +changed_content);
    }
    
    public static void writeAnnotations(String f_name){
    	FileReader fr;
    	try {
    		fr = new FileReader(f_name);

    		BufferedReader br = new BufferedReader(fr);        


    		String arquivo="";
    		int cont = 1;
    		while (br.ready()) {
    			//lê a proxima linha
    			String linha = br.readLine();
    			if(!linha.trim().equals("")){
    				arquivo+=linha + "\n";
    			}        	 
    		}

    		FileWriter pw = new FileWriter(f_name); 

    		pw.write(arquivo);

    		pw.flush();
    		pw.close();

    		FileReader fr_annotation = new FileReader(f_name);
    		BufferedReader br_annotation = new BufferedReader(fr_annotation);  
    		String arquivoAnnotation = "";
    		boolean ann = false;
    		String ant = "";
    		//System.out.println(MutationSystem.annotations.get(0).getAnnotation() + " -- " +  MutationSystem.annotations.get(0).getLine());
    		while (br_annotation.ready()) {
    			String linha = br_annotation.readLine();
    			for (AnnotationManager annotation : MutationSystem.annotations) {
    				//System.out.println("annotation: " + annotation.getAnnotation() + " Linha: " + annotation.getLine());

    				//System.out.println(cont + "--" + annotation.getLine());

    				if(cont == annotation.getLine()){
    					ann = true;
    					ant = annotation.getAnnotation();
    				}        		 
    			}
    			if(ann){
    				if(linha.trim().equals("{") ){
    					//System.out.println(linha + "===" + ant);
    					arquivoAnnotation += linha+ "\n" + ant + "\n";
    					cont+=2;
    				}else if(linha.trim().equals("}")){
    					//System.out.println(linha + "===" + ant);
    					arquivoAnnotation += linha+ "\n" + ant + "\n";
    					cont+=2;
    				}else {
    					//System.out.println(linha + "===" + ant);
    					arquivoAnnotation += ant+ "\n" + linha + "\n";
    					cont+=2;
    				}

    			}else{
    				arquivoAnnotation +=linha+"\n";
    				cont++;
    			}
    			ann=false;
    		}

    		FileWriter pw2 = new FileWriter(f_name); 

    		pw2.write(arquivoAnnotation);

    		pw2.flush();
    		pw2.close();
    	} catch ( IOException e ) {
    		System.err.println( "fails to create " + f_name );
    	} 
    }
    
    
    
    
    
    
    
    public static void writeAnnotationsOperators(String f_name, int number, String annotationName){
    	FileReader fr;
    	try {
    		fr = new FileReader(f_name);

    		BufferedReader br = new BufferedReader(fr);        


    		String arquivo="";
    		int cont = 1;
    		while (br.ready()) {
    			//lê a proxima linha
    			String linha = br.readLine();
    			if(!linha.trim().equals("")){
    				arquivo+=linha + "\n";
    			}        	 
    		}

    		FileWriter pw = new FileWriter(f_name); 

    		pw.write(arquivo);

    		pw.flush();
    		pw.close();

    		FileReader fr_annotation = new FileReader(f_name);
    		BufferedReader br_annotation = new BufferedReader(fr_annotation);  
    		String arquivoAnnotation = "";
    		boolean ann = false;
    		String ant = "";
    		//System.out.println(MutationSystem.annotations.get(0).getAnnotation() + " -- " +  MutationSystem.annotations.get(0).getLine());
    		while (br_annotation.ready()) {
    			String linha = br_annotation.readLine();
    			for (AnnotationManager annotation : MutationSystem.annotations) {
    				//System.out.println("annotation: " + annotation.getAnnotation() + " Linha: " + annotation.getLine());

    				//System.out.println(cont + "--" + annotation.getLine());

    				if(cont == annotation.getLine()){
    					ann = true;
    					if(annotation.getNumber() == number){
    						//System.out.println(annotationName);
    						ant = annotationName;
    					}else{
    						ant = annotation.getAnnotation();
    					}
    				}        		 
    			}
    			if(ann){
    				if(linha.trim().equals("{") ){
    					//System.out.println(linha + "===" + ant);    					
    					arquivoAnnotation += linha+ "\n" + ant + "\n";
    					cont+=2;
    				}else if(linha.trim().equals("}")){
    					//System.out.println(linha + "===" + ant);
    					arquivoAnnotation += linha+ "\n" + ant + "\n";
    					cont+=2;
    				}else {
    					//System.out.println(linha + "===" + ant);
    					arquivoAnnotation += ant+ "\n" + linha + "\n";
    					cont+=2;
    				}
    				
    			}else{
    				arquivoAnnotation +=linha+"\n";
    				cont++;
    			}
    			ann=false;
    		}

    		FileWriter pw2 = new FileWriter(f_name); 

    		pw2.write(arquivoAnnotation);

    		pw2.flush();
    		pw2.close();
    	} catch ( IOException e ) {
    		System.err.println( "fails to create " + f_name );
    	} 
    }
    
    
}
