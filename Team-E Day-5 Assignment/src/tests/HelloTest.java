package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloTest {

	@Test
	public void test1() {
		//our test logic here
		System.out.println("I am Test 1");
	}
	
	
	public void test2() {
		//our test logic here
		System.out.println("I am Test 2");
	}
	
	@Test
	public void notATest() {
		//our test logic here
		System.out.println("I am NOT a Test");
	}

}
