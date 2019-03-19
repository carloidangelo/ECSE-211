package ca.mcgill.ecse211.model;

/**
 * This class handles the robot's navigation from the starting corner
 * to the search zone after localization is complete
 * 
 * @author Carlo D'Angelo
 *
 */
public class SearchZoneLocator {

	private Odometer odo;
	private final double TILE_SIZE = Navigation.TILE_SIZE;
	private int startingCorner, homeZoneLLX, homeZoneLLY, homeZoneURX, homeZoneURY,
					tunnelLLX, tunnelLLY, tunnelURX, tunnelURY;
	private int islandLLX, islandLLY, islandURX, islandURY,
					searchZoneLLX, searchZoneLLY, searchZoneURX, searchZoneURY;
	private LightLocalizer lightLocalizer;
	private Navigation navigator;
	
	/**
	 * 
	 * @param robot
	 * @param lightLocalizer
	 * @param navigator
	 * @throws OdometerExceptions
	 */
	public SearchZoneLocator(Robot robot, LightLocalizer lightLocalizer, 
								Navigation navigator) throws OdometerExceptions {
		odo = Odometer.getOdometer();
		startingCorner = robot.getStartingCorner();	
		homeZoneLLX = robot.getHomeZoneLLX();
		homeZoneLLY = robot.getHomeZoneLLY();
		homeZoneURX = robot.getHomeZoneURX();
		homeZoneURY = robot.getHomeZoneURY();
		tunnelLLX = robot.getTunnelLLX();
		tunnelLLY = robot.getTunnelLLY();
		tunnelURX = robot.getTunnelURX();
		tunnelURY = robot.getTunnelURY();
		islandLLX = robot.getIslandLLX();
		islandLLY = robot.getIslandLLY();
		islandURX = robot.getIslandURX();
		islandURY = robot.getIslandURY();
		searchZoneLLX = robot.getSearchZoneLLX();
		searchZoneLLY = robot.getSearchZoneLLY();
		searchZoneURX = robot.getSearchZoneURX();
		searchZoneURY = robot.getSearchZoneURY();
		this.lightLocalizer = lightLocalizer;
		this.navigator= navigator;
	}
	
	/** 
	 * Method that allows the robot to make its way to the search zone
	 * The path the robot takes will depend on the starting corner (startingCorner) parameter
	 */
	public void goToSearchZone(){
		// set new position and new angle after localization
		// current position and current angle will depend on starting corner
		switch(startingCorner){
			case 0: 
				odo.setXYT((homeZoneLLX + 1) * TILE_SIZE, (homeZoneLLY + 1) * TILE_SIZE, 0.0);
				if (homeZoneURX < islandLLX) {
					if(homeZoneLLY == tunnelLLY) {
						navigator.travelTo(tunnelLLX - 1, tunnelURY);
						lightLocalizer.lightLocalize(tunnelLLX - 1, tunnelURY);
						navigator.turnTo(-180);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(-90);
					} else {
						navigator.travelTo(tunnelLLX - 1, tunnelLLY);
						lightLocalizer.lightLocalize(tunnelLLX - 1, tunnelLLY);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(90);
					}
					navigator.driveForward(((tunnelURX - tunnelLLX) + 2) * TILE_SIZE);
					if (tunnelURY == islandURY) {
						navigator.turnTo(90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(-135);
						lightLocalizer.lightLocalize(tunnelURX + 1, tunnelLLY);
					} else {
						navigator.turnTo(-90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(45);
						lightLocalizer.lightLocalize(tunnelURX + 1, tunnelURY);
					}
					double yComponent = odo.getXYT()[1];
					navigator.travelTo(searchZoneLLX, searchZoneLLY);
					if (yComponent == searchZoneLLY) {
						navigator.turnTo(-45);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (yComponent < searchZoneLLY) {
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (yComponent > searchZoneLLY) {
						navigator.turnTo(-90);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					}
					
				} else if (homeZoneURY < islandLLY) {
					if(homeZoneLLX == tunnelLLX) {
						navigator.travelTo(tunnelURX, tunnelLLY - 1);
						lightLocalizer.lightLocalize(tunnelURX,  tunnelLLY - 1);
						navigator.turnTo(-90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(90);
					} else {
						navigator.travelTo(tunnelLLX, tunnelLLY - 1);
						lightLocalizer.lightLocalize(tunnelLLX, tunnelLLY - 1);
						navigator.turnTo(90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(-90);
					}
					navigator.driveForward(((tunnelURY - tunnelLLY) + 2) * TILE_SIZE);
					if (tunnelURX == islandURX) {
						navigator.turnTo(-90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(135);
						lightLocalizer.lightLocalize(tunnelLLX, tunnelURY + 1);
					} else {
						navigator.turnTo(90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(-45);
						lightLocalizer.lightLocalize(tunnelURX, tunnelURY + 1);
					}
					double xComponent = odo.getXYT()[0];
					navigator.travelTo(searchZoneLLX, searchZoneLLY);
					if (xComponent == searchZoneLLX) {
						navigator.turnTo(45);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (xComponent < searchZoneLLX) {
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (xComponent > searchZoneLLX) {
						navigator.turnTo(90);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					}
				}
				break;
			case 1: 
				odo.setXYT((homeZoneURX - 1) * TILE_SIZE, (homeZoneLLY + 1) * TILE_SIZE, 270.0);
				break;
			case 2: 
				odo.setXYT((homeZoneURX - 1) * TILE_SIZE, (homeZoneURY - 1) * TILE_SIZE, 180.0);
				break;
			case 3:	
				odo.setXYT((homeZoneLLX + 1) * TILE_SIZE, (homeZoneURY - 1) * TILE_SIZE, 90.0);
				break;
		    default:
		    	System.out.println("Error - invalid button"); // None of the above - abort
		        System.exit(-1);
		        break;
		}
		
	} 
  
}
