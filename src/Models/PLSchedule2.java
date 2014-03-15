/**
 * 
 * This arranges schedule for placeits in milestone 2
 * It is the new version for PLSchedule
 */

package Models;

import java.util.LinkedList;
import java.util.List;


public class PLSchedule2 {

	// private variables
	int _id;
	String placeitID; // this is a foreign key to placeIt
	int startweek;
	int day;
	int week;
	//List<Integer> schedules;

	//// constructors ////
	public PLSchedule2(String placeitID) {
		this.placeitID = placeitID;
		this.day = -1;
		this.week = -1;
	}

	public PLSchedule2(String placeitID, int startweek, int day, int week) {
		this.placeitID = placeitID;
		this.startweek = startweek;
		this.day = day;
		this.week = week;
	}

	//// methods ////
	
	public String getPlaceItId() {
		return this.placeitID;
	}

	public void setPlaceItId(String placeitID) {
		this.placeitID = placeitID;
	}
	
	public int getStartWeek() {
		return this.startweek;
	}
	
	public void setStartWeek(int newStartWeek) {
		this.startweek = newStartWeek;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public void setDay(int newDay) {
		this.day = newDay;
	}
	
	public int getWeek() {
		return this.week;
	}
	
	public void setWeek(int newWeek) {
		this.week = newWeek;
	}

	/*  no longer needed functions
	public List<Integer> getInteger() {
		return this.schedules;
	}

	public void setInteger(List<Integer> schedules) {
		this.schedules = schedules;
	}

	public boolean addDay(Integer day) {
		if (this.schedules.contains(day)) {
			return false;
		}
		this.schedules.add(day);
		return true;
	}

	public boolean removeDay(Integer day) {
		if (this.schedules.contains(day)) {
			this.schedules.remove(day);
			return true;
		} else {
			return false;
		}

	}
	*/
}
