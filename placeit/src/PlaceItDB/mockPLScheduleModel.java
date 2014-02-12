package PlaceItDB;

import java.util.List;

import Models.PlaceIt;

/* Create a a private class that holds a place it and add to its list
 * And we can iterate through the name
 */

/*
 * This mock class will return a placeit. 
 * We need to check that this mock will return a proper placeit
 * And check that the mock will have updated the proper database
 */
public class mockPLScheduleModel implements iPLScheduleModel {

	/*
	 * (non-Javadoc)
	 * @see PlaceItDB.iPLScheduleModel#addSchedule(Models.PlaceIt, java.util.List)
	 * Addeds a schedule to a placeit based on teh 45 min/day
	 */
	@Override
	public PlaceIt addSchedule(PlaceIt placeit, List<Integer> day) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see PlaceItDB.iPLScheduleModel#removeSchedule(Models.PlaceIt, java.util.List)
	 * This will remove the schedule for the placeit
	 */
	@Override
	public PlaceIt removeSchedule(PlaceIt placeit, List<Integer> day) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see PlaceItDB.iPLScheduleModel#getSchedule(Models.PlaceIt)
	 * This will get the schedule of the placeit
	 */
	@Override
	public List<Integer> getSchedule(PlaceIt placeit) {
		// TODO Auto-generated method stub
		return null;
	}

}
