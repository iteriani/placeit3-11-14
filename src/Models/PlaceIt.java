/**
 * This is an abstract class of place it
 * it's the parent of both category place it and location place it
 */

package Models;

public abstract class PlaceIt {
	String _id;
	String _title;
	String _description;
	long _activeDate;

	public PlaceIt(String id, String title, String description) {
		this._id = id;
		this._title = title;
		this._description = description;
		this._activeDate = new java.util.Date().getTime();
	}
	
	public PlaceIt(String title, String description) {
		this._title = title;
		this._description = description;
		this._activeDate = new java.util.Date().getTime();
	}
	
	public PlaceIt(String id, String title, String description, long activeDate) {
		this._id = id;
		this._title = title;
		this._description = description;
		this._activeDate = activeDate;
	}
	

	public PlaceIt(String title, String description, long date) {
		this._title = title;
		this._description = description;
		this._activeDate = date;
	}

	// getting ID
	public String getID() {
		return this._id;
	}

	// setting ID
	public void setID(String id) {
		this._id = id;
	}

	// getting title
	public String getTitle() {
		return this._title;
	}

	// setting title
	public void setTitle(String title) {
		this._title = title;
	}

	// getting description
	public String getDescription() {
		return this._description;
	}

	// setting description
	public void setDescription(String description) {
		this._description = description;
	}
	// getting date
	public java.util.Date getActiveDate() {
		return new java.util.Date(this._activeDate);
	}

	// setting description
	public void setActiveDate(long sd) {
		this._activeDate = sd;
	}

	
	public boolean isActive() {
		return this._activeDate != 0;
	}

	
}
