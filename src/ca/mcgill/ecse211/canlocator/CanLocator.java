package ca.mcgill.ecse211.canlocator;

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
	private static final double DISTANCE_ERROR = 2.0;
	
	private int TR;  //this variable stores the integer defining the target can color.
	private int count = 0;
	private int LLx, LLy, URx, URy;
	
	public CanLocator(CanDetect canDetect, SampleProvider usDistance, float[] usData, 
			Navigation navigator, int TR, int LLx, int LLy, int URx, int URy) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.usDistance = usDistance;
		this.usData = usData;
		this.canDetect = canDetect;
		this.navigator = navigator;
		this.TR = TR;
		this.LLx = LLx;
		this.LLy = LLy;
		this.URx = URx;
		this.URy = URy;
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
		
		navigator.moveToCan(CAN_DISTANCE_ON_BORDER);
		
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
	
	private void goToNext() { 
		
		if(odo.getXYT()[0] < LLx || odo.getXYT()[1] > URy || odo.getXYT()[0] > URx || odo.getXYT()[1] < LLy){
			navigator.moveToCan(0.5*TILE_SIZE);
		}
		
		else{
			navigator.moveToCan(TILE_SIZE);
		}
		
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
			navigator.travelTo(odo.getXYT()[0],URy+OFFSET);
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
				
				navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZE);
				navigator.travelTo(URx+OFFSET,(odo.getXYT()[1]/TILE_SIZE) - 1.5);
				navigator.travelTo(URx+OFFSET-1.5,(odo.getXYT()[1]/TILE_SIZE) - 1.5);
				navigator.turnTo(90);
			}
			
			else{
				
				navigator.travelTo(URx+OFFSET,odo.getXYT()[1]/TILE_SIZE);
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
	
	private void outsideDodge() {
		
		//move forward 12.75
		//check for the right color
			//if right color, then travel to UR outside
			//else go back 12.75
		
		navigator.driveBack(CAN_DISTANCE_ON_BORDER);
		navigator.turnTo(-90);
		
		if(odo.getXYT()[1] <= (URy*TILE_SIZE+DISTANCE_ERROR) || odo.getXYT()[1] >= (URy*TILE_SIZE-DISTANCE_ERROR) || odo.getXYT()[0] <= (URx*TILE_SIZE+DISTANCE_ERROR)
				|| odo.getXYT()[0] >= (URx*TILE_SIZE-DISTANCE_ERROR)){
			navigator.driveForward(TILE_SIZE*0.5);
			navigator.turnTo(90);
			navigator.driveForward(TILE_SIZE*1.5);
			navigator.turnTo(90);
		}
		
		else{
			navigator.driveForward(TILE_SIZE);
			navigator.turnTo(90);
		}
			
	}
	
	//returns to the next coordinate on the square without turning 90 deg right 
	private void  insideDodge(){
		
		//check if the robot is on the edge
		
		//turn right 90
		
		//check if the can is there
			//if can is there, go straight 27.5 cm 
			//check if the can is right
				//if right, move to UR
				//if not, move back 27.5, turn left
			
				
//			if(odo.getXYT()[2] <= Math.abs(0-ANGLE_ERROR)){
//				navigator.travelTo(odo.getXYT()[0]+TILE_SIZE/CAN_DISTANCE_ON_BORDER, odo.getXYT()[1]);
//			}
//			
//			else if(odo.getXYT()[2] <= Math.abs(90-ANGLE_ERROR)){
//				navigator.travelTo(odo.getXYT()[0], odo.getXYT()[1]-TILE_SIZE/CAN_DISTANCE_ON_BORDER);
//			}
//			
//			else if(odo.getXYT()[2] <= Math.abs(180-ANGLE_ERROR)){
//				navigator.travelTo(odo.getXYT()[0]-TILE_SIZE/CAN_DISTANCE_ON_BORDER, odo.getXYT()[1]);
//			}
//			
//			else if(odo.getXYT()[2] <= Math.abs(270-ANGLE_ERROR)){
//				navigator.travelTo(odo.getXYT()[0], odo.getXYT()[1]+TILE_SIZE/CAN_DISTANCE_ON_BORDER);
//			}

					
			//System.out.println("wrong can found");
			//on the first edge of square
			navigator.driveBack(CAN_DISTANCE_ON_BORDER);
			navigator.turnTo(-90);
			goToNext();
			navigator.turnTo(90);			
	}
	
	private void travelToURInside(){
		
		navigator.driveBack(CAN_DISTANCE_ON_BORDER+0.5*TILE_SIZE);
		//on the first edge
		if(odo.getXYT()[2] <= Math.abs(0-ANGLE_ERROR)){
			navigator.turnTo(-90);
			navigator.driveForward(URy*TILE_SIZE-odo.getXYT()[1]+CAN_DISTANCE_FROM_OUT);
			navigator.turnTo(90);
			navigator.driveForward(URx*TILE_SIZE-odo.getXYT()[0]);
			navigator.turnTo(90);
			navigator.driveForward(CAN_DISTANCE_FROM_OUT);
		}
		
		//on the second edge
		else if(odo.getXYT()[2] <= Math.abs(90-ANGLE_ERROR)){
			navigator.turnTo(-90);
			navigator.driveForward(URx*TILE_SIZE-odo.getXYT()[0]);
			navigator.turnTo(90);
			navigator.driveForward(CAN_DISTANCE_FROM_OUT);	
		}
		
		//on the third edge
		else if(odo.getXYT()[2] <= Math.abs(180-ANGLE_ERROR)){
			navigator.turnTo(90);
			navigator.driveForward(URy*TILE_SIZE-odo.getXYT()[1]);
			navigator.turnTo(-90);
			navigator.driveForward(CAN_DISTANCE_FROM_OUT);	
		}
		
		//on the fourth edge
		else if(odo.getXYT()[2] <= Math.abs(270-ANGLE_ERROR)){
			navigator.turnTo(90);
			navigator.driveForward(URx*TILE_SIZE-odo.getXYT()[0]+CAN_DISTANCE_FROM_OUT);
			navigator.turnTo(-90);
			navigator.driveForward(URy*TILE_SIZE-odo.getXYT()[0]);
			navigator.turnTo(-90);
			navigator.driveForward(CAN_DISTANCE_FROM_OUT);
		}
		
	}
		
	private int readUSDistance() {
		
		usDistance.fetchSample(usData, 0);
		return (int) (usData[0] * 100);
		
	}
  
}
