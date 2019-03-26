package ca.mcgill.ecse211.model;

public class ReturnHome {
	private final double TILE_SIZE = Navigation.TILE_SIZE;
	private int startingCorner, homeZoneLLX, homeZoneLLY, homeZoneURX, homeZoneURY,
					tunnelLLX, tunnelLLY, tunnelURX, tunnelURY;
	private int islandLLX, islandLLY, islandURX, islandURY,
					searchZoneLLX, searchZoneLLY, searchZoneURX, searchZoneURY;
	private LightLocalizer lightLocalizer;
	private Navigation navigator;
	
	public ReturnHome(Robot robot, LightLocalizer lightLocalizer, 
						Navigation navigator) throws OdometerExceptions {
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
	
	public void goHome() {
		
	}

}
