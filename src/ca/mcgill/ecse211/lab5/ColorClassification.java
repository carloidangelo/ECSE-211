package ca.mcgill.ecse211.lab5;
import java.util.Arrays;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


public class ColorClassification {
  
  private EV3ColorSensor colorSensor;
  private SampleProvider Color;
  
  // use an array to collect color data
  private float[] colorData;
  
  public static String detected_color;
  
  //constructor
  public ColorClassification (EV3ColorSensor colorSensor) {
    this.colorSensor = colorSensor;
    Color = colorSensor.getMode("RGB");// usw the white light
    colorData = new float[Color.sampleSize()];
    
   
  }
  
  
  
  public double run(){
    
    return medianFilter();
    
  }
 
  /**
   * This method use a median filter to filter the data collected by the sensor and toss out the
   * invalid sample
   * 
   * This method can help avoid the effect caused by invalid readings.
   * 
   * @param void
   * @return median value
   * 
   */

  private double medianFilter() {
    double[] filterData = new double[5]; // use an array to store readings
    for (int i = 0; i < 5; i++) { // take 5 readings
      Color.fetchSample(colorData, 0);
      filterData[i] = colorData[0] * 100.0; // put the readings in array and amplify them
    }
    Arrays.sort(filterData); // sort the array
    return filterData[2]; // take median value
  }
}