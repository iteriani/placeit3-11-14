/**
 * 
 * This interface manipulate schedules of a given placeit 
 * and return the new placeit
 * 
 * This is the version used in milestone 1
 *
 */

package PlaceItDB;

import java.util.List;

import Models.PLSchedule;
import Models.PlaceIt;

public interface iPLScheduleModel {
	// Implemented by PLScheduleHandler.java
	
	
	public PlaceIt addSchedule(PlaceIt placeit, int day, int week);

	public PlaceIt removeSchedule(PlaceIt placeit, int day, int week);
	
	public PLSchedule getSchedule(PlaceIt placeit);
	
	
	
}
