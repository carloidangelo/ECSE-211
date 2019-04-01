package ca.mcgill.ecse211.model;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * This class is responsible for driving the clamp motor to load and unload the can.
 * @author Carlo D'Angelo
 * 
 */

public class Clamp {
	private static final int ROTATE_AMOUNT = 198;
	private static final int ROTATION_SPEED = 75;
	private static final int TURN_ERROR = 10;
	private EV3LargeRegulatedMotor clampMotor;
	
	public Clamp(EV3LargeRegulatedMotor clampMotor) {
		this.clampMotor = clampMotor;
	}
	
	/**
	 * grabCan() rotates the motor clockwise to hold onto the can.
	 */
	public void grabCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(ROTATE_AMOUNT);
	}
	
	/**
	 * offloadCan() rotates the motor counter clockwise to let the can go.
	 */
	public void offloadCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(-ROTATE_AMOUNT);
	}
}
