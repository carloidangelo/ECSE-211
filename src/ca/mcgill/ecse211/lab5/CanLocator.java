package ca.mcgill.ecse211.lab5;

import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import ca.mcgill.ecse211.odometer.OdometryDisplay;
import lejos.hardware.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class CanLocator {

  private Odometer odo;
	private Navigation navigation;
	private static int FORWARD_SPEED = 100;
	double [] lightData;
	
	private SampleProvider colorSensor;
	private float[] colorData;
	private float previousValue = 0;
	private float currentValue = 0;
	
	public CanLocator(Odometer odo, SampleProvider colorSensor, float[] colorData) {
		this.odo = odo;
		this.navigation = new Navigation(odo);
		this.lightData = new double [4];
		
		this.colorSensor = colorSensor;
		this.colorData = colorData;
	}
  
  
}
