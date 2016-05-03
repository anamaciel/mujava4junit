package runnerMutants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RunnerMutants {
	
	public static ArrayList<String> arqs = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		String op = "ODL_31";
		String folder = "bubblesort";
		String dir = "C:\\Users\\AnaClaudia\\EXPERIMENTO\\"+folder+"\\mutants";
		String mutClass = "C:\\Users\\AnaClaudia\\Documents\\MuJava\\result\\bubblesort.BubbleSort\\traditional_mutants\\"
				+ "\"double_firstNumberDouble(double)\"\\"+op+"\\BubbleSort.class ";
		String mutJava = "C:\\Users\\AnaClaudia\\Documents\\MuJava\\result\\bubblesort.BubbleSort\\traditional_mutants"
				+ "\\\"double_firstNumberDouble(double)\"\\"+op+"\\BubbleSort.java ";
		
		File mutants = new File(dir);
		
		listar(mutants, folder);
		
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:\\Users\\AnaClaudia\\EXPERIMENTO\\copyFILES.bat"));
		buffWrite.write("copy " + mutClass + "C:\\Users\\AnaClaudia\\EXPERIMENTO\\" + folder+"\n");
		buffWrite.write("copy " + mutJava + "C:\\Users\\AnaClaudia\\EXPERIMENTO\\" + folder+"\n");
		for (String arq:arqs){			
			buffWrite.write("copy " + mutClass + arq + "\n");
			buffWrite.write("copy " + mutJava + arq + "\n");	
		}
		buffWrite.close();
		
		
	}

	
	public static ArrayList<String> listar(File directory, String folder) {
		//System.out.println(directory.getAbsolutePath());
		int first = directory.getPath().indexOf(folder);
		int last = directory.getPath().lastIndexOf(folder);
		//System.out.println(first + "-" + last);
        if(directory.isDirectory()) {
            //System.out.println(directory.getPath());
            String[] subDirectory = directory.list();
                for(String dir : subDirectory){
                	if(first!=last){
                		//System.out.println("if");
                		System.out.println(directory.getPath()+ "\\" + dir );
                		arqs.add(directory.getPath()+ "\\");
                	}
                    listar(new File(directory + File.separator  + dir),folder);                   
                }
            
        }
        //System.out.println(mutants.size());
        return arqs;
    }
}
