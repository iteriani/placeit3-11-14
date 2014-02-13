package com.classproj.placeit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.PLScheduleHandler;
import PlaceItDB.PlaceItHandler;
import PlaceItDB.iPLScheduleModel;
import com.classproj.placeit.R;
import PlaceItDB.iPlaceItModel;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		OnMapClickListener, LocationListener, iView {

	/* record object is used in database handler to bind to activity */
	FragmentActivity record = this;
	LocationManager locationManager;

	/* googleMap is our singleton map to add ui elements to. */
	GoogleMap googleMap;

	/* Current location of user */
	Location location;

	/* */

	/* Markers on the map */
	List<Marker> mMarkers;

	/* Reference to items in swipe-bar */
	String[] swipebarElements;
	private ListView viewLists;

	/* Controller */
	PlaceItController controller;

	@SuppressLint("NewApi")
	private GoogleMap setUpMapIfNeeded() {
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
		return googleMap;
	}
	public void expand() {
	    String[] newArray = new String[swipebarElements.length + 1];
	    System.arraycopy(swipebarElements, 0, newArray, 0, swipebarElements.length);

	    //an alternative to using System.arraycopy would be a for-loop:
	    // for(int i = 0; i < OrigArray.length; i++)
	    //     newArray[i] = OrigArray[i];
	    swipebarElements = newArray;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMarkers = new LinkedList<Marker>();

		GoogleMap map = this.setUpMapIfNeeded();
		googleMap.setOnMapClickListener(this);
		googleMap.setMyLocationEnabled(true);

		iPLScheduleModel scheduleDB = new PLScheduleHandler(record);
		iPlaceItModel db = new PlaceItHandler(record);
		PlaceItScheduler scheduler = new PlaceItScheduler(scheduleDB, db);
		controller = new PlaceItController(db, this);
		controller.initializeView();
		swipebarElements = new String[]{"No Reminders"};
		ArrayList<String> newList = new ArrayList<String>();
		DrawerLayout myDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		viewLists = (ListView) findViewById(R.id.left_drawer);
		if (controller.getList()== null || controller.getList().size()== 0)
		{	
			newList.add("No Reminders");
		}
		else
		{
			for (int i =0; i < controller.getList().size(); i++)
			{
				newList.add(controller.getList().get(i).getTitle());
			}
	
		}

		viewLists.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_left, newList));
		
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		Location coords = locationManager
				.getLastKnownLocation(locationManager.GPS_PROVIDER);
		if (coords != null) {
			controller.checkCoordinates(coords);
		}
		this.setUpFindButton();
		List<PlaceIt> checkList = null;
		Location myLocationNow = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
	    if (myLocationNow != null) {
	    	 checkList= controller.checkCoordinates(myLocationNow);
	    }
	    
	    if (checkList != null)
	    {
	    	createNotifs(checkList);
	    }
	
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
					new GeocoderTask(getBaseContext(), googleMap)
							.execute(location);
				}
			}
		};

		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);
	}

	@Override
	public void onMapClick(final LatLng position) {
		setUpDialog(position);
	}

	public void setUpDialog(final LatLng position) {
		/* Initialize dialog box */
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Create Place-It");
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_form, null);
		final EditText title = (EditText) dialog.findViewById(R.id.title);
		final EditText description = (EditText) dialog
				.findViewById(R.id.description);
		alert.setView(dialog);
		/* Initialize submission button. */
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				/* User submits his/her placeit and this method is called */
				String descText = description.getText().toString();
				String titleText = title.getText().toString();
				/* Notification of added place-it */
				Toast.makeText(MainActivity.this, "Place-it added!",
						Toast.LENGTH_SHORT).show();

				controller.AddPlaceIt(titleText, descText, position);

			}
		});
		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(MainActivity.this, "Nothing added!",
								Toast.LENGTH_SHORT).show();
					}
				});

		alert.show();
	}

	public void setUpPlaceItNotification(List<PlaceIt> pc) {
		/* Initialize dialog box */
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Create Place-It");
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_form, null);
		final EditText title = (EditText) dialog.findViewById(R.id.title);
		final EditText description = (EditText) dialog
				.findViewById(R.id.description);
		alert.setView(dialog);
		/* Initialize submission button. */
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(MainActivity.this, "Nothing added!",
								Toast.LENGTH_SHORT).show();
					}
				});

		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addMarker(PlaceIt pc) {
		String log = "Id: " + pc.getID() + " ,Name: " + pc.getTitle()
				+ " ,Desc: " + pc.getDescription() + "coords : "
				+ pc.getLatitude() + "," + pc.getLongitude();
		String descText = pc.getDescription() + "\r\n Set on "
				+ pc.getActiveDate();
		Marker added = googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(pc.getLatitude(), pc.getLongitude()))
				.title(pc.getTitle()).snippet(descText));
		mMarkers.add(added);
	}

	@Override
	public void removeMarker(PlaceIt pc) {
		for (Marker marker : mMarkers) {
			if (marker.getTitle() == pc.getTitle()
					&& marker.getSnippet() == pc.getDescription()) {
				marker.remove();
				mMarkers.remove(marker);
			}
		}

	}

	@Override
	public void notifyUser(List<PlaceIt> pc) {

	}

	@Override
	public Marker getMarker(int id) {
		for(Marker marker : mMarkers){
			if(marker.getId() == Integer.toString(id)){
				return marker;
			}
		}
		return null;
		
	}
	
	public void createNotifs (List<PlaceIt> cleanList)
	{
		for (int i = 0; i < cleanList.size(); i++ )
		{
			NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(this)
		    .setSmallIcon(R.drawable.cat)
		    .setContentTitle(cleanList.get(i).getTitle())
		    .setContentText(cleanList.get(i).getDescription());;
			
			// Sets an ID for the notification
			int mNotificationId = 001;
			// Gets an instance of the NotificationManager service
			NotificationManager mNotifyMgr = 
			        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.notify(mNotificationId, mBuilder.build());
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		List<PlaceIt> cleanList = controller.checkCoordinates(arg0);
		createNotifs(cleanList);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
