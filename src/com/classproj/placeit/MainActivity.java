package com.classproj.placeit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.PLScheduleHandler;
import PlaceItDB.PlaceItHandler;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMapClickListener, LocationListener, iView,
		com.google.android.gms.location.LocationListener,
		 ListView.OnItemClickListener{
	GeocoderTask findPlace;
	/* record object is used in database handler to bind to activity */
	FragmentActivity record = this;
	LocationManager locationManager;
	int deleteId = 0;
	/* googleMap is our singleton map to add ui elements to. */
	GoogleMap googleMap;

	/* Current location of user */
	Location location;
	boolean discard = false;
	boolean delete = false	;
	int index = 0;
	/* */
	LocationRequest mLocationRequest;
	LocationClient mLocationClient;
	com.google.android.gms.location.LocationListener mListener = this;
	boolean deleteCalled= false;
	/* Markers on the map */
	List<Marker> mMarkers;
	Marker locMarker;
	/* Reference to items in swipe-bar */
	String[] swipebarElements;
	private ListView viewLists;
	private ListView rightList;
	/* Controller */
	
	PlaceItController controller;
	PlaceItScheduler scheduler;
	ArrayList<String> newList = new ArrayList<String>();
	ArrayList<String> nonActive = new ArrayList<String>();

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
		System.arraycopy(swipebarElements, 0, newArray, 0,
				swipebarElements.length);

		// an alternative to using System.arraycopy would be a for-loop:
		// for(int i = 0; i < OrigArray.length; i++)
		// newArray[i] = OrigArray[i];
		swipebarElements = newArray;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMarkers = new LinkedList<Marker>();

		GoogleMap googleMap = this.setUpMapIfNeeded();
		googleMap.setOnMapClickListener(this);
		googleMap.setMyLocationEnabled(true);

		iPLScheduleModel scheduleDB = new PLScheduleHandler(record);
		iPlaceItModel db = new PlaceItHandler(record);
		scheduler = new PlaceItScheduler(scheduleDB, db, this);
		controller = new PlaceItController(db, this);
		controller.initializeView();

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		this.setUpSideBar();
		this.setUpFindButton();

		List<PlaceIt> checkList = null;
		Location myLocationNow = locationManager
				.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

		if (myLocationNow != null) {
			checkList = controller.checkCoordinates(myLocationNow);
		}

		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(PlaceItSettings.NOTIFICATION_INTERVAL);
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();

	}

	
	public void setUpSideBar() {
		swipebarElements = new String[] { "Active Reminders" };
		DrawerLayout myDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		newList = new ArrayList<String>();
		newList.add ("Active Reminders");
		nonActive = new ArrayList<String>();
		this.nonActive.add("Non Active Remidners");
		List<PlaceIt> activeOne = new Vector<PlaceIt>();
		List<PlaceIt> nonActiveOne = new Vector<PlaceIt>();
		activeOne = controller.getActiveList();
		nonActiveOne = controller.getNonActivePlaceIts();
		if (this.deleteCalled)
		{
			Toast.makeText(this, ""+deleteId+"", Toast.LENGTH_LONG).show();
			activeOne.remove(deleteId-1);
		}
		if (activeOne.size() == 0) {
			
			
		} else {
			for (PlaceIt now : activeOne) {
				newList.add(now.getTitle());
			}
		}
		
		if (nonActiveOne.size()==0)
		{
			
		}
		else{
			for (PlaceIt now: nonActiveOne)
			{
				nonActive.add(now.getTitle());
			}
		}
		
		
		viewLists = (ListView) findViewById(R.id.left_drawer);
		viewLists.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_left, newList));
		viewLists.setOnItemClickListener(this);
		rightList = (ListView)findViewById(R.id.right_drawer);
		rightList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_left,nonActive));
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
					findPlace = new GeocoderTask(getBaseContext(), googleMap);
					findPlace.execute(location);
				}
			}
		};

		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);
	}

	@Override
	public void onMapClick(final LatLng position) {
		setUpDialog(position);
		if (findPlace != null) {
			findPlace.removeMarkers();
		}
	}

	public void setupTimeDialog(final String title, final String description,
			final LatLng location) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Set recurrence for PlaceIt " + title);
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_time_form, null);

		int checkedItem = 0;

		// Create single choice list
		builder.setSingleChoiceItems(R.array.days_array, checkedItem,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int checkedItem) {
						// do something with checkedItem
					}
				});
		
		// Create textbox for number of weeks
		TextView every = (TextView) dialog.findViewById(R.id.every);
		final EditText numweeks = (EditText) dialog.findViewById(R.id.numweeks);
		TextView weeks = (TextView) dialog.findViewById(R.id.weeks);

		// Set the action buttons
		builder.setPositiveButton(R.string.recurrence_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					
						PlaceIt placeit = controller.AddPlaceIt(title,
								description, location);
						List<Integer> ints = new Vector<Integer>();
						int ScheduleID  = ((AlertDialog)dialog).getListView().getCheckedItemPosition()-1;
						ints.add(Integer.valueOf(ScheduleID));
						Log.d("creation id ", ints.toString());
						if(ScheduleID >= 0 ){
							scheduler.addSchedules(placeit, ints);
						}
						scheduler.scheduleNextActivation(placeit);
						setUpSideBar();
		
						/* Notification of added place-it */
						Toast.makeText(MainActivity.this, "Place-it added!",
								Toast.LENGTH_SHORT).show();

					}
				});
		
		builder.setNegativeButton(R.string.recurrence_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						setUpSideBar();
					}
				});

		builder.setView(dialog);
		builder.show();
	}

	public void setUpDiscard()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Discard or Delete");
		LayoutInflater inflater = getLayoutInflater();
	
		
		alert.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				if (MainActivity.this.nonActive.size()!= 0)
				{
					index = (index+ nonActive.size())-2;
				}
				else
				{
					index = index - 1;
				}
				//Toast.makeText(MainActivity.this, "" + index + "", Toast.LENGTH_SHORT).show();
				String temp = controller.movePlaceIts(index);
				setUpSideBar();
			}
		});

		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						int tempInd = index;
						if (nonActive.size()==1)
						{
							index = index - 1;
						}
						else 
						{
							index = (newList.size() - nonActive.size())+1;
						}
						//Toast.makeText(MainActivity.this, "" + nonActive.size()+ " = " + newList.size()+"", Toast.LENGTH_SHORT).show();
						controller.deletePlaceIts(index, MainActivity.this.getApplicationContext());
						deleteCalled = true;
						deleteId = tempInd;
						setUpSideBar();
					}
				});

		alert.show();
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
				setupTimeDialog(titleText, descText, position);
				if (descText.matches("") && titleText.matches("")) {
					Toast.makeText(MainActivity.this, "Please enter a title or descripion.",
							Toast.LENGTH_SHORT).show();
				}
				else {
					
					setUpSideBar(); 
				}
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

	public void setUpNotification(final List<PlaceIt> placeits) {
		if (placeits.size() == 0) {
			return;
		}

		createNotifs(placeits);
		/* Initialize dialog box */
		final PlaceIt placeit = placeits.get(0);
		final PlaceIt initial = scheduler.scheduleNextActivation(placeit);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("You got a Place-It!");
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_notification,
				null);
		TextView textViewTitle = (TextView) dialog.findViewById(R.id.title);
		TextView textViewDescription = (TextView) dialog
				.findViewById(R.id.description);
		textViewTitle.setText(placeit.getTitle());
		textViewDescription.setText(placeit.getDescription());
		alert.setView(dialog);
		
		
		/* Initialize submission button. */
		alert.setPositiveButton("Repost",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						scheduler.repostPlaceit(placeit);
						List<PlaceIt> newplaceits = new ArrayList<PlaceIt>();
						for (int i = 1; i < placeits.size(); i++) {
							newplaceits.add(placeits.get(i));
						}
						setUpNotification(newplaceits);
					}
				});
		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Discard",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// gotta rename

						Toast.makeText(MainActivity.this, initial.getActiveDate().toLocaleString(), Toast.LENGTH_SHORT).show();
						
						MainActivity.this.removeMarker(initial);
						controller.deactivatePlaceIt(initial);
						List<PlaceIt> newplaceits = new ArrayList<PlaceIt>();
						for (int i = 1; i < placeits.size(); i++) {
							newplaceits.add(placeits.get(i));
						}
						setUpNotification(newplaceits);
					}
				});

		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addMarker(PlaceIt pc) {
		String title = pc.getTitle();
		String descText = pc.getDescription();
		Marker added = googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(pc.getLatitude(), pc.getLongitude()))
				.title(title).snippet(descText));
		mMarkers.add(added);
	}

	@Override
	public void removeMarker(PlaceIt pc) {

		List<Marker> markersRemoved = new Vector<Marker>();
		for (Marker marker : mMarkers) {
			
			if (pc.equals(marker)) {
				markersRemoved.add(marker);
			}
		}
		
		for (int i = 0; i < markersRemoved.size(); i++) {
			mMarkers.remove(markersRemoved.get(i));
			markersRemoved.get(i).remove();

		}

	}

	@Override
	public Marker getMarker(int id) {
		for (Marker marker : mMarkers) {
			if (marker.getId() == Integer.toString(id)) {
				return marker;
			}
		}
		return null;

	}

	public void createNotifs(List<PlaceIt> cleanList) {
		for (int i = 0; i < cleanList.size(); i++) {
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					this)
					.setSmallIcon(R.drawable.cat)
					.setSound(
							RingtoneManager
									.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
					.setContentTitle(cleanList.get(i).getTitle())
					.setContentText(cleanList.get(i).getDescription());
			;

			// Sets an ID for the notification
			int mNotificationId = 001;
			// Gets an instance of the NotificationManager service
			NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.notify(mNotificationId, mBuilder.build());
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		Toast.makeText(
				MainActivity.this,
				"Location changed : " + arg0.getLatitude() + ","
						+ arg0.getLongitude(), Toast.LENGTH_SHORT).show();

		List<PlaceIt> cleanList = controller.checkCoordinates(arg0);

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

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(MainActivity.this, "Place-it connected!",
				Toast.LENGTH_SHORT).show();
		mLocationClient.requestLocationUpdates(mLocationRequest, mListener);

	}

	@Override
	public void notifyUser(List<PlaceIt> placeits, String ControllerType) {
		Toast.makeText(MainActivity.this, "Place-it notified by " + ControllerType + " with " + placeits.size() + " placeits",
				Toast.LENGTH_SHORT).show();
		if (ControllerType.equals("Controller")) {
			scheduler.checkActive(placeits);
		} else if (ControllerType.equals("Scheduler")) {
			setUpNotification(placeits);
		}

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
	
	public GoogleMap getViewMap(){
		return googleMap;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		if (arg3 != 0 && !(newList.get(arg2).equals("No Reminders")))
		{
			index = arg2;
			this.setUpDiscard();	
		}

	}

}