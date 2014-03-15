/**
 * This is the webservice for user class
 * it implements userdbmodel
 * 
 * it gets signup/login/logout status of a user on a webservice level
 */

package HTTP;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import PlaceItDB.UserDBModel;

public class WebUserService extends WebService implements UserDBModel{
	private HttpContext context;

	public HttpContext getContext(){
		return context;
	}
	
	public WebUserService(){
		CookieStore cookieStore = new BasicCookieStore();
		context = new BasicHttpContext();
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}
	@Override 
	public HttpContext signup(String username, String password, RequestReceiver receiver) {
		List<NameValuePair> values = new Vector<NameValuePair>();
		NameValuePair user = new BasicNameValuePair("pid", username);
		NameValuePair pass = new BasicNameValuePair("password", password);
		values.add(user);
		values.add(pass);
		new RequestTask(receiver,context, values).execute(SIGNUP_URL);
		return context;
	}

	@Override
	public HttpContext login(String username, String password, RequestReceiver receiver) {
		
		List<NameValuePair> login = new Vector<NameValuePair>();
		login.add(new BasicNameValuePair("pid", username));
		login.add(new BasicNameValuePair("password", password));
		new RequestTask(receiver,context, login).execute(LOGIN_URL);
		return context;
	}

	@Override
	public void logout(RequestReceiver receiver) {
		this.context = null;
		new RequestTask(receiver,context, new Vector<NameValuePair>()).execute(LOGOUT_URL);

	}

	@Override
	public boolean isUserLoggedIn() {
		return true;
		
	}

}
