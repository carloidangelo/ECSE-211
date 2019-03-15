package ca.mcgill.ecse211.model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import org.json.simple.parser.ParseException;

import ca.mcgill.ecse211.WiFiClient.WifiConnection;
import ca.mcgill.ecse211.lab5.Project;

public class Robot {
	//Robot related parameters
	public static final double WHEEL_RAD = 2.2;
	public static final double TRACK = 13.2;
	
	private final int TEAM_NUMBER = Project.TEAM_NUMBER;
	// Holds the Wifi data
	private Map data;
	
	public Robot(WifiConnection wifi) throws UnknownHostException, IOException, ParseException {
		data = wifi.getData();
	}
	
	private int getRedTeam() {
		return 0;
	}
	
	public int getStartingCorner() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getHomeZoneLLX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getHomeZoneLLY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getHomeZoneURX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getHomeZoneURY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getIslandLLX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getIslandLLY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getIslandURX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getIslandURY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getTunnelLLX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getTunnelLLY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getTunnelURX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getTunnelURY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getSearchZoneLLX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getSearchZoneLLY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getSearchZoneURX() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
	public int getSearchZoneURY() {
		if (getRedTeam() == TEAM_NUMBER) {
			
		}else {
			
		}
		return 0;
	}
}
