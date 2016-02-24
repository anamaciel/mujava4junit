package classLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		try {
            ArrayList<Class> test = test();
            Object invoke = test.get(0).newInstance();
            System.out.println(test.get(0).getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	
	
	
	
	public static ArrayList<Class> test() {
        ArrayList<Class> classList = new ArrayList<Class>();
        File dir = new File("E:/workspace/CheckPalindrome/bin");
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
                        	System.out.println(ff.getName());
                            classes.add((f != dir ? f.getName() + "." : "") + ff.getName().substring(0, ff.getName().length() - 6));
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
}
