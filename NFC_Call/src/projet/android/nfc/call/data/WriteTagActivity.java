package projet.android.nfc.call.data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import projet.android.nfc.call.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WriteTagActivity extends Activity {
	private NfcAdapter	mNfcAdapter;
	private IntentFilter[] mWriteTagFilters;
	private PendingIntent mNfcPendingIntent;
	private Boolean mWriteMode = false;
	private TextView id_TextView;
	private TextView contact_TextView;
	
	private TextView tel_TextView;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.write_tag_layout);
	        Log.d("WriteTagActivity()", "**");
	        Bundle extras;
	        //Binding UI
	        id_TextView = (TextView) findViewById(R.id.textView_id);
	        contact_TextView =(TextView) findViewById(R.id.textView_contact);
	        tel_TextView =(TextView) findViewById(R.id.textView_tel);
	        Log.d("extras()", "**");
	        if((extras = getIntent().getExtras()) != null){
	        	//extract data
	        	Log.d("extras() recu ok", "**");
	        	String id = extras.getString("id");
	        	String pos = extras.getString("pos");
	        	Log.d(pos, "**");
	        	String fname = extras.getString("fname");
	        	Log.d("fname() recu ok", "**");
	        	String lname = extras.getString("lname");
	        	Log.d("lname() recu ok", "**");
	        	String tel = extras.getString("tel");
	        	Log.d("tel() recu ok", "**");
	        	
	        	id_TextView.setText(id);
	        	contact_TextView.setText(fname +" "+lname);
	        	tel_TextView.setText(tel);
	        	
	        	
	        
		        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		       // NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(getBaseContext());   
		        
		        if(mNfcAdapter != null) {
		            boolean enabled = mNfcAdapter.isEnabled();
		            if(enabled)
		            	 Log.i("Enabled()", "**");
		            	
		            else
		            	Log.i("Disabled()", "**");
		            	
		        }   
		        
		        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
		                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		        Log.i("mNfcPendingIntent()", "**");
		        
		       // if(mNfcAdapter !=null)
		        	enableTagWriteMode();
	        }
	        
	        	
//	        
//	        new AlertDialog.Builder(WriteTagActivity.this).setTitle("Touch tag to write")
//	            .setOnCancelListener(new DialogInterface.OnCancelListener() {
//	                @Override
//	                public void onCancel(DialogInterface dialog) {
//	                    disableTagWriteMode();
//	                }
//	            }).create().show();
      
	    }
	 
	
	//Create  NDEF record
	private NdefRecord createRecord() throws UnsupportedEncodingException {
	    String text       = id_TextView.getText().toString();
	    String lang       = "en";
	    byte[] textBytes  = text.getBytes();
	    byte[] langBytes  = lang.getBytes("US-ASCII");
	    int    langLength = langBytes.length;
	    int    textLength = textBytes.length;
	    byte[] payload    = new byte[1 + langLength + textLength];

	    // set status byte (see NDEF spec for actual bits)
	    payload[0] = (byte) langLength;

	    // copy langbytes and textbytes into payload
	    System.arraycopy(langBytes, 0, payload, 1,              langLength);
	    System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

	    NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, 
	                                       NdefRecord.RTD_TEXT, 
	                                       new byte[0], 
	                                       payload);

	    return record;
	}
	//Write the record as an Ndef Message
	private void write(Tag tag) throws IOException, FormatException {
	    NdefRecord[] records = { createRecord() };
	    NdefMessage  message = new NdefMessage(records);

	    // Get an instance of Ndef for the tag.
	    Ndef ndef = Ndef.get(tag);

	    // Enable I/O
	    ndef.connect();

	    // Write the message
	    ndef.writeNdefMessage(message);

	    // Close the connection
	    ndef.close();
	}
	
	private void enableTagWriteMode() {
	    mWriteMode = true;
	    
//	    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
//	    
//	    mWriteTagFilters = new IntentFilter[] { tagDetected };
//	    
//	    mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
	    Log.i("enableForegroundDispatch()", "**");
	}
	private void disableTagWriteMode() {
	    mWriteMode = false;
//	    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
//	    mWriteTagFilters = new IntentFilter[] { tagDetected };
//	    mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
	}

	@Override
	     public void onResume() {
	          super.onResume();
	          mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, null, null);
	      }
	@Override
	
	     public void onNewIntent(Intent intent) {
	         Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	         //mCountView.setText("Tag " + mCount++);
	         //if (mMessage != null) {
	         try {
				write(tag);
				Toast.makeText(this, "tag ecrit reissie" , 7000).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	     }
	 
	     @Override
	     public void onPause() {
	         super.onPause();
	         mNfcAdapter.disableForegroundDispatch(this);
	     }
	

}
