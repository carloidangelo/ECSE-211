package ca.mcgill.ecse211.canlocator;

import ca.mcgill.ecse211.lab5.ColorClassification;
import ca.mcgill.ecse211.lab5.Lab5;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class CanDetect {
	
	private EV3LargeRegulatedMotor csMotor;
	private ColorClassification csFront;
	private double radius = Lab5.WHEEL_RAD;
	public static final double ROTATE_DIS = 5;
	
	public CanDetect(EV3LargeRegulatedMotor csMotor, ColorClassification csFront) {
		this.csMotor = csMotor;
		this.csFront = csFront;
	}
	
	public int run() {
		
		int count = 0;
		while(csFront.run() == "no object") {
			csMotor.rotate(-convertDistance(radius, ROTATE_DIS));
			count++;
		}
		
		csMotor.stop();
		String result = csFront.run();
		csMotor.rotate(convertDistance(radius, ROTATE_DIS) * count);
		
		switch (result) {
		case "red      ":
			return 4;
		case "yellow   ":
			return 3;
		case "blue     ":
			return 2;
		case "green    ":
			return 1;
		default:
			return 0;	
		}
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