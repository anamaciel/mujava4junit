package runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mutants.TestingCalculator_ASM;
import mutants.TestingCalculator_ASM_2;
import mutants.TestingCalculator_ATV;
import mutants.TestingCalculator_DCfTV_1;
import mutants.TestingCalculator_ICfTV_1;
import mutants.TestingCalculator_MPPTO;
import mutants.TestingCalculator_RTV;

import org.junit.runner.JUnitCore;

import tests.TestingCalculator;

public class TestClass {
	
	public static String arquivo;
	
	public static ArrayList<Result> results;
	
	public static int contGeral=0, contVivos=0, contMortos=0;

	
	public static void main(String[] args) {
		results = new ArrayList<Result>();
		arquivo="";
		contGeral=7;
		System.out.println("=======================================");	
		JUnitCore.runClasses(TestingCalculator.class);
		System.out.println(arquivo);
		makeReportOriginal("src/reports/TestingCalculator.txt");
		readReportOriginal("src/reports/TestingCalculator.txt");
		initializeReport("src/reports/REPORT_TestingCalculator_2.txt","TestingCalculator");
		arquivo="";
		System.out.println("=======================================");
		JUnitCore.runClasses(TestingCalculator_ASM.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_ASM_2.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_ATV.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_DCfTV_1.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_ICfTV_1.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_MPPTO.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		JUnitCore.runClasses(TestingCalculator_RTV.class);
		makeReport(arquivo);
		System.out.println(arquivo);
		arquivo="";
		finalizeReport("src/reports/REPORT_TestingCalculator_2.txt");
	 
	}

	
	public static void makeReportOriginal(String file){
		BufferedWriter buffWrite;
		try {
			File arq = new File(file);
			buffWrite = new BufferedWriter(new FileWriter(file));
			buffWrite.write(arquivo); 
			buffWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
	
	public static void readReportOriginal(String path){
		BufferedReader buffRead;
		String method="";
		boolean result=true;
		try {
			buffRead = new BufferedReader(new FileReader(path));			
			String linha = ""; 
			while (true) { 
				if (linha != null) { 
					if(linha.contains("MethodName")){
						if(linha.contains("Failed")){
							//System.out.println("if failed");
							method=linha.substring(linha.indexOf(":")+1, linha.indexOf("Failed")).trim();
							System.out.println("original failed: " + method);
							result=false;
						}else{
							//System.out.println("linha: " + linha);
							method=linha.substring(linha.indexOf(":")+1, linha.length()-1).trim();
							System.out.println("original pass: " + method);
							result=true;
						}
						//System.out.println("Method: " + method + " result: " + result);
						results.add(new Result(method, result));
					}
				} else 
					break; 
				linha = buffRead.readLine(); 
			}
			//System.out.println(results.size());
			buffRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 						
	}
	
	public static void makeReport(String arquivo){
			
			String method="";
			boolean pass=false;
			for (Result result : results) {
				//System.out.println(arquivo);
				//System.out.println(arquivo.indexOf(result.getMethod()));
				method=arquivo.substring(arquivo.indexOf(result.getMethod()), arquivo.indexOf("\n", arquivo.indexOf(result.getMethod()))).trim();
				//System.out.println(method);
				if(method.contains("Failed")){					
					method = method.substring(0,method.indexOf("Failed")).trim();
					pass=false;
				}else{
					method = method.trim();
					pass=true;
				}
				if(result.getMethod().equals(method)){
					//System.out.println("IF EQUALS");
					System.out.println(result.getMethod() +"--"+ method);
					System.out.println(result.isResult() + "--" + pass);
					if(result.isResult()!=pass){
						contMortos++;
						System.out.println("mutante morto");
					}
				}
			}	
	}
	
	
	public static void initializeReport(String path, String oracle){
		BufferedWriter buffWrite;
		try {
			File arq = new File(path);
			buffWrite = new BufferedWriter(new FileWriter(path));
			buffWrite.append("Oráculo\t\t\t\t\t\t#Mutantes\t\t#Vivos\t\t#Mortos\n"); 
			buffWrite.append(oracle+"\t\t\t\t\t\t"+contGeral+"\t\t");
			buffWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}
	
	public static void finalizeReport(String path){
		BufferedWriter buffWrite;
		try {
			contVivos=contGeral-contMortos;
			File arq = new File(path);
			buffWrite = new BufferedWriter(new FileWriter(path,true));
			buffWrite.append(contVivos+"\t\t");
			buffWrite.append(contMortos+"\t\t");
			buffWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}
	
}
