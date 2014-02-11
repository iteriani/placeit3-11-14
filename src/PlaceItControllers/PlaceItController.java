package PlaceItControllers;

import java.util.List;
import java.util.Vector;

import PlaceItDB.PlaceIt;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceItController {

	private iPlaceItModel db;
	private GoogleMap googleMap;
	private List<Marker> mMarkers;
	private List<PlaceIt> placeits;
	private iPLScheduleModel scheduler;

	public PlaceItController(iPlaceItModel db, GoogleMap googleMap,
			iPLScheduleModel scheduler) {
		this.db = db;
		this.googleMap = googleMap;
		this.mMarkers = new Vector<Marker>();
		this.placeits = new Vector<PlaceIt>();
		this.scheduler = scheduler;
	}

	public void initializeMarkers() {
		Log.d("Reading: ", "Reading all placeits..");
		placeits = db.getAllPlaceIts();
		Log.d("placeitcount", Integer.toString(placeits.size()));
		for (PlaceIt pc : placeits) {
			if (pc.isActive()) {
				String log = "Id: " + pc.getID() + " ,Name: " + pc.getTitle()
						+ " ,Desc: " + pc.getDescription() + "coords : "
						+ pc.getLatitude() + "," + pc.getLongitude();
				// Writing Contacts to log
				Log.d("Name: ", log);
				String descText = pc.getDescription() + "\r\n Set on "
						+ pc.getActiveDate();
				Marker added = googleMap
						.addMarker(new MarkerOptions()
								.position(
										new LatLng(pc.getLatitude(), pc
												.getLongitude()))
								.title(pc.getTitle()).snippet(descText));
				mMarkers.add(added);
			}
		}
	}
	
	public void AddPlaceIt(String titleText, String descText, final LatLng position){
		/* Add marker to database */
		Log.d("Insert: ", "Inserting ..");
		PlaceIt placeit = new PlaceIt(titleText, descText,
				position.longitude, position.latitude);
		placeits.add(placeit);
		db.addPlaceIt(placeit);

		descText += placeit.getActiveDate();

		/* Add marker to the map */
		Marker added = googleMap.addMarker(new MarkerOptions()
				.position(position).title(titleText).snippet(descText));
		mMarkers.add(added);		
		
		}
	
}


