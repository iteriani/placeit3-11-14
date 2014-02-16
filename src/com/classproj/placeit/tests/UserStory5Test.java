package com.classproj.placeit.tests;

import java.util.LinkedList;
import java.util.List;

import com.classproj.placeit.charlieMockList;
import com.classproj.placeit.charlieMockView;
import com.classproj.placeit.mockView;
import com.google.android.gms.maps.model.Marker;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItDB.mockPlaceItHandler;
import junit.framework.TestCase;

public class UserStory5Test extends TestCase {
	
	String title1 = "hello";
	String title2 = "helloworld";
	String title3 = "superhello"; 
	String title = "just a title";
	String titleugh = "boombox"; 
	String desc = "just a description"; 
	double lat = 20; 
	double longt = 30; 
	long date = 0; 
	PlaceIt justAPlaceit = new PlaceIt(title, desc, lat, longt, date);
	PlaceIt justAPlaceit4 = new PlaceIt(title2, desc, lat+1, longt+1, date);
	PlaceIt justAPlaceit3 = new PlaceIt(title3, desc, lat-2, longt-2, date);
	PlaceIt justAPlaceit2 = new PlaceIt(title1, desc, lat+3, longt+3, date);  
	PlaceIt justAPlaceit1 = new PlaceIt(titleugh, desc, lat-4, longt-4, date);  
	List<PlaceIt>testList = new LinkedList<PlaceIt>();  
	
	mockPlaceItHandler mockList = new mockPlaceItHandler(testList);
	List<PlaceIt> testViews = new LinkedList<PlaceIt>(); 
	charlieMockView testView = new charlieMockView(testViews);  
	charlieMockList testList2 = new charlieMockList(testViews); 
	
	PlaceItController testController = new PlaceItController(mockList, testView); 
	//Used to test the list portion instead of the view portion
	PlaceItController testControllerWithList = new PlaceItController(mockList, testList2); 
	
	public void testdeletePlaceit() {
	/*
	 * controller calls delete Placeit, and it needs to be gone 
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
	assertEquals(mockList.getPlaceItsCount(), 4); 	
	}
	
	public void testNoPlaceItOnMap() {
		/*	
		 * when called remove marker called from view 
		 * Delete by title match was bad because the same title comes up
		 * When I just similar placeits, probably better to match id
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
		
		int a = testView.getPlaceItsCountInView(); 
		System.out.println("Size of the view is currently: " + a); 
		assertEquals(a, 5);
		testController.RemovePlaceIt(justAPlaceit2); 
		int b = testView.getPlaceItsCountInView();
		System.out.println("Size of the deleted view is currently: " + b); 		
	}
	
	
	public void testNoPlaceItOnList() {
	/*
	 * when call delete, wthere should be a call for remove from list
	 * test that
	 * 
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
		
		int a = testList2.getPlaceItsCountInView(); // Should be a list instead
		System.out.println("Size of the view is currently: " + a); 
		assertEquals(a, 5);
		testControllerWithList.RemovePlaceIt(justAPlaceit2); //Uses a mock for both
		int b = testList2.getPlaceItsCountInView();
		System.out.println("Size of the deleted view is currently: " + b); 		
	}
	
	public void testDeleteCurrentPlaceit() {
	/*
	 * the view sends back a marker, 
	 * view calls controller method to delete a marker on the ma
	 * 
	 * The controller will call itself to delete the stuff ion the view. We need to
	 * test to make sure the two delete functions called are actually called, assert that.
	 * Added a flag in delete to make sure that the delete function is called 
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
		
		int a = testList2.getPlaceItsCountInView(); 
		System.out.println("Size of the view is currently: " + a); 
		
		assertEquals(a, 5);
		assertFalse(mockList.getCall()); 
		assertFalse(testList2.getCaller());
		System.out.println("This is what checks for the call: "+ mockList.getCall()); 
		testControllerWithList.RemovePlaceIt(justAPlaceit2); 
		System.out.println("This is what checks for the call: "+ mockList.getCall()); 
		
		assertTrue(mockList.getCall()); //Checking that the delete from database is called
		assertTrue(testList2.getCaller()); //Check that the delete from view is called
		int b = testList2.getPlaceItsCountInView();
		System.out.println("Size of the deleted view is currently: " + b); 
	}ls
	
	
}