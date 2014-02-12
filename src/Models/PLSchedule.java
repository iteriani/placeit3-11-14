package Models;

import java.util.LinkedList;
import java.util.List;

import PlaceItDB.Schedule;

public class PLSchedule {

	// private variables
	int _id;
	int placeitID; // this is a foreign key to placeIt
	List<Schedule> schedules;

	protected PLSchedule(int placeitID) {
		this.placeitID = placeitID;
		this.schedules = new LinkedList<Schedule>();
	}

	protected PLSchedule(int placeitID, List<Schedule> schedules) {
		this(placeitID);
		this.schedules = schedules;
	}

	public int getPlaceItId() {
		return this.placeitID;
	}

	public void setPlaceItId(int placeitID) {
		this.placeitID = placeitID;
	}

	public List<Schedule> getSchedule() {
		return this.schedules;
	}

	public void setSchedule(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public boolean addDay(Schedule day) {
		if (this.schedules.contains(day)) {
			return false;
		}
		this.schedules.add(day);
		return true;
	}

	public boolean removeDay(Schedule day) {
		if (this.schedules.contains(day)) {
			this.schedules.remove(day);
			return true;
		} else {
			return false;
		}

	}
}
