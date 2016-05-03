package runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomRunWith {
	
	static ArrayList<String> mutants = new ArrayList<>();
	
	
	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\AnaClaudia\\EXPERIMENTO\\bubblesort\\mutants\\AEC";
		File dir = new File(path);
		listar(dir);
		addMyRunner();
	}
	
	public static void addMyRunner() throws IOException{
		String text="";
		for(String mutant:mutants){
			BufferedReader buffRead = new BufferedReader(new FileReader(mutant)); 
			String linha = ""; 
			while (true) { 
				if (linha != null) { 
					System.out.println(linha);
					if(linha != ""){
						if(linha.contains("public class")){
							//text+="import static org.junit.Assert.*;
							text+="import java.io.*;";
							text+="\nimport org.junit.runner.RunWith;\n";
							text+="\nimport runner.MyRunner;\n";
							text+="\n@RunWith(MyRunner.class)\n";
							text+=linha+"\n";
						}else{
							text+=linha+"\n";
						}
					}
				} else 
					break; 
				linha = buffRead.readLine(); 
			} 
			buffRead.close();		
			
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(mutant)); 
			buffWrite.write(text);
			buffWrite.close();			
			text="";
		}
	}
	
	
	
	public static ArrayList<String> listar(File directory) {
		//System.out.println(directory.getAbsolutePath());
        if(directory.isDirectory()) {
            //System.out.println(directory.getPath());
            String[] subDirectory = directory.list();
            if(subDirectory != null) {
                for(String dir : subDirectory){
                	if(dir.contains(".java")){
                		System.out.println(directory.getPath()+ "\\" + dir );
                		mutants.add(directory.getPath()+ "\\" + dir );
                	}
                    listar(new File(directory + File.separator  + dir));                   
                }
            }
        }
        //System.out.println(mutants.size());
        return mutants;
    }

}
