package tests;



import static tests.MyMaths.divide;
import static tests.MyMaths.minus;
import static tests.MyMaths.multiply;
import static tests.MyMaths.plus;

import org.junit.Assert;
import org.junit.Test;



public class MyMathTest {

	@Test
	public void testPlus() {
		int result=plus(3, 4);
		
		if(result!=7)
			Assert.fail("Test failed.");
		
	}
	
	@Test
	public void testMinus() {
		int result=minus(3, 4);
		if(result!=(3-4))
			Assert.fail("Test failed.");
	}
	
	@Test
	public void testMultiply() {
		int result=multiply(3, 4);
		
		Assert.assertEquals(3*4, result);
	}
	@Test
	public void testDivide() {
		int result=divide(3, 4);
		
		Assert.assertEquals(3/4, result);
	}
	
	@Test
	public void testDivideByZero() {
		int result=divide(2,0);
		System.out.println("Last line should crash!");
	}
	
	
	
}
