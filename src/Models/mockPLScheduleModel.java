package Models;

import java.util.ArrayList;
import java.util.List;

import PlaceItDB.iPLScheduleModel;

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
	 * Addeds a schedule to a placeit based on the 45 min/day
	 */
	
	List<PLSchedule> scheduleList = new ArrayList<PLSchedule>();
	
	private PLSchedule findSchedule(int placeitID){
		for(PLSchedule s : scheduleList){
			if (s.getPlaceItId()==placeitID){
				return s;
			}
		}
		return null;
	}
	
	@Override
	public PlaceIt addSchedule(PlaceIt placeit, int day, int week) {
		// TODO Auto-generated method stub
		/*int id = placeit.getID();
		PLSchedule ns = this.findSchedule(id);
		if(ns != null){
			ns.setInteger(day);
		}else{
			ns = new PLSchedule(id,day);
		}
		scheduleList.set(scheduleList.indexOf(ns), ns); */
		return placeit;
	}
	/*
	 * (non-Javadoc)
	 * @see PlaceItDB.iPLScheduleModel#removeSchedule(Models.PlaceIt, java.util.List)
	 * This will remove the schedule for the placeit
	 */
	@Override
	public PlaceIt removeSchedule(PlaceIt placeit, int day, int week) {
		// TODO Auto-generated method stub
		int id = placeit.getID();
		PLSchedule rs = this.findSchedule(id);
		if(rs!=null){
			scheduleList.remove(rs);
		}
		return placeit;
	}

	/*
	 * (non-Javadoc)
	 * @see PlaceItDB.iPLScheduleModel#getSchedule(Models.PlaceIt)
	 * This will get the schedule of the placeit
	 */
	@Override
	public PLSchedule getSchedule(PlaceIt placeit) {
		// TODO Auto-generated method stub
		PLSchedule gs = this.findSchedule(placeit.getID());
		return gs;
	}

}