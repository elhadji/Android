package projet.android.nfc.call.data;


import projet.android.nfc.call.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DataActivity extends Activity {
	
	private SharedPreferences      mPreferenceMgr;
	
	
	//data base
	
	
	public static final int RESQUEST_CODE_ADD =					1;
	public static final int RESQUEST_CODE_DELETE =				2;
	public static final int RESQUEST_CODE_MODIFY =				3;
	
	
	
	 @Override
	  protected void onCreate(Bundle savedInstanceState){
	    // Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.data_base_layout);
	    
	    Button buttonAdd = (Button) findViewById(R.id.btn_add);
	   // buttonAdd.setOnClickListener(this);
	   Button buttonDelete = (Button) findViewById(R.id.btn_delete);
	    //buttonDelete.setOnClickListener(this);
	    Button buttonModify = (Button) findViewById(R.id.btn_modify);
	   // buttonModify.setOnClickListener(this);
	    	 
	    
	    Log.d("DataActivity:onCreate()", "**");
	 }
	 
	public void seeData(View v) {
		 Intent intent = new  Intent(this, ContactListActivity.class);
	     startActivity(intent);
	}
	public void addContact(View v) {
		 Intent intent = new  Intent(this, AddContactActivity.class);
	     startActivityForResult(intent, RESQUEST_CODE_ADD);
	}
	
	public void deleteContact(View v) {
		 Intent intent = new  Intent(this, DeleteContactActivity.class);
		 startActivityForResult(intent, RESQUEST_CODE_DELETE);
	}
	public void modifyContact(View v) {
		 Intent intent = new  Intent(this, ModifyContactActivity.class);
		 startActivityForResult(intent, RESQUEST_CODE_MODIFY);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case RESQUEST_CODE_ADD:
			if(resultCode == Activity.RESULT_OK){
				//add a contact
				ContactDataBase contactDataBase = new ContactDataBase(getApplicationContext());
				contactDataBase.open();
			    Bundle extras = data.getExtras() ;
			    if(extras != null){
			    	String first_name = extras.getString("first_name");
			    	String last_name = extras.getString("last_name");
			    	String tel = extras.getString("tel");
			    	Boolean write =extras.getBoolean("writeTag");
			    	
			    	Contact contact = new Contact(first_name, last_name, tel);
			    	contactDataBase.insertContat(contact);
			    	Contact c = contactDataBase.getConatctWithTel(tel);
			    	String id_db = Integer.toString(c.getId());
			    	contactDataBase.close();
			    	if(write == true)
			    	{//write on tag
			    		
			    	Intent writeTagintent = new  Intent(this, WriteTagActivity.class);
			    		
			   		 writeTagintent.putExtra("id", id_db);
			   		 writeTagintent.putExtra("fname", first_name);
			   		 writeTagintent.putExtra("lname", last_name);
			   		 writeTagintent.putExtra("tel", tel);
			   		 
			   		 Toast.makeText(this, c.toString(), 7000).show();
			          startActivity(writeTagintent);
			           
			    	}
			    	
			    	
			    	Toast.makeText(this, R.string.contact_saved , 7000).show();
			    }
			    else
			    	Toast.makeText(this, R.string.contact_not_saved , 7000).show();
			    	
			    
				
			}
			break;
		case RESQUEST_CODE_DELETE:
			if(resultCode == Activity.RESULT_OK){
				//delete a contact
				ContactDataBase contactDataBase = new ContactDataBase(getApplicationContext());
				contactDataBase.open();
			    Bundle extras = data.getExtras();
			    if(extras != null){
			    	int id = extras.getInt("id");
			    	contactDataBase.removeContactWithId(id);
			    	contactDataBase.close();
			    	Toast.makeText(this, R.string.contact_delete , 7000).show();
			    }
			    else
			    	Toast.makeText(this,  R.string.contact_not_delete, 7000).show();
			    
				
			}
			break;
		case RESQUEST_CODE_MODIFY:
			if(resultCode == Activity.RESULT_OK){
				//add a contact
				ContactDataBase contactDataBase = new ContactDataBase(getApplicationContext());
				contactDataBase.open();
			    Bundle extras = data.getExtras() ;
			    if(extras != null){
			    	String first_name = extras.getString("first_name");
			    	String last_name = extras.getString("last_name");
			    	String New_tel = extras.getString("New_tel");
			    	int id = extras.getInt("id");
			    	
			    	Contact contact = new Contact(first_name, last_name, New_tel);
			    	contactDataBase.updateContact(id, contact);
			    	contactDataBase.close();
			    	
			    	
			    	Toast.makeText(this, R.string.contact_modified , 7000).show();
			    }
			    else
			    	Toast.makeText(this, R.string.contact_not_modified , 7000).show();
			break;
			}

		default:
			break;
		}
    }
	
	
	

}