package com.classproj.placeit.tests;

import junit.framework.TestCase;
import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;

import com.classproj.placeit.iView;
import com.google.android.gms.maps.model.LatLng;

public class UserStory3Test extends TestCase {

	public void testNoDescrip() {
		/*
		 * Even if we put an empty space for description
		 * it is okay. 
		 * If they put an empty title it is okay
		 * Test these three things THis will fail liek a lil bithc
		 * if both are empty, not okay. 
		 * Tese the contains. 
		 */
		
		/*
		iPlaceItModel mockdb;
		iView mockview;
		iPLScheduleModel db;
		PlaceItScheduler mockscheduler = new PlaceItScheduler(db, mockdb);
		
		PlaceItController controller = new PlaceItController(
				mockdb, mockview, mockscheduler);
		
		// Test that controller has been implemented correctly.
		assertEquals(mockdb.getPlaceItsCount(), 0);
		
		/* 
		 * User enters a title but no description and no recurrence.
		 * Verify that a Place-It has been created with that title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		/*
		String title = "Title";
		String descrip = null;
		LatLng pos = new LatLng(0,0);
		PlaceIt placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		
		controller.AddPlaceIt(title, descrip, pos);
		
		verifyPlaceIt(controller, placeit, 0);
		
		/* 
		 * User enters a description but no title.
		 * Verify that a Place-It has been created, using the first few words of the 
		 * description as the title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		/*
		title = null;
		descrip = "This is a description. It is very descriptive.";
		pos = new LatLng(0,0);
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		
		controller.AddPlaceIt(title, descrip, pos);
		
		verifyPlaceIt(controller, placeit, 1);
		
		/* 
		 * User enters a title, description, and recurrence time. 
		 * Verify that a Place-It has been created all those values.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		/*
		title = "Title";
		descrip = "This is a description. It is very descriptive.";
		pos = new LatLng(0,0);
		long date = 0;
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude, date);
		
		/*
		 * User does not enter a title, description, and recurrence time. 
		 * An error should be given that the user didn’t enter anything.
		 */
		
		String title11 = null;
		String descrip11 = null;
		double lat = 0;
		double lng = 0;
		PlaceIt testplaceit = new PlaceIt(title11, descrip11, lat, lng);
		

	}
	
	public void verifyPlaceIt(PlaceItController controller, PlaceIt pi, int i) {
		/*assertEquals(mockdb.getPlaceItsCount(), i+1);
		assertEquals(placeit, mockdb.getPlaceIt(i));
		assertEquals(placeit, mocklist.get(i));
		assertEquals(placeit, mockview.getMarker(i));
		PlaceIt addedPI = mockdb.getPlaceIt(i);
		assertEquals(addedPI.getTitle(), title);
		assertEquals(addedPI.getDescription(), descrip);
		assertEquals(addedPI.getLatitude(), pos.latitude);
		assertEquals(addedPI.getLongitude(), pos.longitude);*/
	}
		
	public void testAllFieldsBlank() {
		/*
		 * this should fail when all fields are blank, it will will return null. 
		 * When the placeit is made
		 * controller will call add, and hte controller will call add place it
		 * and it will return null
		 * and the dataase did not do anything
		 * and the view did not have a marker called
		 * 
		 */
	}
	
	public void testPlaceitHandler() {
		
	}
	
	public void testPlaceitScheduler() {

		
	}
	
	public void testEmptySchedule() {
		/*
		 * If we replace a placeit
		 * scheduler is going to call get schedule
		 * Calling the the scheduleingnext method ehre to be rtested. 
		 * but when it does schedule, it will try to go for the enxt date
		 * for example test for a monday, like a next monday for a not empty schedule for a next test. 
		 */
		
	}
	
}
