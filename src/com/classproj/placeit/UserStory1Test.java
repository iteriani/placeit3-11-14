package com.classproj.placeit;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.classproj.placeit.MainActivity;
import com.google.android.gms.maps.GoogleMap;

public class UserStory1Test extends ActivityUnitTestCase<MainActivity> {
	public UserStory1Test(){
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception{
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MainActivity.class);
        startActivity(mLaunchIntent, null, null);
	}
	
	public void testMapAppears(){
		final MainActivity act = getActivity();
		GoogleMap map = act.getViewMap();
		assertNotNull(map);
		//check the api key and see if it validates
		fail();
	}
}
