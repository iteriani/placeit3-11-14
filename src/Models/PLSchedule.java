package Models;

import java.util.LinkedList;
import java.util.List;


public class PLSchedule {

	// private variables
	int _id;
	int placeitID; // this is a foreign key to placeIt
	List<Integer> schedules;

	protected PLSchedule(int placeitID) {
		this.placeitID = placeitID;
		this.schedules = new LinkedList<Integer>();
	}

	protected PLSchedule(int placeitID, List<Integer> schedules) {
		this(placeitID);
		this.schedules = schedules;
	}

	public int getPlaceItId() {
		return this.placeitID;
	}

	public void setPlaceItId(int placeitID) {
		this.placeitID = placeitID;
	}

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
}
