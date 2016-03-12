package game.client.view.gui.model;

import game.client.view.ClientLogger;
import game.client.view.gui.SelectableObject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * ENCLOSURE
 * OBJECT CLASS
 * 
 * INHERIT ABILITY FROM SUPERCLASSES
 * 
 * @author Leonardo
 */
public class Enclosure extends SelectableObject {
	
	/**
	 * CLASS SERIAL VERSION ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * CLASS VARS
	 */
	private int enclosureNum;
	private boolean isInUse;
	private int coordUse = -1;
	
	//scaling
	private int boundX = 20;
	private int boundY = boundX;

	/**
	 * CLASS CONTSTRUCTOR
	 */
	public Enclosure(int id){
		
		ImageIcon img = null;
		
		//set id
		enclosureNum = id;
		
		try{
			
			//get files
			img = new ImageIcon(ImageIO.read(new File(dirPath + "enclosure.png")));
			//scaling
			img = new ImageIcon(img.getImage().getScaledInstance(boundX, boundY, Image.SCALE_AREA_AVERAGING));
			
		}catch (IOException e){
			ClientLogger.exceptionClientLogger("impossible to open the resource - IO EXCEPTION ", e);
		}
		
		//do not allow selection
		setIsSelectableObject(false);
		
		setBounds(0, 0, boundX, boundY);
		setIcon(img);
	}
			
	/**
	 * Set the number of standard enclosure
	 * @param i
	 */
	public void setEnclosureNum(int i){
		enclosureNum = i; 
	}
	
	/**
	 * Get the number of standard enclosure
	 * @return
	 */
	public int getEnclosureNum(){
		return enclosureNum;
	}

	/**
	 * Set true if is in use 
	 * or set false otherwise
	 * @param status
	 */
	public void setIsInUse(boolean status){
		isInUse = status;
	}

	/**
	 * True if is in use 
	 * or false otherwise
	 * @return
	 */
	public boolean isInUse(){
		return isInUse;
	}
	
	/**
	 * Set if the coordinates are used
	 * @param i
	 */
	public void setCoordUse(int i){
		coordUse = i; 
	}
	
	/**
	 * Get if the coordinates are used
	 * @return
	 */
	public int getCoordUse(){
		return coordUse; 
	}
	
}