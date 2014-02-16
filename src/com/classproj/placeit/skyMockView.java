package com.classproj.placeit;

import java.util.LinkedList;
import java.util.List;

import Models.PlaceIt;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * @author SKY
 *
 */
public class skyMockView implements iView{
	
	List<Marker> mMarkers = new LinkedList<Marker>();
	
	public skyMockView(List<PlaceIt> userInput){
		Marker m = new Marker(null);
		LatLng l = null;
		for (PlaceIt p : userInput){
			m.setTitle(p.getTitle());
			l = new LatLng(p.getLatitude(),p.getLongitude());
			m.setPosition(l);
			mMarkers.add(m);
		}
	}

	@Override
	public void addMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		Marker m = new Marker(null);
		LatLng l = null;
		m.setTitle(pc.getTitle());
		l = new LatLng(pc.getLatitude(),pc.getLongitude());
		m.setPosition(l);
		mMarkers.add(m);		
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		for(int i = 0; i<mMarkers.size(); i++){
			System.out.println("sizeis: " + mMarkers.size()); 
			Marker m1 = mMarkers.get(i); 
			if(((Integer)pc.getID()).toString() == m1.getId() && pc.getTitle() == m1.getTitle())  {
				System.out.println("The deleted marker is ID: " + m1.getId()); 
				mMarkers.remove(m1);
			}
		}
	}

	@Override
	public Marker getMarker(int id) {
		// TODO Auto-generated method stub
		for (Marker m : mMarkers){
			if (m.getId() == ((Integer)id).toString()){
				return m;
			}
		}
		return null;
	}

	@Override
	public void notifyUser(List<PlaceIt> placeits, String ControllerType) {
		// TODO Auto-generated method stub
		
	}
	
	public int getMarkerCount(){
		return mMarkers.size();
	}

}
