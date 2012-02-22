package projet.android.nfc.call.data;

import projet.android.nfc.call.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DeleteContactActivity  extends Activity{
	@Override
	  protected void onCreate(Bundle savedInstanceState){
	    // Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.delete_contact_layout);
	    Log.d("DeleteContactActivity:onCreate()", "**");
	 }
	public void deleteContact(View v) {
		EditText idTextEdit = (EditText) findViewById(R.id.editText_Delete_Id);
		
		int id = Integer.parseInt(idTextEdit.getText().toString());
		
		Intent intentDataActivity = new Intent();
		if( idTextEdit.getText().toString() != null  ){
			
			intentDataActivity.putExtra("id", id);
					
			//Contol if result ok
			setResult(RESULT_OK, intentDataActivity);
		}
		else{
			setResult(RESULT_CANCELED, intentDataActivity);
		}
		finish();
	}
}
