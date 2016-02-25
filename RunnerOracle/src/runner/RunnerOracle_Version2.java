package runner;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import logs.MyFormatter;

import org.junit.runner.JUnitCore;

import classLoader.JavaClassLoader;

public class RunnerOracle_Version2 {
	
	public static ArrayList<String> mutants = new ArrayList<String>();
	public static ArrayList<String> methods = new ArrayList<String>();
	public static FileHandler filehand;
	
	public static void main(String[] args) throws SecurityException, IOException {
		
		reportOriginal(args[0]);
		
		
		//********************************************************//
		
		//ler pasta com os oráculos mutantes
		
		reportMutants(args[1]);
		
		estatisticas();
	}
	
	public static ArrayList<String> listar(File directory) {
        if(directory.isDirectory()) {
            //System.out.println(directory.getPath());
            String[] subDirectory = directory.list();
            if(subDirectory != null) {
                for(String dir : subDirectory){
                	if(dir.contains(".java")){
                		//System.out.println(dir);
                		mutants.add(directory.getPath()+ "\\" + dir );
                	}
                    listar(new File(directory + File.separator  + dir));                   
                }
            }
        }
        return mutants;
    }
	
	public static void reportOriginal(String path) throws SecurityException, IOException{
		//System.out.println("original");
		//lendo o oráculo original
		ArrayList<Class> classList = new ArrayList<Class>();

		JavaClassLoader loader = new JavaClassLoader();

		classList = loader.loadClazz(path);	

		filehand = new FileHandler("src/reports_logs/original.txt");

		JUnitCore.runClasses(classList.get(0));	

		//System.out.println(methods.size());

		Logger logger = Logger.getLogger(RunnerOracle_Version2.class.getName());
		MyFormatter formatter = new MyFormatter();
		// ConsoleHandler handler = new ConsoleHandler();
		RunnerOracle_Version2.filehand.setFormatter(formatter);

		logger.addHandler(RunnerOracle_Version2.filehand);

		logger.setUseParentHandlers(false);

		for(String method:methods){
			logger.info(method);
		}
		methods.clear();
	}
	
	public static void reportMutants(String path) throws SecurityException, IOException {
		//System.out.println("MUTANTS");
		ArrayList<Class> classList = new ArrayList<Class>();
		
		FileHandler fileha;

		JavaClassLoader loader = new JavaClassLoader();
		mutants= listar(new File(path));

		for (String mutant:mutants){
			//System.out.println("mutant: " + mutant);
			classList = loader.loadClazz(mutant);
			JUnitCore.runClasses(classList.get(0));	

			int comeco=0;
			int fim=mutant.lastIndexOf("\\");
			int aux=mutant.indexOf("\\");
			for(int i=0;i<fim;i++){
				aux=mutant.indexOf("\\",i);
				if(aux!=fim){					
					comeco=aux;
				}				
			}
			
			FileWriter arq = new FileWriter("src/reports_logs/"+ mutant.substring(comeco+1, fim) + ".txt"); 
			
			PrintWriter gravarArq = new PrintWriter(arq);			

			for(String method:methods){
				gravarArq.println(method);
			}
			
			arq.close();

			methods.clear();
		}	
		methods.clear();
	}
	
	public static ArrayList<String> lerOriginal() throws IOException{

		FileReader arq = new FileReader("src\\reports_logs\\original.txt");
		ArrayList<String> results = new ArrayList<String>();
		BufferedReader lerArq = new BufferedReader(arq); 
		String linha = lerArq.readLine(); 
		while (linha != null) { 
			//System.out.printf("%s\n", linha); 
			
			if(linha!=""){
				results.add(linha);
			}
			linha = lerArq.readLine(); // lê da segunda até a última linha  			
		}
		arq.close();
		return results;
	}
	
	public static void estatisticas() throws IOException{
		ArrayList<String> results = lerOriginal();
		ArrayList<String> mutants=new ArrayList<String>();
		File dir = new File("src\\reports_logs\\");
		int vivos=0,mortos=0;
		int asm=0,atv=0,dcftv=0,icttv=0,mppto=0,msm=0,rba=0,rna=0,rsa=0,rfm=0,rsm=0,rtv=0,aec=0,dcft=0,
				ictt=0,ria=0,rta=0;
		boolean vivo=false, morto = false;
		String[] logs = dir.list();
		for(String log: logs){
			FileReader arq;
			try {
				if(!log.contains("lck") && (!log.equals("original.txt"))){
					String op = log.substring(0, log.indexOf("_"));
					
					switch (op) {
						case "ASM": asm++;
							break;
						case "ATV": atv++;
							break;
						case "DCfTV": dcftv++;
							break;
						case "ICtTV": icttv++;
							break;
						case "MPPTO": mppto++;
							break;
						case "MSM": msm++;
							break;
						case "RBA": rba++;
							break;
						case "RNA": rna++;
							break;
						case "RSA": rsa++;
							break;
						case "RFM": rfm++;
							break;
						case "RSM": rsm++;
							break;
						case "RTV": rtv++;
							break;
						case "AEC": aec++;
							break;
						case "DCfT": dcft++;
							break;
						case "ICtT": ictt++;
							break;
						case "RIA": ria++;
							break;
						case "RTA": rta++;
							break;
					}					
					
					arq = new FileReader("src\\reports_logs\\" + log);
					BufferedReader lerArq = new BufferedReader(arq); 
					String linha = lerArq.readLine(); 
					while (linha != null) { 
						if(linha!="\n"){
							mutants.add(linha);
						}
						linha = lerArq.readLine(); // lê da segunda até a última linha } 						
					}
					if(isAlive(results, mutants)){
						vivos++;
					}else{
						mortos++;
					}
					arq.close();
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 				
		}
				
		System.out.println("\n**********SIGNATURE**********");
		
		System.out.println("ASM: " + asm + " -- ATV: " + atv );
		
		System.out.println("DCfTV: " + dcftv + " -- ICtTV: " + icttv );
		
		System.out.println("MPPTO: " + mppto + " -- MSM: " + msm );
		
		System.out.println("RBA: " + rba + " -- RNA: " + rna );
		
		System.out.println("RSA: " + rsa + " -- RFM: " + rfm );
		
		System.out.println("RSM: " + rsm + " -- RTV: " + rtv );
		
		System.out.println("\n**********ANNOTATION**********");
		
		System.out.println("AEC: " + aec + " -- DCfT: " + dcft );
		
		System.out.println("ICtT: " + ictt + " -- RIA: " + ria );
		
		System.out.println("RTA" + rta);
		
		System.out.println("Mutantes vivos:" + vivos);
		System.out.println("Mutantes mortos:" + mortos);
	}
	
	public static boolean isAlive(ArrayList<String> original, ArrayList<String> mutants){
		for(String orig:original){
			for(String mutant:mutants){
				if(orig.equals(mutant)){
					return true;
				}
			}
		}
		return false;
	}

}
