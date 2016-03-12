package game.server.model;

import static org.junit.Assert.*;

import game.server.interfaces.DatabaseInterface;
import game.server.model.Land;

import org.junit.Test;

/**
 * MAIN LAND-TEST CLASS
 * 
 * @author Dario
 */

public class LandTest {
	
	//CLASS VARS
	public Land tester = new Land();
	private DatabaseInterface db = new Database();

	/**
	 * CHeck the land identifier
	 */
	@Test
	public void getThisLandIdentifierTest () {
		int temp;
		int min = 0; 
	 	int max = db.getIslandNum();
	
		for(int i=0; i<max; i++){
		
			setNewLandIdentifierTest(i);
			temp = tester.getThisLandIdentifier();
		
			if(!(min <= temp && temp <= max)){
			assertTrue(false);
			break;
		}
	}
	
	assertTrue(true);
	}
	private void setNewLandIdentifierTest(int idtotest){
		tester.setNewLandIdentifier(idtotest);
	}
	
	@Test
	public void setNewLandIdentifierTest1(){
		this.getThisLandIdentifierTest();
	}
	
	/**
	 * Constructor test class 
	 */
	@Test
	public void constructorTest(){
	 Land test1 = new Land();
	 if(test1.getThisLandIdentifier() == 0){
		 assertTrue(true);
	 }
	}
	
	/**
	 * Constructor test class
	 */
	@Test
	public void costurtorTest2(){
	Land test2 = new Land(10);
	if(test2.getThisLandIdentifier() == 10){
		assertTrue(true);
	}
 }
}
