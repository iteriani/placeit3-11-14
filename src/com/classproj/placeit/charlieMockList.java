package com.classproj.placeit;

import java.util.LinkedList;
import java.util.List;

import Models.PlaceIt;

import com.google.android.gms.maps.model.Marker;

/*
 * Should just model over what the view does, instead be implemented towards a list
 */
public class charlieMockList implements iView{

List<PlaceIt> mMarkers = new LinkedList<PlaceIt>();
public static boolean methodCaller = false; 

	public charlieMockList (List<PlaceIt> userInput) {
		mMarkers = userInput; 
	}
	
	public boolean getCaller() {
	 return methodCaller; 
	}
	
	public int getPlaceItsCountInView() {
		return mMarkers.size(); 
	}
	@Override
	public void addMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		
		mMarkers.add(pc);
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		// TODO Auto-generated method stub
		methodCaller = true; 
		for(int i = 0; i<mMarkers.size(); i++){
			System.out.println("sizeis: " + mMarkers.size()); 
			PlaceIt pc1 = mMarkers.get(i); 
			if(pc.getID() == pc1.getID() && pc.getTitle() == pc1.getTitle() 
					&& pc.getDescription() == pc1.getDescription() && pc.getLongitude() == pc1.getLongitude())  {
			System.out.println("The deleted placeit is ID: " + pc1.getID()); 
				mMarkers.remove(pc1);
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
		return null;
	}
}


