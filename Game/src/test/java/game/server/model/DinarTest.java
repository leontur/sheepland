package game.server.model;

import static org.junit.Assert.*;

import game.server.model.Dinar;

import org.junit.Test;

/**
 * MAIN DINAR-TEST CLASS
 * 
 * @author Dario
 */

public class DinarTest {
	
	//CLASS VARS
	private Dinar tester = new Dinar();		
	
	
	/**
	 * Check this coin identifier
	 * @param id
	 */
	@Test
	public void getIdentifierTest() {
		int testInt = 1;
		tester.setIdentifier(testInt);
		assertEquals(tester.getIdentifier(), testInt);
	}
	
	@Test
	public void setIdentifierTest(){
		getIdentifierTest();
	}
	
	/**
	 * Constructor test
	 */
	@Test
	public void ContructorTest(){
		Dinar test = new Dinar();
		if(test.getIdentifier() == 0 && test.getObjType() == "Dinar"){
			assertTrue(true);
		}
	}
	
	/**
	 * Constructor test
	 */
	@Test
	public void ContructorTestA(){
		Dinar test = new Dinar(10);
		if(test.getIdentifier() == 10 && test.getObjType() == "Dinar"){
			assertTrue(true);
		}
	}
}
