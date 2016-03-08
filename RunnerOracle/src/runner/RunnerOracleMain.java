package runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.junit.runner.JUnitCore;

public class RunnerOracleMain {
	
	public static ArrayList<String> mutants = new ArrayList<String>();
	public static ArrayList<String> methods = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		reportOriginal(args[0], args[2]);
		
		reportMutants(args[1], args[2]);
		
		estatisticas(args[2]);
	}
	
	
	public static void reportOriginal(String path, String pathReport) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		FileWriter arq = new FileWriter(pathReport+"/original.txt");

		String clazzPath = path.substring(path.indexOf("tests"), path.length());
		clazzPath = clazzPath.replace("/", ".");
		clazzPath = clazzPath.replace(".java", "");
		
		System.out.println(clazzPath);
		
		URL[] urlArray={new File(System.getProperty("user.home")+"/EXPERIMENTO/").toURL()};  
		URLClassLoader cl=new URLClassLoader(urlArray);  
		cl.loadClass(clazzPath).newInstance(); 
		Class c =  cl.loadClass(clazzPath);

		JUnitCore.runClasses(c);

		PrintWriter gravarArq = new PrintWriter(arq);			

		for(String method:methods){
			gravarArq.println(method);
		}		
		arq.close();		
	}
	
	public static void reportMutants(String path, String pathReport) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		mutants= listar(new File(path));
		//System.out.println(mutants.size());
		for (String mutant:mutants){
			methods.clear();
			System.out.println(mutant);
			String clazzPath = mutant.substring(mutant.indexOf("tests"), mutant.length());			
			clazzPath = clazzPath.replace(".java", "");
			clazzPath = clazzPath.replace("\\", ".");
			
			System.out.println(clazzPath);
			
			String pathJunit=mutant.substring(mutant.indexOf("EXPERIMENTO"), mutant.lastIndexOf("tests"));
			System.out.println(pathJunit);
			
			URL[] urlArray={new File(System.getProperty("user.home")+"/" + pathJunit).toURL()};  
			URLClassLoader cl=new URLClassLoader(urlArray);  
			cl.loadClass(clazzPath).newInstance(); 
			Class c =  cl.loadClass(clazzPath);			

			JUnitCore.runClasses(c);
			
			//System.out.println(mutant);
			System.out.println("teste: " + mutant.substring(mutant.indexOf("()\\")+3, mutant.lastIndexOf("\\tests")));
			
			FileWriter arq = new FileWriter(pathReport+"/"+ mutant.substring(mutant.indexOf("()\\")+3, mutant.lastIndexOf("\\tests")) + ".txt");
			
			
			
			PrintWriter gravarArq = new PrintWriter(arq);	
			System.out.println(methods.size());
			
			for(String method:methods){
				//System.out.println("metodo: " + method);
				gravarArq.println(method);
			}
			
			arq.close();

			methods.clear();
			System.out.println("===========================================");
		}
	}
	
	
	public static ArrayList<String> listar(File directory) {
		//System.out.println(directory.getAbsolutePath());
        if(directory.isDirectory()) {
            //System.out.println(directory.getPath());
            String[] subDirectory = directory.list();
            if(subDirectory != null) {
                for(String dir : subDirectory){
                	if(dir.contains(".java")&&directory.getPath().contains("tests")){
                		//System.out.println(dir);
                		mutants.add(directory.getPath()+ "\\" + dir );
                	}
                    listar(new File(directory + File.separator  + dir));                   
                }
            }
        }
        //System.out.println(mutants.size());
        return mutants;
    }
	
	
	public static void estatisticas(String path) throws IOException{
		ArrayList<String> results = lerOriginal(path);
		ArrayList<String> mutants=new ArrayList<String>();
		File dir = new File(path);
		int vivos=0,mortos=0;
		int asm=0,atv=0,dcftv=0,icttv=0,mppto=0,msm=0,rba=0,rna=0,rsa=0,rfm=0,rsm=0,rtv=0,aec=0,dcft=0,
				ictt=0,ria=0,rta=0;
		int asmD=0,atvD=0,dcftvD=0,icttvD=0,mpptoD=0,msmD=0,rbaD=0,rnaD=0,rsaD=0,rfmD=0,rsmD=0,rtvD=0,aecD=0,dcftD=0,
				icttD=0,riaD=0,rtaD=0;
		boolean vivo=false;
		String[] logs = dir.list();
		for(String log: logs){
			//System.out.println(log);
			FileReader arq;
			try {
				if(!log.equals("original.txt")){
					String op = log.substring(0, log.indexOf("_"));
					
					arq = new FileReader(path +"/" + log);
					BufferedReader lerArq = new BufferedReader(arq); 
					String linha = lerArq.readLine(); 
					while (linha != null) { 
						linha = lerArq.readLine();  
						mutants.add(linha);
					}
					//System.out.println(mutants.size());
					if(isAlive(results, mutants)){
						vivos++;
						vivo=true;
					}else{
						mortos++;
					}
					mutants.clear();
					arq.close();
					
					switch (op) {
					case "ASM": asm++;
					if(!vivo)
						asmD++;							
					break;
					case "ATV": atv++;
					if(!vivo)
						atvD++;
					break;
					case "DCfTV": dcftv++;
					if(!vivo)
						dcftvD++;
					break;
					case "ICtTV": icttv++;
					if(!vivo)
						icttvD++;
					break;
					case "MPPTO": mppto++;
					if(!vivo)
						mpptoD++;
					break;
					case "MSM": msm++;
					if(!vivo)
						msmD++;
					break;
					case "RBA": rba++;
					if(!vivo)
						rbaD++;
					break;
					case "RNA": rna++;
					if(!vivo)
						rnaD++;
					break;
					case "RSA": rsa++;
					if(!vivo)
						rsaD++;
					break;
					case "RFM": rfm++;
					if(!vivo)
						rfmD++;
					break;
					case "RSM": rsm++;
					if(!vivo)
						rsmD++;
					break;
					case "RTV": rtv++;
					if(!vivo)
						rtvD++;
					break;
					case "AEC": aec++;
					if(!vivo)
						aecD++;
					break;
					case "DCfT": dcft++;
					if(!vivo)
						dcftD++;
					break;
					case "ICtT": ictt++;
					if(!vivo)
						icttD++;
					break;
					case "RIA": ria++;
					if(!vivo)
						riaD++;
					break;
					case "RTA": rta++;
					if(!vivo)
						rtaD++;
					break;
					}					


					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			vivo = false;
		}
				
		System.out.println("\n**********SIGNATURE**********");
		
		System.out.println("ASM: " + asm +"/"+ asmD + " -- ATV: " + atv +"/"+ atvD);
		
		System.out.println("DCfTV: " + dcftv +"/"+ dcftvD + " -- ICtTV: " + icttv +"/"+ icttvD);
		
		System.out.println("MPPTO: " + mppto +"/"+ mpptoD+ " -- MSM: " + msm +"/"+ msmD);
		
		System.out.println("RBA: " + rba +"/"+ rbaD+ " -- RNA: " + rna +"/"+ rnaD);
		
		System.out.println("RSA: " + rsa +"/"+ rsaD+ " -- RFM: " + rfm +"/"+ rfmD);
		
		System.out.println("RSM: " + rsm +"/"+ rsmD+ " -- RTV: " + rtv +"/"+ rtvD);
		
		System.out.println("\n**********ANNOTATION**********");
		
		System.out.println("AEC: " + aec + " -- DCfT: " + dcft );
		
		System.out.println("ICtT: " + ictt + " -- RIA: " + ria );
		
		System.out.println("RTA: " + rta);
		
		System.out.println("\n******************************");
		
		System.out.println("Mutantes vivos:" + vivos);
		System.out.println("Mutantes mortos:" + mortos);
	}
	
	public static boolean isAlive(ArrayList<String> original, ArrayList<String> mutants){
		//System.out.println("is alive");
		boolean alive = true;
		if(original.size()!=mutants.size()){
			alive=false;
		}else{
			alive=true;
		}
		return alive;
	}
	
	
	public static ArrayList<String> lerOriginal(String path) throws IOException{

		FileReader arq = new FileReader(path + "/original.txt");
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
}
