package Models;

public class CategoryPlaceIt extends PlaceIt{
	String category;
	
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

}
