package game.server.rmi;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;

import game.server.AddPlayer;
import game.server.controller.*;
import game.server.interfaces.AddPlayerInterface;
import game.server.model.*;

/**
 * 
 * SERVER RMI
 * CONNECTION CLASS
 * 
 * GET PLAYERS AND START GAME IN NEW THREAD
 * 
 * @author Leonardo
 *
 */
public class StartRmi {
		
	//RMI VARS
	private static final int PORT = 1099;
	
	//CREATING NEW GAME > MAIN CREATION of a new game object <
	private Game rmigame = new Game();
	
	//players vars
	private static int rmiPlayers;
	
	//timeout
	private static boolean waitForPlayer = true;
	
	/**
	 * TIMEOUT FOR PLAYERS LOGIN
	 * time given to a new game to search new players
	 */
	private static int playersLoginGlobalTimeout = 60000;
	
	/**
	 * RMI CLASS MAIN METHOD 
	 */
	public void runServerRmi() throws Exception {
		
		//CREATING REGISTRY
		LocateRegistry.createRegistry(PORT);
						
		//PUBLISH INTERFACES
		AddPlayerInterface addPlayerInterface = null;
		try {
			
			addPlayerInterface = new AddPlayer(rmigame);
			ManageRmi.publishInterfaces(addPlayerInterface);
			CustomLogger.logConfig("StartRmi", "runServerRmi", "interfaces published");
			
		} catch (AccessException e) {
			CustomLogger.logEx("StartRmi", "runServerRmi", "ManageRmi - remote exception", Level.SEVERE, e);
		} catch (RemoteException e) {
			CustomLogger.logEx("StartRmi", "runServerRmi", "ManageRmi - remote exception", Level.SEVERE, e);
		} catch (AlreadyBoundException e) {
			CustomLogger.logEx("StartRmi", "runServerRmi", "ManageRmi - remote exception", Level.SEVERE, e);
		}

		//start timer
		AsyncTimer timer = new AsyncTimer();
		timer.asyncServiceTimeout(playersLoginGlobalTimeout, "StartRmi");
		
		//wait time for player to sign in	
		while(waitForPlayer){
			WaitPlayer.waitPlayer(rmigame);
		}
		
		//check found players
		if(rmiPlayers>=2){
		//if there are two or more players the game can start
			
			//SET IN GAME THE RMI MODE
			rmigame.setIsSocketMode(false);
			
			//START NEW GAME
			//IN NEW THREAD
			GameManage newGameManage = new GameManage(rmigame, addPlayerInterface, false, false);
			
			//starting the thread
			Thread newGameManageThread = new Thread(newGameManage);
			newGameManageThread.start();
			
			//notify
			rmigame.getGameViewer().printToConsole("New game was created and correctly started");
			
			//reset players number for a new game request
			rmiPlayers=0;
			
		}else{
			//not enough players
			//reset player number
			rmiPlayers=0;
			
			//the waiting is in timeout
			rmigame.getGameViewer().printToConsole("New game was not create due to players logging timeout, please restart server.");
			
			//notify all
			rmigame.getGameViewer().notifyAllClients("New game was not create due to players logging timeout, please restart client.");
			
			//stop server
			Thread.currentThread().interrupt();
		}	
	}
	
	/**
	 * Set the number of players
	 * @param newn
	 */
	public static void setPlayersNumber(int newn){
		rmiPlayers=newn;
	}
	
	/**
	 * Get the number of players
	 * @return
	 */
	public static int getPlayersNumber(){
		return rmiPlayers;
	}
	
	/**
	 * Set true if wait for player
	 */
	public static void setWaitForPlayerFalse(){
		waitForPlayer = false;
	}
	
	/**
	 * Timeout if the login of a new player exceeds the established time
	 * @return
	 */
	public static int getAddPlayerLoginTimeout(){
		return playersLoginGlobalTimeout;
	}
	
	/**
	 * Get started game
	 * @return
	 */
	public Game getStartedGame(){
		return rmigame;
	}
}