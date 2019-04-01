package ca.mcgill.ecse211.model;

import lejos.robotics.SampleProvider;

/**
 * This class allows the robot to correctly identify a can's weight by
 * fetching the touch sensor's sample. 
 * @author Carlo D'Angelo, Mohamed Samee
 *
 */

public class AssessCanWeight {

	private float[] tsData;
	private SampleProvider myTouchStatus;
	
	public AssessCanWeight(float[] tsData, SampleProvider myTouchStatus) {
		this.tsData = tsData;
		this.myTouchStatus = myTouchStatus;
	}
	
	/**
 	* This method returns the touch sensor's fetched data casted into an int. 
 	*/
	public int run() {
		return (int) sampleData();
	}
	
	/**
	 * This method fetches the data from the touch sensor.
	 */
	private float sampleData(){
		myTouchStatus.fetchSample(tsData, 0);
		return tsData[0];
	}
}
