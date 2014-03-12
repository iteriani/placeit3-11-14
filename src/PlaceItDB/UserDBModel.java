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
