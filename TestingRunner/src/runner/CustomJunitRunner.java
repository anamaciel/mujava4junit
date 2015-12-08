package runner;

import mutants.*;
import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;


public class CustomJunitRunner {
	 /**
	   * Listener which overrides default functionality
	   * to print class and method to system out.
	   */
	  static class ConsoleJunitListener extends TextListener {

	    public ConsoleJunitListener(JUnitSystem system) {
	     super(system);
	    
	    }

	    @Override
	    public void testStarted(final Description description) {
	       System.out.format("Run: %s testing now -> %s \n",
	           description.getClassName(),
	          description.getMethodName());
	    }
	  }

	  public static void main(String[] args){
	    JUnitCore runner = new JUnitCore();
	    final JUnitSystem system = new RealSystem();
	    runner.addListener(new ConsoleJunitListener(system));
	    TestingCalculator_ASM t1 = new TestingCalculator_ASM();
	    runner.run(t1.getClass());
	   // runner.runClasses(mutants.TestingCalculator_ASM.class);
	    /*try {
	      List<Class<?>> classes = new ArrayList<>();
	      for (String arg : args) {
	        classes.add(Class.forName(arg));
	      }
	      runner.run(classes.toArray(new Class[1]));

	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }*/
	  }
	}