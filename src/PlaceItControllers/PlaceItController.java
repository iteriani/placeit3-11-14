package PlaceItControllers;

import java.util.List;
import java.util.Vector;

import HTTP.PlaceItListReceiver;
import HTTP.PlaceItReceiver;
import HTTP.RequestReceiver;
import Models.CategoryPlaceIt;
import Models.LocationPlaceIt;
import Models.PlaceIt;
import PlaceItDB.iPlaceItModel;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.classproj.placeit.iView;
import com.google.android.gms.maps.model.LatLng;

public class PlaceItController {

	private iPlaceItModel db;
	private iView view;
	private List<PlaceIt> placeits;
	List<PlaceIt> nonActive = new Vector<PlaceIt>();
	List<PlaceIt> active = new Vector<PlaceIt>();

	public PlaceItController(iPlaceItModel db, iView view) {
		this.db = db;
		this.view = view;
		this.placeits = new Vector<PlaceIt>();
	}

	public void initializeView() {

		db.getAllPlaceIts(new PlaceItListReceiver() {
			@Override
			public void receivePlaceIts(List<PlaceIt> freshPlaceIts) {
				for (PlaceIt pc : freshPlaceIts) {
					if (pc.isActive()) {
						Log.d("adding marker", pc.getTitle() + "-" + pc.getDescription());
						view.addMarker(pc);
						placeits = freshPlaceIts;
					}
				}

			}

		});
	}

	// Adds a Category Place-It to the database
	public void AddPlaceIt(String titleText, String descText,
			String[] categories, final PlaceItReceiver receiver) {
		// If title and description empty. no Place-It is created
		if (titleText.length() == 0 && descText.length() == 0) {
			return;
		}

		// If title is empty but description is not, take first 10 chars of the
		// description to be the title.
		if (titleText.length() == 0) {
			int descLength = descText.length();
			if (descLength < 10) {
				titleText = descText.substring(0, descLength);
			} else {
				titleText = descText.substring(0, 10);
			}
		}
		
		// Convert the string array of categories into one string.
		CategoryAdapter adapter = new CategoryAdapter();
		String categoryString = adapter.convertArrayToString(categories);

		CategoryPlaceIt placeit = new CategoryPlaceIt(titleText, descText,
				categoryString);

		db.addPlaceIt(placeit, new PlaceItReceiver() {

			@Override
			public void receivePlaceIt(PlaceIt placeit) {
				placeits.add(placeit);
				receiver.receivePlaceIt(placeit);
			}

		});
	}
	
	// Adds a Location Place-It to the database
	@SuppressLint("NewApi")
	public PlaceIt AddPlaceIt(String titleText, String descText,
			final LatLng position, final PlaceItReceiver receiver) {

		// If title and description empty. no Place-It is created, and return
		// null.
		if (titleText.length() == 0 && descText.length() == 0) {
			return null;
		}

		// If title is empty but description is not, take first 10 chars of the
		// description to be the title.
		if (titleText.length() == 0) {
			int descLength = descText.length();
			if (descLength < 10) {
				titleText = descText.substring(0, descLength);
			} else {
				titleText = descText.substring(0, 10);
			}
		}

		LocationPlaceIt placeit = new LocationPlaceIt(titleText, descText,
				position.latitude, position.longitude);

		db.addPlaceIt(placeit, new PlaceItReceiver() {

			@Override
			public void receivePlaceIt(PlaceIt placeit) {
				placeits.add(placeit);
				view.addMarker(placeit);
				receiver.receivePlaceIt(placeit);
			}

		});
		return placeit;
	}

	public PlaceIt deactivatePlaceIt(final PlaceIt placeit) {
		placeit.setActiveDate(0);
		db.updatePlaceIt(placeit, new RequestReceiver() {

			@Override
			public void receiveTask(String s) {
				Log.d("updated placeit", placeit.getID() + " - " + s);
			}

		});
		return placeit;
	}

	public void removePlaceIt(final PlaceIt placeit) {
		db.deletePlaceIt(placeit, new RequestReceiver() {

			@Override
			public void receiveTask(String s) {
				placeits.remove(placeit);
				view.removeMarker(placeit);

			}
		});

	}

	public List<PlaceIt> getList() {
		return placeits;
	}
	
	public boolean checkCategoryLocation(CategoryPlaceIt placeit,double lat, double longitude){
		return false;
	}

	public List<PlaceIt> checkCoordinates(Location coords) {

		List<PlaceIt> clean = new Vector<PlaceIt>();
		LatLng currLoc = new LatLng(coords.getLatitude(), coords.getLongitude());
		for (int i = 0; i < placeits.size(); i++) {
			Log.d("is it a instanceof", "" + (placeits.get(i) instanceof LocationPlaceIt));
			PlaceIt placeit = placeits.get(i);
			if (placeit.isActive() && placeit instanceof LocationPlaceIt ) {
				LocationPlaceIt currMarker = (LocationPlaceIt) placeits.get(i);
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
						clean.add(currMarker);
					}
				}
			}else{
				
			}

		}
		view.notifyUser(clean, "Controller");
		return clean;

	}

	public List<PlaceIt> getNonActivePlaceIts() {
		nonActive = new Vector<PlaceIt>();
		for (PlaceIt i : placeits) {
			if (!i.isActive()) {
				nonActive.add(i);
			}
		}

		return nonActive;
	}

	public List<PlaceIt> getActiveList() {
		active = new Vector<PlaceIt>();
		for (PlaceIt i : placeits) {
			if (i.isActive()) {
				active.add(i);
			}
		}

		return active;
	}

	public String movePlaceIts(int id) {
		deactivatePlaceIt(placeits.get(id));
		return placeits.get(id).getTitle();
	}

	public PlaceIt repostIt(int id) {
		return placeits.get(id);
	}

	public void deletePlaceIts(int id, Context cont) {
		this.removePlaceIt(placeits.get(id));
	}

	public iPlaceItModel getDB() {
		return this.db;
	}

	public iView getView() {
		return this.view;
	}

}
