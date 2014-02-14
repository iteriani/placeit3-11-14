package com.classproj.placeit.tests;

import java.util.LinkedList;
import java.util.List;

import com.classproj.placeit.charlieMockView;
import com.classproj.placeit.mockView;
import com.google.android.gms.maps.model.Marker;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItDB.mockPlaceItHandler;
import junit.framework.TestCase;

public class UserStory5Test extends TestCase {
	
	
	String title = "just a title";
	String desc = "just a description"; 
	double lat = 20; 
	double longt = 30; 
	long date = 0; 
	PlaceIt justAPlaceit = new PlaceIt(title, desc, lat, longt, date);
	PlaceIt justAPlaceit4 = new PlaceIt(title, desc, lat+1, longt+1, date);
	PlaceIt justAPlaceit3 = new PlaceIt(title, desc, lat-2, longt-2, date);
	PlaceIt justAPlaceit2 = new PlaceIt(title, desc, lat+3, longt+3, date);  
	PlaceIt justAPlaceit1 = new PlaceIt(title, desc, lat-4, longt-4, date);  
	List<PlaceIt>testList = new LinkedList<PlaceIt>();  
	
	mockPlaceItHandler mockList = new mockPlaceItHandler(testList);
	List<PlaceIt> testViews = new LinkedList<PlaceIt>(); 
	charlieMockView testView; 
	
	PlaceItController testController = new PlaceItController(mockList, testView); 
	
	public void testdeletePlaceit() {
	/*
	 * controller calls delte placeit, and it needs to be gone 
	 * delete from db 
	 */
	
	testView.addMarker(justAPlaceit); 
	testView.addMarker(justAPlaceit4); 
	testView.addMarker(justAPlaceit3); 
	testView.addMarker(justAPlaceit2); 
	testView.addMarker(justAPlaceit1);
	
	mockList.addPlaceIt(justAPlaceit); 
	mockList.addPlaceIt(justAPlaceit4); 
	mockList.addPlaceIt(justAPlaceit3); 
	mockList.addPlaceIt(justAPlaceit2); 
	mockList.addPlaceIt(justAPlaceit1);
	
	
	int a = mockList.getPlaceItsCount(); 
	System.out.println("Size of the db is currently: " + a); 
	assertEquals(a, 5);
	testController.RemovePlaceIt(justAPlaceit3); 
	int b = mockList.getPlaceItsCount(); 
	System.out.println("Size of the deleted db is currently: " + b); 
	assertNull(mockList.getPlaceIt(justAPlaceit3.getID())); 
	
	
		
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