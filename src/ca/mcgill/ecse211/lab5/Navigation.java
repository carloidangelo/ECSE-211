package ca.mcgill.ecse211.lab5;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import ca.mcgill.ecse211.odometer.*;

public class Navigation {

  public final static int ROTATION_SPEED = 100;
  private final static int FORWARD_SPEED = 150; 
  
  private static final double TILE_SIZE = 30.48;
  
  private double radius = Lab5.WHEEL_RAD;
  private double track = Lab5.TRACK;
  
  private Odometer odo;
  private EV3LargeRegulatedMotor leftMotor, rightMotor;
 
  public static double minAng;

  public Navigation(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) throws OdometerExceptions {
	odo = Odometer.getOdometer();
	this.leftMotor = leftMotor;
	this.rightMotor = rightMotor;
	}
  
  public void travelTo(double x, double y) {
	double currentX = odo.getXYT()[0];
	double currentY = odo.getXYT()[1];

	double deltaX = x * TILE_SIZE - currentX;
	double deltaY = y * TILE_SIZE - currentY;
	
	double currentA = odo.getXYT()[2];
	
	// find minimum angle
	minAng = -currentA + Math.atan2(deltaX, deltaY) * 180 / Math.PI;
	
	// special cases so that robot does not turn at maximum angle
	if (minAng < 0 && (Math.abs(minAng) >= 180)) {
		minAng += 360;
	}else if (minAng > 0 && (Math.abs(minAng) >= 180)){
		minAng -= 360;
	}
	turnTo(minAng);
	
	double distance = Math.hypot(deltaX, deltaY);

	leftMotor.setSpeed(FORWARD_SPEED);
	rightMotor.setSpeed(FORWARD_SPEED);

	leftMotor.rotate(convertDistance(radius, distance), true);
	rightMotor.rotate(convertDistance(radius, distance), false);
	
  }

  public void turnTo(double theta) {
	leftMotor.setSpeed(ROTATION_SPEED);
	rightMotor.setSpeed(ROTATION_SPEED);
	leftMotor.rotate(convertAngle(radius, track, theta), true);
	rightMotor.rotate(-convertAngle(radius, track, theta), false);

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