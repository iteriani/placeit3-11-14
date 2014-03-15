/**
 * This is a subclass of AsyncTask class
 * Tasks are requested and run in background
 */

package HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class RequestTask extends AsyncTask<String, String, String>{
	RequestReceiver receiver;
	List<NameValuePair> values;
	HttpContext localContext;
	
	public RequestTask(RequestReceiver receiver) {
		this.receiver = receiver;
	}
	
	public RequestTask(RequestReceiver receiver,HttpContext localContext){
		this.receiver = receiver;
		this.localContext = localContext;
	}
	public RequestTask(RequestReceiver receiver, HttpContext localContext, List<NameValuePair> values){
		this.receiver = receiver;
		this.values = values;
		this.localContext = localContext;
	}
/*
 * HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(values, "UTF-8"));
 * */
    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
        	if(this.values == null){
        		if(localContext != null)
        			response = httpclient.execute(new HttpGet(uri[0]), localContext);
        		else
        			response = httpclient.execute(new HttpGet(uri[0]));
        	}else{
        		HttpPost httppost = new HttpPost(uri[0]);
        		httppost.setEntity(new UrlEncodedFormEntity(values, "UTF-8"));
        		response = httpclient.execute(httppost, localContext);
        	}
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        receiver.receiveTask(result);
    }
}