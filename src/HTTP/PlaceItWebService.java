package HTTP;

import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import Models.CategoryPlaceIt;
import Models.LocationPlaceIt;
import Models.PLSchedule;
import Models.PLSchedule2;
import Models.PlaceIt;
import Models.PlaceItFactory;
import PlaceItDB.iPLScheduleModelv2;
import PlaceItDB.iPlaceItModel;

public class PlaceItWebService extends WebService implements iPlaceItModel, iPLScheduleModelv2 {
	HttpContext context;

	public PlaceItWebService(HttpContext context) {
		this.context = context;
	}

	@Override
	public void getPlaceIt(String id, final PlaceItReceiver receiver) {
		new RequestTask(new RequestReceiver() {

			@Override
			public void receiveTask(String s) {
				try {
					receiver.receivePlaceIt(PlaceItFactory
							.CreatePlaceIt(new JSONObject(s)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}, context).execute(ALL_PLACEITS + id);
	}

	@Override
	public void getAllPlaceIts(final PlaceItListReceiver receiver) {
		Log.d("INITIAZING", "ALL VIEWS");
		new RequestTask(new RequestReceiver() {
			@Override
			public void receiveTask(String s) {
				Log.d("RECEIVED TASK", s);
				try {
					JSONArray arr = new JSONArray(s);
					List<PlaceIt> placeits = new Vector<PlaceIt>();
					for (int i = 0; i < arr.length(); i++) {
						Log.d("PLACEIT CHECK", arr.getJSONObject(i).toString());
						placeits.add(PlaceItFactory.CreatePlaceIt(arr
								.getJSONObject(i)));
					}
					receiver.receivePlaceIts(placeits);
				} catch (JSONException e) {
					Log.d("RECEIVED TASK", ":(");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}, context).execute(ALL_PLACEITS);
	}

	@Override
	public void updatePlaceIt(PlaceIt placeit, RequestReceiver receiver) {
		List<NameValuePair> values = new Vector<NameValuePair>();
		NameValuePair id = new BasicNameValuePair("id", placeit.getID());
		NameValuePair activedate = new BasicNameValuePair("active_date",
				Long.toString(placeit.getActiveDate().getTime()));
		values.add(id);
		values.add(activedate);
		Log.d("updating placeit", placeit.getID() + " to " + Long.toString(placeit.getActiveDate().getTime()) + " to " + UPDATE_PLACEIT);
		new RequestTask(receiver, context, values).execute(UPDATE_PLACEIT);
	}

	@Override
	public void deletePlaceIt(PlaceIt PlaceIt, RequestReceiver receiver) {
		List<NameValuePair> values = new Vector<NameValuePair>();
		NameValuePair id = new BasicNameValuePair("id", PlaceIt.getID());
		values.add(id);
		new RequestTask(receiver, context, values).execute(DELETE_PLACEIT);

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivatePlaceit(PlaceIt placeit, RequestReceiver receiver) {
		placeit.setActiveDate(0);
		this.updatePlaceIt(placeit, receiver);

	}

	/*
	 * title : String, description : String, latitude : Number, longitude :
	 * Number, active_date : Number, start_week : Number, day : Number, week :
	 * Number, category : String,
	 */
	@Override
	public void addPlaceIt(final PlaceIt placeit, final PlaceItReceiver receiver) {
		// TODO Auto-generated method stub
		List<NameValuePair> values = new Vector<NameValuePair>();
		values.add(new BasicNameValuePair("title", placeit.getTitle()));
		values.add(new BasicNameValuePair("description", placeit
				.getDescription()));
		values.add(new BasicNameValuePair("active_date", Long.toString(placeit
				.getActiveDate().getTime())));
		if (placeit instanceof LocationPlaceIt) {
			LocationPlaceIt lplaceit = (LocationPlaceIt) placeit;
			values.add(new BasicNameValuePair("latitude", Double
					.toString(lplaceit.getLatitude())));
			values.add(new BasicNameValuePair("longitude", Double
					.toString(lplaceit.getLongitude())));
			values.add(new BasicNameValuePair("category", null));
		} else if (placeit instanceof CategoryPlaceIt){
			CategoryPlaceIt cplaceit = (CategoryPlaceIt) placeit;
			values.add(new BasicNameValuePair("category", (cplaceit
					.getCategory())));
			values.add(new BasicNameValuePair("latititude", null));
			values.add(new BasicNameValuePair("longitude", null));
		}
		values.add(new BasicNameValuePair("start_week", ""));
		values.add(new BasicNameValuePair("day", ""));
		values.add(new BasicNameValuePair("week", ""));

		new RequestTask(new RequestReceiver() {

			@Override
			public void receiveTask(String s) {
					placeit.setID(s);
					receiver.receivePlaceIt(placeit);


			}

		}, context, values).execute(SINGLE_PLACEIT);
	}
	

	@Override
	public void getSchedule(PlaceIt placeit, PLScheduleReceiver receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSchedule(PLSchedule2 schedule,PlaceItReceiver receiver) {
		// TODO Auto-generated method stub
		
	}

}
