package com.classproj.placeit.tests;

import Models.PlaceIt;
import junit.framework.TestCase;

public class UserStory3Test extends TestCase {

	public void testNoDescrip() {
		/* 
		 * User enters a title but no description and no recurrence.
		 * Verify that a Place-It has been created with that title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		String title = "Title";
		String descrip = null;
		double lat = 0;
		double lng = 0;
		PlaceIt testplaceit = new PlaceIt(title, descrip, lat, lng);
		
		// Verify these fields.
		assertEquals(title, testplaceit.getTitle());
		assertEquals(descrip, testplaceit.getDescription());
		

	}
	
	public void testNoTitle() {
		/* 
		 * User enters a description but no title.
		 * Verify that a Place-It has been created, using the first few words of the description as the title.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		String title = null;
		String descrip = "This is a description. It is very descriptive.";
		double lat = 0;
		double lng = 0;
		PlaceIt testplaceit = new PlaceIt(title, descrip, lat, lng);
		
	}
	
	public void testAllFieldsFilled() {
		/* 
		 * User enters a title, description, and recurrence time. 
		 * Verify that a Place-It has been created all those values.
		 * Verify that the Place-It exists on the map, in the list, and in the database.
		 */
		
		String title = "Title";
		String descrip = "This is a description. It is very descriptive.";
		double lat = 0;
		double lng = 0;
		long date = 0;
		PlaceIt testplaceit = new PlaceIt(title, descrip, lat, lng, date);
		
		
	}
	
	public void testAllFieldsBlank() {
		/*
		 * Verify that there is a box where the user can enter a title, description, and recurrence.
		 * User does not enter a title, description, and recurrence time. 
		 * An error should be given that the user didn’t enter anything.
		 */
		
		String title = null;
		String descrip = null;
		double lat = 0;
		double lng = 0;
		PlaceIt testplaceit = new PlaceIt(title, descrip, lat, lng);
		
	}
	
	public void testPlaceitHandler() {
		
	}
	
	public void testPlaceitScheduler() {

		
	}
	
	public void testEmptySchedule() {
		
		
	}
	
}
