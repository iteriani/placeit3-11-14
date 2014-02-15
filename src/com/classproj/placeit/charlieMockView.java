package com.classproj.placeit;

import java.util.LinkedList;
import java.util.List;

import Models.PlaceIt;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class charlieMockView implements iView{

	List<PlaceIt> mMarkers = new LinkedList<PlaceIt>();
	
	public charlieMockView (List<PlaceIt> userInput) {
		mMarkers = userInput; 
	}
	
	@Override
	public void addMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		
		mMarkers.add(pc);
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Marker getMarker(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyUser(List<PlaceIt> placeits, String ControllerType) {
		// TODO Auto-generated method stub
		
	}

}
