package ca.mcgill.ecse211.canlocator;

import ca.mcgill.ecse211.lab5.ColorClassification;
import ca.mcgill.ecse211.lab5.Navigation;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class CanLocator {

	private Odometer odo;
	private Navigation navigator;
	private CanDetect canDetect;
	private SampleProvider usDistance;
	private float[] usData;
	
	private static int FORWARD_SPEED = 100;
	private static double OFFSET = 0.5;
	private static final double TILE_SIZE = 30.48;
	private static final double CAN_DISTANCE_ON_BORDER = 27.5;
	private static final double CAN_DISTANCE_FROM_OUT = 12.75;
	private static final double ANGLE_ERROR = 10.0;
	
	private int TR;  //this variable stores the integer defining the target can color.
	private int count = 0;
	
	public CanLocator(CanDetect canDetect, SampleProvider usDistance, float[] usData, 
			Navigation navigator, int TR) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.usDistance = usDistance;
		this.usData = usData;
		this.canDetect = canDetect;
		this.navigator = navigator;
		this.TR = TR;
	}
	
	public void RunLocator(){
		
		while (true) {	
			
			//check front
			//if can detected, check color
				//if color correct, go to UR
				//else dodge to next
			//else goToNext()
			////////////////////////////
			
			/*
			
			false { 
				if (count > 0) turn 90 deg left;
				//if there is a can, check color;
				goToNext();
				count++;
			}
		
			*/
		}
	}	
	
	/**
	*checkCan() returns true if a can was spotten by the ultrasonic sensor within the
	*range of a tile. Otherwise, it returns false.
	*/
	
	private boolean checkCan(){
	
		//read sensor and see if a can is detected in range
		if(readUSDistance() <= TILE_SIZE) return true;
		else return false;
		
	}
	
	/**
	*checkColor() is a method that is called after checkCan(). It 
	*makes the EV3 beep once and return true if the can scanned is the target can
	*Otherwise, it will beep twice and return false.
	*/
	
	private boolean checkColor(double distance){
		
		navigator.moveToCan(distance);
		
		//if the can color is the target color, beep once
		if (TR == canDetect.run()) {
			Sound.beep();
			return true;
		}
		
		//otherwise, beep twice
		else {
			Sound.beep(); 
			Sound.beep();
			return false;
		}
	} 
	
	/**
	*goToNext() moves the EV3 forward to the next position when no cans are detected.
	*/
	
	private void goToNext() { //navigator.travelTo(LLx,1);///////////////
		
		navigator.moveToCan(TILE_SIZE);

	}
	
	/**
	*travelToURBorder() is called when the correct can is found on the edge of the search zone. This
	*method will use travelTo() from the Navigator class to get the EV3 to the upper right corner.
	*/
	
	private void travelToURBorder() {
		
		if ( (odo.getXYT()[2] >= 0-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 0+ANGLE_ERROR)){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(LLx-OFFSET, odo.getXYT()[1]/TILE_SIZE);
			navigator.travelTo(LLx-OFFSET,URy+OFFSET);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);		
		}
		
		else if ( (odo.getXYT()[2] >= 90-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 90+ANGLE_ERROR) ){
			
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo((odo.getXYT()[0]/TILE_SIZE,URy+OFFSET);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 180-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 180+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZE);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 270-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 270+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(odo.getXYT()[0]/TILE_SIZE,LLy-OFFSET);
			navigator.travelTo(URx+OFFSET,LLy-OFFSET);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
	}
	
	/**
	*travelToUROutside() is called when the correct can is found from the outside of
	*the search zone. This method will use travelTo() from the Navigator class
	*to get the EV3 to the upper right corner.
	*/
					   
	private void travelToUROutside() {
		
		if ( (odo.getXYT()[2] >= 0-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 0+ANGLE_ERROR)){
		
			navigator.driveBack(CAN_DISTANCE_FROM_OUT);
			navigator.travelTo(odo.getXYT()[0]/TILE_SIZE, URy+OFFSET);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);	
		}
		
		else if ( (odo.getXYT()[2] >= 90-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 90+ANGLE_ERROR) ){
			
			navigator.driveBack(CAN_DISTANCE_FROM_OUT);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 180-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 180+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_FROM_OUT);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 270-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 270+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_FROM_OUT);
			navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZE);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
	}
	
	/**
	*borderDodge() is called when an incorrect color of a can is detected. 
	*The EV3 will dodge the can and continue its trip to look for the correct one.
	*/
	
	private void borderDodge() {
		
		if ( (odo.getXYT()[2] >= 0-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 0+ANGLE_ERROR)){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			
			if(odo.getXYT()[1]/TILE_SIZE >= URy-1-DISTANCE_ERROR ||
			   	odo.getXYT()[1]/TILE_SIZE <= URy-1+DISTANCE_ERROR){
				
				//the 1.5 added is to make the EV3 dodge 1.5 times a tile
				navigator.travelTo(LLx-OFFSET, odo.getXYT()[1]/TILE_SIZE);
				navigator.travelTo(LLx-OFFSET,(odo.getXYT()[1]/TILE_SIZE) + 1.5);
				navigator.travelTo(LLx-OFFSET+1.5,(odo.getXYT()[1]/TILE_SIZE) + 1.5);
				navigator.turnTo(90);
			}
			
			else{
				//the 2 added is to make the EV3 dodge 2 times a tile
				navigator.travelTo(LLx-OFFSET, odo.getXYT()[1]/TILE_SIZE);
				navigator.travelTo(LLx-OFFSET,(odo.getXYT()[1]/TILE_SIZE) + 2);
				navigator.turnTo(90);
			}
		}
		
		else if ( (odo.getXYT()[2] >= 90-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 90+ANGLE_ERROR) ){
			
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(odo.getXYT()[0]/TILE_SIZE,URy+OFFSET);
			navigator.travelTo((odo.getXYT()[0]/TILE_SIZE) + 2, URy+OFFSET);
			navigator.turnTo(90);
		}
		
		else if ( (odo.getXYT()[2] >= 180-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 180+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			
			if(odo.getXYT()[1]/TILE_SIZE <= LLy+1+DISTANCE_ERROR ||
			   	odo.getXYT()[1]/TILE_SIZE >= LLy+1-DISTANCE_ERROR){
				
				navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZ);
				navigator.travelTo(URx+OFFSET,(odo.getXYT()[1]/TILE_SIZE) - 1.5);
				navigator.travelTo(URx+OFFSET-1.5,(odo.getXYT()[1]/TILE_SIZE) - 1.5);
				navigator.turnTo(90);
			}
			
			else{
				
				navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZ);
				navigator.travelTo(URx+OFFSET,(odo.getXYT()[1]/TILE_SIZE) - 2);
				navigator.turnTo(90);
			}
		}
		
		else if ( (odo.getXYT()[2] >= 270-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 270+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(odo.getXYT()[0]/TILE_SIZE,LLy-OFFSET);
			navigator.travelTo((odo.getXYT()[0]/TILE_SIZE) - 2, LLy-OFFSET);
			navigator.turnTo(90);
		}
	}
	
	/**
	*outsideDodge()  
	*
	*/				   
	
	private void outsideDodge() {
		
		//
		
	}
		
	private int readUSDistance() {
		
		usDistance.fetchSample(usData, 0);
		return (int) (usData[0] * 100);
		
	}
  
}
