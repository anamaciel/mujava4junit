package runner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Util {
	
	
	public static Object loadClazz(String clazzPath)  {
		Object obj=null;
		try {			
			URL[] urlArray = {new File(System.getProperty("user.home")).toURL()};
			URLClassLoader cl=new URLClassLoader(urlArray);			
			obj = cl.loadClass(clazzPath).newInstance();
		} catch (MalformedURLException e1){
			e1.printStackTrace();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public static String getClazzPath(String clazzPath){
		return clazzPath;
	}
	
	public static Object getObject(){
		return RunnerOracle_Version2.objOracle;
	}

}
