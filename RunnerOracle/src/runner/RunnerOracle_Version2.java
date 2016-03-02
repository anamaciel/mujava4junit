package runner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.runner.JUnitCore;

public class RunnerOracle_Version2 {
	
	static Object objOracle;
	
	public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String clazzpath;
		clazzpath = Util.getClazzPath("quicksort.QuickSort");
		objOracle = Util.loadClazz(clazzpath);
		System.out.println(objOracle.getClass());
		URL[] urlArray={new File(System.getProperty("user.home")).toURL()};  
		URLClassLoader cl=new URLClassLoader(urlArray);  
		cl.loadClass("tests.TestingQuickSort_TEST").newInstance();
		Class c =  cl.loadClass("tests.TestingQuickSort_TEST");
		

		//JUnitCore.runClasses(c);

	}
	
	
	public static Object executeOracle(String clazzpath){
		clazzpath = Util.getClazzPath(clazzpath);
		return Util.loadClazz(clazzpath);		
	}
}
