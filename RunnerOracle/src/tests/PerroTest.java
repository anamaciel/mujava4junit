package tests;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import runner.MyRunner;

@RunWith (MyRunner.class)
public class PerroTest {
	
	private String nombre;  
	  public PerroTest(String nombre) {
	     this.nombre = nombre;
	  }
	 
	  @Parameters
	  public static Collection<Object[]> data() {
	    Object[][] data = new Object[][] { {"Fido" }, {"Dido"}, {"Rudolf" } };
	    return Arrays.asList(data);
	  }
	  
	  @Test
	  public void testNombrePerros() {
	    System.out.println("Nombre del perro: " + nombre);
	  } 

}
