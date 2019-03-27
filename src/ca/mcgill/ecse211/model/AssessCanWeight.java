package ca.mcgill.ecse211.model;

import lejos.robotics.SampleProvider;

public class AssessCanWeight {

	private float[] tsData;
	private SampleProvider myTouchStatus;
	
	public AssessCanWeight(float[] tsData, SampleProvider myTouchStatus) {
		this.tsData = tsData;
		this.myTouchStatus = myTouchStatus;
	}
	
	public int run() {
		return (int) sampleData();
	}
	
	private float sampleData(){
		myTouchStatus.fetchSample(tsData, 0);
		return tsData[0];
	}
}
