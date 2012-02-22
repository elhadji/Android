package projet.android.nfc.call.data;

import projet.android.nfc.call.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactListActivity extends ListActivity{
	
/** Called when the activity is first created. */
	private Cursor mCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		Log.d("ContactListActivity:onCreat()", "**");
		ContactDataBase contactDataBase = new ContactDataBase(getApplicationContext());
		contactDataBase.open();
		mCursor = contactDataBase.getAllContact_Cursor();
		startManagingCursor(mCursor);
		// Now create a new list adapter bound to the cursor.
		// SimpleListAdapter is designed for binding to a Cursor.
		ListAdapter adapter = new SimpleCursorAdapter(this, // Context.
					R.layout.contacts_row, // Specify the row template
														// to use (here, two
														// columns bound to the
														// two retrieved cursor
														// rows).
				mCursor, // Pass in the cursor to bind to.
				// Array of cursor columns to bind to.
				new String[] { ContactDataBase.COLUMN_ID,
				ContactDataBase.COLUMN_FIRST_NAME },
				// Parallel array of which template objects to bind to those
				// columns.
				new int[] { R.id.IdDB, R.id.FName });

		// Bind to our new adapter.
		Log.i("oncreat", "sortie");
		setListAdapter(adapter);
		
		contactDataBase.close(); 
		
	}

	
		
		

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = (Cursor) getListView().getItemAtPosition(position) ;
				 
		String id_db = c.getString(ContactDataBase.NUM_COLUMN_ID);
		String fname = c.getString(ContactDataBase.NUM_COLUMN_FIRST_NAME);
		String lname = c.getString(ContactDataBase.NUM_COLUMN_LAST_NAME);
		String tel = c.getString(ContactDataBase.NUM_COLUMN_TEL);
		c.close();
		Log.i("clos cursor()", tel+lname+id_db);
		 Intent writeTagintent = new  Intent(ContactListActivity.this, WriteTagActivity.class);
		
		 writeTagintent.putExtra("id", id_db);
		 writeTagintent.putExtra("fname", fname);
		 writeTagintent.putExtra("lname", lname);
		 writeTagintent.putExtra("tel", tel);
		 
          startActivity(writeTagintent);
         finish();
		Toast.makeText(this, "id_db" , 7000).show();
	}

}
		
	


