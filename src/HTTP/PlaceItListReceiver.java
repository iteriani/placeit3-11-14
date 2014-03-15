/**
 * This interface receive a list of placeit
 * 
 */

package HTTP;

import java.util.List;
import Models.PlaceIt;

public interface PlaceItListReceiver {
	public void receivePlaceIts(List<PlaceIt> placeit);
}
