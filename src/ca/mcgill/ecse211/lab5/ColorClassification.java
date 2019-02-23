package ca.mcgill.ecse211.lab5;

import lejos.robotics.SampleProvider;

// testing : return the sensor value and print it on the screen

public class ColorClassification {

  // use an array to collect color data
  private float[] colorData;
  private SampleProvider colorId;

  public static String detected_color;

  private final float[][] meanRGB = {// the mean RGB values for cans
      {0.0656862865f, 0.0127450985f, 0.0117643253f}, // red can
      {0.0404248312f, 0.0287563221f, 0.0134254234f}, // yellow can
      {0.0147062342f, 0.0405322324f, 0.0444449999f}, // blue can
      {0.0137257231f, 0.0232034322f, 0.0114382312f} // green can
  };

  // constructor
  public ColorClassification(float[] colorData, SampleProvider colorId) {
    this.colorData = colorData;
    this.colorId = colorId;
 
    



  }

  public String run() {
    int a = findColor(sampleData());

    if (a !=4) {
      String[] clrName = {"red      ", "yellow   ", "blue     ", "green    "};
      return clrName[a];
    } else {
      return "no object";
    }


  }


  public int findColor(float[] colorData) {
    float eucDistance = (float) Math.sqrt((Math.pow(colorData[0], 2) + Math.pow(colorData[1], 2) + Math.pow(colorData[2], 2)));

    // normalize R,G,B values
    float nR = colorData[0] / eucDistance;
    float nG = colorData[1] / eucDistance;
    float nB = colorData[2] / eucDistance;
    // use a counter and difference in RGB values to classify the color
    for (int i = 0; i < 4; i++) {
      float deltaR = Math.abs(nR - (meanRGB[i][0]/eucDistance));
      float deltaG = Math.abs(nG - (meanRGB[i][1]/eucDistance));
      float deltaB = Math.abs(nB - (meanRGB[i][2]/eucDistance));
      
     // System.out.println(deltaB);
 
      if (deltaR < 0.5 && deltaG < 0.5 && deltaB < 0.5) {
        
        return i;
      }

    }

    return 4;
  }
  
  public float[] sampleData(){
  colorId.fetchSample(colorData, 0);
  return colorData;
  }

}
