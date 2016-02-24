package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import runner.MyRunner;
import calculator.Calculator;

@RunWith(MyRunner.class)
public class TestingCalculator 
{
	private Calculator c;
	@Test
	public void testAdd()
	{
		c = new Calculator();
		assertEquals(c.Sum(2,2), 4.0);
	}
	
	@Test
	public void testAdd2()
	{
		c = new Calculator();
		assertEquals(c.Sum(2,2), 5.0,1);
	}

}
