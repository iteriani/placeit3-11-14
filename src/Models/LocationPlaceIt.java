/**
 * This is a location placeit class. 
 * It inherits the abstract class PlaceIt and has latitude and longitude to store its location.
 */

package Models;

public class LocationPlaceIt extends PlaceIt {

	double _latitude;
	double _longitude;

	public LocationPlaceIt(String title, String description) {
		super(title, description);
	}

	public LocationPlaceIt(String id, String title, String description, double latitude,
			double longitude, long date) {
		
		super(id, title, description, date);
		this._latitude = latitude;
		this._longitude = longitude;
	}

	public LocationPlaceIt(String title, String description, double latitude,
			double longitude, long date) {
		super(title, description, date);
		this._latitude = latitude;
		this._longitude = longitude;
	}

	public LocationPlaceIt(String title, String description, double latitude,
			double longitude) {
		
		super(title, description);
		this._latitude = latitude;
		this._longitude = longitude;
		this._activeDate = new java.util.Date().getTime();
	}

	// getting longitude
	public double getLongitude() {
		return this._longitude;
	}

	// setting description
	public void setLongitude(double longitude) {
		this._longitude = longitude;
	}

	// getting longitude
	public double getLatitude() {
		return this._latitude;
	}

	// setting description
	public void setLatitude(double latitude) {
		this._latitude = latitude;
	}

}
