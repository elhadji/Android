package projet.android.nfc.call;

import projet.android.nfc.call.data.DataActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class TabMainActivity extends TabActivity {
	 public static final String PREFERENCES_TAB_LABEL = "Preference";
	  public static final String DATA_TAB_LABEL = "Data";
	
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    Log.i("TabMainActivity:onCreate()", "**");
	    setContentView(R.layout.main_tab_layout);
	    
	 // create data base tab: DataBaseActivity
	    Intent intent = new Intent(this, DataActivity.class);
	    TabHost.TabSpec spec = getTabHost().newTabSpec("DataTab");
	    spec.setIndicator(DATA_TAB_LABEL, getResources().getDrawable(android.R.drawable.ic_dialog_map));
	    spec.setContent(intent);
	    getTabHost().addTab(spec);
	    
	    // create the preferences tab: PreferenceActivity  
	    intent = new Intent(this, PrefActivity.class);
	    spec = getTabHost().newTabSpec("PrefTab");
	    spec.setIndicator(PREFERENCES_TAB_LABEL, getResources().getDrawable(android.R.drawable.ic_menu_manage));
	    spec.setContent(intent);
	    getTabHost().addTab(spec);
	    
	    // set the default tab to be display at first
	    setDefaultTab(0);
	    
	  //getTabHost().setCurrentTabByTag("PrefTab");
	    
	  }

}
