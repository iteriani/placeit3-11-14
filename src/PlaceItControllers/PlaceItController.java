package PlaceItControllers;

import java.util.List;
import java.util.Vector;

import Models.PlaceIt;
import PlaceItDB.iPlaceItModel;
import android.location.Location;

import com.classproj.placeit.iView;
import com.google.android.gms.maps.model.LatLng;

public class PlaceItController {

	private iPlaceItModel db;
	private iView view;
	private List<PlaceIt> placeits;

	public PlaceItController(iPlaceItModel db, iView view) {
		this.db = db;
		this.view = view;

		this.placeits = new Vector<PlaceIt>();
	}

	public void initializeView() {

		placeits = db.getAllPlaceIts();
		for (PlaceIt pc : placeits) {
			if (pc.isActive()) {
				view.addMarker(pc);
			}
		}
	}

	public PlaceIt AddPlaceIt(String titleText, String descText,
			final LatLng position) {

		PlaceIt placeit = new PlaceIt(titleText, descText, position.latitude,
				position.longitude);
		placeits.add(placeit);
		db.addPlaceIt(placeit);
		view.addMarker(placeit);
		return placeit;
	}
	
	public void RemovePlaceIt(PlaceIt placeit){
	//	placeits.remove(placeit);
		db.deactivatePlaceit(placeit);
		view.removeMarker(placeit);
	}
	
	public List<PlaceIt> getList()
	{
		return placeits;
	}

	public List<PlaceIt> checkCoordinates(Location coords) {
		
		List<PlaceIt> clean = new Vector<PlaceIt>();
		LatLng currLoc = new LatLng(coords.getLatitude(), coords.getLongitude());
		for (int i = 0; i < placeits.size(); i++) {
			PlaceIt currMarker = placeits.get(i);
			Location start = new Location("Start");
			Location end = new Location("End");
			if (currLoc != null && currMarker != null) {
				start.setLatitude(currLoc.latitude);
				start.setLongitude(currLoc.longitude);
				end.setLongitude(currMarker.getLongitude());
				end.setLatitude(currMarker.getLatitude());
				float dist = start.distanceTo(end);
				// Convert to miles
				dist = (float) (dist * 0.000621371);
				if (dist <= .5) {
					clean.add(placeits.get(i));
				}	
			}
		}
		
		return clean;

	}

}
