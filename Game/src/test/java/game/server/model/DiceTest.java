package game.server.model;

import static org.junit.Assert.*;

import game.server.model.Dice;

import org.junit.Test;

/**
 * Dice test class 
 * 
 * @author Dario
 *
 */
public class DiceTest {
	
	//CLASS VARS
	private Dice tester = new Dice();

	/**
	 * Check the current object identifier
	 */
	@Test
	public void getThisObjectTypeTest(){
		tester = new Dice();
		assertEquals(tester.getThisObjectType(), "Dice");
	}
	
	/**
	 * MAIN RANDOM METHOD
	 * RETURN A NUMBER FROM 1 AND 6
	 * LIKE A REAL DICE
	 * 
	 * @return
	 */
	@Test
	public void getNewRandomNumTest(){
		int temp = tester.getNewRandomNum();
		int min = 1; 
		int max = 6;
		assertTrue(min <= temp && temp <= max);
	}

	/**
	 * Constructor test
	 */
	@Test
	public void ContructorTest(){
		Dice test = new Dice();
		if(test.getObjType() == "Dice"){
			assertTrue(true);
		}
	}
}
