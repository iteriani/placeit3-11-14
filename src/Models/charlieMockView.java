/**
 * This is the mocking view for testing in MS1
 * Charlie created this to assist his part of testing
 */

package Models;

import java.util.LinkedList;
import java.util.List;

import com.classproj.placeit.iView;
import com.google.android.gms.maps.model.Marker;

public class charlieMockView implements iView{

	List<PlaceIt> mMarkers = new LinkedList<PlaceIt>();
	
	public charlieMockView (List<PlaceIt> userInput) {
		mMarkers = userInput; 
	}
	
	@Override
	public void addMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		
		mMarkers.add(pc);
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Marker getMarker(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyUser(List<PlaceIt> placeits, String ControllerType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearMap() {
		// TODO Auto-generated method stub
		
	}

}
