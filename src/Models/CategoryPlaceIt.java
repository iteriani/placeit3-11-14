/**
 * This is a category placeit class. 
 * It inherits the abstract class PlaceIt and has one more field to store its categories.
 */

package Models;

public class CategoryPlaceIt extends PlaceIt{
	String category;
	String place_name;
	String place_addy;
	
	public CategoryPlaceIt(String title, String description) {
		super(title, description);
	}
	
	public CategoryPlaceIt(String title, String description, String category) {
		super(title, description);
		this.category = category;
	}

	
	public String getCategory(){
		return this.category;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	
	public String getPlaceName(){
		return this.place_name;
	}
	
	public void setPlaceName(String name) {
		this.place_name = name;
	}

	public String getPlaceAddy(){
		return this.place_addy;
	}
	
	public void setPlaceAddy(String addy) {
		this.place_addy = addy;
	}

	
}
