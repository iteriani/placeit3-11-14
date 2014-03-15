/**
 * This is an interface for the main view
 * On the map it add and remove markers
 * it notifies user and get markers
 */

package com.classproj.placeit;

import java.util.List;

import com.google.android.gms.maps.model.Marker;

import Models.PlaceIt;

public interface iView {
	
	public void addMarker(PlaceIt pc);
	
	public void removeMarker(PlaceIt pc);

	public Marker getMarker(int id);

	void notifyUser(List<PlaceIt> placeits, String ControllerType);

	void clearMap();

}
