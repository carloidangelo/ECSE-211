package ca.mcgill.ecse211.localization;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.SampleProvider;
import ca.mcgill.ecse211.odometer.*;
import ca.mcgill.ecse211.lab5.*;

public class UltrasonicLocalizer {

	public static final int ROTATION_SPEED = 100;
	
	public static final double CRITICAL_DISTANCE = 30.00;
	public static final double NOISE_MARGIN = 2.00;

	private static final double TURN_ERROR = 3.5;
  
	private double radius = Lab5.WHEEL_RAD;
	private double track = Lab5.TRACK;
 
	private Odometer odo;
	private SampleProvider usDistance;
	private float[] usData;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
  
	private double deltaTheta;

	public UltrasonicLocalizer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
		SampleProvider usDistance, float[] usData) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.usDistance = usDistance;
		this.usData = usData;

		leftMotor.setSpeed(ROTATION_SPEED);
		rightMotor.setSpeed(ROTATION_SPEED);
	}

	public void fallingEdge() {

		double angleA, angleB, turningAngle;

		
		while (readUSDistance() < CRITICAL_DISTANCE + NOISE_MARGIN) {
			leftMotor.forward();
			rightMotor.backward();
		}
		
		while (readUSDistance() > CRITICAL_DISTANCE) {
			leftMotor.forward();
			rightMotor.backward();
		}
		
		angleA = odo.getXYT()[2];

		while (readUSDistance() < CRITICAL_DISTANCE + NOISE_MARGIN) {
			leftMotor.backward();
			rightMotor.forward();
		}

		while (readUSDistance() > CRITICAL_DISTANCE) {
			leftMotor.backward();
			rightMotor.forward();
		}
		angleB = odo.getXYT()[2];

		leftMotor.stop(true);
		rightMotor.stop();

		if (angleA < angleB) {
			deltaTheta = -angleB + ((angleA + angleB) / 2) + 135;

		} else if (angleA > angleB) {
			deltaTheta = -angleB + (angleA + angleB) / 2 - 45;
		}

		turningAngle = deltaTheta - TURN_ERROR;

		leftMotor.rotate(convertAngle(radius, track, turningAngle), true);
		rightMotor.rotate(-convertAngle(radius, track, turningAngle), false);
		odo.setTheta(0.0);

	}

	private int readUSDistance() {
		usDistance.fetchSample(usData, 0);
		return (int) (usData[0] * 100);
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