package PlaceItControllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.classproj.placeit.iView;
import com.classproj.placeit.PlaceItSettings;

import android.provider.SyncStateContract.Constants;
import android.util.Log;

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
				List<Integer> schedules = this.scheduleRepository
						.getSchedule(placeit);
				placeit = this.initializeSchedule(placeit, schedules);
			}
		}
	}

	/*
	 * Will modify PLSchedule database and then return a new placeit to be
	 * updated. Given that it has been initialized
	 */
	public PlaceIt initializeSchedule(PlaceIt placeit, List<Integer> schedules) {
		Date currDate = placeit.getActiveDate();
		Calendar cal = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		Calendar min = Calendar.getInstance();
		min.setTime(placeit.getActiveDate());
		min.add(Calendar.YEAR, Integer.MAX_VALUE);
		Date minDate = min.getTime();

		for (Integer schedule : schedules) {
			Calendar date = this.nextDayOfWeek(currDate, schedule);
			Date curr = date.getTime();
			if (minDate.before(curr) == true) {
				minDate = curr;
			}
		}

		placeit.setActiveDate(minDate.getTime());
		this.PLrepository.updatePlaceIt(placeit);
		return placeit;
	}

	public PlaceIt startSchedule(PlaceIt placeit, List<Integer> days) {
		this.addSchedules(placeit, days);
		return this.initializeSchedule(placeit, days);
	}

	/*
	 * Will add schedule to PLSchedule database and return a new placeit to be
	 * updated.
	 */
	public void addSchedules(PlaceIt placeit, List<Integer> days) {
		this.scheduleRepository.addSchedule(placeit, days);
	}

	/* Will remove schedule from placeit and return a new placeit to be updated. */
	public PlaceIt removeSchedule(PlaceIt placeit, List<Integer> days) {
		return this.scheduleRepository.removeSchedule(placeit, days);
	}

	/*
	 * Upon receiving a placeit, it will look for the next scheduled time and
	 * return the place it with the activated date.
	 */
	public PlaceIt scheduleNextActivation(PlaceIt placeit) {
		List<Integer> schedules = this.scheduleRepository.getSchedule(placeit);
		if (schedules.size() == 0) {
			return this.repostPlaceit(placeit, new Date(0));
		} else if (schedules.contains(0) == true) {
			Log.d("IN THE MINUTE SCHEUDLER", "TRUE");
			return this.repostPlaceit(placeit, Calendar.MINUTE, 1);
		} else {
			return this.initializeSchedule(placeit, schedules);
		}
	}

	public PlaceIt repostPlaceit(PlaceIt placeit, int TIMEVAL, int timeAMT) {

		java.util.Date date = placeit.getActiveDate();
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(date); // sets calendar time/date
		cal.add(TIMEVAL, timeAMT*2); // adds amt
		java.util.Date newDate = cal.getTime(); // returns new date object, one
												// hour in the future
		placeit.setActiveDate(newDate.getTime());
		this.PLrepository.updatePlaceIt(placeit);
		 return placeit;
	}

	public PlaceIt repostPlaceit(PlaceIt placeit, Date date) {
		placeit.setActiveDate(date.getTime());
		this.PLrepository.updatePlaceIt(placeit);
		return placeit;
	}
	
	public PlaceIt repostPlaceit(PlaceIt placeit) {
		Calendar cal = Calendar.getInstance();
		cal.add(PlaceItSettings.INTERVAL_TYPE, PlaceItSettings.INTERVAL_NUMBER);
		placeit.setActiveDate(cal.getTimeInMillis());
		this.PLrepository.updatePlaceIt(placeit);
		return placeit;
	}
	
	public List<PlaceIt> checkActive(List<PlaceIt> placeits){
		List<PlaceIt> newActive = new Vector<PlaceIt>();
		for(PlaceIt placeit : placeits){
			Log.d("CHECKING ID", Integer.toString(placeit.getID()));
			PlaceIt plDB = this.PLrepository.getPlaceIt(placeit.getID());
			if(plDB.isActive()){		
				newActive.add(placeit);
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
