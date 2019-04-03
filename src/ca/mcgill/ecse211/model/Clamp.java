package ca.mcgill.ecse211.model;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * This class is responsible for driving the clamp motor to load and unload the can.
 * @author Carlo D'Angelo
 * 
 */

public class Clamp {
	private static final int ROTATE_AMOUNT = 197;
	private static final int ROTATE_AMOUNT_GRIP = 10;
	private static final int ROTATION_SPEED = 75;
	private EV3LargeRegulatedMotor clampMotor;
	
	
	/**
	 * This is the default constructor of this class.
	 * @param clampMotor motor that rotates the arm that grasps the can
	 */
	public Clamp(EV3LargeRegulatedMotor clampMotor) {
		this.clampMotor = clampMotor;
	}
	
	/**
	 * This method rotates the motor clockwise to hold onto the can.
	 */
	public void grabCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(ROTATE_AMOUNT);
	}
	
	/**
	 * This method rotates the motor counter-clockwise to let the can go.
	 */
	public void offloadCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(-ROTATE_AMOUNT);
	}
	
	/**
	 * This method rotates the motor to tighten the grip of the arm
	 * on the can. The purpose of this method is to reduce the friction the can 
	 * produces with the surface.
	 */
	public void gripCan() {
		clampMotor.setAcceleration(500);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(ROTATE_AMOUNT_GRIP);
	}
}
