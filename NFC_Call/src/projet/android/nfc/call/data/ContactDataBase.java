package projet.android.nfc.call.data;

import projet.android.nfc.call.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

public class ContactDataBase {
	private static final int DATA_BASE_VERSION = 				4;
	//name of the data base and table
	private static final String CONTACT_BASE_NAME = 			"contact.db";
	public static final String CONTACT_TABLE_NAME = 			"Contact";
	//colomn
	public static final String COLUMN_ID = 						"_id";
	public static final int NUM_COLUMN_ID = 					0;		
	public static final String COLUMN_FIRST_NAME = 				"FIRST_NAME";
	public static final int NUM_COLUMN_FIRST_NAME = 			1;
	public static final String COLUMN_LAST_NAME = 				"LAST_NAME";
	public static final int NUM_COLUMN_LAST_NAME = 				2;
	public static final String COLUMN_TEL = 					"TEL";
	public static final int NUM_COLUMN_TEL = 					3;
	private SQLiteDatabase bdd;
	private ContactOpenHelper myBaseContactSQLite;

	public ContactDataBase(Context context){
		
		//creat database and table
		myBaseContactSQLite = new ContactOpenHelper(context, CONTACT_BASE_NAME, null, DATA_BASE_VERSION);
	}
	public void open(){
		//open in writing
		bdd = myBaseContactSQLite.getWritableDatabase();
	}
 
	public void close(){
		//close the access to db
		bdd.close();
	}
	
	public SQLiteDatabase getBDD(){
		//get the db
		return bdd;
	}
	public long insertContat(Contact contact){
		
		ContentValues values = new ContentValues();
		//values.put(COLUMN_ID, contact.getId());
		values.put(COLUMN_FIRST_NAME, contact.getFirstName());
		values.put(COLUMN_LAST_NAME, contact.getLastName());
		values.put(COLUMN_TEL, contact.getTel());
		return bdd.insert(CONTACT_TABLE_NAME, null, values);
		
	}
	
	
	public int getIDWithTel(String tel) {
		Cursor c = bdd.query(CONTACT_TABLE_NAME, new String[] {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME,COLUMN_TEL}, COLUMN_TEL + " LIKE \"" + tel +"\"", null, null, null, null);
		return cursorToContact(c).getId();
		
	}
	public int updateContact(int id, Contact contact){
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_FIRST_NAME, contact.getFirstName());
		values.put(COLUMN_LAST_NAME, contact.getLastName());
		values.put(COLUMN_TEL, contact.getTel());
		return bdd.update(CONTACT_TABLE_NAME, values, COLUMN_ID + " = " +id, null);

	}
	public int removeContactWithID(int id){
		return bdd.delete(CONTACT_TABLE_NAME, COLUMN_ID + " = " +id, null);
		
	}
	public int removeContactWithId(int id){
		return bdd.delete(CONTACT_TABLE_NAME, COLUMN_ID + " = " +id, null);
		
	}
	public Cursor getAllContact_Cursor(){
		return bdd.query(CONTACT_TABLE_NAME, new String[] {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME,COLUMN_TEL}, null, null, null, null, null);
		//String SELECT = "SELECT * FROM " + CONTACT_TABLE_NAME;
		//return bdd.rawQuery(SELECT, null);
		
	}
	public Contact getConatctWithId(int id){
		Cursor c = bdd.query(CONTACT_TABLE_NAME, new String[] {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME,COLUMN_TEL}, COLUMN_ID + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToContact(c);
	}
	
	public Contact getConatctWithTel(String tel){
		Cursor c = bdd.query(CONTACT_TABLE_NAME, new String[] {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME,COLUMN_TEL}, COLUMN_TEL + " LIKE \"" + tel +"\"", null, null, null, null);
		return cursorToContact(c);
	}
	private Contact cursorToContact(Cursor c) {
		if (c.getCount() == 0)
			return null;
		//place the cursor the first element
		c.moveToFirst();
		Contact contact = new Contact();
		contact.setId(c.getInt(NUM_COLUMN_ID));
		contact.setFirstName(c.getString(NUM_COLUMN_FIRST_NAME));
		contact.setLastName(c.getString(NUM_COLUMN_LAST_NAME));
		contact.setTel(c.getString(NUM_COLUMN_TEL));
		c.close();
		
		return contact;
		
	}
	
	
}





