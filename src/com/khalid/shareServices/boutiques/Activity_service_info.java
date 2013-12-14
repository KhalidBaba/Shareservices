package com.khalid.shareServices.boutiques;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.khalid.shareServices.R;

@SuppressLint("SetJavaScriptEnabled")
public class Activity_service_info extends Activity {
    /** Called when the activity is first created. */
	 WebView mWebView; 
		// Progress Dialog
	 ProgressDialog mProgress;
	    boolean loadingFinished = true;
	    boolean redirect = false;
		private ProgressDialog pDialog;
		final Activity activity = this;
		public final String CLASS_TAG = "ScoutBrowser";
		public String webContent;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boutique_info);
        WebView view = (WebView) findViewById(R.id.webview);
    	activity.setTitle("Listes des Télophones");
        view.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
            	activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if (progress == 100)
                    activity.setTitle(R.string.app_name);
            }
        });

        view.setWebViewClient(new NewsClient());
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);
        webContent = "<html><body>initialized...</body></html>";
        new Thread(new Runnable() {
               public void run() {
                   webContent = callURL();
                   WebView view = (WebView) findViewById(R.id.webview);
                   view.loadDataWithBaseURL("http://10.0.0.3/shareServices/JQM/demos/annonce_detail.php", webContent, "text/html", null, null);
                   
               }
        }).start(); 

      //  mWebView = getWebView();


        Log.i(CLASS_TAG, "Loaded App...");
        
       // setHeader(getString(R.string.FroyoActivityTitle), true, true);
     //   mWebView = (WebView) findViewById(R.id.webview);  
       // mProgress = ProgressDialog.show(Activity_service_info.this, "Loading", "Please wait for a moment...");
        //mWebView.setWebViewClient(new NewsClient());  
        //mWebView.getSettings().setJavaScriptEnabled(true);  
       // mWebView.getSettings().setDomStorageEnabled(true);
        
        //mWebView.loadUrl("http://10.0.0.3/shareServices/JQM/demos/lists.php?tag=SelectAnnonceTele&id_type=1");
        
        
    
       
        
    }
    
 /*   private WebView getWebView() {
        
        //view.loadDataWithBaseURL("http://www.somewebsite.com", webContent, "text/html", null, null);


        return view;
    }*/
    
    public String callURL(){
        String content = "<html><body>loading...</body></html>";

        //Creates web clientclient
        DefaultHttpClient httpclient = new DefaultHttpClient();

        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();

        // Create local HTTP context
        HttpContext localContext = new BasicHttpContext();
        // Bind custom cookie store to the local context
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        HttpGet httpget = new HttpGet("http://10.0.0.3/shareServices/JQM/demos/annonce_detail.php"); 

        //System.out.println("executing request " + httpget.getURI());
        Log.d(CLASS_TAG,"executing request " + httpget.getURI());


        // Pass local context as a parameter
        try {
            HttpResponse response = httpclient.execute(httpget, localContext);

            int code = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();

                content = convertInputStream(instream);
                //tv.setText(content);

            }

            Log.d(CLASS_TAG,"Response Code: "+code+" - "+reason);


        } catch (ClientProtocolException e) {
            Log.e(CLASS_TAG,"ProtocolException",e);
        } catch (IOException e) {
            Log.e(CLASS_TAG,"IOException",e);
        }
        Log.d(CLASS_TAG, webContent);
        return content;
    }
   
    
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	   if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {  
    	     mWebView.goBack();  
    	     return true;  
    	   }  
    	   return super.onKeyDown(keyCode, event);
    }
    
    public String convertInputStream(InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
        is.close();
        return sb.toString();
}
    
   private class NewsClient extends WebViewClient {  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
          view.loadUrl("javascript:changeLocation('" + url + "')");  
          if (!loadingFinished) {
	          redirect = true;
	       }

	    loadingFinished = false;
		view.loadUrl(url);
		return true;
        }  
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
         // TODO Auto-generated method stub
         super.onPageStarted(view, url, favicon);
         loadingFinished = false;
         
         //mProgress.show();
        }

		
		
		// when finish loading page
    	public void onPageFinished(WebView view, String url) {
    		if(!redirect){
    	          loadingFinished = true;
    	       }

    	       if(loadingFinished && !redirect){
    	    	   if(null !=mProgress) {
    	    		   if(mProgress.isShowing()) {
    		    			mProgress.dismiss();
    		    			
    		    		}
    	    		}
    	    	   
    	       } else{
    	          redirect = false; 
    	       }
    	}
    	
		
        
      }  

}
