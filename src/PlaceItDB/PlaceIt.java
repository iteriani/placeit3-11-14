package PlaceItDB;

public class PlaceIt {
    
    //private variables
    int _id;
    String _title;
    String _description;
    double _latitude;
    double _longitude;
    
     
    // Empty constructor
    public PlaceIt(){
         
    }
    // constructor
    public PlaceIt(String title, String description, double latitude, double longitude){
        this._title = title;
        this._description = description;
        this._latitude = latitude;
        this._longitude = longitude;
    }
     
    // constructor
    public PlaceIt(String title, String description){
        this._title = title;
        this._description = description;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting title
    public String getTitle(){
        return this._title;
    }
     
    // setting title
    public void setTitle(String title){
        this._title = title;
    }
     
    // getting description
    public String getDescription(){
        return this._description;
    }
     
    // setting description
    public void setDescription(String description){
        this._description = description;
    }
    
    // getting longitude
    public double getLongitude(){
        return this._longitude;
    }
     
    // setting description
    public void setLongitude(double longitude){
        this._longitude = longitude;
    }
    
    // getting longitude
    public double getLatitude(){
        return this._latitude;
    }
     
    // setting description
    public void setLatitude(double latitude){
        this._latitude = latitude;
    }
}