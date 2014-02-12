package com.classproj.placeit.tests;

import com.google.android.gms.maps.model.LatLng;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import junit.framework.TestCase;

public class UserStory3Test extends TestCase {

	public void testNoDescrip() {
		
		mockiPlaceItModel mockdb;
		mockiView mockview;
		mockList mocklist;
		mockPlaceItScheduler mockscheduler;
		
		PlaceItController controller = new PlaceItController(
				mockdb, mockview, mocklist, mockscheduler);
		
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
		
		controller.AddPlaceIt(title, descrip, pos);
		
		verifyPlaceIt(controller, placeit, 0);
		
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
		
		controller.AddPlaceIt(title, descrip, pos);
		
		verifyPlaceIt(controller, placeit, 1);
		
		/* 
		 * User enters a title, description, and recurrence time. 
		 * Verify that a Place-It has been created all those values.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		title = "Title";
		descrip = "This is a description. It is very descriptive.";
		pos = new LatLng(0,0);
		long date = 0;
		placeit = new PlaceIt(title, descrip, pos.latitude, pos.longitude, date);
		
		/*
		 * User does not enter a title, description, and recurrence time. 
		 * An error should be given that the user didn’t enter anything.
		 */
		
		String title = null;
		String descrip = null;
		double lat = 0;
		double lng = 0;
		PlaceIt testplaceit = new PlaceIt(title, descrip, lat, lng);
		

	}
	
	public void verifyPlaceIt(PlaceItController controller, PlaceIt pi, int i) {
		assertEquals(mockdb.getPlaceItsCount(), i+1);
		assertEquals(placeit, mockdb.getPlaceIt(i));
		assertEquals(placeit, mocklist.get(i));
		assertEquals(placeit, mockview.getMarker(i));
		PlaceIt addedPI = mockdb.getPlaceIt(i);
		assertEquals(addedPI.getTitle(), title);
		assertEquals(addedPI.getDescription(), descrip);
		assertEquals(addedPI.getLatitude(), pos.latitude);
		assertEquals(addedPI.getLongitude(), pos.longitude);
	}
		
	public void testAllFieldsBlank() {
		
		
	}
	
	public void testPlaceitHandler() {
		
	}
	
	public void testPlaceitScheduler() {

		
	}
	
	public void testEmptySchedule() {
		
		
	}
	
}
