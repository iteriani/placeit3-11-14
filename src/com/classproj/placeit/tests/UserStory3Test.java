// UserStory3Test.java

package com.classproj.placeit.tests;

import java.util.List;

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
		 * Test the contains. 
		 */
		
		iPlaceItModel mockdb = null;
		iView mockview = null;
		iPLScheduleModel mockschedule = null;
		PlaceItScheduler mockscheduler = new PlaceItScheduler(mockschedule, mockdb, mockview);

		PlaceItController controller = new PlaceItController(mockdb, mockview);
		
		// Test that controller has been implemented correctly.
		assertEquals(mockdb.getPlaceItsCount(), 0);
		
		/* 
		 * User enters a title but no description and no recurrence.
		 * Verify that a Place-It has been created with that title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		String title = "Title";
		String descrip = null;
		LatLng pos = new LatLng(0,0);
		PlaceIt placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		PlaceIt addedPL = controller.AddPlaceIt(title, descrip, pos);
		assertNotNull(addedPL);
		verifyPlaceItDetails(title, descrip, pos, addedPL);
		verifyPlaceItAdded(controller, addedPL, 0);
		
		/* 
		 * User enters a description but no title.
		 * Verify that a Place-It has been created, using the first few words of the 
		 * description as the title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		title = null;
		descrip = "This is a description. It is very descriptive.";
		pos = new LatLng(0,0);
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		addedPL = controller.AddPlaceIt(title, descrip, pos);
		assertNotNull(addedPL);
		verifyPlaceItDetails(title, descrip, pos, addedPL);
		verifyPlaceItAdded(controller, addedPL, 1);
		
		/* 
		 * User enters a title, description, and recurrence time. 
		 * Verify that a Place-It has been created all those values.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		title = "Title";
		descrip = "This is a description. It is very descriptive.";
		pos = new LatLng(0,0);
		long date = 0;
		int recurrence = 2;
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		addedPL = controller.AddPlaceIt(title, descrip, pos);
		assertNotNull(addedPL);
		verifyPlaceItDetails(title, descrip, pos, addedPL);
		verifyPlaceItRecurrence(date, addedPL);
		verifyPlaceItAdded(controller, addedPL, 2);
		
		/*
		 * User does not enter a title, description, and recurrence time. 
		 * An error should be given that the user didn’t enter anything.
		 */
		
		title = null;
		descrip = null;
		pos = null;
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude);
		// should this place-it be able to be created?
		verifyPlaceItDetails(title, descrip, pos, placeit);
		addedPL = controller.AddPlaceIt(title, descrip, pos);
		assertNull(addedPL);
		verifyPlaceItAdded(controller, addedPL, 3);
		

	}
	
	private void verifyPlaceItDetails(String title, String descrip, LatLng pos,
			PlaceIt placeit) {
		// verify that Place-It's info is correct. disregards recurrence.
		
		if(title == null) {
			assertTrue(descrip.contains(title));
		}
		else {
			assertEquals(placeit.getTitle(), title);
		}
		assertEquals(placeit.getDescription(), descrip);
		assertEquals(placeit.getLatitude(), pos.latitude);
		assertEquals(placeit.getLongitude(), pos.longitude);
		
	}

	private void verifyPlaceItRecurrence(long date, PlaceIt placeit) {
		// verify that the recurrence information goes into the scheduler.
		// don't need to check if it's correct; that will be for userstory7test
		
		
		
		if(date == 0) {
			// wat do?
		}
		else {
			
		}
		
	}

	/*
	 * This method verifies that the Place-It has been added to:
	 * the database, the list, and the map.
	 */
	private void verifyPlaceItAdded(PlaceItController controller, PlaceIt placeit, int i) {
		iPlaceItModel mockdb = controller.getDB();
		iView mockview = controller.getView();
		List<PlaceIt> mocklist = controller.getList();
		
		// verify that Place-It has been added to the database
		assertEquals(mockdb.getPlaceItsCount(), i+1);
		assertEquals(placeit, mockdb.getPlaceIt(i));
		// verify that Place-It has been added to the list
		assertEquals(placeit, mocklist.get(i));
		// verify that Place-It has been added to the map
		assertEquals(placeit, mockview.getMarker(i));

		
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
