package com.classproj.placeit.tests;

import junit.framework.TestCase;

public class UserStory3Test extends TestCase {

	public void testOnMapClick() {
			/*User taps on a location. Verify that a “create” button pops up.
User clicks on “create” button. Verify that there is a box where the user can enter a title, description, and recurrence.
User enters a title but no description and no recurrence. Verify that a Place-It has been created with that title.
Verify that the Place-It exists on the map, in the list, and in the database.
*/
		
	}
	
	public void testPlaceitHandler() {
		/*User taps on a location. Verify that a “create” button pops up.
User clicks on “create” button. Verify that there is a box where the user can enter a title, description, and recurrence.
User enters a description but no title. Verify that a Place-It has been created, using the first few words of the description as the title.
Verify that the Place-It exists on the map, in the list, and in the database.
*/
		
	}
	
	public void testPlaceitScheduler() {
		/*User taps on a location. Verify that a “create” button pops up.
User clicks on “create” button. Verify that there is a box where the user can enter a title, description, and recurrence.
User enters a title, description, and recurrence time. Verify that a Place-It has been created all those values.
Verify that the Place-It exists on the map, in the list, and in the database.

*/
		
	}
	
	public void testEmptySchedule() {
		/*User taps on a location. Verify that a “create” button pops up.
User clicks on “create” button. Verify that there is a box where the user can enter a title, description, and recurrence.
User does not enter a title, description, and recurrence time. An error should be given that the user didn’t enter anything.
*/
		
	}
	
}
