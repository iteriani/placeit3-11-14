package com.classproj.placeit.tests;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.classproj.placeit.mockView;

import com.classproj.placeit.charlieMockView;

import Models.PlaceIt;
import Models.mockPLScheduleModel;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.mockPlaceItHandler;
import junit.framework.TestCase;

public class UserStory7Test extends TestCase {

	String title = "just a title";
	String desc = "just a description"; 
	double lat = 20; 
	double longt = 30; 
	long date = 0; 
	PlaceIt justAPlaceit4 = new PlaceIt(title, desc, lat+1, longt+1, date);
	PlaceIt justAPlaceit3 = new PlaceIt(title, desc, lat-2, longt-2, date);
	PlaceIt justAPlaceit2 = new PlaceIt(title, desc, lat+3, longt+3, date);  
	PlaceIt justAPlaceit1 = new PlaceIt(title, desc, lat-4, longt-4, date);  
	List<PlaceIt>testList = new LinkedList<PlaceIt>();  
	
	mockPlaceItHandler mockList = new mockPlaceItHandler(testList);
	PlaceIt justAPlaceit = new PlaceIt(title, desc, lat, longt, date); 
	mockPLScheduleModel testscheduleModel = new mockPLScheduleModel(); 


	PlaceItScheduler testScheduler = new PlaceItScheduler(testscheduleModel, mockList, new mockView(null));
	public void testRepostPlaceit() {
	 /*
	  * call repost, and check that the new one updated in database has time 
	  * that is equal to the repost amount
	  */
		testList.add(justAPlaceit4); 
		testList.add(justAPlaceit3); 
		testList.add(justAPlaceit2); 
		testList.add(justAPlaceit1); 
		
	
		System.out.println(new Date(0).toString());
		Date whatisdate = justAPlaceit.getActiveDate(); 
		assertNotNull(whatisdate);
		assertEquals(whatisdate, justAPlaceit.getActiveDate());
		System.out.println("this is what date is: " + whatisdate); 
		testScheduler.repostPlaceit(justAPlaceit ); 
		Date whatisnewdate = justAPlaceit.getActiveDate(); 
		System.out.println("this is the updated date: " + whatisnewdate); 
<<<<<<< HEAD
		assertNull(whatisdate); 
=======
>>>>>>> 48e5103722ef3570a21ba6bc60d66045e73d43f1
		assertNotSame(whatisdate, whatisnewdate); 
		
	}
	
	public void testdelay() {
		/*
		 *  Test the delay so that the amount that is reposted by is 45 minutes
		 */
		
		testList.add(justAPlaceit4); 
		testList.add(justAPlaceit3); 
		testList.add(justAPlaceit2); 
		testList.add(justAPlaceit1); 
		
		int a = Calendar.MINUTE; 
		int b = 45; 
		
		System.out.println(new Date(0).toString());
		Date date1 = justAPlaceit.getActiveDate(); 
		assertNotNull(date1);
		assertEquals(date1, justAPlaceit.getActiveDate());
		System.out.println("this is what date is: " + date1); 
		testScheduler.repostPlaceit(justAPlaceit, a, b ); 
		Date date2 = justAPlaceit.getActiveDate(); 
		System.out.println("this is the updated date: " + date2); 
		assertNotSame(date1, date2); 
		assertTrue(date1.before(date2)); 
		assertFalse(date1.after(date2)); 
		
		
	}
	
	public void testMoveList() {
		/*
		 * when adding a placeit, in the controller
		 * in the getactiveplaceits
		 * check the placeits that we have added is. 
		 * Same method but htis one checks the list view
		 * assert that getactiveplaceits gets called when it is active. 
		 */
		int a = Calendar.YEAR; 
		int b = 252; 
		testList.add(justAPlaceit4); 
		
		Date date1 = justAPlaceit4.getActiveDate();
		System.out.println("this is what date is: " + date1); 
		//Should have a move from list to (list) so I can test this. 
		//But I can add a checks the list of view is updated. 
		//And a we need a get-active placeits method 
		
		testScheduler.repostPlaceit(justAPlaceit, a, b ); 
		PlaceIt takeOutDataBase = testList.get(0); 
		
		
		assertEquals(takeOutDataBase, justAPlaceit4); 
		
		Date date2 = takeOutDataBase.getActiveDate(); 
		System.out.println("this is the updated date: " + date2); 
		
		assertTrue(date1.before(date2)); 
		assertTrue(date2.after(date1)); 
		assertFalse(date1.after(date2)); 
		assertFalse(date2.before(date1)); 
		/*
		 * After reposting placeit, I should be able to test a method of movinglist
		 * When adding placeit that's repost, the movelist should be called and returned
		 * 
		 */
	}
	
	
	
	public void testDataBaseRepost() {
		/*
		 * added to database, call repost, 
		 * make sure time is updated higher than original. 
		 * activation date is larger
		 * logic will handle in controller, else fail. 
		 */
		
		int a = Calendar.YEAR; 
		int b = 252; 
		testList.add(justAPlaceit4); 
		
		Date date1 = justAPlaceit4.getActiveDate();
		System.out.println("this is what date is: " + date1); 
		testScheduler.repostPlaceit(justAPlaceit, a, b ); 
		PlaceIt takeOutDataBase = testList.get(0); 
		assertEquals(takeOutDataBase, justAPlaceit4); 
		Date date2 = takeOutDataBase.getActiveDate(); 
		System.out.println("this is the updated date: " + date2); 
		
		assertTrue(date1.before(date2)); 
		assertTrue(date2.after(date1)); 
		assertFalse(date1.after(date2)); 
		assertFalse(date2.before(date1)); 
		
	}
	
}