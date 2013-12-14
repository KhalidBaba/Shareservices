package com.khalid.shareServices.boutiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.khalid.project.library.JSONParser;
import com.khalid.shareServices.R;

public class Activity_service_emploi extends DashBoardActivity {
    /** Called when the activity is first created. */
	// All static variables
	private static final String URL_Annonce = "http://10.0.0.3/shareServices/include/annonce_xml.php";
	private static String annonce_tag = "SelectAnnonceTele";
		static final String URL = "http://10.0.0.3/shareServices/include/annonce.xml";
		// XML node keys
		static final String KEY_SONG = "annonce_tel"; // parent node
		static final String KEY_ID = "id_annonce";
		static final String KEY_TITLE = "title";
		static final String KEY_ARTIST = "content";
		static final String KEY_DURATION = "date";
		static final String KEY_THUMB_URL = "image";
		// Progress Dialog
		private ProgressDialog pDialog;
		ListView list;
	    LazyAdapter adapter;
	 // Creating JSON Parser object
		JSONParser jsonParser = new JSONParser();

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_boutique_emploi);
			
			new LoadAnnonce().execute();
			
			ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // getting XML from URL
			Document doc = parser.getDomElement(xml); // getting DOM element
			
			NodeList nl = doc.getElementsByTagName(KEY_SONG);
			// looping through all song nodes <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
				map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
				map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
				map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

				// adding HashList to ArrayList
				songsList.add(map);
			}
			

			list=(ListView)findViewById(R.id.list);
			
			// Getting adapter by passing xml data ArrayList
	        adapter=new LazyAdapter(this, songsList);        
	        list.setAdapter(adapter);
	        

	        // Click event for single list row
	        list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String id_courrier = ((TextView) view.findViewById(R.id.title)).getText().toString();		
					Toast.makeText(getApplicationContext(),  id_courrier, Toast.LENGTH_SHORT).show();
					view.setBackgroundColor(Color.BLUE);
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
				pDialog = new ProgressDialog(Activity_service_emploi.this);
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

//				try {				
//					Courrier = new JSONArray(json);
//					
//					if (Courrier != null) {
//						// looping through All albums
//						for (int i = 0; i < Courrier.length(); i++) {
//							JSONObject c = Courrier.getJSONObject(i);
//
//							// Storing each json item values in variable
//							String id = c.getString(TAG_ID);
//							String title = c.getString(TAG_TITLE);
//							String content = c.getString(TAG_CONTENT);
//							String date_c = c.getString(TAG_DATE);
//							String image = c.getString(KEY_THUMB_URL);
//
//							// creating new HashMap
//							HashMap<String, String> map = new HashMap<String, String>();
//
//							// adding each child node to HashMap key => value
//							map.put(TAG_ID, id);
//							map.put(TAG_TITLE, title);
//							map.put(TAG_CONTENT, content);
//							map.put(TAG_DATE, date_c);
//							map.put(KEY_THUMB_URL, image);
//						//	com.khalid.shareServices.imagesLoade.ImageLoader.DisplayImage(map.get(KEY_THUMB_URL), image);
//						//	ImageLoader.displayImage(KEY_THUMB_URL, image, options, animateFirstListener);
//							// adding HashList to ArrayList
//							courrierArriverList.add(map);
//						}
						
					//	list=(ListView)findViewById(R.id.list);
						
						// Getting adapter by passing xml data ArrayList
				       
//					}else{
//						Log.d("Annonce : ", "null");
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}

				
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
//					ListAdapter adapter = new SimpleAdapter(
////								Activity_service_tele.this, courrierArriverList,R.layout.list_row, new String[] { TAG_ID,KEY_THUMB_URL,
////										TAG_TITLE, TAG_CONTENT ,TAG_DATE }, new int[] {
////										R.id.id_annonce,R.id.image, R.id.title, R.id.content ,R.id.duration});
////						
////						// updating listview
////						// adapter=new LazyAdapter(Activity_service_tele, courrierArriverList);        
////					    // list.setAdapter(adapter);
////						setListAdapter(adapter);
					}
				});

			}

		}
}
