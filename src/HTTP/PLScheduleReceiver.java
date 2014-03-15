/**
 * This interface receive the scheulde for a placeit from the placeit schedule database
 */

package HTTP;

import Models.PLSchedule;

public interface PLScheduleReceiver {
	public void receiveSchedule(PLSchedule placeit);
}
