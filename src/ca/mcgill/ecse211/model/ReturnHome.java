package ca.mcgill.ecse211.model;

public class ReturnHome {
	private final double TILE_SIZE = Navigation.TILE_SIZE;
	private int startingCorner, homeZoneLLX, homeZoneLLY, homeZoneURX, homeZoneURY,
					tunnelLLX, tunnelLLY, tunnelURX, tunnelURY;
	private int islandLLX, islandLLY, islandURX, islandURY,
					searchZoneLLX, searchZoneLLY, searchZoneURX, searchZoneURY;
	private LightLocalizer lightLocalizer;
	private Navigation navigator;
	private Clamp clamp;
	private Odometer odo;
	
	public ReturnHome(Robot robot, LightLocalizer lightLocalizer, 
						Clamp clamp, Navigation navigator) throws OdometerExceptions {
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
		this.navigator = navigator;
		this.clamp = clamp;
	}
	
	public void goHome() {
		switch(startingCorner){
			case 0: 
				// corner 0
				if (homeZoneURX < islandLLX) { // horizontal tunnel
					double yComponent = odo.getXYT()[1];
					double tunnelY;
					if(islandLLY == tunnelLLY) {
						navigator.travelTo(tunnelURX + 1, tunnelY = tunnelURY);
					} else {
						navigator.travelTo(tunnelURX + 1, tunnelY = tunnelLLY);
					}
					if (yComponent == tunnelY) {
						navigator.turnTo(-45);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (yComponent < searchZoneLLY) {
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					} else if (yComponent > searchZoneLLY) {
						navigator.turnTo(-90);
						lightLocalizer.lightLocalize(searchZoneLLX, searchZoneLLY);
					}		
					navigator.travelTo(tunnelLLX - 1, tunnelURY);
					navigator.turnTo(-45);
					lightLocalizer.lightLocalize(tunnelLLX - 1, tunnelURY);
					navigator.turnTo(-180);
					navigator.driveForward(0.5 * TILE_SIZE);						navigator.turnTo(-90);
				} else {
					navigator.travelTo(tunnelLLX - 1, tunnelLLY);
					if ((homeZoneLLY + 1) == tunnelLLY) {
						navigator.turnTo(-45);
					}
					lightLocalizer.lightLocalize(tunnelLLX - 1, tunnelLLY);
					navigator.driveForward(0.5 * TILE_SIZE);
					navigator.turnTo(90);
				}
					clamp.grabCan();
					navigator.driveForward(((tunnelURX - tunnelLLX) + 2) * TILE_SIZE);
					clamp.offloadCan();
					double yComponent;
					if (tunnelURY == islandURY) {
						navigator.turnTo(90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(-135);
						lightLocalizer.lightLocalize(tunnelURX + 1, yComponent = tunnelLLY);
					} else {
						navigator.turnTo(-90);
						navigator.driveForward(0.5 * TILE_SIZE);
						navigator.turnTo(45);
						lightLocalizer.lightLocalize(tunnelURX + 1, yComponent = tunnelURY);
					}
					navigator.travelTo(searchZoneLLX, searchZoneLLY);
				break;
			case 1: 
				// corner 1
				break;
			case 2: 
				// corner 2
				break;
			case 3:	
				// corner 3
				break;
		    default:
		    	System.out.println("Error - invalid button"); // None of the above - abort
		        System.exit(-1);
		        break;
		}
		
	}

}
