package PlaceItDB;

import java.util.List;

import Models.PlaceIt;

public interface iPlaceItModel {

	// Adding new PlaceIt
	public void addPlaceIt(PlaceIt PlaceIt);

	// Getting single PlaceIt
	public PlaceIt getPlaceIt(int id);

	// Getting All PlaceIts
	public List<PlaceIt> getAllPlaceIts();

	// Getting PlaceIts Count
	public int getPlaceItsCount();

	// Updating single PlaceIt
	public int updatePlaceIt(PlaceIt placeit);

	// Deleting single PlaceIt
	public void deletePlaceIt(PlaceIt PlaceIt);
	
	// deleteAll 
	public void deleteAll();
	
	public int repostPlaceit(PlaceIt placeit);
	
	public void deactivatePlaceit(PlaceIt placeit);
}
