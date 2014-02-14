package com.classproj.placeit.tests;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;

import com.classproj.placeit.mockView;
import com.google.android.gms.maps.model.LatLng;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItDB.mockPlaceItHandler;
import junit.framework.TestCase;

/**
 * 
 */

/*
 * For each of these tests
 * you should have a boolean 
 * addedplaceit tester, and then 
 * when the place it is changed from database, update variable and assert it. 
 * method have been called and that the method has been called with right values. 
 */
/**
 * @author SKY
 *
 */
public class UserStory4Test extends TestCase {
	
	private List<PlaceIt> plist4 = new ArrayList<PlaceIt>();
	private mockPlaceItHandler mphandler4 = new mockPlaceItHandler(plist4);
	private mockView mview4 = new mockView(null);
	protected PlaceItController pcontroller4 = new PlaceItController(mphandler4, mview4);
	
	private boolean[] added = new boolean[10];
	private Location mockUserLocation = new Location("user story 4");
	private LatLng mockUserPosition = new LatLng(mockUserLocation.getLatitude(), mockUserLocation.getLongitude());
	
	//**********place-it repository**********
	private String title1 = "user story for place-it no.1";
	private String desc1 = "this place-it is within 0.5 miles of the user's location";
	
	private String title2 = "user story 4 place-it no.2";
	private String desc2 = "this place-it is newly created and should be active";
	//**********/**********/**********/**********
	
	
	public void testAddPlaceItMiles(){
		//Create a Place-It within(further than) 0.5 miles of the user¡¯s location.
		LatLng newPosition = new LatLng(mockUserPosition.latitude+0.1,mockUserPosition.longitude+0.1);
		
		pcontroller4.AddPlaceIt(title1,desc1, newPosition);
		added[0] = true;
		pcontroller4.checkCoordinates(mockUserLocation);
		assertEquals(mockUserPosition.latitude,newPosition.latitude,0.5);
		assertEquals(mockUserPosition.longitude,newPosition.longitude,0.5);
		
		/*
		 * make some fake coords, make a placeit withint he 0.5 miles
		 * then do controller check coordinates
		 * the assert that place it got returned back. 
		 */
	}
	
	public void testIsActive(){
		//Verify that the Place-It is active
		//pcontroller4.AddPlaceIt(title2, desc2, mockUserPosition);
		//added[1] = true;
		if (added[0] == false){
			this.testAddPlaceItMiles();
		}
		PlaceIt np = pcontroller4.getList().get(pcontroller4.getList().size());
		assertTrue(np.isActive());
		/*
		 * test that the place it is active
		 * then do controller.checkactive
		 * and assert that the thing returned is active and the same one
		 * 
		 */
	}
	
	public void testNotification(){
		//Verify that there is(not) a notification for this Place-It, that shows up in the Android notification tray.
		//later~~
		/*
		 * test that the place it is active
		 * then do controller.checkactive
		 * and assert that the thing returned is active and the same one
		 * check that notify user is called in the view. 
		 * 
		 */
	}
	
	public void testMoveToPDList_DB(){
		//Verify that the Place-It is moved to the pulled-down list, and this change is reflected in the database.
		//after notification is done
		/*
		 * assert addplaceit to database
		 * The map is gonna tell the controller the user moved it to the pull down list. 
		 * The map is pulling down, the controller is: okay, controlelr tell view to remove marker
		 * and conotrolelr suppose to pull down.
		 */
	}
	
}
