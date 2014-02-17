package com.classproj.placeit.tests;

import java.util.ArrayList;
import java.util.List;

import com.classproj.placeit.skyMockView;
import com.google.android.gms.maps.model.LatLng;

import Models.PlaceIt;
import Models.mockPLScheduleModel;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.mockPlaceItHandler;
import junit.framework.TestCase;

/**
 * @author SKY
 *
 */
public class UserStory8Test extends TestCase {
	
	private List<PlaceIt> plist8 = new ArrayList<PlaceIt>();
	private mockPlaceItHandler mphandler8 = new mockPlaceItHandler(plist8);
	private skyMockView mview8 = new skyMockView(plist8);
	private PlaceItController pcontroller8 = new PlaceItController(mphandler8, mview8);
	
	private LatLng pos8 = new LatLng(88.8,888.88);
	PlaceIt np;
	
	public void testViewLists_addPlaceIt(){
		/*
		 * Instatiate controller
		 * add placeit
		 * test that the view has the view add marker is called, 
		 * check placeit that was added, actually in the database 
		 * 
		 */
		pcontroller8.initializeView();
		np = pcontroller8.AddPlaceIt("user story 8", "this is a description for us8", pos8);
		mview8 = (skyMockView) pcontroller8.getView();
		mview8.addMarker(np);
		assertEquals(np,mphandler8.getPlaceIt(np.getID()));
		
	}
	
	public void testViewLists_deletePlaceIt(){
		/*
		 * Instatiate controller
		 * add placeit
		 * test that the view has the view add marker is called, 
		 * check placeit that was added, actually in the database 
		 * Check for deletion as well 
		 */
		
		this.testViewLists_addPlaceIt();
		pcontroller8.removePlaceIt(np);
		mview8.removeMarker(np);
		assertFalse(pcontroller8.getList().contains(np));
		assertEquals(null,mview8.getMarker(np.getID()));
		
	}
	
	/*public void testViewLists_taskScrolling(){
		
	}
	
	public void testViewLists_listTypeSwitch(){
		
	}
	*/
	public void testViewLists_repostPlaceit(){
		this.testViewLists_addPlaceIt();
		mockPLScheduleModel mp8 = new mockPLScheduleModel();
		PlaceItScheduler ms8 = new PlaceItScheduler(mp8, mphandler8, mview8);
		
		assertEquals(np,ms8.repostPlaceit(np));
	}

}
