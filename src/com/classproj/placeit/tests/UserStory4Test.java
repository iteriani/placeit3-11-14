package com.classproj.placeit.tests;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import Models.PlaceIt;
import Models.mockPLScheduleModel;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.mockPlaceItHandler;
import android.location.Location;

import com.classproj.placeit.mockView;
import com.classproj.placeit.skyMockView;
import com.google.android.gms.maps.model.LatLng;


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
	/*
	 * User Story 4: User can be notified by Place-It when close to its location and the Place-It is moved to the pulled-down list.
	 */
	
	//// variables ////
	private List<PlaceIt> plist4 = new ArrayList<PlaceIt>();
	private mockPlaceItHandler mphandler4 = new mockPlaceItHandler(plist4);

	private skyMockView mview4 = new skyMockView(plist4);
	private mockPLScheduleModel mschedule4 = new mockPLScheduleModel();
	

	protected PlaceItController pcontroller4 = new PlaceItController(mphandler4, mview4);
	protected PlaceItScheduler pscheduler4 = new PlaceItScheduler(mschedule4, mphandler4, mview4);
	
	private boolean[] added = new boolean[10];
	//private Location mockUserLocation = new Location("user story 4");
	//private LatLng mockUserPosition = new LatLng(mockUserLocation.getLatitude(), mockUserLocation.getLongitude());
	
	//**********place-it repository**********
	private String title1 = "user story for place-it no.1";
	private String desc1 = "this place-it is within 0.5 miles of the user's location";
	
	private String title2 = "user story 4 place-it no.2";
	private String desc2 = "this place-it is newly created and should be active";
	//**********/faked coordinates/**********
	private LatLng fakeCo = new LatLng(11.1,22.2);	

	private PlaceIt expectPL;
	
	
	private double toMiles(double d){
		return (d * 0.000621371);
	}
	 
	private double fromMiles(double d){
		return (d / 0.000621371);
	}
		
	public void testNotificationOfPlaceIt() {
		/* Given there is a Place-It on the map
		 * And that Place-It is active
		 * When the user is within 0.5 miles of the active Place-It
		 * (the Place-It was created within 0.5 miles of the user position)
		 */
		addPlaceIt();
		testIsActive();
		
		/*
		 *  Then a notification is given via the Android interface
		 *  And the user can see which Place-It is notifying him
		 *  And the Place-It is moved to the Pulled-Down list
		 *  And the user can take further action (repost or discard) from notification.
		 */
		testNotification();
		testMoveToPDList_DB();
		
	}
	
	public void addPlaceIt() {
		//Create a Place-It within(further than) 0.5 miles of the user’s location.
		
		Location fakeLo = new Location("within 0.5");
		double lat = fromMiles(toMiles(fakeCo.latitude)+0.1);
		double log = fromMiles(toMiles(fakeCo.longitude)+0.1);
		fakeLo.setLatitude(lat);
		fakeLo.setLongitude(log);
		fakeLo.setTime(new Date().getTime());
		
		pcontroller4.initializeView();
		//verify initialization is successful
		assertEquals(mview4.getMarkerCount(),0);
		LatLng nco = new LatLng(lat,log);
		expectPL = pcontroller4.AddPlaceIt(title1, desc1, nco);
		
		//verify the placeit is added and within 0.5 miles
		added[0] = pcontroller4.checkCoordinates(fakeLo).contains(expectPL);
		assertTrue(added[0]);
	}
	
	
	public void testAddPlaceItMiles(){
		//LatLng newPosition = new LatLng(mockUserPosition.latitude+0.1,mockUserPosition.longitude+0.1);
		
		//pcontroller4.AddPlaceIt(title1,desc1, newPosition);
		//added[0] = true;
		//pcontroller4.checkCoordinates(mockUserLocation);
		//assertEquals(mockUserPosition.latitude,newPosition.latitude,0.5);
		//assertEquals(mockUserPosition.longitude,newPosition.longitude,0.5);
		
		/*
		 * make some fake coords, make a placeit within 0.5 miles
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
		List<PlaceIt> activeList = pscheduler4.checkActive(plist4);
		for (PlaceIt p : activeList){
			assertTrue(p.isActive());
		}
		
		
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
		assertEquals(1,mphandler4.addPlaceIt(expectPL));
		pcontroller4.removePlaceIt(expectPL);
		assertFalse(pcontroller4.getList().contains(expectPL));
		pcontroller4.getView().removeMarker(expectPL);
		assertEquals(null,pcontroller4.getView().getMarker(expectPL.getID()));
		
	}
	
}
