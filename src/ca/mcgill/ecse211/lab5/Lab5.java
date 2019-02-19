package ca.mcgill.ecse211.lab5;

import lejos.hardware.Button;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import ca.mcgill.ecse211.localization.*;

public class Lab5 {

	// Motor Objects, and Robot related parameters
	private static final EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private static final EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	private static final TextLCD LCD = LocalEV3.get().getTextLCD();
	private static final Port US_PORT = LocalEV3.get().getPort("S1");
	private static final Port CS_PORT = LocalEV3.get().getPort("S4");
	
	//Robot related parameters
	public static final double WHEEL_RAD = 2.2;
	public static final double TRACK = 14.7;

	public static void main(String[] args) throws OdometerExceptions {

		int buttonChoice;

		// Odometer related objects
		Odometer odometer = Odometer.getOdometer(LEFT_MOTOR, RIGHT_MOTOR, TRACK, WHEEL_RAD);
		Display odometryDisplay = new Display(LCD); 

		@SuppressWarnings("resource") // Because we don't bother to close this resource
		SensorModes usSensor = new EV3UltrasonicSensor(US_PORT);
		SampleProvider usDistance = usSensor.getMode("Distance");
		float[] usData = new float[usDistance.sampleSize()];
		
		@SuppressWarnings("resource") // Because we don't bother to close this resource
		SensorModes csSensor = new EV3ColorSensor(CS_PORT);
		SampleProvider csLineDetector = csSensor.getMode("Red");
		float[] csData = new float[csLineDetector.sampleSize()];
		
		do {
			
			LCD.clear();

			// ask the user whether robot should do Rising Edge or Falling Edge
			LCD.drawString("< Left | Right >", 0, 0);
			LCD.drawString("       |        ", 0, 1);
			LCD.drawString("Falling|	    ", 0, 2);
			LCD.drawString(" Edge  |        ", 0, 3);
			LCD.drawString("       |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT);

		Thread odoThread = new Thread(odometer);
		odoThread.start();

		Thread odoDisplayThread = new Thread(odometryDisplay);
		odoDisplayThread.start();

		UltrasonicLocalizer ultrasonicLocalizer = new UltrasonicLocalizer(LEFT_MOTOR, RIGHT_MOTOR, usDistance, usData);
		LightLocalizer lightLocatizer = new LightLocalizer(LEFT_MOTOR, RIGHT_MOTOR, csLineDetector, csData);

		ultrasonicLocalizer.fallingEdge();

		lightLocatizer.lightLocalize();
		
		
        
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}

}