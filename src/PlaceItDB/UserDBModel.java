/**
 * 
 * This interface models the user database
 * it can store a newly signed-up user
 * it can check the login information for an existing user
 * it checks if a given user is logged in or not
 * it can help a logged in user log out
 *
 */

package PlaceItDB;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.protocol.HttpContext;

import HTTP.RequestReceiver;

public interface UserDBModel {

	public HttpContext signup(String username, String password, RequestReceiver receiver);
	public HttpContext login(String username, String password, RequestReceiver receiver); 
	public boolean isUserLoggedIn();
	public void logout(RequestReceiver receiver);
}
