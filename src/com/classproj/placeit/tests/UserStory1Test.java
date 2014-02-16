package com.classproj.placeit.tests;

import junit.framework.TestCase;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.classproj.placeit.charlieMockView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItDB.mockPlaceItHandler;

public class UserStory1Test extends TestCase {
	public void testMapAppears(){
		//Given
		//create 5 placeits
		PlaceIt testPlace1 = new PlaceIt("Ankoor", "I want food", 84.999, 43.4567);
		PlaceIt testPlace2 = new PlaceIt("Ankoor", "I want Food", 32.0003, 32.98323);
		PlaceIt testPlace3 = new PlaceIt("get whipped cream", "i am fat and need whipped cream", 46.957, 25.439);
		PlaceIt testPlace4 = new PlaceIt("Manuja is fat", "bubble butt bubble bubble butt", 69.999, 23.34);
		PlaceIt testPlace5 = new PlaceIt("not in list", "HAHAHAHAHAHAHA", 32.5567, 74.234);
	
		//list of placeits
		
		List<Marker> testMarker = new Vector<Marker>();
		List<PlaceIt> testList = new Vector<PlaceIt>();
		//test handler
		mockPlaceItHandler mocklist = new mockPlaceItHandler(testList);
		
		mocklist.addPlaceIt(testPlace1);
		mocklist.addPlaceIt(testPlace2);
		mocklist.addPlaceIt(testPlace3);
		mocklist.addPlaceIt(testPlace4);
		mocklist.addPlaceIt(testPlace5);
		
		testList = mocklist.getAllPlaceIts(); 
		
		
		charlieMockView view = new charlieMockView(testList);
		
		PlaceItController testControl = new PlaceItController(mocklist, view);
		
		//when
		testControl.initializeView();
		
		//then
		assertTrue( (view.getPlaceItList()).size() != 0);
		assertTrue ( (view.getPlaceItList()).size() == 5);
		
		assertEquals( ((view.getPlaceIt(0)).getLongitude()), (testList.get(0)).getLongitude() );
		assertEquals( ((view.getPlaceIt(1)).getLongitude()), (testList.get(1)).getLongitude() );
		assertEquals( ((view.getPlaceIt(2)).getLongitude()), (testList.get(2)).getLongitude() );
		assertEquals( ((view.getPlaceIt(3)).getLongitude()), (testList.get(3)).getLongitude() );
		assertEquals( ((view.getPlaceIt(4)).getLongitude()), (testList.get(4)).getLongitude() );
		
		assertEquals( ((view.getPlaceIt(0)).getLatitude()), (testList.get(0)).getLatitude() );
		assertEquals( ((view.getPlaceIt(1)).getLatitude()), (testList.get(1)).getLatitude() );
		assertEquals( ((view.getPlaceIt(2)).getLatitude()), (testList.get(2)).getLatitude() );
		assertEquals( ((view.getPlaceIt(3)).getLatitude()), (testList.get(3)).getLatitude() );
		assertEquals( ((view.getPlaceIt(4)).getLatitude()), (testList.get(4)).getLatitude() );
		
	}
}