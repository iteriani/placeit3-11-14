package Models;

import java.util.LinkedList;
import java.util.List;


public class PLSchedule {

	// private variables
	int _id;
	int placeitID; // this is a foreign key to placeIt
	int day;
	int week;
	//List<Integer> schedules;

	//// constructors ////
	public PLSchedule(int placeitID) {
		this.placeitID = placeitID;
		this.day = -1;
		this.week = -1;
	}

	public PLSchedule(int placeitID, int day, int week) {
		this.placeitID = placeitID;
		this.day = day;
		this.week = week;
	}

	//// methods ////
	
	public int getPlaceItId() {
		return this.placeitID;
	}

	public void setPlaceItId(int placeitID) {
		this.placeitID = placeitID;
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
