package ca.mcgill.ecse211.searchzonelocator;

import ca.mcgill.ecse211.lab5.Lab5;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;
import lejos.hardware.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class SearchZoneLocator {

	private Odometer odo;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	private static final int FORWARD_SPEED = 100;
	public final static int ROTATION_SPEED = 100;
	private static final double TILE_SIZE = 30.48;
	private int LLx, LLy, URx, URy, SC;
	
	private double currentX;
	private double currentY;	
	private double currentA;
	private double deltaX;
	private double deltaY;
	
	private double radius = Lab5.WHEEL_RAD;
	private double track = Lab5.TRACK;
	
	public SearchZoneLocator(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
			int SC, int LLx, int LLy, int URx, int URy) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.SC = SC;	
		this.LLx = LLx;
		this.LLy = LLy;
		this.URx = URx;
		this.URy = URy;
	}
	
	/** CheckSC() checks the starting corner of the EV3. Depending on where
	 *  it was placed to begin, the EV3 will make its way to the search zone
	 * and corrects its angle to follow the convention given in Lab 5.
	 */
	public void goToSearchZone(){
		
		switch(SC){ //NOTE, check if splitting LLx LLy is more accurate
			case 0: 
				//current position is (1,1)
				odo.setXYT(1.0 * TILE_SIZE, 1.0 * TILE_SIZE, 0.0);
				//go to LLx
				//go to LLy
				travelTo(LLx,1);
				travelTo(LLx,LLy);
				break;
			case 1: 
				//current position is (7,1)
				odo.setXYT(7.0 * TILE_SIZE, 1.0 * TILE_SIZE, 270.0);
				//go to LLx
				//go to LLy
				travelTo(LLx,1);
				travelTo(LLx,LLy);
				break;
			case 2: 
				//current position is (7,7)
				odo.setXYT(7.0 * TILE_SIZE, 7.0 * TILE_SIZE, 180.0);
				//go to LLx
				//go to LLy
				travelTo(LLx,7);
				travelTo(LLx,LLy);
				break;
			case 3:	
				//current position is (1,7)
				odo.setXYT(1.0 * TILE_SIZE, 7.0 * TILE_SIZE, 90.0);
				//go to LLx
				//go to LLy
				travelTo(LLx,7);
				travelTo(LLx,LLy);
				break;
		    default:
		    	System.out.println("Error - invalid button"); // None of the above - abort
		        System.exit(-1);
		        break;
		}
		
	}  
	public void travelTo(double x, double y) {
		currentX = odo.getXYT()[0];
		currentY = odo.getXYT()[1];

		deltaX = x * TILE_SIZE - currentX;
		deltaY = y * TILE_SIZE - currentY;
			
		currentA = odo.getXYT()[2];
			
		// find minimum angle
		double minAng = - currentA + Math.atan2(deltaX, deltaY) * 180 / Math.PI;
			
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

		leftMotor.stop(true);
		rightMotor.stop(true);
			
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
