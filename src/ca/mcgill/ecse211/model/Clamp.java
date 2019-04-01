package ca.mcgill.ecse211.model;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Clamp {
	private static final int ROTATE_AMOUNT = 198;
	private static final int ROTATION_SPEED = 75;
	private static final int TURN_ERROR = 10;
	private EV3LargeRegulatedMotor clampMotor;
	
	public Clamp(EV3LargeRegulatedMotor clampMotor) {
		this.clampMotor = clampMotor;
	}
	
	public void grabCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(ROTATE_AMOUNT);
	}
	
	public void offloadCan() {
		clampMotor.setAcceleration(1000);
		clampMotor.setSpeed(ROTATION_SPEED);
		clampMotor.rotate(-ROTATE_AMOUNT);
	}
}
