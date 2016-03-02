package loader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.runner.JUnitCore;

public class Teste {
	
	public static void main(String[] args) throws Exception { 
		
		URL[] urlArray={new File(System.getProperty("user.home")).toURL()};  
	      URLClassLoader cl=new URLClassLoader(urlArray);  
	      cl.loadClass("tests.TestingQuickSort").newInstance(); 
	      Class c =  cl.loadClass("tests.TestingQuickSort");
	      JUnitCore.runClasses(c);
	      System.out.println("****************************************");
	      
	     // cl.loadClass("mutants.ASM_1.tests.TestingQuickSort").newInstance(); 
	      Class c2 =  cl.loadClass("mutants.ASM_1.tests.TestingQuickSort");	      
	      JUnitCore.runClasses(c2);
	      System.out.println("****************************************");
	      
	      Class c5 =  cl.loadClass("mutants.RNA_1.tests.TestingQuickSort");
	      JUnitCore.runClasses(c5);
	      
	      

		
	} 

}
