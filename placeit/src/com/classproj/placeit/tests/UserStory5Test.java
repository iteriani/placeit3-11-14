package com.classproj.placeit.tests;

import java.util.List;
import java.util.Vector;

import Models.*;
import PlaceItDB.mockPlaceItHandler;

import junit.framework.TestCase;

public class UserStory5Test extends TestCase {
	
//Setup() part? haha
	String yo = "yoyo";
	String yoyoyo = "yoyoyoyo";
	double yabish2 = 30;
	double hallbackgurl = 30; 
	long yabish = 20;
	
	PlaceIt newPlaceit = null; 
	PlaceIt newPlaceit2 = null;
	PlaceIt newPlaceit3 = null; 
	PlaceIt newPlaceit4 = new PlaceIt(yo, yo, yabish2, hallbackgurl, yabish);
	List<PlaceIt> tmp = new Vector<PlaceIt>();
	mockPlaceItHandler mph = new mockPlaceItHandler(tmp);
	
	
	public void testdeleteAll() {
		
		assertEquals(mph.getPlaceItsCount(), 0);
		mph.addPlaceIt(newPlaceit);
		mph.addPlaceIt(newPlaceit2);
		mph.addPlaceIt(newPlaceit3);
		mph.addPlaceIt(newPlaceit4);
		mph.deleteAll();
		assertEquals(mph.getPlaceItsCount(), 0);
	}
	
	public void testdeletePlaceit() {
	
	}
	
	public void testNoPlaceItOnMap() {
		
	}
	
	public void testremoveMarker() {
		
	}
	
	public void testNoPlaceItOnList() {
	
	}
	
	public void testDeleteButtonAppears() {
		
	}
	
	public void testDeleteCurrentPlaceit() {
	
	}
	
	public void testDeleteMultiplePlaceit() {
	}
	
	public void testDeleteFromList() {
	}
	
	
}