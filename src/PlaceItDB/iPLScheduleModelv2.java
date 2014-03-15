/**
 * 
 *This interface manipulate schedule for placeits
 *it adds or get schedules
 *
 *This is the version used in milestone2
 */

package PlaceItDB;

import HTTP.PLScheduleReceiver;
import HTTP.PlaceItReceiver;
import Models.PLSchedule2;
import Models.PlaceIt;

public interface iPLScheduleModelv2 {

	public void addSchedule(PLSchedule2 schedule, final PlaceItReceiver receiver);
	public void getSchedule(PlaceIt placeit, final PLScheduleReceiver receiver);
	
}
