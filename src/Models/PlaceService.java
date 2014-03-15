/**
 * 
 * Implement Google Places API
 * Provide places information to support category placeit
 *
 */

package Models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import HTTP.PlaceReceiver;
import HTTP.RequestReceiver;
import HTTP.RequestTask;

public class PlaceService {

	private String API_KEY = "AIzaSyCaYppB5Eb4aHzc6TlVdM7J1wZaAz7iMlA";

	// constructor
	 public PlaceService(){}
	 
	 public PlaceService(String apikey) {
	  this.API_KEY = apikey;
	 }

	 public void setApiKey(String apikey) {
	  this.API_KEY = apikey;
	 }

	 /*
	  * This method finds all places that are near the user and fit the specifications.
	  * Parameters: latitude and longitude of the user, and categories
	  * Returns an ArrayList containing Place objects that fit the requirements.
	  */
	 
	 /* 			PlaceItReceiver rec
		 * new RequestTask(new RequestReceiver(){
		 * 	publi void receiveTask(String s){
		 * 	try{
		 * 		JSONArray arr = new JSONArray(s);
		 * 			placeit.addLocations(arr);
		 * 		 	receiver.receivePlaceIt(placeit);
		 * 		}catch(Exception e){
		 * 			e.printStackTrace();
		 * 		}
		 * 	}
		 * }).execute(URL);
		 * */
	 
	 public void findPlaces(final double latitude, final double longitude,
	   final String categories, final PlaceReceiver receiver) {
		 
		 new RequestTask(new RequestReceiver() {
			 public void receiveTask(String s) {
				 String urlString = makeUrl(latitude, longitude, categories);
					
				  try {
					   String json = getJSON(urlString);
				
					   System.out.println(json);
					   JSONObject object = new JSONObject(json);
					   JSONArray array = object.getJSONArray("results");
				
					   List<Place> arrayList = new ArrayList<Place>();
					   for (int i = 0; i < array.length(); i++) {
						    try {
						     Place place = Place
						       .jsonToPlace((JSONObject) array.get(i));
						     //Log.v("Places Services ", "" + place);
						     arrayList.add(place);
						    } catch (Exception e) {
						    }
					   }
					   receiver.receivePlaces(arrayList); 
				  } catch (JSONException ex) {
					   Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE,
					     null, ex);
					   receiver.receivePlaces(new ArrayList<Place>());
				  }
			 }
		 });

	 }

	 // https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey
	 private String makeUrl(double latitude, double longitude, String categorys) {
		  StringBuilder urlString = new StringBuilder(
		    "https://maps.googleapis.com/maps/api/place/search/json?");
	
		  if (categorys.equals("")) {
			   urlString.append("&location=");
			   urlString.append(Double.toString(latitude));
			   urlString.append(",");
			   urlString.append(Double.toString(longitude));
			   urlString.append("&radius=900");
			   // urlString.append("&types="+place);
			   urlString.append("&sensor=false&key=" + API_KEY);
		  } else {
			   urlString.append("&location=");
			   urlString.append(Double.toString(latitude));
			   urlString.append(",");
			   urlString.append(Double.toString(longitude));
			   urlString.append("&radius=900");
			   urlString.append("&types=" + categorys);
			   urlString.append("&sensor=false&key=" + API_KEY);
		  }
		  return urlString.toString();
		 }
	
		 protected String getJSON(String url) {
			 return getUrlContents(url);
	 }

	 private String getUrlContents(String theUrl) {
	  StringBuilder content = new StringBuilder();
	  try {
	   URL url = new URL(theUrl);
	   URLConnection urlConnection = url.openConnection();
	   BufferedReader bufferedReader = new BufferedReader(
	     new InputStreamReader(urlConnection.getInputStream()), 8);
	   String line;
	   while ((line = bufferedReader.readLine()) != null) {
	    content.append(line + "\n");
	   }
	   bufferedReader.close();
	  }catch (Exception e) {
	   e.printStackTrace();
	  }
	  return content.toString();
	 }
	 
	}

