package com.khalid.shareServices.boutiques;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.khalid.helper.AlertDialogManager;
import com.khalid.helper.ConnectionDetector;
import com.khalid.project.library.JSONParser;
import com.khalid.shareServices.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class Activity_service_tele extends ListActivity {
    /** Called when the activity is first created. */
	
	
	// Connection detector
	
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> courrierArriverList;

	// albums JSONArray
	JSONArray Courrier = null;

	// albums JSON url
	private static final String URL_Annonce = "http://10.0.0.3/shareServices/include/annonce.php";
	private static String annonce_tag = "SelectAnnonceTele";
	// ALL JSON node names
	private static final String TAG_ID = "id_annonce";
	private static final String TAG_TITLE = "title";
	private static final String TAG_CONTENT = "content";
	private static final String TAG_DATE = "date";
	static final String KEY_THUMB_URL = "image";
	private static String id_user;
	ListView list;
    LazyAdapter adapter;
    Activity Activity_service_tele = null;
    DisplayImageOptions options;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_service_tele);
        Log.i("Apres SetContent", "Valide");
       // setHeader(getString(R.string.GingerbreadActivityTitle), true, true);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(Activity_service_tele.this, "Internet erreur",
                    "Merci de se connecter a un réseaux internet ", false);
            // stop executing code by return
            return;
        }
        options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
        
       // Intent i =getIntent();
        //id_user = i.getStringExtra("id_user");
        //Log.i("Id _ user  >>   ", id_user);
		// Hashmap for ListView
		courrierArriverList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		  Log.i("Avant execute", "Valide");
	    	new LoadAnnonce().execute();
		Log.i("apres execute", "Valide");
		// get listview
		ListView lv = getListView();
		
		/**
		 * Listview item click listener
		 * TrackListActivity will be lauched by passing album id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// on selecting a single album
				// TrackListActivity will be launched to show tracks inside the album
				Intent i = new Intent(getApplicationContext(), AnnonceSelectedActivity.class);
				
				// send album id to tracklist activity to get list of songs under that album
				String id_courrier = ((TextView) view.findViewById(R.id.id_annonce)).getText().toString();
				i.putExtra("id_courrier", id_courrier);				
				
				startActivity(i);
			}
		});		
	}

	/**
	 * Background Async Task to Load all Albums by making http request
	 * */
	class LoadAnnonce extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Activity_service_tele.this);
			pDialog.setMessage("List des Annonce Téléphones ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("tag",annonce_tag));
			params.add(new BasicNameValuePair("id_type","1"));
			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_Annonce, "GET",params);
			//ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
			// Check your log cat for JSON reponse
			Log.d("courrier JSON: ", "> " + json);

			try {				
				Courrier = new JSONArray(json);
				
				if (Courrier != null) {
					// looping through All albums
					for (int i = 0; i < Courrier.length(); i++) {
						JSONObject c = Courrier.getJSONObject(i);

						// Storing each json item values in variable
						String id = c.getString(TAG_ID);
						String title = c.getString(TAG_TITLE);
						String content = c.getString(TAG_CONTENT);
						String date_c = c.getString(TAG_DATE);
						String image = c.getString(KEY_THUMB_URL);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_TITLE, title);
						map.put(TAG_CONTENT, content);
						map.put(TAG_DATE, date_c);
						map.put(KEY_THUMB_URL, image);
					//	com.khalid.shareServices.imagesLoade.ImageLoader.DisplayImage(map.get(KEY_THUMB_URL), image);
					//	ImageLoader.displayImage(KEY_THUMB_URL, image, options, animateFirstListener);
						// adding HashList to ArrayList
						courrierArriverList.add(map);
					}
					
				//	list=(ListView)findViewById(R.id.list);
					
					// Getting adapter by passing xml data ArrayList
			       
				}else{
					Log.d("Annonce : ", "null");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
				ListAdapter adapter = new SimpleAdapter(
							Activity_service_tele.this, courrierArriverList,R.layout.list_row, new String[] { TAG_ID,KEY_THUMB_URL,
									TAG_TITLE, TAG_CONTENT ,TAG_DATE }, new int[] {
									R.id.id_annonce,R.id.image, R.id.title, R.id.content ,R.id.duration});
					
					// updating listview
					// adapter=new LazyAdapter(Activity_service_tele, courrierArriverList);        
				    // list.setAdapter(adapter);
					setListAdapter(adapter);
				}
			});

		}

	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
        
    }

