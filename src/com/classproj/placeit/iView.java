package com.classproj.placeit;

import java.util.List;

import com.google.android.gms.maps.model.Marker;

import Models.PlaceIt;

public interface iView {
	
	public void addMarker(PlaceIt pc);
	
	public void removeMarker(PlaceIt pc);

	public void notifyUser(List<PlaceIt> pc);

	public Marker getMarker(int id);

	
}
