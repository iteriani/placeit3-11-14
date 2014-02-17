package PlaceItControllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.classproj.placeit.iView;
import com.classproj.placeit.PlaceItSettings;

import android.provider.SyncStateContract.Constants;
import android.util.Log;
import Models.PLSchedule;
import Models.PlaceIt;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;

public class PlaceItScheduler {

	private iPlaceItModel PLrepository;
	private iPLScheduleModel scheduleRepository;
	private iView view;

	public PlaceItScheduler(iPLScheduleModel scheduleDB, iPlaceItModel db, iView view) {
		this.PLrepository = db;
		this.scheduleRepository = scheduleDB;
		this.view = view;
	}

	public void setUpSchedules() {
		List<PlaceIt> placeits = this.PLrepository.getAllPlaceIts();
		for (PlaceIt placeit : placeits) {
			if (placeit.isActive() == true) {
				PLSchedule schedule = this.scheduleRepository.getSchedule(placeit);
				placeit = this.initializeSchedule(placeit, schedule);
			}
		}
	}

	/*
	 * Will modify PLSchedule database and then return a new Place-It to be
	 * updated. Given that it has been initialized
	 */
	public PlaceIt initializeSchedule(PlaceIt placeit, PLSchedule schedule) {
		Date currDate = placeit.getActiveDate();
		Calendar cal = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		Calendar min = Calendar.getInstance();
		min.setTime(placeit.getActiveDate());
		min.add(Calendar.YEAR, Integer.MAX_VALUE);
		Date minDate = min.getTime();

		//for (Integer schedule : schedule2) {
			Calendar date = this.nextDayOfWeek(currDate, schedule.getDay());
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			Date curr = date.getTime();
			if (minDate.before(curr) == true) {
				minDate = curr;
			}
		//}

		placeit.setActiveDate(minDate.getTime());
		this.PLrepository.updatePlaceIt(placeit);
		return placeit;
	}

	public PlaceIt startSchedule(PlaceIt placeit, PLSchedule schedule) {
		this.addSchedules(placeit, schedule.getDay(), schedule.getWeek());
		return this.initializeSchedule(placeit, schedule);
	}

	/*
	 * Called from MainActivity when user sets a Place-It's recurrence.
	 * Will add the given schedule to PLSchedule database and return a new placeit to be
	 * updated.
	 */
	public void addSchedules(PlaceIt placeit, int day, int week) {
		this.scheduleRepository.addSchedule(placeit, day, week);
	}

	/* Will remove schedule from placeit and return a new placeit to be updated. */
	public PlaceIt removeSchedule(PlaceIt placeit, int day, int week) {
		return this.scheduleRepository.removeSchedule(placeit, day, week);
	}

	/*
	 * Upon receiving a placeit, it will look for the next scheduled time and
	 * return the place it with the activated date.
	 */
	public PlaceIt scheduleNextActivation(PlaceIt placeit) {
		
		PLSchedule schedule = this.scheduleRepository.getSchedule(placeit);
		if (schedule == null) { 
			return this.repostPlaceit(placeit, 
					PlaceItSettings.INTERVAL_TYPE, 
					PlaceItSettings.INTERVAL_NUMBER);
		}
		
		int startweek = schedule.getStartWeek();
		int day = schedule.getDay();
		int week = schedule.getWeek();
		
		if (day == 0) {
			// the "day" interval is 1 minute
			Log.d("IN THE MINUTE SCHEDULER", "TRUE");
			return this.repostPlaceit(placeit, Calendar.MINUTE, 1);
		}
		else if (day > 0) {
			return this.repostPlaceit(placeit, 
					PlaceItSettings.INTERVAL_TYPE, 
					PlaceItSettings.INTERVAL_NUMBER);
		} 
		return null;
	}

	public PlaceIt repostPlaceit(PlaceIt placeit, int TIMEVAL, int timeAMT) {
												  //TIMEVAL IS NOT AN INT!
		java.util.Date date = placeit.getActiveDate();
		int increment = 0;
		if(TIMEVAL == Calendar.MINUTE){
			increment = 60000;
		}else if(Calendar.HOUR == TIMEVAL){
			increment = 60000 * 60;
		}else if (TIMEVAL == Calendar.SECOND){
			increment = 1000;
		}
		else{
			increment = 60000*60*24;
		}
		
		Date newDate = new Date(date.getTime() + increment * timeAMT);
		placeit.setActiveDate(newDate.getTime());
		//Log.d("NEW ACTIVE DATE ", placeit.getActiveDate().toLocaleString());
		Log.d("NEW ACTIVE DATE ", placeit.getActiveDate().toLocaleString());
		this.PLrepository.updatePlaceIt(placeit);
		 return placeit;
	}
	
	public PlaceIt repostPlaceit(PlaceIt placeit) {
		Calendar cal = Calendar.getInstance();
		cal.add(PlaceItSettings.INTERVAL_TYPE, PlaceItSettings.INTERVAL_NUMBER);
		placeit.setActiveDate(cal.getTime().getTime());
		this.PLrepository.updatePlaceIt(placeit);
		return placeit;
	}
	
	// check if the current week is valid to repost a Place-It
	public boolean isValidWeek(PLSchedule schedule) {
		int currWeek = Calendar.WEEK_OF_YEAR;
		int weekInterval = schedule.getWeek();
		int startWeek = schedule.getStartWeek();
		
		if (weekInterval == -1) { return false; }
		
		if ( (currWeek-startWeek) % weekInterval == 0) {
			return true;
		}
		else return false;
	}
	
	public List<PlaceIt> checkActive(List<PlaceIt> placeits){
		List<PlaceIt> newActive = new Vector<PlaceIt>();
		for (int i = 0; i < placeits.size(); i++) {
			PlaceIt plDB = this.PLrepository.getPlaceIt(placeits.get(i).getID());
			Log.d(plDB.getActiveDate().toLocaleString(), new Date().toLocaleString());
			if(plDB.isActive() && plDB.getActiveDate().getTime() - new Date().getTime() < 0){	
				
				PLSchedule schedule = this.scheduleRepository.getSchedule(plDB);
				Integer currentDay = Calendar.DAY_OF_WEEK;
				
				if (schedule == null) {
					newActive.add(placeits.get(i));
				}
				else if (schedule.getDay() == 0) {
					newActive.add(placeits.get(i));
				}
				else if (isValidWeek(schedule)) {
					// check if the Place-It should be reposted based on the day and week
					newActive.add(placeits.get(i));
				}
			}
		}
		view.notifyUser(newActive, "Scheduler");
		return newActive;
	}

	private Calendar nextDayOfWeek(Date curr, int dow) {
		Calendar date = Calendar.getInstance();
		date.setTime(curr);
		int diff = dow - date.get(Calendar.DAY_OF_WEEK);
		if (!(diff > 0)) {
			diff += 7;
		}
		date.add(Calendar.DAY_OF_MONTH, diff);
		return date;
	}
}
