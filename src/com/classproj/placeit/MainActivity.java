package com.classproj.placeit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.http.protocol.HttpContext;
import HTTP.PlaceItListReceiver;
import HTTP.PlaceItReceiver;
import HTTP.PlaceItWebService;
import HTTP.RequestReceiver;
import HTTP.WebUserService;
import Models.CategoryPlaceIt;
import Models.LocationPlaceIt;
import Models.PlaceIt;
import PlaceItControllers.PlaceItController;
import PlaceItControllers.PlaceItScheduler;
import PlaceItDB.PLScheduleHandler;
import PlaceItDB.iPLScheduleModel;
import PlaceItDB.iPlaceItModel;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Spinner;
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
		ListView.OnItemClickListener {
	GeocoderTask findPlace;
	/* record object is used in database handler to bind to activity */
	FragmentActivity record = this;
	LocationManager locationManager;
	Holder myHold = new Holder();
	int deleteId = 0;
	/* googleMap is our singleton map to add ui elements to. */
	GoogleMap googleMap;
	int reportIndex = 0;
	/* Current location of user */
	Location location;
	boolean discard = false;
	boolean delete = false;
	Button addButton;
	int index = 0;
	int counter = 1;
	Spinner categories;
	Button spinnerSubmit;
	EditText categoryTitle;
	EditText categoryDesc;
	String catTitleStr = "";
	String catDescStr = "";
	String[] selectedThree = new String[3];
	View dialog;
	Button logoutButton;
	/* */
	LocationRequest mLocationRequest;
	LocationClient mLocationClient;
	com.google.android.gms.location.LocationListener mListener = this;
	boolean deleteCalled = false;
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
	String[] categoryList;

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
		HttpContext context = myHold.getContext();
		iPlaceItModel db = new PlaceItWebService(context);
		controller = new PlaceItController(db, this);
		// Toast.makeText(MainActivity.this, "placeits loading2",
		// Toast.LENGTH_LONG).show();

		scheduler = new PlaceItScheduler(db, this);
		// controller = new PlaceItController(db, this);*/
		controller.initializeView(new RequestReceiver() {
			public void receiveTask(String s) {
				setUpSideBar();
				setUpFindButton();
			}
		});
		categoryList = getResources().getStringArray(R.array.categories);
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		addButton = (Button) findViewById(R.id.add);
		logoutButton = (Button) findViewById(R.id.logout);
		categoryTitle = (EditText) findViewById(R.id.catTitle);
		categoryDesc = (EditText) findViewById(R.id.catdescription);
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupCategoryDialog();

			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener(){
			public void onClick(final View v) {
				
				new WebUserService().logout(new RequestReceiver() {
					public void receiveTask(String s){
								Intent intent = new Intent(v.getContext(), Login.class);
								Holder.myContext=null;
								startActivity(intent);
								finish();	
						}
					}
				);
			}
		});
		
		

		List<PlaceIt> checkList = null;
		Location myLocationNow = locationManager
				.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

		if (myLocationNow != null) {
			checkList = controller.checkCoordinates(myLocationNow);
		}

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				PlaceItSettings.NOTIFICATION_INTERVAL, 0, this);

	}

	public void setUpSideBar() {
		swipebarElements = new String[] { "Active Reminders" };
		DrawerLayout myDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		newList = new ArrayList<String>();
		newList.add("Active Reminders");
		nonActive = new ArrayList<String>();
		this.nonActive.add("Non Active Remidners");
		List<PlaceIt> activeOne = new Vector<PlaceIt>();
		List<PlaceIt> nonActiveOne = new Vector<PlaceIt>();
		activeOne = controller.getActiveList();
		nonActiveOne = controller.getNonActivePlaceIts();

		if (activeOne.size() == 0) {

		} else {
			for (PlaceIt now : activeOne) {
				newList.add(now.getTitle());
			}
		}

		if (nonActiveOne.size() == 0) {

		} else {
			for (PlaceIt now : nonActiveOne) {
				nonActive.add(now.getTitle());
			}
		}

		viewLists = (ListView) findViewById(R.id.left_drawer);
		viewLists.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_left, newList));
		viewLists.setOnItemClickListener(this);
		rightList = (ListView) findViewById(R.id.right_drawer);
		rightList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_left, nonActive));
		rightList.setOnItemClickListener(this);
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

	public void setupCategoryDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Create Place-It");
		LayoutInflater inflater = getLayoutInflater();
		dialog = inflater.inflate(R.layout.placeit_category, null);
		final boolean[] checkItems = new boolean[100];
		final EditText catTitle = (EditText)dialog.findViewById(R.id.catTitle);
		final EditText catDesc =(EditText)dialog.findViewById(R.id.catdescription);
		for (int i = 0; i < 100; i++) {
			checkItems[i] = false;
		}
		int tempNext = 0;
		int checkedItem1 = 0;
		int checkCOunt = 0;
		final boolean[] checked = null;
		selectedThree = new String[3];
		// Create single choice list
		alert.setMultiChoiceItems(R.array.categories, checkItems,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						int temp = 0;
						for (int i = 0; i < 100; i++) {

							if (checkItems[i] == true) {
								temp++;
							}
						}
					
						if (temp > 3) {
							checkItems[which] = false;
							((AlertDialog) dialog).getListView()
									.setItemChecked(which, false);
							Toast.makeText(MainActivity.this,
									"Please choose only three",
									Toast.LENGTH_LONG).show();
						}

					}

				});

		alert.setPositiveButton("Submit",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						int temp = 0;
						for (int i = 0; i < 100; i++) {

							if (checkItems[i] == true) {
								temp++;
							}
						}
						
						if ((temp == 0) && (catTitle.getText().toString()).compareTo("") == 0 
								&& (catDesc.getText().toString().compareTo(""))==0)
						{
							//Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_LONG).show();
							setupCategoryDialog();

						}
						int count = 0;
						for (int i = 0; i < checkItems.length; i++) {
							if (checkItems[i] == true) {
								selectedThree[count] = categoryList[i];
								count++;
							}
						}

						if (catTitle== null && catDesc == null)
						{
							
							Toast.makeText(MainActivity.this, "Please enter both a title and a description", Toast.LENGTH_LONG).show();
							setupCategoryDialog();
						}					
					
						else
						{
							
							catTitleStr = catTitle.getText().toString();
							catDescStr = catDesc.getText().toString();
						
							controller.AddPlaceIt(catTitleStr, catDescStr, selectedThree, new PlaceItReceiver() {

							@Override
							public void receivePlaceIt(PlaceIt placeit) {
								scheduler
										.scheduleNextActivation(placeit);
								setUpSideBar();
								/* Notification of added place-it */
								Toast.makeText(MainActivity.this,
										"Category !!!! Place-it added!",
										Toast.LENGTH_SHORT).show();

							}
							
						});
						
						}

					}
					
					

				});
		

		alert.setView(dialog);
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

				// Check that user has entered a title or description
				if (descText.matches("") && titleText.matches("")) {
					Toast.makeText(MainActivity.this,
							"Please enter a title or descripion.",
							Toast.LENGTH_SHORT).show();
					setUpDialog(position);
				} else {
					setupTimeDialog(titleText, descText, position);
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

						int selectedDay = ((AlertDialog) dialog).getListView()
								.getCheckedItemPosition() - 1;
						// subtract 1 from interval because 1st option is
						// "no interval"
						String weekString = numweeks.getText().toString();
						int week = -1;

						Toast.makeText(MainActivity.this,
								"the checkeditemposition is " + selectedDay,
								Toast.LENGTH_SHORT).show();
						if (selectedDay > 0 && weekString.matches("")) {
							Toast.makeText(MainActivity.this,
									"Please enter a week interval.",
									Toast.LENGTH_SHORT).show();
							setupTimeDialog(title, description, location);
						} else if (weekString.matches("")) {
							week = 1;
						} else {
							week = Integer.valueOf(weekString);
						}

						controller.AddPlaceIt(title, description, location,
								new PlaceItReceiver() {

									@Override
									public void receivePlaceIt(PlaceIt placeit) {
										scheduler
												.scheduleNextActivation(placeit);
										setUpSideBar();
										/* Notification of added place-it */
										Toast.makeText(MainActivity.this,
												"Place-it added!",
												Toast.LENGTH_SHORT).show();

									}

								});

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

	public void setUpDiscard() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Discard or Delete");
		LayoutInflater inflater = getLayoutInflater();

		alert.setPositiveButton("Dismiss",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						if (MainActivity.this.nonActive.size() != 1) {

							if (index == 1) {
								index = 0;
							} else {
								index = (newList.size() + nonActive.size()
										- index - 2 - counter);
								counter += 2;
							}

						} else {

							index = index - 1;
						}

						Log.d("Test = PlaceIt", "" + index + "");
						String temp = controller.movePlaceIts(index);
						// Toast.makeText(MainActivity.this, "" + temp + "",
						// Toast.LENGTH_SHORT).show();
						setUpSideBar();
					}
				});

		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						int tempInd = index;
						if (nonActive.size() == 1) {
							index = index - 1;
						} else {
							index = (newList.size() - nonActive.size()) + 1;
						}

						// Toast.makeText(MainActivity.this, "" +
						// nonActive.size()+ " = " + newList.size()+" = " +
						// index + "", Toast.LENGTH_SHORT).show();
						controller.deletePlaceIts(index,
								MainActivity.this.getApplicationContext());
						deleteCalled = true;
						deleteId = tempInd;
						setUpSideBar();
					}
				});

		alert.show();
	}

	public void setupRepost() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Repost or Delete");
		LayoutInflater inflater = getLayoutInflater();

		alert.setPositiveButton("Repost",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						PlaceIt correct = scheduler
								.scheduleNextActivation(controller
										.repostIt(reportIndex - 1));

						setUpSideBar();
					}
				});

		/* Cancel button which does nothing when clicked and exits the dialog. */
		alert.setNegativeButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						int tempInd = index;
						if (nonActive.size() == 1) {
							index = index - 1;
						} else {
							index = (newList.size() - nonActive.size()) + 1;
						}

						// Toast.makeText(MainActivity.this, "" +
						// nonActive.size()+ " = " + newList.size()+" = " +
						// index + "", Toast.LENGTH_SHORT).show();
						controller.deletePlaceIts(index,
								MainActivity.this.getApplicationContext());
						deleteCalled = true;
						deleteId = tempInd;
						setUpSideBar();
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
		placeits.set(0, controller.deactivatePlaceIt(placeit));

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("You got a Place-It!");
		LayoutInflater inflater = getLayoutInflater();
		final View dialog = inflater.inflate(R.layout.placeit_notification,
				null);
		TextView textViewTitle = (TextView) dialog.findViewById(R.id.title);
		TextView textViewDescription = (TextView) dialog
				.findViewById(R.id.description);
		TextView locationDescription = (TextView) dialog
				.findViewById(R.id.location);
		textViewTitle.setText(placeit.getTitle());
		textViewDescription.setText(placeit.getDescription());
		if (placeit instanceof CategoryPlaceIt) {
			CategoryPlaceIt cplaceit = (CategoryPlaceIt) placeit;
			locationDescription.setText(cplaceit.getPlaceName() + "\r\n"
					+ cplaceit.getPlaceAddy());
		}
		alert.setView(dialog);

		/* Initialize submission button. */
		alert.setPositiveButton("Repost",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						scheduler.scheduleNextActivation(placeit);
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
						removeMarker(placeit);
						List<PlaceIt> newplaceits = new ArrayList<PlaceIt>();
						for (int i = 1; i < placeits.size(); i++) {
							newplaceits.add(placeits.get(i));
						}
						setUpNotification(newplaceits);
						MainActivity.this.setUpSideBar();
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

	public void addMarker(PlaceIt _pc) {

		LocationPlaceIt pc = (LocationPlaceIt) _pc;
		Toast.makeText(
				MainActivity.this,
				pc.getLatitude() + "-" + pc.getLongitude() + pc.getTitle()
						+ "-" + pc.getDescription(), Toast.LENGTH_SHORT).show();
		Log.d("aa",
				"adding marker " + pc.getLatitude() + "-" + pc.getLongitude()
						+ pc.getTitle() + "-" + pc.getDescription());
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

			if (pc.getTitle() == marker.getTitle() && pc.getDescription() == marker.getSnippet()) {
				markersRemoved.add(marker);
			}
		}

		for (int i = 0; i < markersRemoved.size(); i++) {
			markersRemoved.get(i).remove();
			mMarkers.remove(markersRemoved.get(i));
			

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
	public void onLocationChanged(final Location arg0) {
		clearMap();
		controller.initializeView(new RequestReceiver() {
			public void receiveTask(String s) {
				setUpSideBar();
				setUpFindButton();
				controller.checkCoordinates(arg0);
			}
		});
	}

	@Override
	public void clearMap() {
		this.googleMap.clear();
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
		Toast.makeText(
				MainActivity.this,
				"Place-it notified by " + ControllerType + " with "
						+ placeits.size() + " placeits", Toast.LENGTH_SHORT)
				.show();
		if (ControllerType.equals("Controller")) {
			setUpNotification(placeits);
		}

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	public GoogleMap getViewMap() {
		return googleMap;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (viewLists == arg0) {
			if (arg3 != 0 && !(newList.get(arg2).equals("No Reminders"))) {
				index = arg2;
				this.setUpDiscard();
			}
		} else {
			// Toast.makeText(this, "Came here", Toast.LENGTH_LONG).show();
			this.reportIndex = arg2;
			this.setupRepost();
		}

	}

}
