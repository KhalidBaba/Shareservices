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

import com.khalid.helper.AlertDialogManager;
import com.khalid.helper.ConnectionDetector;
import com.khalid.project.library.JSONParser;
import com.khalid.shareServices.R;
import com.khalid.shareServices.boutiques.Activity_service_tele.LoadAnnonce;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_service_vehicule extends DashBoardActivity {
	 /** Called when the activity is first created. */
		// All static variables
			static final String URL = "http://10.0.0.3/shareServices/include/annonce.xml";
			// XML node keys
			static final String KEY_SONG = "annonce_tel"; // parent node
			static final String KEY_ID = "id_annonce";
			static final String KEY_TITLE = "title";
			static final String KEY_ARTIST = "content";
			static final String KEY_DURATION = "date";
			static final String KEY_THUMB_URL = "image";
			
			ListView list;
		    LazyAdapter adapter;

			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.layout_service_vehicule);
				

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
		        
			}
			}
