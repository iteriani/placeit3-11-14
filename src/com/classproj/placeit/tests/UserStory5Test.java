/**
 * 
 */
package com.classproj.placeit.tests;

import junit.framework.TestCase;

/**
 * @author SKY
 *
 */
public class UserStory5Test extends TestCase {
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
	}
	
	public void testClickRepost(){
		//The Place-It is moved from Pulled-Down list to the Active list.
		//The corresponding entry in the database is changed to reflect this.
		/*
		 * At some click - tell controller, moving from active to deactivate
		 * and the controller will call deactivate
		 * and the iview will call deactivate as well. 
		 */
	}
}
