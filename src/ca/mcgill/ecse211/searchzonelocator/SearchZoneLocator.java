package ca.mcgill.ecse211.searchzonelocator;

import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.hardware.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class SearchZoneLocator {

	private Odometer odo;
	private static final int FORWARD_SPEED = 100;
	private static final double TILE_SIZE = 30.48;
	private int LLx, LLy, URx, URy, SC;
	
	public SearchZoneLocator(int SC, int LLx, int LLy, int URx, int URy) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.SC = SC;	
		this.LLx = LLx;
		this.LLy = LLy;
		this.URx = URx;
		this.URy = URy;
	}
	
	/** CheckSC() checks the starting corner of the EV3. Depending on where
	 *  it was placed to begin, the EV3 will make its way to the search zone
	 * and corrects its angle to follow the convention given in Lab 5.
	 */
	public void goToSearchZone(){
		
		switch(SC){ //NOTE, check if splitting LLx LLy is more accurate
			case 0: 
				//current position is (1,1)
				odo.setXYT(1.0 * TILE_SIZE, 1.0 * TILE_SIZE, 0.0);
				
				//go to LLy, turn 90 RIGHT
				//go to LLx, turn 90 LEFT
				break;
			case 1: 
				//current position is (7,1)
				odo.setXYT(7.0 * TILE_SIZE, 1.0 * TILE_SIZE, 270.0);
				
				//go to LLx, then turn 90 RIGHT and reset angle to zero
				//go to LLy
				break;
			case 2: 
				//current position is (7,7)
				odo.setXYT(7.0 * TILE_SIZE, 7.0 * TILE_SIZE, 180.0);
				
				//go to LLy, turn 90 RIGHT
				//go to LLx, turn 90 RIGHT and reset angle to zero				
				break;
			case 3:	
				//current position is (1,7)
				odo.setXYT(1.0 * TILE_SIZE, 7.0 * TILE_SIZE, 90.0);
				
				//go to LLx, turn 90 RIGHT
				//go to LLy, turn 180 RIGHT and reset angle to zero
				break;
		    default:
		    	System.out.println("Error - invalid button"); // None of the above - abort
		        System.exit(-1);
		        break;
		}
		
	}  
  
}
