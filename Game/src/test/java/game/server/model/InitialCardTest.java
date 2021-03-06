package game.server.model;

import static org.junit.Assert.*;
import game.server.interfaces.DatabaseInterface;
import game.server.model.InitialCard;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * MAIN INITIALCARD-TEST CLASS
 * 
 * @author Dario
 */

public class InitialCardTest {
	
	//CLASS VARS
	public InitialCard tester = new InitialCard();
    private DatabaseInterface db = new Database();
	
	//METHODS VARS
	public String testString = "test";
	public int testInt = 1;
	public List<String> posList = new ArrayList<String>();

	/** 
	 * Check the identifier of initial card 
	 */
	@Test
	public void getInitialCardIdentifier() {
		
		int temp;
		int min = 0; 
	 	int max = db.getInitialPlotCardNum();
	
		for(int i=0; i<max; i++){
		
			setInitialCardIdentifierTest(i);
			temp = tester.getInitialCardIdentifier();
		
			if(!(min <= temp && temp <= max)){
			assertTrue(false);
			break;
		}
		
	}
		assertTrue(true);
	}
	private void setInitialCardIdentifierTest(int idtotest){
		tester.setInitialCardIdentifier(idtotest);
	}
	
	@Test
	public void setinitialCardIdentifierTest1(){
		this.getInitialCardIdentifier();
	}
	
	/**
	 * Check the type of the initial card
	 */
	@Test
	public void getInitialCardPlotTypeTest(){
		tester.setInitialCardPlotType(testString);
		assertEquals(tester.getInitialCardPlotType(), testString);
	}
	
	@Test
	public void setInitialTestPlotTypeTest1(){
		this.getInitialCardPlotTypeTest();
	}
	
	/**
	 * Check the plot type classified by identifier 
	 */
	@Test
	public void getPlotTypeByIdTest(){
		for(int i=0; i<db.getPlotTypes().size(); i++){
	assertNotNull(tester.getPlotTypeById(i));
		}
	}
	
	/**
	 * Constructor test class
	 */
	@Test
	public void ConstructorTest(){
		InitialCard test = new InitialCard();
		if(test.getObjType() == "InitialCard" && test.getInitialCardIdentifier()== 0 && test.getInitialCardPlotType() == null){
			assertTrue(true);
		}
	}
	
	/**
	 * Constructor test class
	 */
	@Test
	public void ConstructorTestA(){
		String st = "test";
		InitialCard test = new InitialCard(st,10);
		if(test.getObjType() == "InitialCard" && test.getInitialCardIdentifier() == 10 && test.getInitialCardPlotType() == st){
			assertTrue(true);
		}
	}
}
