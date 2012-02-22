package projet.android.nfc.call;


import android.os.Bundle;
import android.preference.PreferenceActivity;


public class PrefActivity extends PreferenceActivity {
	public static final String CALL_MODE ="call_mode_choice";
	public static final String CALL_MODE_CONFIRMATION = "to_confirm";
	public static final String CALL_MODE_AUTOMATIC = "automatic";
	
	
	
	 @Override
	  protected void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    
	    addPreferencesFromResource(R.xml.pref);
	 }
	
	 
}

	 


