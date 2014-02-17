package com.classproj.placeit.tests;

import java.util.ArrayList;
import java.util.List;

import Models.PlaceIt;
import Models.mockPLScheduleModel;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.mockPlaceItHandler;
import android.location.Location;

import com.classproj.placeit.skyMockView;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * @author SKY
 *
 */
public class UserStory6Test extends TestCase {
	
	private List<PlaceIt> plist6 = new ArrayList<PlaceIt>();
	private mockPlaceItHandler mphandler6 = new mockPlaceItHandler(plist6);
	private skyMockView mview6 = new skyMockView(plist6);
	private mockPLScheduleModel mschedule6 = new mockPLScheduleModel();
	
	protected PlaceItController pcontroller6 = new PlaceItController(mphandler6, mview6);
	protected PlaceItScheduler pscheduler6 = new PlaceItScheduler(mschedule6, mphandler6, mview6);
	
	private boolean[] added = new boolean[10];
	
	//**********place-it repository**********
	private String title1 = "user story for place-it no.1";
	private String desc1 = "this place-it is within 0.5 miles of the user's location";
	
	private String title2 = "user story 6 place-it no.2";
	private String desc2 = "this place-it is newly created and should be active";
	//**********/faked coordinates/**********
	private LatLng fakeCo = new LatLng(11.1,22.2);	

	private PlaceIt expectPL;
	
	public void testRepostWhenReminded(){
		//When the user is reminded about a place-it, user sees an option to repost it.
		/*
		 * initalize a bunch of a placeits
		 * when we feed the coordinates into the method
		 * he will see the option to repost it
		 * use coordinates, controller check coordinates, to check for placeits
		 * return back placeits from controller, the view will take these place its and 
		 * the view will allow for the repost. 
		 * the iview, it should be able to repost. 
		 */
		pcontroller6.AddPlaceIt(title1, desc1, fakeCo);
		Location loc6 = new Location("user story 6");
		loc6.setLatitude(fakeCo.latitude);
		loc6.setLongitude(fakeCo.longitude);
		List<PlaceIt> tmp = pcontroller6.checkCoordinates(loc6);
		mview6.notifyUser(tmp, "controller");
		for(PlaceIt p : tmp){
			assertEquals(p,pscheduler6.repostPlaceit(p));
		}
	}
	
	public void testClickRepost(){
		//The Place-It is moved from Pulled-Down list to the Active list.
		//The corresponding entry in the database is changed to reflect this.
		/*
		 * At some click - tell controller, moving from active to deactivate
		 * and the controller will call deactivate
		 * and the iview will call deactivate as well. 
		 */
		mphandler6.deactivatePlaceit(expectPL);
		assertFalse(expectPL.isActive());
		
	}
}
