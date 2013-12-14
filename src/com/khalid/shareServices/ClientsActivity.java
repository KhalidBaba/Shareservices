package com.khalid.shareServices;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressWarnings("deprecation")
public class ClientsActivity extends Fragment {

	public ClientsActivity(){}
	
	Activity a;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.layout_clients_potentiel, container, false);

        Cursor c = rootView.getContext().getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        ((Activity) rootView.getContext()).startManagingCursor(c);
        
        ListView list = ((ListView) rootView.findViewById(R.id.list));
        
        SimpleCursorAdapter adpt = new SimpleCursorAdapter(rootView.getContext(), 
                android.R.layout.simple_list_item_2,
                c, 
                new String[] {People.NAME, People.NUMBER} ,
                new int[] {android.R.id.text1,android.R.id.text2}); 
        
        list.setAdapter(adpt);
        return rootView;
        
    }
}
