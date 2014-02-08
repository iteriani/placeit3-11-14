package com.classproj.placeit;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import PlaceItDB.PlaceIt;
import PlaceItDB.PlaceItHandler;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapClickListener {
	
	/*  record object is used in database handler to bind to activity*/
	FragmentActivity record = this;
	
	/* googleMap is our singleton map to add ui elements to.*/
	GoogleMap googleMap;
	
	/* Current location of user*/
	Location location;
	
	/* Reference to items in swipe-bar*/
	String[] swipebarElements;
	private ListView viewLists;
	
	/* reference to markers on the map*/
	private List<Marker> mMarkers;
	private Iterator<Marker> marker;
	
	/* Reference to all placeits*/
	List<PlaceIt> placeits;
	
	/* Database handler */
	private PlaceItHandler db;

	@SuppressLint("NewApi")
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (googleMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		swipebarElements = new String[] { "Ankoor", "Hitler", "Stalin" };
		DrawerLayout myDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		viewLists = (ListView) findViewById(R.id.left_drawer);

		mMarkers = new Vector<Marker>();
		this.setUpMapIfNeeded();
		db = new PlaceItHandler(record);
		this.initializeMarkers();
		this.setUpFindButton();
		googleMap.setOnMapClickListener(this);
		googleMap.setMyLocationEnabled(true);

	}

	public void setUpFindButton() {
		// Getting reference to btn_find of the layout activity_main
		Button btn_find = (Button) findViewById(R.id.find);

		// Defining button click event listener for the find button
		OnClickListener findClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Getting reference to EditText to get the user input location
				EditText etLocation = (EditText) findViewById(R.id.location);

				// Getting user input location
				String location = etLocation.getText().toString();

				if (location != null && !location.equals("")) {
					new GeocoderTask().execute(location);
				}
			}
		};

		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);
	}
	
	
	@Override
	public void onMapClick(final LatLng position) {
		/* Initialize dialog box*/
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Create Place-It");
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_form, null);
		final EditText title = (EditText) dialog.findViewById(R.id.title);
		final EditText description = (EditText) dialog
				.findViewById(R.id.description);
		alert.setView(dialog);
		/* Initialize submission button.*/
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				/* User submits his/her placeit and this method is called */
				String descText = description.getText().toString();
				String titleText = title.getText().toString();
				/* Notification of added place-it*/
				Toast.makeText(MainActivity.this, "Place-it added!",
						Toast.LENGTH_SHORT).show();
				
				/* Add marker to database*/
				Log.d("Insert: ", "Inserting ..");
				PlaceIt placeit = new PlaceIt(titleText, descText,
						position.longitude, position.latitude);
				placeits.add(placeit);
				db.addPlaceIt(placeit);		
				
				
				descText += placeit.getActiveDate();
				
				/* Add marker to the map*/
				Marker added = googleMap.addMarker(new MarkerOptions()
						.position(position).title(titleText).snippet(descText));
				mMarkers.add(added);
				

			}
		});
		/* Cancel button which does nothing when clicked and exits the dialog.*/
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(MainActivity.this, "Nothing added!",
								Toast.LENGTH_SHORT).show();
					}
				});
		
		alert.show();
	}

	private void initializeMarkers() {
		Log.d("Reading: ", "Reading all placeits..");
		placeits = db.getAllPlaceIts();
		Log.d("placeitcount", Integer.toString(placeits.size()));
		for (PlaceIt pc : placeits) {
			String log = "Id: " + pc.getID() + " ,Name: " + pc.getTitle()
					+ " ,Desc: " + pc.getDescription() + "coords : "
					+ pc.getLatitude() + "," + pc.getLongitude();
			// Writing Contacts to log
			Log.d("Name: ", log);
			String descText = pc.getDescription() + "\r\n Set on " +  pc.getActiveDate();
			Marker added = googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(pc.getLatitude(), pc.getLongitude()))
					.title(pc.getTitle()).snippet(descText));
			mMarkers.add(added);

		}
	}

	private void centerMapOnMyLocation() {

		googleMap.setMyLocationEnabled(true);

		location = googleMap.getMyLocation();

		if (location != null) {
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
					8.0f));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// An AsyncTask class for accessing the GeoCoding Web Service
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		private void centerMapOnMyLocation() {

			googleMap.setMyLocationEnabled(true);

			location = googleMap.getMyLocation();

			if (location != null) {
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						latLng, 8.0f));
			}

		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			if (addresses == null || addresses.size() == 0) {
				Toast.makeText(getBaseContext(), "Not found. Lost forever",
						Toast.LENGTH_SHORT).show();
			}

			// Clears all the existing markers on the map
			googleMap.clear();

			// Adding Markers on Google Map for each matching address
			for (int i = 0; i < addresses.size(); i++) {

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				LatLng latLng = new LatLng(address.getLatitude(),
						address.getLongitude());

				String addressText = String.format(
						"%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address
								.getCountryName());

				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title(addressText);

				googleMap.addMarker(markerOptions);

				// Locate the first location
				if (i == 0)
					googleMap.animateCamera(CameraUpdateFactory
							.newLatLng(latLng));
			}
		}
	}

}
