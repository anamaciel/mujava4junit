package classLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class JavaClassLoader extends ClassLoader{

	public ArrayList<Class> loadClass() {
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
