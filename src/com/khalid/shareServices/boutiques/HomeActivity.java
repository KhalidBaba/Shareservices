package com.khalid.shareServices.boutiques;

import com.khalid.shareServices.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class HomeActivity extends FragmentActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boutiques_choix);
       // setHeader(getString(R.string.HomeActivityTitle), false, true);
        
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
			intent = new Intent(this, Activity_service_info.class);
			startActivity(intent);
			break;

		case R.id.main_btn_froyo:
			intent = new Intent(this, Activity_service_info.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_gingerbread:
			intent = new Intent(this, Activity_service_tele.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_honeycomb:
			intent = new Intent(this, Activity_service_vehicule.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_ics:
			intent = new Intent(this, Activity_service_imob.class);
			startActivity(intent);
			break;
			
		case R.id.main_btn_jellybean:
			intent = new Intent(this, Activity_service_deco.class);
			startActivity(intent);
			break;	
		default:
			break;
		}
    }
}

