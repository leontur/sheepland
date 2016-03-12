package game.server.controller;

import game.server.interfaces.DatabaseInterface;
import game.server.model.Database;
import game.server.model.Game;
import game.server.model.Region;
import game.server.model.Sheep;
import game.server.model.Shepherd;
import game.server.view.ViewerInterface;

import java.util.List;

/**
 * MOVE MANAGEMENT CLASS
 * For Sheeps manual movement (Black and White)
 * 
 * @author Leonardo
 */
public class MoveSheep {
		
	/**
	 * CLASS VARS
	 */
	private static DatabaseInterface db = new Database();
	
	/**
	 * DEFAULT CONSTRUCTOR
	 * private for static classes
	 */
	private MoveSheep(){
		
	}
	
	/**
	 * Sheep main movement call
	 * (manual move - for black and white)
	 * 
	 *  -control feasibility
	 *  -select a sheep
	 *  -ask to user if ok to move that sheep
	 *  	-if ok, move
	 *  	-if not ok, reask
	 *  		-if finished sheep, notify
	 */
	public static void moveSheep(
			Game game, 
			Shepherd shepherd, 
			List<Region> allRegions, 
			ViewerInterface mainGameViewer,
			boolean isTest
			){
		
		//find region around shepherd
		List<Region> regionAroundShepherd = CheckMove.findRegionsWithSamePos(allRegions, shepherd.getCurrShepherdTarget());
		
		//notify
		mainGameViewer.showSheepsAroundShepherd(regionAroundShepherd);
		
		//MOVE CHECK AND WAIT
		//check for rules
		if(!game.isSheepBeenMoved()){
			
			//search for sheep to move
			if(CheckMove.isAtLeastASheepInRegionsAround(regionAroundShepherd)){
				//if there is at least a sheep
				//ask and wait for move
				selectSheepToMove(game, regionAroundShepherd, mainGameViewer, isTest);
				
			}else{
				//notify: no sheeps to move
				mainGameViewer.notifySheepsAreOut();
			}
			
		}else{
			//notify: move already done
			mainGameViewer.notifyMoveAbortSheepAlreadyMoved();
		}
	}

	//SHEEP CONTROL ENGINE
	
	/**
	 * Select the sheep to move
	 * @param game
	 * @param regionsAround
	 * @param mainGameViewer
	 * @param isTest
	 */
	private static void selectSheepToMove(Game game, List<Region> regionsAround,  ViewerInterface mainGameViewer, boolean isTest){
		
		int i, j;
		
		//WHITE
		//cross-check
		loop:
		for(i=0; i<regionsAround.size(); i++){
			for(j=regionsAround.size()-1; j>=0; j--){
				
				//Check (last < excludes all cities)
				if(
					i!=j
					&&
					regionsAround.get(i).getAllSheeps().size() > 0
					&&
					regionsAround.get(j).getRegionIdentifier() < db.getRegionNum() 
					&&
					//check ok: request for move confirm (if confirmed, break. else try different)
					moveSheepBetweenRegions(game, regionsAround.get(i), regionsAround.get(j), mainGameViewer, isTest)
					){
					break loop;
				}
			}
		}
		
		//BLACK (manual movement)
		//cross-check
		loop:
		for(i=0; i<regionsAround.size(); i++){
			for(j=regionsAround.size()-1; j>=0; j--){
				
				//Check (last < excludes all cities)
				if(
					i!=j
					&&
					regionsAround.get(i).getAllBlackSheeps().size() > 0
					&&
					regionsAround.get(j).getRegionIdentifier() < db.getRegionNum()
					&&
					//from class for black sheep
					//check ok: request for move confirm (if confirmed break. else try different)
					MoveBlackSheep.moveBlackSheepBetweenRegions(game, regionsAround.get(i), regionsAround.get(j), mainGameViewer, isTest)
					){
					break loop;
				}
			}
		}
	}
	
	//SHEEP SELECTION ENGINE
	
	/**
	 * AUXILIARY METHODS THAT CONTROLS THE MOVEMENT OF THE SHEEP BEETWEEN THE REGIONS
	 * @param game
	 * @param prevSheepReg
	 * @param nextSheepReg
	 * @param mainGameViewer
	 * @param isTest
	 * @return
	 */
	private static boolean moveSheepBetweenRegions(Game game, Region prevSheepReg, Region nextSheepReg, ViewerInterface mainGameViewer, boolean isTest){
		
		boolean thisCombination;
		
		//TEST CLAUSE ONLY
		if(!isTest){
			//requested for every combination of 
			//ask if move sheep found
			thisCombination = mainGameViewer.askForSheepToMove(prevSheepReg.getRegionIdentifier()+1, nextSheepReg.getRegionIdentifier()+1);
		}else{
			//OVERRIDE FOR TEST
			thisCombination = true;
		}
		
		if(thisCombination){
			
			//invoke move method
			moveThisSheep(prevSheepReg.getLastSheepInThisRegion(), prevSheepReg, nextSheepReg);
			
			//notify
			mainGameViewer.notifyMoveSuccessSheep(nextSheepReg.getLastSheepInThisRegion().getSheepIdentifier()+1, prevSheepReg.getRegionIdentifier()+1, nextSheepReg.getRegionIdentifier()+1);
			
			//update move count
			game.increaseMoveDoneCountByOne();
			
			//set as move
			game.setSheepBeenMoved(true);
		}
		
		return thisCombination;
	}
	
	/**
	 * SHEEP REAL MOVEMENT EXECUTOR
	 * @param sheep
	 * @param prevSheepReg
	 * @param nextSheepReg
	 */
	private static void moveThisSheep(Sheep sheep, Region prevSheepReg, Region nextSheepReg){
		nextSheepReg.addANewSheep(sheep);
		prevSheepReg.removeASheep();
	}
	
}
