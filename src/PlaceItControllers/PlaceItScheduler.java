package PlaceItControllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Models.PlaceIt;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;

public class PlaceItScheduler {

	private iPlaceItModel PLrepository;
	private iPLScheduleModel scheduleRepository;

	public PlaceItScheduler(iPLScheduleModel scheduleDB, iPlaceItModel db) {
		this.PLrepository = db;
		this.scheduleRepository = scheduleDB;
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
			Calendar date = null;
			if (schedule > 0) { // schedule 0 is MINUTE
				date = this.nextDayOfWeek(currDate, schedule);
			} else {
				date = Calendar.getInstance();
				date.add(Calendar.MINUTE, 1);
			}
			Date curr = date.getTime();
			if (minDate.before(curr) == true) {
				minDate = curr;
			}
		}

		placeit.setActiveDate(minDate.getTime());
		return placeit;
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

	public PlaceIt startSchedule(PlaceIt placeit, List<Integer> days) {
		for (Integer num : days) {
			this.addSchedule(placeit, num);
		}
		return this.initializeSchedule(placeit, days);
	}

	/*
	 * Will add schedule to PLSchedule database and return a new placeit to be
	 * updated.
	 */
	public void addSchedule(PlaceIt placeit, Integer day) {
		Vector<Integer> ints = new Vector<Integer>();
		ints.add(day);
		this.scheduleRepository.addSchedule(placeit, ints);
	}

	/* Will remove schedule from placeit and return a new placeit to be updated. */
	public PlaceIt removeSchedule(PlaceIt placeit, Integer day) {
		Vector<Integer> ints = new Vector<Integer>();
		ints.add(day);
		return this.scheduleRepository.addSchedule(placeit, ints);
	}

	/*
	 * Upon receiving a placeit, it will look for the next scheduled time and
	 * return the place it with the activated date.
	 */
	public PlaceIt scheduleNextActivation(PlaceIt placeit) {
		List<Integer> schedules = this.scheduleRepository.getSchedule(placeit);
		if (schedules.size() == 0) {

		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 24);
			return this.initializeSchedule(placeit, schedules);
		}

	}
}
