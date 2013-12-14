package com.khalid.shareServices;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.khalid.shareServices.boutiques.Activity_service_deco;
import com.khalid.shareServices.boutiques.Activity_service_emploi;
import com.khalid.shareServices.boutiques.Activity_service_imob;
import com.khalid.shareServices.boutiques.Activity_service_info;
import com.khalid.shareServices.boutiques.Activity_service_tele;
import com.khalid.shareServices.boutiques.Activity_service_vehicule;

public class HomeFragment extends Fragment implements OnClickListener {
	
	protected static final Context HomeFragement = null;

	

	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.layout_boutiques_choix, container, false);
      
        
        return rootView;
    }
	
	 /**
     * Button click handler on Main activity
     * @param v
     */
    public void onButtonClicker(View v)
    {
    	Intent intent;
    	
    	
    	switch (v.getId()) {
		case R.id.main_btn_eclair:
			intent = new Intent(this.getActivity(), Activity_service_emploi.class);
			startActivity(intent);
			
			    v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// Launching News Feed Screen
					Intent i = new Intent(HomeFragement, Activity_service_emploi.class);
					 
					startActivity(i);
				}
			});
			
			break;

		case R.id.main_btn_froyo:
			intent = new Intent(this.getActivity(), Activity_service_info.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_gingerbread:
			intent = new Intent(this.getActivity(), Activity_service_tele.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_honeycomb:
			intent = new Intent(this.getActivity(), Activity_service_vehicule.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_ics:
			intent = new Intent(this.getActivity(), Activity_service_imob.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_jellybean:
			intent = new Intent(this.getActivity(), Activity_service_deco.class);
			startActivity(intent);
			break;	
		default:
			break;
		}
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
