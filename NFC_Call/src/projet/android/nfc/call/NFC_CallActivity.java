package projet.android.nfc.call;

import java.io.UnsupportedEncodingException;
import java.util.List;

import projet.android.nfc.call.R;

import projet.android.nfc.call.data.Contact;
import projet.android.nfc.call.data.ContactDataBase;
import projet.android.nfc.call.record.ParsedNdefRecord;
import projet.android.nfc.call.record.TextRecord;

import android.R.integer;
import android.app.Activity;
//import android.content.DialogInterface.OnClickListener;
//import android.content.DialogInterface;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NFC_CallActivity extends Activity implements OnClickListener {
	
	
	
	 // internal processing variables:
	private SharedPreferences      mPreferenceMgr;
	private boolean                mIsModeConfirmation               = true; // to choose between Confirmation or Automatic Mode
	 
	private String mTagMessage;
	static final String TAG = "ViewTag";
	
	
	
	  static final int ACTIVITY_TIMEOUT_MS = 1 * 1000;

	    TextView mTitle;
	    private TextView mContact;
	    private TextView mNumber;
	    private ImageView mContactPhoto;
	    LinearLayout mTagContent;
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_layout);

        //init preference API
        initPreferences();    

        mContact = (TextView) findViewById(R.id.textViewName);
        mNumber = (TextView) findViewById(R.id.textViewNumber);
    
        resolveIntent(getIntent());
        
       
    }
    // handler for listening to preference changes
    private final SharedPreferences.OnSharedPreferenceChangeListener mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences aSharedPrefs, String aKey){
          if(aKey.equals(PrefActivity.CALL_MODE)){
            String callModeStr = aSharedPrefs.getString(PrefActivity.CALL_MODE, PrefActivity.CALL_MODE_CONFIRMATION);
            if(callModeStr.equals(PrefActivity.CALL_MODE_CONFIRMATION)){
            	mIsModeConfirmation = true;
              
            }
            else {
            	mIsModeConfirmation = false;
            }
           
          }
        }
      };
      /**
       * Preferences initialisations
       * - get an instance on the preference manager: mPreferenceMgr 
       * - register the preferences handler
       * - set the mode in: mIsModeConfirmation
       */
      private void initPreferences(){
       
        // set preference manager
        mPreferenceMgr = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferenceMgr.registerOnSharedPreferenceChangeListener(mPrefListener);

        // set the default mode  retrieved from preferences
        String callMode = mPreferenceMgr.getString(PrefActivity.CALL_MODE , PrefActivity.CALL_MODE_CONFIRMATION);
        if(callMode.equals(PrefActivity.CALL_MODE_CONFIRMATION)) {
        	mIsModeConfirmation = true;
        	 Toast.makeText(this, "mIsModeConfirmation = true", 7000).show();
        }
        else {
        	mIsModeConfirmation = false;
        	Toast.makeText(this, "mIsModeConfirmation = false", 7000).show();
        }
      }
      
    
    void resolveIntent(Intent intent){
    	String action = intent.getAction();
    	
    	 if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
             
             Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
             NdefMessage[] msgs;
             if (rawMsgs != null) {
                 msgs = new NdefMessage[rawMsgs.length];
                 for (int i = 0; i < rawMsgs.length; i++) {
                     msgs[i] = (NdefMessage) rawMsgs[i];
                     
                 }
             } else {
                 // Unknown tag type
                 byte[] empty = new byte[] {};
                 NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                 NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
                 msgs = new NdefMessage[] {msg};
                 
             }
             // Setup the views
            // setTitle(R.string.title_scanned_tag);
             
             NdefRecord record = msgs[0].getRecords()[0];
            

             //mTagMessage = getTextData(record.getPayload());
 
             List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
             final int size = records.size();
             for (int i = 0; i < size; i++) {
               ParsedNdefRecord record2 = records.get(i);
                String number = record2.getData();
                
                dial(mIsModeConfirmation,number);
          }
                   
              
         }
    	 else{
    		 Log.e(TAG, "Unknown intent " + intent);
         finish();
         return;}
    }
    
    public void dial(Boolean callModeConfirmation, String number){
    	 
	   if(number.contains("tel:")){	       			
			number = number.replaceFirst("tel:", "");
	     }
	   
	   Contact contact = getContactNumbers(number); 
	   
	   if(callModeConfirmation== true){
	       
	 	   Button button = (Button) findViewById(R.id.buttonCall);
	       button.setOnClickListener(this);
	    
	        if(contact  !=null){
	     	   mContact.setText(contact.getFirstName() +" "+ contact.getLastName());
	     	   mNumber.setText(contact.getTel());
	        }
	        else{
	     	   mContact.setText("Not Identified");
	        }
	    }
	    else{
	 	    String phoneNumber =  contact.getTel();
	        Toast.makeText(this, phoneNumber, 7000).show();
	        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber)));
	    }
    }

    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

         String phoneNumber =  (String) mNumber.getText();
         
         
         Toast.makeText(this, phoneNumber.toString(), 7000).show();
         startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber)));
		
	}
	public Contact getContactNumbers(String number) {
		Contact contact = new Contact();
		ContactDataBase contactDataBase = new ContactDataBase(getApplicationContext());
		contactDataBase.open();
		int id = Integer.parseInt(number);
		contact = contactDataBase.getConatctWithId(id);
		return contact;
		
	}
		
       
	  
}