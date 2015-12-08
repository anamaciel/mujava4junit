package mutants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import calculator.Calculator;
import runner.MyRunner;

@RunWith(MyRunner.class)
public class TestingCalculator_ASM
{
	private Calculator c;
	@Test
    public  void testAdd()
    {
        c = new calculator.Calculator();
        assertEquals( "Mutant ASM", c.Sum( 2, 2 ), 4.0 );
    }
	@Test
    public  void testAdd2()
    {
        c = new calculator.Calculator();
        org.junit.Assert.assertEquals( c.Sum( 2, 2 ), 5.0, 1 );
    }
}
