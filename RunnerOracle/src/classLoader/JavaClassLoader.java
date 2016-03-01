package classLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class JavaClassLoader extends ClassLoader{

	public ArrayList<Class> loadClazz(String path) {
		//System.out.println(path + " LOAD");
        ArrayList<Class> classList = new ArrayList<Class>();
        
        String newPath = path.replace("src", "bin");
        newPath=newPath.substring(0, newPath.lastIndexOf("bin\\")+4);
        String className = path.substring(path.lastIndexOf("\\")+1, path.length());
        className = className.replace(".java", ".class");
        
        //System.out.println(newPath);
        
        File dir = new File(newPath);
        try {
            if (dir.exists()) {
                ArrayList<File> paths = new ArrayList<File>();
                ArrayList<String> classes = new ArrayList<String>();
                for (File f : dir.listFiles()) {
                    if (f.isDirectory()) {
                        paths.add(f);
                    }
                }
                paths.add(dir);
                for (File f : paths) {
                    for (File ff : f.listFiles()) {
                        if (!ff.isDirectory() && ff.getName().endsWith(".class")) {                        	
                        	if(ff.getName().equals(className)){
                        		System.out.println("TESTE: " + ff.getAbsolutePath());
                        		//System.out.println(ff.getName());
                        		classes.add((f != dir ? f.getName() + "." : "") + ff.getName().substring(0, ff.getName().length() - 6));
                            }
                        }
                    }
                }
                ClassLoader cl = new URLClassLoader(new URL[]{dir.toURI().toURL()});
                for (String s : classes) {
                    try {
                        classList.add(cl.loadClass(s));
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return classList;
    }
	
	public ArrayList<Class> loadClazzMutants(String path) {
		//System.out.println(path + " LOAD");
        ArrayList<Class> classList = new ArrayList<Class>();
        
        String newPath = path.replace("src", "bin");
        newPath=newPath.substring(0, newPath.lastIndexOf("tests\\"));
        String className = path.substring(path.lastIndexOf("\\")+1, path.length());
        className = className.replace(".java", ".class");
        
        //System.out.println("new path: " + newPath);
        
        File dir = new File(newPath);
        try {
            if (dir.exists()) {
                ArrayList<File> paths = new ArrayList<File>();
                ArrayList<String> classes = new ArrayList<String>();
                for (File f : dir.listFiles()) {
                    if (f.isDirectory()) {
                        paths.add(f);
                    }
                }
                paths.add(dir);
                for (File f : paths) {
                    for (File ff : f.listFiles()) {
                        if (!ff.isDirectory() && ff.getName().endsWith(".class")) {                        	
                        	if(ff.getName().equals(className)){
                        		//System.out.println("TESTE: " + ff.getAbsolutePath());
                        		//System.out.println("absolute path: " + ff.getAbsolutePath());
                        		//System.out.println((f != dir ? f.getName() + "." : "") + ff.getName().substring(0, ff.getName().length() - 6));
                        		classes.add((f != dir ? f.getName() + "." : "") + ff.getName().substring(0, ff.getName().length() - 6));
                            }
                        }
                    }
                }
                ClassLoader cl = new URLClassLoader(new URL[]{dir.toURI().toURL()});
                for (String s : classes) {
                    try {
                    	//System.out.println(s);
                        classList.add(cl.loadClass(s));
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        //System.out.println(classList.size());
        return classList;
    }
}
