package ca.mcgill.ecse211.odometer;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Odometer extends OdometerData implements Runnable {

  private OdometerData odoData;
  private static Odometer odo = null; // Returned as singleton

  // Motors and related variables
  private int leftMotorTachoCount;
  private int rightMotorTachoCount;
  //previous values of motor's tacho count
  private int leftMotorTachoCountOld;
  private int rightMotorTachoCountOld;
  private double Theta;
  private EV3LargeRegulatedMotor leftMotor;
  private EV3LargeRegulatedMotor rightMotor;

  private final double TRACK;
  private final double WHEEL_RAD;

  private static final long ODOMETER_PERIOD = 25; // odometer update period in ms

  /**
   * This is the default constructor of this class. It initiates all motors and variables once.It
   * cannot be accessed externally.
   * 
   * @param leftMotor
   * @param rightMotor
   * @throws OdometerExceptions
   * */
   
  private Odometer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
      final double TRACK, final double WHEEL_RAD) throws OdometerExceptions {
    odoData = OdometerData.getOdometerData(); // Allows access to x,y,z
                                              // manipulation methods
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;

    // Reset the values of x, y and z to 0
    odoData.setXYT(0, 0, 0);

    this.leftMotorTachoCount = 0;
    this.rightMotorTachoCount = 0;

    this.TRACK = TRACK;
    this.WHEEL_RAD = WHEEL_RAD;

  }

  /**
   * This method is meant to ensure only one instance of the odometer is used throughout the code.
   * 
   * @param leftMotor
   * @param rightMotor
   * @return new or existing Odometer Object
   * @throws OdometerExceptions
   **/
  public synchronized static Odometer getOdometer(EV3LargeRegulatedMotor leftMotor,
      EV3LargeRegulatedMotor rightMotor, final double TRACK, final double WHEEL_RAD)
      throws OdometerExceptions {
    if (odo != null) { // Return existing object
      return odo;
    } else { // create object and return it
      odo = new Odometer(leftMotor, rightMotor, TRACK, WHEEL_RAD);
      return odo;
    }
  }

 /**   * This class is meant to return the existing Odometer Object. It is meant to be used only if an/   * odometer object has been created
   * 
   * @return error if no previous odometer exists
   */
  public synchronized static Odometer getOdometer() throws OdometerExceptions {

    if (odo == null) {
      throw new OdometerExceptions("No previous Odometer exits.");

    }
    return odo;
  }

  /**
   * This method is where the logic for the odometer will run. Use the methods provided from the
   * OdometerData class to implement the odometer.
 */
  // run method (required for Thread)
  public void run() {
    long updateStart, updateEnd;

    while (true) {
      updateStart = System.currentTimeMillis();
      
      //get tacho count
      leftMotorTachoCount = leftMotor.getTachoCount();
      rightMotorTachoCount = rightMotor.getTachoCount();
      
      double distL, distR, deltaD, deltaT, dX, dY;
      //compute wheel displacements
      distL = Math.PI * WHEEL_RAD * (leftMotorTachoCount - leftMotorTachoCountOld) / 180;
      distR = Math.PI * WHEEL_RAD * (rightMotorTachoCount - rightMotorTachoCountOld) / 180;

      //getting previous tacho count values
      leftMotorTachoCountOld = leftMotorTachoCount;
      rightMotorTachoCountOld = rightMotorTachoCount;
      
      //compute vehicle displacement
      deltaD = 0.5*(distL+distR);
      
      //compute change in heading
      deltaT = ((distL-distR)/TRACK)* 180/Math.PI;
      
      //update heading
      Theta += deltaT;
      
      //compute X and Y of displacement
      dX = deltaD*Math.sin(Theta*Math.PI/180);
      dY = deltaD*Math.cos(Theta*Math.PI/180);
      
      odo.update(dX, dY, deltaT);

      // this ensures that the odometer only runs once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < ODOMETER_PERIOD) {
        try {
          Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
        } catch (InterruptedException e) {
          // there is nothing to be done
        }
      }
    }
  }
  
  /**
   * Get the left and the right motor
   * @return
   */
  public EV3LargeRegulatedMotor [] getMotors() {
		return new EV3LargeRegulatedMotor[] {this.leftMotor, this.rightMotor};
  }
  
  /**
   * Get the wheel radius
   * @return
   */
  public double getWheelRadius(){
		return this.WHEEL_RAD;
  }

}

