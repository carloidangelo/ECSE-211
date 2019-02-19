package ca.mcgill.ecse211.canlocator;

import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.hardware.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class CanLocator {

	private Odometer odo;
	private static int FORWARD_SPEED = 100;
	double [] lightData; //"angles"
	
	private int TR;
	
	private SampleProvider colorSensor;
	private float[] colorData;
	
	public CanLocator(Odometer odo, SampleProvider colorSensor, float[] colorData, int TR) {
		this.odo = odo;
		this.lightData = new double [4]; //"angles"
		this.colorSensor = colorSensor;
		this.colorData = colorData;
		this.TR = TR;
	}
	
	public void RunLocator(){
	
		switch(TR){
			case 1: //blue can
				
				break;
			case 2: //green can
				
				break;
			case 3: //yellow can
				
				break;
			case 4:	//red can
				
				break;
		}
	}	
  
}
