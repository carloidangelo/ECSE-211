package ca.mcgill.ecse211.canlocator;

import ca.mcgill.ecse211.lab5.ColorClassification;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.robotics.SampleProvider;

public class CanLocator {

	private Odometer odo;
	private static int FORWARD_SPEED = 100;
	double [] lightData; //"angles"
	
	private int TR;
	
	ColorClassification csFront;
	private SampleProvider usDistance;
	private float[] usData;
	
	public CanLocator(ColorClassification csFront, SampleProvider usDistance,
						float[] usData, int TR) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.lightData = new double [4]; //"angles"
		this.usDistance = usDistance;
		this.usData = usData;
		this.csFront = csFront;
		this.TR = TR;
	}
	
	public void RunLocator(){
		
		while (true) {
			
		}
		
		//check front
			//if object check color
				//if color correct, signal & go to UR
				//else dodge to next
			//else goToNext()
		
		/*
		switch(TR){
			case 1: //blue can
				
				break;
			case 2: //green can
				
				break;
			case 3: //yellow can
				
				break;
			case 4:	//red can
				
				break;
		}*/
	}	
	
	private void goToNext() {
		
		//
		
	}
	
	private void borderDodge() {
		
		//
		
	}
	
	private void outsideDodge() {
		
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
