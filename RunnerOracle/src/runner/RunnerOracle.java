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
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import logs.MyFormatter;

import org.junit.runner.JUnitCore;

import classLoader.JavaClassLoader;

public class RunnerOracle {
	
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
		System.out.println("original");
		//lendo o oráculo original
		ArrayList<Class> classList = new ArrayList<Class>();

		JavaClassLoader loader = new JavaClassLoader();

		classList = loader.loadClazz(path);	

		filehand = new FileHandler("src/reports_logs/original.txt");

		JUnitCore.runClasses(classList.get(0));	

		System.out.println(methods.size());

		Logger logger = Logger.getLogger(RunnerOracle.class.getName());
		MyFormatter formatter = new MyFormatter();
		// ConsoleHandler handler = new ConsoleHandler();
		RunnerOracle.filehand.setFormatter(formatter);

		logger.addHandler(RunnerOracle.filehand);

		logger.setUseParentHandlers(false);

		for(String method:methods){
			logger.info(method);
		}
		methods.clear();
	}
	
	public static void reportMutants(String path) throws SecurityException, IOException {
		System.out.println("MUTANTS");
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
			System.out.printf("%s\n", linha); 
			linha = lerArq.readLine(); // lê da segunda até a última linha  
			if(linha!=""){
				results.add(linha);
			}
			arq.close();
		}
		return results;
	}
	
	public static void estatisticas() throws IOException{
		ArrayList<String> results = lerOriginal();
		File dir = new File("src\\reports_logs\\");
		int vivos=0,mortos=0;
		boolean vivo=false, morto = false;
		String[] logs = dir.list();
		System.out.println("dir: " + dir.getAbsolutePath());
		for(String log: logs){
			FileReader arq;
			try {
				if(!log.contains("lck") || log.equals("original.txt")){
					arq = new FileReader("src\\reports_logs\\" + log);
					BufferedReader lerArq = new BufferedReader(arq); 
					String linha = lerArq.readLine(); 
					while (linha != null) { 
						/*for (String result:results){
							if(result.equals(linha)){
								vivo=true;
							}
						}*/
						if(results.contains(linha)){
							vivo=true;
						}else{
							morto=true;
						}
						System.out.printf("%s\n", linha); 
						linha = lerArq.readLine(); // lê da segunda até a última linha } 						
					}
					arq.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
		}		
	}

}
