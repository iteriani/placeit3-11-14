/**
 * This is the interface for receiving a place from the server 
 * for a category placeit
 */

package HTTP;

import java.util.List;

import Models.Place;

public interface PlaceReceiver {

	public void receivePlaces(List<Place> list);
}
