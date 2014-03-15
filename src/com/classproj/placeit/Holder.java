/**
 * This class stores a static variable 
 * to temporarily store things between the two activity
 */

package com.classproj.placeit;

import org.apache.http.protocol.HttpContext;

import HTTP.WebUserService;

public class Holder {
	static HttpContext myContext;
	static WebUserService user; 
	
	public void setContext(HttpContext temp)
	{
		myContext = temp;
	}
	
	public HttpContext getContext()
	{
		return myContext;
	}
	
	public void setUser(WebUserService temp){
		user = temp;
	}
	
	public WebUserService getUser(){
		return user; 
	}
}
