package game.server.model;

import static org.junit.Assert.*;
import game.server.interfaces.DatabaseInterface;
import game.server.model.FinalEnclosure;

import org.junit.Test; 

/**
 * MAIN FINALENCLOSURE-TEST CLASS
 * 
 * @author Dario
 */

public class FinalEnclosureTest {

	//CLASS VARS
		public FinalEnclosure tester = new FinalEnclosure();
	    private DatabaseInterface db = new Database();

		/**
		 * Check the current enclosure identifier
		 */
		@Test
		public void getFinalEnclosureIdentifier(){
		
			int temp;
			int min = 0; 
		 	int max = db.getFinalEnclosureNum();
		
			for(int i=0; i<max; i++){
			
				setFinalEnclosureIdentifierTest(i);
				temp = tester.getEnclosureIdentifier();
			
				if(!(min <= temp && temp <= max)){
				assertTrue(false);
				break;
			}
		}
		
		assertTrue(true);
		
	    }
		private void setFinalEnclosureIdentifierTest(int idtotest){
			tester.setEnclosureIdentifier(idtotest);
		}
		
		@Test
		public void setFinalEnclosureIdentifierTset1(){
			this.getFinalEnclosureIdentifier();
		}
		
		@Test
		public void constructorTest(){
		 FinalEnclosure test1 = new FinalEnclosure();
		 if(test1.getEnclosureIdentifier() == 0 && test1.getObjType() == "FinalEnclosure"){
			 assertTrue(true);
		 }
		}
		
		
		@Test
		public void costurtorTest2(){
		FinalEnclosure test2 = new FinalEnclosure(10);
		if(test2.getEnclosureIdentifier() ==10 && test2.getObjType() == "FinalEnclosure"){
			assertTrue(true);
		}
		}
	}
