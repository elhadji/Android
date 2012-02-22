package projet.android.nfc.call.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
//creation and acces to the base

	public class ContactOpenHelper extends SQLiteOpenHelper{
		//version of the data base
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
		
		//request to create  the base
		private static final String REQUEST_CREATION_DB = "CREATE TABLE "				
				        + CONTACT_TABLE_NAME + " (" + COLUMN_ID	 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				        + COLUMN_FIRST_NAME + " TEXT NOT NULL, " 
				        + COLUMN_LAST_NAME + " TEXT NOT NULL, "
				        + COLUMN_TEL + " TEXT NOT NULL);";
		
	
	public ContactOpenHelper(Context context, String name,CursorFactory factory, int version) {
		super(context, CONTACT_BASE_NAME, factory, DATA_BASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUEST_CREATION_DB);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//when we change the num of version we drop the table and re-create it
		if (newVersion > DATA_BASE_VERSION) {
			        db.execSQL("DROP TABLE " + CONTACT_TABLE_NAME + ";");
			        onCreate(db);
			    }
		
	}
		
	}
