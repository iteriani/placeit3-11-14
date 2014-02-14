package com.classproj.placeit.tests;

import junit.framework.TestCase;

public class UserStory5Test extends TestCase {
	public void testdeleteAll() {
		/*
		 * Debugging method, leave not doing it
		 */
	}
	
	public void testdeletePlaceit() {
	/*
	 * controller calls delte placeit, and it needs to be gone 
	 * delete from db 
	 * 
	 */
	}
	
	public void testNoPlaceItOnMap() {
		/*
		 * when called remove marker called from view 
		 * 
		 */
	}
	
	public void testNoPlaceItOnList() {
	/*
	 * when call delete, wthere should be a call for remove from list
	 * test that
	 * 
	 */
	}
	
	/*public void testDeleteButtonAppears() {
		
	}
	*/
	public void testDeleteCurrentPlaceit() {
	/*
	 * the view sends back a marker, 
	 * view calls controller method to delete a marker on the map
	 * assert that the controller
	 * 
	 * The controller will call itself to delete the stuff ion the view. We need to
	 * test to make sure the two delete fucntions called are acutally called, assert that. 
	 */
	}

	
}