/**
 * 
 */
package PlaceItDB;

import java.util.List;
import java.util.ListIterator;

import Models.PlaceIt;

/**
 * @author SKY
 *
 */
public class mockPlaceItHandler implements iPlaceItModel{

	public List<PlaceIt> mocklist;
	
	//constructor
	
	public mockPlaceItHandler (List<PlaceIt> userInput){
		mocklist = userInput;
	}
	
	@Override
	public void addPlaceIt(PlaceIt PlaceIt) {
		// TODO Auto-generated method stub
		mocklist.add(PlaceIt);
	}

	@Override
	public PlaceIt getPlaceIt(int id) {
		
		PlaceIt result = null;
		// TODO Auto-generated method stub
		for (ListIterator<PlaceIt> iter = mocklist.listIterator(); iter.hasNext(); ) {
		    PlaceIt element = iter.next();
		    if (element.getID() == id){
		    	result = element;
		    }
		}
		return result;
	}

	@Override
	public List<PlaceIt> getAllPlaceIts() {
		// TODO Auto-generated method stub
		return mocklist;
	}

	@Override
	public int getPlaceItsCount() {
		// TODO Auto-generated method stub
		return mocklist.size();
	}

	@Override
	public int updatePlaceIt(PlaceIt placeit) {
		// TODO Auto-generated method stub
		int num = 0;
		int id = placeit.getID();
		String title = placeit.getTitle();
		String description = placeit.getDescription();
		Double latitude = placeit.getLatitude();
		Double longitutde = placeit.getLongitude();
		java.util.Date activeDate= placeit.getActiveDate();
		
		// TODO Auto-generated method stub
		for (PlaceIt p:mocklist ) {
		    if (p.getID() == id){
		    	boolean e = p.getTitle().equals(title) 
		    			&& p.getDescription().equals(description) 
		    			&& ((Double)p.getLatitude()).equals(latitude)
		    			&& ((Double)p.getLongitude()).equals(longitutde)
		    			&& p.getActiveDate().equals(activeDate);
		    	if (!e){
		    		int i = mocklist.indexOf(p);
		    		mocklist.set(i,placeit);
		    	}
		    	num++;
		    }
		}
		return num;
	}

	@Override
	public void deletePlaceIt(PlaceIt PlaceIt) {
		// TODO Auto-generated method stub
		int id = PlaceIt.getID();
		String title = PlaceIt.getTitle();
		String description = PlaceIt.getDescription();
		Double latitude = PlaceIt.getLatitude();
		Double longitutde = PlaceIt.getLongitude();
		java.util.Date activeDate= PlaceIt.getActiveDate();
		
		// TODO Auto-generated method stub
		for (PlaceIt p:mocklist ) {
		    if (p.getID() == id){
		    	boolean e = p.getTitle().equals(title) 
		    			&& p.getDescription().equals(description) 
		    			&& ((Double)p.getLatitude()).equals(latitude)
		    			&& ((Double)p.getLongitude()).equals(longitutde)
		    			&& p.getActiveDate().equals(activeDate);
		    	if (e){
		    		mocklist.remove(p);
		    	}
		    }
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		mocklist = null;
	}
/*
	@Override
	public int repostPlaceit(PlaceIt placeit) {
		// TODO Auto-generated method stub
		int num = 0;
		int id = placeit.getID();
		String title = placeit.getTitle();
		String description = placeit.getDescription();
		Double latitude = placeit.getLatitude();
		Double longitutde = placeit.getLongitude();
		java.util.Date activeDate= placeit.getActiveDate();
		
		// TODO Auto-generated method stub
		for (PlaceIt p:mocklist ) {
		    if (p.getID() == id){
		    	boolean e = p.getTitle().equals(title) 
		    			&& p.getDescription().equals(description) 
		    			&& ((Double)p.getLatitude()).equals(latitude)
		    			&& ((Double)p.getLongitude()).equals(longitutde)
		    			&& p.getActiveDate().equals(activeDate);
		    	if (e){
		    		int k = mocklist.indexOf(p);
		    		p.setActiveDate(p.getActiveDate().getTime()+45);
		    		mocklist.set(k, p);
		    		num++;
		    	}
		    }
		}
		return num;
	}
*/
	@Override
	public void deactivatePlaceit(PlaceIt placeit) {
		// TODO Auto-generated method stub
		placeit.setActiveDate(0); /* maybe...*/
		this.updatePlaceIt(placeit);
	}
	

}
