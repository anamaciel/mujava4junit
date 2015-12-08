package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import calculator.Calculator;


public class CalculatorTest 
{
	
	Calculator c1 = new Calculator();
	
	//ASM, ATV, MPPT, MPPTO
	@Test
	public void testAdd()
	{
		assertEquals(c1.Sum(32, 25), 57);
	}
	
	//ASM, ATV, MPPT, MPPTO
	@Test
	public void testAdd2()
	{
		assertEquals(c1.Sum(32, 25), 55, 2);
	}
	
	
	@Test
	public void testAdd3()
	{
		assertEquals(c1.Sum(32, 25), 57, 2);
	}
	
	@Test
	public void testAdd4()
	{
		assertEquals("Testing Calculator ADD", c1.Sum(32, 25), 57);
	}
	
	@Test
	public void testSub()
	{
		assertEquals("Testing Calculator ADD", c1.Subtract(10, 1), 9);
	}
	
	@Test
	public void testSub2()
	{
		assertEquals(c1.Subtract(10, 1), 9);
	}
	
	@Test
	public void testSub3()
	{
		assertEquals(c1.Subtract(45,2), 43);
	}
	
	@Test
	public void testSub4()
	{
		assertEquals(c1.Subtract(45,2), 40, 3);
	}
	
	@Test
	public void testSub5()
	{
		assertEquals("Testing Calculator ADD", c1.Subtract(45,2), 40, 3);
	}
	
	@Test
	public void testDiv()
	{
		assertEquals(c1.Divide(40,2), 20);
	}

	/*@Test(expected = ArithmeticException.class)
	public void testDiv2(){
		assertEquals(c1.Divide(20,0), 20);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDiv3(){
		assertEquals(c1.Divide(20,1), 20);
	}*/
	
	@Test
	public void testMult()
	{
		assertEquals(c1.Multiply(20,1), 20);
	}
	
	@Test
	public void testMult2()
	{
		assertEquals(c1.Multiply(20,0), 0);
	}
}
