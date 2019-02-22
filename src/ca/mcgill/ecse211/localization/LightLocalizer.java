package ca.mcgill.ecse211.localization;

import lejos.hardware.Sound;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.SampleProvider;
import ca.mcgill.ecse211.odometer.*;
import ca.mcgill.ecse211.lab5.*;

public class LightLocalizer {

  public final static int ROTATION_SPEED = 100;
  private final static int FORWARD_SPEED = 150; 
  private static final double TILE_SIZE = 30.48;
  
  private final static int COLOUR_DIFF = 20;  
  private final static double LIGHT_LOC_DISTANCE = 14.5;
  private final static int EXTRA_DISTANCE = 5;
  private static final double TURN_ERROR = 10;
  
  private double radius = Lab5.WHEEL_RAD;
  private double track = Lab5.TRACK;
  
  private Odometer odo;
  private EV3LargeRegulatedMotor leftMotor, rightMotor;
 
  double[] linePosition;
  private Navigation navigator;
  
  private SampleProvider csLineDetector;
  private float[] csData;

  public LightLocalizer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
		  				  SampleProvider csLineDetector, float[] csData, Navigation navigator) throws OdometerExceptions {
	odo = Odometer.getOdometer();
	this.leftMotor = leftMotor;
	this.rightMotor = rightMotor;
	linePosition = new double[4];
	this.csLineDetector = csLineDetector;
	this.csData = csData;
	this.navigator = navigator;
	}

  public void lightLocalize(double pointX, double pointY) {
	  
      leftMotor.setSpeed(ROTATION_SPEED);
	  rightMotor.setSpeed(ROTATION_SPEED);
	  
	  int count = 0;
	  float firstReading = readLineDarkness();
	  float sample;
	  while (count < 4) {

		leftMotor.forward();
		rightMotor.backward();

		sample = readLineDarkness();

		if (100*Math.abs(sample - firstReading)/firstReading > COLOUR_DIFF) {
          linePosition[count] = odo.getXYT()[2];
		  Sound.beep();
		  count++;
		}
	  }

	  leftMotor.stop(true);
	  rightMotor.stop();

	  double deltaX, deltaY, angleX, angleY, deltaA;

	  angleY = linePosition[3] - linePosition[1];
	  angleX = linePosition[2] - linePosition[0];

	  deltaX = -LIGHT_LOC_DISTANCE * Math.cos(Math.toRadians(angleY / 2));
	  deltaY = -LIGHT_LOC_DISTANCE * Math.cos(Math.toRadians(angleX / 2));
	  
	  deltaA = 90 - (angleY / 2.0) - TURN_ERROR;
	  
	  leftMotor.rotate(convertAngle(radius, track, deltaA), true);
	  rightMotor.rotate(-convertAngle(radius, track, deltaA));

	  odo.setXYT(pointX * TILE_SIZE + deltaX, pointY * TILE_SIZE + deltaY, 0.0);
	  
	  navigator.travelTo(pointX, pointY);

	  leftMotor.rotate(-convertAngle(radius, track, Navigation.minAng), true);
	  rightMotor.rotate(convertAngle(radius, track, Navigation.minAng));
	  
	  leftMotor.stop(true);
	  rightMotor.stop();

  }

  public void moveCloseOrigin() {
	 
	//float firstReading = readLineDarkness();
	
    navigator.turnTo(45);

	leftMotor.setSpeed(FORWARD_SPEED);
	rightMotor.setSpeed(FORWARD_SPEED);
	
	leftMotor.rotate(convertDistance(radius, EXTRA_DISTANCE), true);
	rightMotor.rotate(convertDistance(radius, EXTRA_DISTANCE));
	
	/*
	float sample = readLineDarkness();
	while (100*Math.abs(sample - firstReading)/firstReading < COLOUR_DIFF) {
	  sample = readLineDarkness();
	  leftMotor.forward();
	  rightMotor.forward();

	}
	leftMotor.stop(true);
	rightMotor.stop();
	  
    leftMotor.rotate(convertDistance(radius, -(LIGHT_LOC_DISTANCE + EXTRA_DISTANCE)), true);
	rightMotor.rotate(convertDistance(radius, -(LIGHT_LOC_DISTANCE + EXTRA_DISTANCE)));
	*/

  }
  
	/**
	 * This method allows the conversion of a distance to the total rotation of each wheel needed to
	 * cover that distance.
	 * 
	 * @param radius
	 * @param distance
	 * @return
	 */
  private static int convertDistance(double radius, double distance) {
	  return (int) ((180.0 * distance) / (Math.PI * radius));
  }
	
	/**
	 * This method allows the conversion of a robot's rotation in place into 
	 * the total number of rotations of each wheel needed to cause that rotation.
	 * 
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
  private static int convertAngle(double radius, double width, double angle) {
	return convertDistance(radius, Math.PI * width * angle / 360.0);
  }  
  
  private float readLineDarkness() {
	  csLineDetector.fetchSample(csData, 0);
	  return csData[0] * 1000;
  }
	
}