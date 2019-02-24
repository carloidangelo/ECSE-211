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
	private static final int TILE_SIZE = 31;
	private static final double CAN_DISTANCE = 27.5;
	
	/**
	*TR is the variable that stores the integer defining the target can color we are looking
	*cX,cY store the changes in x and why distance so that the EV3 knows which ways to turn.
	*i.e if cX is zero but cY is a positive number, then the EV3 is traveling straight
	*up the search zone.
	*/
	
	private int TR;
	private int cX,xY = 0;
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
	
	private void checkColor(){
		
		navigator.moveToCan(CAN_DISTANCE);
		
		//if the can color is the target color, beep once
		if (TR == canDetect.run()) Sound.beep();
		
		//otherwise, beep twice
		else {
			Sound.beep(); 
			Sound.beep();
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
		
		if (cY > 0 && cX == 0){
		
		navigator.travelTo(LLx,curY-0.5);
		navigator.travelTo(LLx-0.5,curY-0.5);
		navigator.travelTo(LLx-0.5,URy+0.5);
		navigator.travelTo(URx,URy+0.5);
		navigator.travelTo(URx,URy);
		
		}
		
		if (cX > 0 && cY == 0){
		
		navigator.travelTo(curX-0.5,URy);
		navigator.travelTo(curX-0.5,URy+0.5);
		navigator.travelTo(URx,URy+0.5);
		navigator.travelTo(URx,URy);
		
		}
		
		if (cY < 0 && cX == 0){
		
		navigator.travelTo(URx,curY+0.5);
		navigator.travelTo(URx+0.5,curY+0.5);
		navigator.travelTo(URx+0.5,URy);
		navigator.travelTo(URx,URy);
		
		}
		
		if (cX < 0 && cY == 0){
		
		navigator.travelTo(curX+0.5,LLy);
		navigator.travelTo(curX+0.5,LLy-0.5);
		navigator.travelTo(URx+0.5,LLy-0.5);
		navigator.travelTo(URx+0.5,URy);
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
