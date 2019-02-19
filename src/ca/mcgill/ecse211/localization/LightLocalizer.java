package ca.mcgill.ecse211.localization;

import lejos.hardware.Sound;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.SampleProvider;
import ca.mcgill.ecse211.odometer.*;
import ca.mcgill.ecse211.lab5.*;

public class LightLocalizer {


  public final static int ROTATION_SPEED = 100;
  private final static int FORWARD_SPEED = 80;
  private final static int ROTATE_SPEED = 100;  
  
  private final static int COLOUR_DIFF = 20;  
  private final static double LIGHT_LOC_DISTANCE = 14.3;
  
  private Odometer odo;
  private EV3LargeRegulatedMotor leftMotor, rightMotor;
 
  private double firstReading;
  private float sample;
  double[] linePosition;
  
  private double currentx;
  private double currenty;	
  private double currentTheta;
  private double deltax;
  private double deltay;
  
  private SampleProvider csLineDetector;
  private float[] csData;

  public LightLocalizer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
		  SampleProvider csLineDetector, float[] csData) throws OdometerExceptions {

	this.odo = Odometer.getOdometer();
	this.leftMotor = leftMotor;
	this.rightMotor = rightMotor;
	this.csLineDetector = csLineDetector;
	this.csData = csData;
	linePosition = new double[4];
	}

	/**
	 * This method localizes the robot using the light sensor to precisely move to
	 * the right location
	 * @param sample
	 * @param count
	 */
  public void lightLocalize() {
	  
	  int count = 0;
	  leftMotor.setSpeed(ROTATION_SPEED);
	  rightMotor.setSpeed(ROTATION_SPEED);
	  
	  moveCloseOrigin();

	  firstReading = readLineDarkness();
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

	  double deltax, deltay, anglex, angley, deltathetaY;

	  
	  angley = linePosition[3] - linePosition[1];
	  anglex = linePosition[2] - linePosition[0];

	  deltax = -LIGHT_LOC_DISTANCE * Math.cos(Math.toRadians(angley / 2));
	  deltay = -LIGHT_LOC_DISTANCE * Math.cos(Math.toRadians(anglex / 2));
	  
	  deltathetaY = (Math.PI / 2.0) - linePosition[3] + Math.PI + (angley / 2.0);

	  
	  odo.setXYT(deltax, deltay, odo.getXYT()[2]);
	  travelTo(0.0, 0.0);

      leftMotor.setSpeed(ROTATION_SPEED / 2);
	  rightMotor.setSpeed(ROTATION_SPEED / 2);

	  
	  if (odo.getXYT()[2] <= 350 && odo.getXYT()[2] >= 10.0) {
		Sound.beep();
		leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, -odo.getXYT()[2]+ deltathetaY), true);
		rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, -odo.getXYT()[2] + deltathetaY), false);
		}

		leftMotor.stop(true);
		rightMotor.stop();

	}

  public void moveCloseOrigin() {
	  
	firstReading = readLineDarkness();
    turnTo(Math.PI / 4);

	leftMotor.setSpeed(ROTATION_SPEED);
	rightMotor.setSpeed(ROTATION_SPEED);

	sample = readLineDarkness();

	while (100*Math.abs(sample - firstReading)/firstReading < COLOUR_DIFF) {
	  sample = readLineDarkness();
	  leftMotor.forward();
	  rightMotor.forward();

	  }
	  leftMotor.stop(true);
	  rightMotor.stop();
	  
      leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, -LIGHT_LOC_DISTANCE), true);
	  rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, -LIGHT_LOC_DISTANCE), false);

	}
  
  public void travelTo(double x, double y) {
	  currentx = odo.getXYT()[0];
	  currenty = odo.getXYT()[1];

	  deltax = x - currentx;
	  deltay = y - currenty;
	
	
	  currentTheta = (odo.getXYT()[2]) * Math.PI / 180;
	  double mTheta = Math.atan2(deltax, deltay) - currentTheta;
	
	  double traveldistance = Math.hypot(deltax, deltay);

	
	  turnTo(mTheta);

	  leftMotor.setSpeed(FORWARD_SPEED);
	  rightMotor.setSpeed(FORWARD_SPEED);

	  leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, traveldistance), true);
	  rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, traveldistance), false);

		
	  leftMotor.stop(true);
	  rightMotor.stop(true);
	}

  public void turnTo(double theta) {
	
	double currentT = (odo.getXYT()[2]) * Math.PI / 180;
   
	double deltatheta = (theta - currentT) * 180 / Math.PI;
	
    deltatheta = (deltatheta + 360) % 360;
    
    if (Math.abs(deltatheta - 360) < deltatheta) {
      deltatheta -= 360;
	  }

	leftMotor.setSpeed(ROTATE_SPEED);
	rightMotor.setSpeed(ROTATE_SPEED);
	leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, deltatheta), true);
	rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, deltatheta), false);

  }
  
  private float readLineDarkness() {
	  csLineDetector.fetchSample(csData, 0);
	  return csData[0] * 1000;
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
	
}