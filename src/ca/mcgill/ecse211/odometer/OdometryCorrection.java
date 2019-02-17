/*
 * OdometryCorrection.java
 */
package ca.mcgill.ecse211.odometer;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;
  private static final double TILE_LENGTH = 30.48;
  private Odometer odometer;
  
  //getting port for the light sensor
  private static final Port colorSamplerPort = LocalEV3.get().getPort("S1");

  private SensorModes colorSamplerSensor = new EV3ColorSensor(colorSamplerPort);
  
  //setting the sensor mode to red
  private SampleProvider colorSensorValue = colorSamplerSensor.getMode("Red");

  //storing all the captured sensored data into an array
  private float[] colorSensorData = new float[colorSamplerSensor.sampleSize()];

  private float prevValue;
	
  private int counterX;
  private int counterY;
	
  private double theta;

  /**
   * This is the default class constructor. An existing instance of the odometer is used. This is to
   * ensure thread safety.
   * 
   * @throws OdometerExceptions
   */
  public OdometryCorrection() throws OdometerExceptions {

    this.odometer = Odometer.getOdometer();

  }

  /**
   * Here is where the odometer correction code should be run.
   * 
   * @throws OdometerExceptions
   */
  // run method (required for Thread)
  public void run() {
    long correctionStart, correctionEnd;

    while (true) {
      correctionStart = System.currentTimeMillis();

      // TODO Trigger correction (When do I have information to correct?)
      // TODO Calculate new (accurate) robot position

      // TODO Update odometer with new calculated (and more accurate) vales
      
    //fetching the values from the color sensor
      colorSensorValue.fetchSample(colorSensorData, 0);
		
      //getting the value returned from the sensor, and multiply it by 1000 to scale
      float value = colorSensorData[0]*1000;
		
      //computing the derivative at each point
      float difference = value - prevValue;

      //storing the current value so that we can get the derivative on the next iteration
      prevValue = value;
      
      //if the difference at a given point is less than -40, this means that a black line is detected
      if(difference < -40) {
			
    	  theta = odometer.getXYT()[2] * 180 / Math.PI;
    	  //robot beeps
    	  Sound.beep();
    	  
    	  //when going to positive x-axis
    	  if(theta > 45 && theta <= 135){
        	 odometer.setX(counterX * TILE_LENGTH);
        	 counterX++;	
    	  }
    	//when going to positive y-axis
    	  else if( (theta <= 360 && theta >= 315) || (theta >=0 && theta <= 45)){
    		  odometer.setY(counterY * TILE_LENGTH);
    		  counterY++;
    	  }
    	  
    	//when going to negative y-axis
    	  else if(theta > 135 && theta <= 225){ 
    		  odometer.setY((counterY-1) * TILE_LENGTH);
    		  counterY--;
    	  }
    	  
    	//when going to negative x-axis
    	  else if(theta > 225 && theta < 270){
    		  odometer.setX((counterX-1) * TILE_LENGTH);
    		  counterX--;
    	  }
		}

      //odometer.setXYT(0.3, 19.23, 5.0);

      // this ensure the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        try {
          Thread.sleep(CORRECTION_PERIOD - (correctionEnd - correctionStart));
        } catch (InterruptedException e) {
          // there is nothing to be done here
        }
      }
    }
  }
}

