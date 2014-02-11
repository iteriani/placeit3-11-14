package PlaceItControllers;

import java.util.List;
import java.util.Vector;

import Models.PlaceIt;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;
import android.util.Log;

import com.classproj.placeit.iView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceItController {

	private iPlaceItModel db;
	private iView view;
	private List<PlaceIt> placeits;
	private iPLScheduleModel scheduler;

	public PlaceItController(iPlaceItModel db, iView view,
			iPLScheduleModel scheduler) {
		this.db = db;
		this.view = view;

		this.placeits = new Vector<PlaceIt>();
		this.scheduler = scheduler;
	}

	public void initializeMarkers() {
		
		placeits = db.getAllPlaceIts();
		for (PlaceIt pc : placeits) {
			if (pc.isActive()) {
				view.addMarker(pc);
			}
		}
	}
	
	public void AddPlaceIt(String titleText, String descText, final LatLng position){
		
		PlaceIt placeit = new PlaceIt(titleText, descText,
				position.longitude, position.latitude);
		placeits.add(placeit);
		db.addPlaceIt(placeit);

		descText += placeit.getActiveDate();

		/* Add marker to the map */
		view.addMarker(placeit);
		
		}
	
}


