/**
 * This class has the sole purpose of ADAPTING the categories given by the user in MainActivity as a string array into a single string
 * with the categories separated by "|" character.
 */

package PlaceItControllers;

public class CategoryAdapter {

	// constructor
	public CategoryAdapter() {
		
	}
	
	public String convertArrayToString(String[] list) {
		if (list == null) return null;
		int length = list.length;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0 && list[i] != null) {
				builder.append(list[i].toLowerCase().replace(" ", "_"));
			}
			else if (list[i] != null) {
				builder.append("|");
				builder.append(list[i].toLowerCase().replace(" ", "_"));
			}
		}
		return builder.toString();
	}
}

