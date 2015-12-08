package mutants;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import runner.MyRunner;
import calculator.Calculator;
@RunWith(MyRunner.class)
public class TestingCalculator_MPPTO
{
    private calculator.Calculator c;
	@Test
    public  void testAdd()
    {
        c = new calculator.Calculator();
        assertEquals( (Object)c.Sum( 2, 2 ), (Object)4.0 );
    }
	@Test
    public  void testAdd2()
    {
        c = new calculator.Calculator();
        org.junit.Assert.assertEquals( c.Sum( 2, 2 ), 5.0, 1 );
    }
}
