package com.classproj.placeit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Models.PlaceIt;

public class mockView implements iView{
	GoogleMap googleMap; 
	
	List<Marker> mMarkers;
	
	public mockView (List<Marker> userInput) {
		mMarkers = userInput; 
	}
	
	List<PlaceIt> testMarker = new LinkedList<PlaceIt>(); 
	

	@Override
	public void addMarker(PlaceIt pc) {
		String mockText = "fake"; 
		Marker added = googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(pc.getLatitude(), pc.getLongitude()))
				.title(pc.getTitle()).snippet(mockText));
		mMarkers.add(added);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		for(Marker marker : mMarkers){
			if(marker.getTitle() == pc.getTitle()) {
				mMarkers.remove(marker);
			}
		// TODO Auto-generated method stub
			}
		}

	@Override
	public void notifyUser(List<PlaceIt> pc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Marker getMarker(int id) {
		// TODO Auto-generated method stub
		for(Marker m : mMarkers){
			if (m.getId().equals(id)){
				return m;
			}
		}
		return null;
	}
	}