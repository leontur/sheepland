package game.client.socket;

import game.client.interfaces.ClientConsoleInterface;
import game.client.view.ClientLogger;
import game.client.view.gui.GuiInitializer;
import game.client.view.gui.model.Sheepland;
import game.server.controller.CustomLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * RECEIVE ALL COMMANDS FROM SERVER 
 * AND CALL CORRECT METHODS IN THE CLIENT
 * (ROUTER CLASS)
 * 
 * for socket only
 * 
 * @author Leonardo
 */
public class ManageClientSocket implements Runnable {

	/**
	 * GAME VAR
	 * that represents the running instance of the client
	 * while is true, the client is up
	 */
	private static boolean isRunningGame;
	
	//CLASS VARS
	private static int clientId;
	private static int clientPort;
	private static ClientConsoleInterface cci;
	
	//CONNECTION VARS
	private static Socket socket;
	private static ObjectOutputStream outStream;
	private static ObjectInputStream inStream;
	
	
	/**
	 * Constructor
	 * Set the necessary vars to receive and send stream with server
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ManageClientSocket() throws Exception {
		
		//set id and port
		clientId = StartClientSocket.getClientId();
		clientPort = StartClientSocket.getClientPort();
		
		//set console interface
		cci = StartClientSocket.getClientConsoleInterface();
		
		//crate a new client socket for all game requests:
		//get the localhost (automatically detect 127.0.0.1)
		InetAddress host = InetAddress.getLocalHost();
		
		//socket connection creation
        socket = new Socket(host.getHostName(), clientPort);
	}
	
	/**
	 * RUN METHOD (RUN IN SEPARATE THREAD)
	 * CALLED AT THE CLIENT OPENING BY StartClientSocket
	 * 
	 * REMAINS ACTIVE FOR ENTIRE CLIENT EXECUTION
	 * MANAGE AND ROUTE ALL REQUESTS OF THE SERVER
	 * 
	 */
	public void run() {
		
		//WHILE THE GAME IS RUNNIG
		while(isRunningGame){
		
			String msg = "";
			
			try{
				
				/////////////////////////////////////////////////////////////////
				//RETRIEVE THE REQUEST FROM SERVER
	
				//get the localhost (automatically detect 127.0.0.1)
				InetAddress host = InetAddress.getLocalHost();
				
				//socket connection creation
		        socket = new Socket(host.getHostName(), clientPort);
		        
		        //timeout
		        socket.setSoTimeout(6000);
				
				//creating reader from server
		        inStream = new ObjectInputStream(socket.getInputStream());
		        
		        //read from server
		        msg = (String) inStream.readObject();
		        
	            //close resources
		        inStream.close();
	
		        /* MESSAGE CHECK
		         * 
		         * MODE
				 * 	-0 string (message) mode
				 * 	-1 command mode (for remote recognize and execution)
				 * 	-2 input request (for remote sending of value to server)
				 * 
		         * INPUT STRUCTURE
				 * 	(String) "[mode int]@[message str]"   
				 * 	command messages are splitted by options by "!"
				 * 
				 * 	example:
				 * 		0@hello
				 * 		1@#new-dice#!optionssting
		         */
		        
		        //split of the arrived message for mode and message
		        String[] parts = msg.split("@");
				String mode = parts[0];
				String message = "";
				try{
					message = parts[1];
				}catch(Exception e){
					message = "";
					CustomLogger.logInfoEx("ManageClientSocket", "run", "cannot find message for this response: " + msg, e);
				}

		        if("1".equals(mode)){
		        	
					/////////////////////////////////////////////////////////////////
					//IF IS A METHOD CALL REQUEST
		        	
		        	//PRELIMINARY CHECK (for commands without options)
		        	if("#clear-screen#".equals(message)){
		        		cci.clearConsole();
		        		continue;
		        	}

		        	//RETRIEVE COMMAND AND OPTIONS FROM MESSAGE
		        	// parts[1]: [cmd]![opt]
		        	String[] cmdsplit = message.split("!");
					String command = cmdsplit[0];
					String options = "";
					try{
						options = cmdsplit[1];
					}catch(Exception e){
						options = "";
						CustomLogger.logInfoEx("SocketGuiUpdater", "run", "cannot find options for this command: " + options, e);
					}
					
					//EXECUTING command
		        	if("#play-sound#".equals(command)){
		        		cci.playSound(options);
		        		
		        	}else if("#start-gui#".equals(command)){
		        		cci.showOnClient("Starting GUI..");
	        			cci.showGameWindow(Integer.parseInt(options));
	        			
		        	}else{

						/////////////////////////////////////////////////////////////////
						//IF GUI OR CONSOLE COMMAND
						
		        		//instantiate
						Sheepland sheeplandGui = GuiInitializer.getSheeplandGui();
						
						//set new request in game (client)
						sheeplandGui.updateServerNewRequest(command, options);
						
						//launch the move engine checker
						sheeplandGui.newServerRequestArrived(command, options);
					
		        	}
					
		        }else if("0".equals(mode)){	
		        	
		        	/////////////////////////////////////////////////////////////////
		        	//IF MESSAGE SHOW

		        	//display message
		        	cci.showOnClient(message);
		        	
		        }else if("2".equals(mode)){
		        	
					/////////////////////////////////////////////////////////////////
					//IF INPUT REQUEST
		        	
		        	int toSend = cci.readFromClient();
		        	sendToServer(Integer.toString(toSend));
		        }

			}catch(Exception e){
				ClientLogger.silentExceptionClientLogger("next check for command - client id: " + clientId + " - command: " + msg, e);
			}
			
			//Wait for next check
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				ClientLogger.silentExceptionClientLogger("next check for command", e);
			}
		}
	}
	
	
	/**
	 * MAIN SENDER FROM CLIENT TO SERVER 
	 * of a string
	 * @throws IOException 
	 */
	private void sendToServer(String msg) throws IOException{
		
		//get the localhost (automatically detect 127.0.0.1)
		InetAddress host = InetAddress.getLocalHost();
		
		//socket connection creation
        socket = new Socket(host.getHostName(), clientPort);
        
		//creating writer to server
        outStream = new ObjectOutputStream(socket.getOutputStream());
        
        //write to server
        outStream.writeObject(msg);
        
        //close resources
        outStream.close();
	}
	
	/**
	 * @return true if the game is running, else false (breaker var)
	 */
	public static boolean isRunningGame(){
		return isRunningGame;
	}
	/**
	 * set if the game is running (breaker var)
	 * @param is - true or false 
	 */
	public static void setIsRunningGame(boolean is){
		isRunningGame = is;
	}
	
}
