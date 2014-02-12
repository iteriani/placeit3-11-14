package com.classproj.placeit;

import Models.PlaceIt;

public interface iView {
	
	public void addMarker(PlaceIt pc);
	
	public void removeMarker(PlaceIt pc);
	
	public void getMarker(int id);
	// get marker based on the Place-It's ID
	
}
