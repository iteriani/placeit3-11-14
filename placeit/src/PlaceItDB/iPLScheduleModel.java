package PlaceItDB;

import java.util.List;

import Models.PlaceIt;

public interface iPLScheduleModel {
	
	
	public PlaceIt addSchedule(PlaceIt placeit, List<Integer> day);

	public PlaceIt removeSchedule(PlaceIt placeit, List<Integer> day);
	
	public List<Integer> getSchedule(PlaceIt placeit);
	
	
	
}
