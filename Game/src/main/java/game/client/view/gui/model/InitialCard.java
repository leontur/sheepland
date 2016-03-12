package game.client.view.gui.model;

import game.client.view.ClientLogger;
import game.client.view.gui.StaticObject;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * INITIAL PLOT CARDS
 * OBJECT CLASS
 * 
 * INHERIT ABILITY FROM SUPERCLASSES
 * 
 * @author Leonardo
 */
public class InitialCard extends StaticObject {
	
	/**
	 * CLASS SERIAL VERSION ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * CLASS VARS
	 */
	private static int initialCardNum;
	
	//scaling
	private int boundX = 50;
	private int boundY = boundX;

	/**
	 * CLASS CONTSTRUCTOR
	 */
	public InitialCard(int id, String type){
		
		ImageIcon img = null;
		
		//set id
		initialCardNum = id;
		
		try{
			//get files
			img = new ImageIcon(ImageIO.read(new File(dirPathCards + type.toLowerCase() + "Init" + ".gif")));
		}catch (IOException e){
			ClientLogger.exceptionClientLogger("impossible to open the resource - IO EXCEPTION ", e);
		}
		
		setBounds(0, 0, boundX, boundY);
		setIcon(img);
	}
	
	/**
	 * Set the number of initial cards
	 * @param i
	 */
	public void setInitialCardsNum(int i){
		initialCardNum = i; 
	}
	
	/**
	 * Get the number of initial cards
	 * @return
	 */
	public int getInitialCardsNum(){
		return initialCardNum;
	}

}