package runner;

import java.util.ArrayList;

import org.junit.runner.JUnitCore;

import classLoader.JavaClassLoader;

public class RunnerOracle {
	
	public static void main(String[] args) {
		
		System.out.println("Argumentos: " + args.length);
		
		ArrayList<Class> classList = new ArrayList<Class>();
		
		JavaClassLoader loader = new JavaClassLoader();
		
		Class originalOracle=null;
		
		classList = loader.loadClass();
		
		JUnitCore.runClasses(classList.get(1));		
	}

}
