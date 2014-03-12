package com.classproj.placeit;

import org.apache.http.protocol.HttpContext;

public class Holder {
	static HttpContext myContext;
	
	public void setContext(HttpContext temp)
	{
		myContext = temp;
	}
	
	public HttpContext getContext()
	{
		return myContext;
	}
}
