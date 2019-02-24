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
	private static final int TILE_SIZE = 31;
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
	
	private boolean checkColor(){
		
		navigator.moveToCan(CAN_DISTANCE);
		
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
	
	private boolean checkCan(){
	
		//read sensor and see if a can is detected in range
		if(readUSDistance() <= TILE_SIZE) return true;
		else return false;
		
	}
	
	private void goToNext() { //navigator.travelTo(LLx,1);///////////////
				
		//if x == LLx && y == LLy || y
			//go forward one tile
			//turn 90 deg right
		//else outside
			//go forward half tile
		
	}
	
	private void borderToUR() {
		
		if ( (odo.getXYT()[2] >= 0-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 0+ANGLE_ERROR)){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(LLx-OFFSET, odo.getXYT()[1]);
			navigator.travelTo(LLx-OFFSET,URy+OFFSET);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);		
		}
		
		else if ( (odo.getXYT()[2] >= 90-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 90+ANGLE_ERROR) ){
			
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo((odo.getXYT()[0],URy+OFFSET);
			navigator.travelTo(URx,URy+OFFSET);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 180-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 180+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(URx+OFFSET,odo.getXYT()[1]);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
		
		else if ( (odo.getXYT()[2] >= 270-ANGLE_ERROR) || 
		    	(odo.getXYT()[2] <= 270+ANGLE_ERROR) ){
		
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.travelTo(odo.getXYT()[0],LLy-OFFSET);
			navigator.travelTo(URx+OFFSET,LLy-OFFSET);
			navigator.travelTo(URx+OFFSET,URy);
			navigator.travelTo(URx,URy);
		}
	}
	
	private void outsideToUR() {
		
		//
		
	}
	
	private void borderDodge() {
		
		//
		
	}
	
	private void outsideDodge() {
		
		//
		
	}
		
	private int readUSDistance() {
		
		usDistance.fetchSample(usData, 0);
		return (int) (usData[0] * 100);
		
	}
  
}
