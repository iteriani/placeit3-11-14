package com.classproj.placeit;

import android.support.v4.app.Fragment;
import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
public class MainActivity extends FragmentActivity {
	 	GoogleMap googleMap;
	    MarkerOptions markerOptions;
	    LatLng latLng;
	    private LocationManager locationManager;
	    Location location;
	    String[] swipebarElements;
	    private DrawerLayout myDrawLayout;
	    private ListView viewLists;
	    MarkerOptions marker;
	    String provide;
	    NotificationCompat.Builder n;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        swipebarElements = new String[]{"Ankoor", "Hitler", "Stalin"};
	        myDrawLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
	        viewLists = (ListView)findViewById(R.id.left_drawer);
	        
	        SupportMapFragment supportMapFragment = (SupportMapFragment)
	        getSupportFragmentManager().findFragmentById(R.id.map);
	       // viewLists.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, swipebarElements));
	        // Getting a reference to the map
	        googleMap = supportMapFragment.getMap();
	        googleMap.setMyLocationEnabled(true);
	       
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
	 
	                if(location!=null && !location.equals("")){
	                    new GeocoderTask().execute(location);
	                }
	            }
	        };
	        Intent intent = new Intent(this, MainActivity.class);
            final PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationManager nm = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
	        // Setting button click event listener for the find button
	        btn_find.setOnClickListener(findClickListener);
	        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        Criteria criteria = new Criteria();
	        provide = locationManager.getBestProvider(criteria, false);
	        n = new NotificationCompat.Builder(this);
        	n.setContentTitle("Reminder")
            .setContentText("TURN DOWN FOR WHATT!!!")
            .setSmallIcon(R.drawable.catme)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .build();
       
       	NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
       	manager.notify(100, n.build());  
	        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
	            @Override
	            public void onLocationChanged(Location location) {
	            	Location currLoc = locationManager.getLastKnownLocation(provide);
	    	        if (currLoc != null) {
	    	        	if (marker != null)
	    	        	{
	    	        		Location markerLocation = new Location ("Marker Location");
	    	        		markerLocation.setLatitude(marker.getPosition().latitude);
	    	        		markerLocation.setLongitude(marker.getPosition().longitude);
	    	        		float dist = currLoc.distanceTo(markerLocation);
	    	        		dist = (float) (dist * 0.62137);
	    	                if (dist <= 0.5)
	    	                {
	    	               
	    	                	n.setContentTitle("Notification Reminder")
	    	                     .setContentText("You are near the .... [reminder]")
	    	                     .setSmallIcon(R.drawable.ic_launcher)
	    	                     .setContentIntent(pIntent)
	    	                     .setAutoCancel(true)
	    	                     .build();
	    	                
	    	                	NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
	    	                	manager.notify(100, n.build());  
	    	                }
	    	        	}
	    	        }
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
	            public void onStatusChanged(String provider, int status,
	                    Bundle extras) {
	                // TODO Auto-generated method stub
	            }           
	        });
	        
	          //  onLocationChanged(location);

	        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	            @Override
	            public void onMapClick(LatLng point) {
	                // TODO Auto-generated method stub
	                marker = new MarkerOptions().position(
	                        new LatLng(point.latitude, point.longitude)).title("Reminder");
	            	marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
	            	googleMap.addMarker(marker);
	            }
	        });
	 
	    }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.
	        		main, menu);
	        return true;
	    }
	 
	    // An AsyncTask class for accessing the GeoCoding Web Service
	    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{
	 
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
	 
	        @Override
	        protected void onPostExecute(List<Address> addresses) {
	 
	            if(addresses==null || addresses.size()==0){
	                Toast.makeText(getBaseContext(), "Not found. Lost forever", Toast.LENGTH_SHORT).show();
	            }
	 
	            // Clears all the existing markers on the map
	            googleMap.clear();
	 
	            // Adding Markers on Google Map for each matching address
	            for(int i=0;i<addresses.size();i++){
	 
	                Address address = (Address) addresses.get(i);
	 
	                // Creating an instance of GeoPoint, to display in Google Map
	                latLng = new LatLng(address.getLatitude(), address.getLongitude());
	 
	                String addressText = String.format("%s, %s",
	                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
	                address.getCountryName());
	 
	                markerOptions = new MarkerOptions();
	                markerOptions.position(latLng);
	                markerOptions.title(addressText);
	 
	                googleMap.addMarker(markerOptions);
	 
	                // Locate the first location
	                if(i==0)
	                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
	            }
	        }
	    }

}
