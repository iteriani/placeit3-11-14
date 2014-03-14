package HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Models.Place;
import android.util.Log;

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
	   final String categories, final PlaceReceiver receiver) throws UnsupportedEncodingException {

		 String urlString = makeUrl(latitude, longitude, categories);
		 Log.d("URL STR", urlString);
		 new RequestTask(new RequestReceiver() {
			 public void receiveTask(String json) {
				  try {
					   JSONObject object = new JSONObject(json);
					   JSONArray array = object.getJSONArray("results");
					   
					   List<Place> arrayList = new ArrayList<Place>();
					   if(array.length() > 0){
					   for (int i = 0; i < array.length(); i++) {
						    try {
						     Place place = Place
						       .jsonToPlace((JSONObject) array.get(i));
						     //Log.v("Places Services ", "" + place);
						     arrayList.add(place);
						    } catch (Exception e) {
						    }
					   }
					  }
					   receiver.receivePlaces(arrayList); 
				  } catch (JSONException ex) {
					   ex.printStackTrace();
					   Log.d("aaa", "UNABLE TO PARSE...");
					   receiver.receivePlaces(new ArrayList<Place>());
				  }
			 }
		 }).execute(urlString);

	 }

	 // https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey
	 private String makeUrl(double latitude, double longitude, String categorys) throws UnsupportedEncodingException {
		  StringBuilder urlString = new StringBuilder(
		    "https://maps.googleapis.com/maps/api/place/search/json?");
	
		  if (categorys.equals("")) {
			   urlString.append("location=");
			   urlString.append(URLEncoder.encode(String.valueOf(latitude), "UTF-8"));
			   urlString.append(",");
			   urlString.append(URLEncoder.encode(String.valueOf(longitude), "UTF-8"));
			   urlString.append("&radius="+URLEncoder.encode("900", "UTF-8"));
			   // urlString.append("&types="+place);
			   urlString.append("&sensor="+URLEncoder.encode("false", "UTF-8") + URLEncoder.encode(API_KEY, "UTF-8"));
		  } else {
			   urlString.append("location=");
			   urlString.append(URLEncoder.encode(Double.toString(latitude), "UTF-8"));
			   urlString.append(",");
			   urlString.append(URLEncoder.encode(Double.toString(longitude), "UTF-8"));
			   urlString.append("&radius=900");
			   urlString.append("&types=" + URLEncoder.encode(categorys, "UTF-8"));
			   urlString.append("&sensor=false&key=" + URLEncoder.encode(API_KEY, "UTF-8"));
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

