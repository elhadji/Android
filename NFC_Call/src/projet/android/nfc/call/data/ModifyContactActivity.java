package projet.android.nfc.call.data;

import projet.android.nfc.call.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ModifyContactActivity extends Activity{
	@Override
	  protected void onCreate(Bundle savedInstanceState){
	    // Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.modify_contact_layout);
	    Log.d("ModifyContactActivity:onCreate()", "**");
	 }
	
	public void modifyContact(View v) {
		EditText New_fNameTextEdit = (EditText) findViewById(R.id.EditText_Modify_NewFName);
		String New_fName = New_fNameTextEdit.getText().toString();
		
		EditText New_lNameTextEdit = (EditText) findViewById(R.id.EditText_Modify_NewLName);
		String New_lName = New_lNameTextEdit.getText().toString();
		
		EditText New_telTextEdit = (EditText) findViewById(R.id.EditText_Modify_NewTel);
		String New_tel = New_telTextEdit.getText().toString();
		
		EditText Id_TextEdit = (EditText) findViewById(R.id.editText_Modify_Id);
		int id = Integer.parseInt(Id_TextEdit.getText().toString());
		
		Intent intentDataActivity = new Intent();
		if( New_fName != null && New_lName != null && New_tel != null && Id_TextEdit != null ){
			
			intentDataActivity.putExtra("id", id);
			intentDataActivity.putExtra("first_name", New_fName);
			intentDataActivity.putExtra("last_name", New_lName);
			intentDataActivity.putExtra("New_tel", New_tel);
			
					
			//Contol if result ok
			setResult(RESULT_OK, intentDataActivity);
		}
		else{
			setResult(RESULT_CANCELED, intentDataActivity);
		}
		finish();
				
	}
	
}
