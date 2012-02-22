package projet.android.nfc.call.data;

import projet.android.nfc.call.R;
import projet.android.nfc.call.data.Contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends Activity{
	@Override
	  protected void onCreate(Bundle savedInstanceState){
	    // Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_contact_layout);
	    Log.d("AddContactActivity:onCreate()", "**");
	    
	    
	 }
	public void saveContactAndWrite(View v) {

		EditText fNameTextEdit = (EditText) findViewById(R.id.editText_Add_FName);
		String fName = fNameTextEdit.getText().toString();
		
		EditText lNameTextEdit = (EditText) findViewById(R.id.editText_Add_LName);
		String lName = lNameTextEdit.getText().toString();
		
		EditText telTextEdit = (EditText) findViewById(R.id.editText_Add_Tel);
		String tel = telTextEdit.getText().toString();
		
		Intent intentDataActivity = new Intent();
		if( fName != null && lName != null && tel != null  ){
			
			intentDataActivity.putExtra("first_name", fName);
			intentDataActivity.putExtra("last_name", lName);
			intentDataActivity.putExtra("tel", tel);
			intentDataActivity.putExtra("writeTag", true);
					
			//Contol if result ok
			setResult(RESULT_OK, intentDataActivity);
		}
		else{
			setResult(RESULT_CANCELED, intentDataActivity);
		}
		finish();
	}
	public void saveContact(View v) {
		
		EditText fNameTextEdit = (EditText) findViewById(R.id.editText_Add_FName);
		String fName = fNameTextEdit.getText().toString();
		
		EditText lNameTextEdit = (EditText) findViewById(R.id.editText_Add_LName);
		String lName = lNameTextEdit.getText().toString();
		
		EditText telTextEdit = (EditText) findViewById(R.id.editText_Add_Tel);
		String tel = telTextEdit.getText().toString();
		
		Intent intentDataActivity = new Intent();
		if( fName != null && lName != null && tel != null  ){
			
			intentDataActivity.putExtra("first_name", fName);
			intentDataActivity.putExtra("last_name", lName);
			intentDataActivity.putExtra("tel", tel);
			intentDataActivity.putExtra("writeTag", false);
					
			//Contol if result ok
			setResult(RESULT_OK, intentDataActivity);
		}
		else{
			setResult(RESULT_CANCELED, intentDataActivity);
		}
		finish();
				
	}
	

}
