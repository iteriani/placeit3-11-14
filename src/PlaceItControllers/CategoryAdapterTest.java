package PlaceItControllers;

import junit.framework.TestCase;

public class CategoryAdapterTest extends TestCase {

	CategoryAdapter adapter = new CategoryAdapter();

	public void testAdapter() {
		System.out.println("hello?");
		String[] array = {"hi", "hello", "hey"};
		String result = adapter.convertArrayToString(array);
		System.out.println(result);
	}
}

