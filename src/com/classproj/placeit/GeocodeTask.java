package com.classproj.placeit;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// An AsyncTask class for accessing the GeoCoding Web Service
class GeocoderTask extends AsyncTask<String, Void, List<Address>> {
	Context baseContext;
	GoogleMap googlemap;
	Marker addedMark;
	public GeocoderTask(Context context, GoogleMap googlemap) {
		this.baseContext = context;
		this.googlemap = googlemap;
	}

	@Override
	protected List<Address> doInBackground(String... locationName) {
		// Creating an instance of Geocoder class
		Geocoder geocoder = new Geocoder(this.baseContext);
		List<Address> addresses = null;

		try {
			// Getting a maximum of 3 Address that matches the input text
			addresses = geocoder.getFromLocationName(locationName[0], 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addresses;
	}

	@Override
	protected void onPostExecute(List<Address> addresses) {

		if (addresses == null || addresses.size() == 0) {
			Toast.makeText(this.baseContext, "Not found. Lost forever",
					Toast.LENGTH_SHORT).show();
		}

		// Adding Markers on Google Map for each matching address
		for (int i = 0; i < addresses.size(); i++) {

			Address address = (Address) addresses.get(i);

			// Creating an instance of GeoPoint, to display in Google Map
			LatLng latLng = new LatLng(address.getLatitude(),
					address.getLongitude());

			String addressText = String.format("%s, %s", address
					.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0)
					: "", address.getCountryName());

			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(latLng);
			markerOptions.title(addressText);

			addedMark = googlemap.addMarker(markerOptions);

			// Locate the first location
			if (i == 0)
				googlemap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
		}
	}
	
	public void removeMarkers()
	{
		if (addedMark != null)
		{
			addedMark.remove();
		}
	}
}
