package ca.mcgill.ecse211.searchzonelocator;

import ca.mcgill.ecse211.localization.LightLocalizer;
import ca.mcgill.ecse211.odometer.Odometer;
import ca.mcgill.ecse211.odometer.OdometerExceptions;

public class SearchZoneLocator {

	private Odometer odo;
	private static final double TILE_SIZE = 30.48;
	private int LLx, LLy, SC;
	private LightLocalizer lightLocalizer;
	
	public SearchZoneLocator(int SC, int LLx, int LLy, LightLocalizer lightLocalizer) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		this.SC = SC;	
		this.LLx = LLx;
		this.LLy = LLy;
		this.lightLocalizer = lightLocalizer;
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
				lightLocalizer.travelTo(LLx,1);
				if (LLx != 1) {
					lightLocalizer.turnTo(-45);
					lightLocalizer.lightLocalize(LLx, 1);
				}
				lightLocalizer.travelTo(LLx,LLy);
				lightLocalizer.lightLocalize(LLx, LLy);
				break;
			case 1: 
				//current position is (7,1)
				odo.setXYT(7.0 * TILE_SIZE, 1.0 * TILE_SIZE, 270.0);
				//go to LLx
				//go to LLy
				lightLocalizer.travelTo(LLx,1);
				if (LLx != 7) {
					lightLocalizer.turnTo(135);
					lightLocalizer.lightLocalize(LLx, 1);
				}
				lightLocalizer.travelTo(LLx,LLy);
				lightLocalizer.lightLocalize(LLx, LLy);
				break;
			case 2: 
				//current position is (7,7)
				odo.setXYT(7.0 * TILE_SIZE, 7.0 * TILE_SIZE, 180.0);
				//go to LLx
				//go to LLy
				lightLocalizer.travelTo(LLx,7);
				if (LLx != 7) {
					lightLocalizer.turnTo(135);
					lightLocalizer.lightLocalize(LLx, 7);
				}
				lightLocalizer.travelTo(LLx,LLy);
				lightLocalizer.turnTo(-180);
				lightLocalizer.lightLocalize(LLx, LLy);
				break;
			case 3:	
				//current position is (1,7)
				odo.setXYT(1.0 * TILE_SIZE, 7.0 * TILE_SIZE, 90.0);
				//go to LLx
				//go to LLy
				lightLocalizer.travelTo(LLx,7);
				if (LLx != 1) {
					lightLocalizer.turnTo(-45);
					lightLocalizer.lightLocalize(LLx, 7);
				}
				lightLocalizer.travelTo(LLx,LLy);
				lightLocalizer.turnTo(-180);
				lightLocalizer.lightLocalize(LLx, LLy);
				break;
		    default:
		    	System.out.println("Error - invalid button"); // None of the above - abort
		        System.exit(-1);
		        break;
		}
		
	} 
  
}
