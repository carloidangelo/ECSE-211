package ca.mcgill.ecse211.lab5;

import ca.mcgill.ecse211.odometer.Odometer;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class LightLocalizer {
	private Odometer odo;
	private Navigation navigation;
	private static int FORWARD_SPEED = 100;
	private static double SENSOR_DISTANCE = 7;
	double [] lightData;
	
	private SampleProvider colorSensor;
	private float[] colorData;
	private float previousValue = 0;
	private float currentValue = 0;
	
	public LightLocalizer(Odometer odo, SampleProvider colorSensor, float[] colorData) {
		this.odo = odo;
		this.navigation = new Navigation(odo);
		this.lightData = new double [4];
		
		this.colorSensor = colorSensor;
		this.colorData = colorData;
	}
	
	/** This method drives to location listed in tutorial, starts rotating
	 *  and clocks all 4 gridlines do trig to compute (0,0) and 0 degrees 
	 * when done travel to (0,0) and turn to 0 degrees
	 */
	public void doLocalization() {

		// move to our estimated origin
		moveToOriginEstimate(); 
		
		// move our vehicle in a circle and collect data from light sensors
		rotateLightSensor();
		
		// correct position of our robot using light sensor data
		correctPositionUsingLightSensorData();
		
		//travel to 0,0 then turn to the 0 angle
		navigation.travelTo(0, 0);
		navigation.stop();
		navigation.turnTo(0, true);
	}
	
	/**
	 * A method which moves our vehicle to the estimated origin after localizing to 0 degrees
	 */
	private void moveToOriginEstimate() {
		// turn towards corner, and move backwards until sensor reads a line
		navigation.turnTo(45, true);
		this.colorSensor.fetchSample(colorData, 0);
		
		while ( colorData[0] > 0.37 ){
			navigation.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
			this.colorSensor.fetchSample(colorData, 0);
		}
		
		Sound.beep();
 		navigation.stop();
 		//move forward so that the middle point of the robot is approximatelly on 0,0
 		navigation.goForward(SENSOR_DISTANCE);
 		navigation.stop();
	}
	
	/** 
	 * A method to rotate our vehicle and collect data from light sensors
	 */
	private void rotateLightSensor() {
		navigation.setSpeeds(-Navigation.SLOW , Navigation.SLOW);
		int lineIndex=0;
		while(lineIndex < 4){
			this.colorSensor.fetchSample(colorData, 0);
			// Differential filter
		    currentValue = colorData[0] *1000;
		    float difference = currentValue - previousValue;
		    previousValue = currentValue;
			if(difference < -48){
				lightData[lineIndex]=odo.getXYT()[2];
				lineIndex++;
				Sound.beep();
			}
		}
		navigation.stop();
	}
	
	/**
	 * A method to correct the position of our robot using light sensor data
	 */
	private void correctPositionUsingLightSensorData() {
		//compute difference in angles
		double deltaThetaY= (lightData[3]-lightData[1]);
		double deltaThetaX= (lightData[2]-lightData[0]);
		
		//use trig to determine position of the robot 
		double Xnew = (-1)*SENSOR_DISTANCE*Math.cos(Math.PI*deltaThetaX/(2*180));
		double Ynew = (-1)*SENSOR_DISTANCE*Math.cos(Math.PI*deltaThetaY/(2*180));
		
		//set new "corrected" position
		odo.setXYT(Xnew, Ynew, Math.atan2(Ynew, Xnew)+180);
	}

}