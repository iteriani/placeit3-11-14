package Models;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Place {
	
	public Place() {}
	
	    private String name;
	    private String vicinity;
	    private String categories;
	    private Double latitude;
	    private Double longitude;
	    
	    public String getName() {
	    	return this.name; 
	    }
	    
	 
	    public Double getLat(){
	    	return this.latitude; 
	    }
	    
	    public Double getLong() {
	    	return this.longitude; 
	    }
	    
	    public String getCategories() {
	    	return this.categories; 
	    }
	    
	    public String getVic() {
	    	return this.vicinity;
	    }

	    public void setLat(Double newLat){
	    	this.latitude = newLat; 
	    }
	    
	    public void setLong(Double newLong){
	    	this.longitude = newLong; 
	    }
	    
	    public void setName(String newName){
	    	this.name = newName;
	    }
	    
	    public void setVic(String newVic) {
	    	this.vicinity = newVic;
	    }
	    
	    
	   static Place jsonToPlace(JSONObject thePlace){
	    	try {
	            Place result = new Place();
	            JSONObject geometry = (JSONObject) thePlace.get("geometry");
	            JSONObject location = (JSONObject) geometry.get("location");
	            result.setLat((Double) location.get("lat"));
	            result.setLong((Double) location.get("lng"));
	            result.setName(thePlace.getString("name"));
	            result.setVic(thePlace.getString("vicinity"));
	            return result;
	    	} catch (JSONException ex) {
	            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    	return null; 
	    }
	    
	    
	    public String toString() {
	        return "Place{" + "name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
	    }
	    
	    
	    
}
