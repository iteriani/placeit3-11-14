package PlaceItDB;

import java.sql.Date;
import java.util.List;

interface iPLScheduleModel {
	
	/* is set up before db calls to reactivate sleeping placeits
	*/
	public void setUpSchedules();
	
	/* Will modify PLSchedule database and then return a new placeit to be 
	 * updated. 
	 * */
	public PlaceIt initializeSchedule(PlaceIt placeit, List<Schedule> schedules);
	
	/* Will add schedule to PLSchedule database and return a new placeit to be
	 * updated. */
	public PlaceIt addSchedule(PlaceIt placeit, Schedule day);
	
	/* Will remove schedule from placeit and return a new placeit to be updated.*/
	public PlaceIt removeSchedule(PlaceIt placeit, Schedule day);
	
	/* Upon receiving a placeit, it will look for the next scheduled time and 
	 * return the place it with the activated date.*/
	public PlaceIt scheduleNextActivation(PlaceIt placeit);
	
}
