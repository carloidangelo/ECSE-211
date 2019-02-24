package ca.mcgill.ecse211.canlocator;

import java.util.ArrayList;
import java.util.Collections;

import ca.mcgill.ecse211.lab5.ColorClassification;
import ca.mcgill.ecse211.lab5.Lab5;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class CanDetect {
	
	private EV3LargeRegulatedMotor csMotor;
	private ColorClassification csFront;
	private double radius = Lab5.WHEEL_RAD;
	public static final double ROTATE_DIS = 7;
	public static final int ROTATE_COUNT = 20;
	public final static int ROTATION_SPEED = 10;
	
	public CanDetect(EV3LargeRegulatedMotor csMotor, ColorClassification csFront) {
		this.csMotor = csMotor;
		this.csFront = csFront;
	}
	
	public int run() {
		csMotor.setAcceleration(10);
		csMotor.setSpeed(ROTATION_SPEED);
		ArrayList<String> canColor = new ArrayList<String>();
		ArrayList<Integer> frequency = new ArrayList<Integer>();
		int count = 0;
		while (count < ROTATE_COUNT) {
			csMotor.rotate(-convertDistance(radius, ROTATE_DIS));
			canColor.add(csFront.run());
			count++;
		}
		csMotor.stop();
		csMotor.rotate(convertDistance(radius, ROTATE_DIS) * ROTATE_COUNT);
		frequency.add(Collections.frequency(canColor, "blue     "));
		frequency.add(Collections.frequency(canColor, "green    "));
		frequency.add(Collections.frequency(canColor, "yellow   "));
		frequency.add(Collections.frequency(canColor, "red      "));
		Integer obj = Collections.max(frequency);
		int index = frequency.indexOf(obj);
		System.out.println(index + 1);
		return index + 1;	
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
