package com.classproj.placeit;

import java.util.List;

import com.google.android.gms.maps.model.Marker;

import Models.PlaceIt;

public interface iView {
	
	public void addMarker(PlaceIt pc);
	
	public void removeMarker(PlaceIt pc);

	public Marker getMarker(int id);

	void notifyUser(List<PlaceIt> placeits, String ControllerType);
	
}
