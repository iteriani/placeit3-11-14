package com.classproj.placeit.tests;
import java.util.List;
import java.util.Vector;

import com.classproj.placeit.mockView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItDB.mockPlaceItHandler;
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
		
		//PlaceIt testPlace1 = new PlaceIt("Ankoor", "I want food");
		PlaceIt testPlace2 = new PlaceIt("Ankoor", "I want Food", 32.0003, 32.98323);
		PlaceIt testPlace3 = new PlaceIt("get whipped cream", "i am fat and need whipped cream", 35.3456, 69.3948);
		PlaceIt testPlace4 = new PlaceIt("Manuja is fat", "bubble butt bubble bubble butt", 49.999, 23.34);
		PlaceIt testPlace5 = new PlaceIt("not in list", "HAHAHAHAHAHAHA", 32.5567, 74.234);
	
		//list of placeits
		List<PlaceIt> testList = new Vector<PlaceIt>();
		List<Marker> testMarker = new Vector<Marker>();
		
		//test handler
		mockPlaceItHandler mocklist = new mockPlaceItHandler(testList);
		
		//mocklist.addPlaceIt(testPlace1);
		mocklist.addPlaceIt(testPlace2);
		mocklist.addPlaceIt(testPlace3);
		mocklist.addPlaceIt(testPlace4);
		
		mockView view = new mockView(testMarker);

		
		PlaceItController testControl = new PlaceItController(mocklist, view);
		
		//when
		testControl.initializeView();
		
		//then

		assertTrue( testMarker.size() != 0);
		assertTrue ( testMarker.size() == 4);
		
		assertEquals( ((testMarker.get(0)).getPosition()).latitude, (testList.get(0)).getLongitude() );
		assertEquals( ((testMarker.get(1)).getPosition()).latitude, (testList.get(1)).getLongitude() );
		assertEquals( ((testMarker.get(2)).getPosition()).latitude, (testList.get(2)).getLongitude() );
		assertEquals( ((testMarker.get(3)).getPosition()).latitude, (testList.get(3)).getLongitude() );
		//assertEquals( ((testMarker.get(4)).getPosition()).latitude, (testList.get(4)).getLongitude() );

		
	}
}
