package com.khalid.shareServices;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressWarnings("deprecation")
public class ClientPotActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_clients_potentiel);
		  Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
	        startManagingCursor(c);
	        
	        ListView list = ((ListView) this.findViewById(R.id.list));
	        
	        SimpleCursorAdapter adpt = new SimpleCursorAdapter(this, 
	                android.R.layout.simple_list_item_2,
	                c, 
	                new String[] {People.NAME, People.NUMBER} ,
	                new int[] {android.R.id.text1,android.R.id.text2}); 
	        
	        list.setAdapter(adpt);
	}
	
	

}
