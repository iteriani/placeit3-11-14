package PlaceItDB;

import java.util.List;

import Models.PLSchedule;
import Models.PlaceIt;

public interface iPLScheduleModel {
	// Implemented by PLScheduleHandler.java
	
	
	public PlaceIt addSchedule(PlaceIt placeit, int interval, int week);

	public PlaceIt removeSchedule(PlaceIt placeit, int interval, int week);
	
	public PLSchedule getSchedule(PlaceIt placeit);
	
	
	
}
