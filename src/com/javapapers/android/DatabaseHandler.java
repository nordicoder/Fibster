package com.javapapers.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler
  extends SQLiteOpenHelper
{
  private static final String DATABASE_NAME = "contactsManager";
  private static int DATABASE_VERSION = 114;
  private static final String KEY_ID = "id";
  private static final String KEY_INFO = "info";
  private static final String KEY_URL = "url";
  private static final String KEY_FRO = "fro";
  private static final String KEY_TOO = "too";
 static final String TABLE_CONTACTS = "contacts";
  int hh = 0;
  
  public DatabaseHandler(Context paramContext)
  {
    super(paramContext, "contactsManager", null, DATABASE_VERSION);
  }
  
  void addContact(Contact paramContact)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("info", paramContact.getINF());
    localContentValues.put("url", paramContact.getURL());
    localContentValues.put("fro", paramContact.getFROM());
    localContentValues.put("too", paramContact.getTOO());


    localSQLiteDatabase.insert("contacts", null, localContentValues);
    localSQLiteDatabase.close();
  }
  
  public void deleteContact(Contact paramContact)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramContact.getINF();
    localSQLiteDatabase.delete("contacts", "info = ?", arrayOfString);
    localSQLiteDatabase.close();
  }
  
  public List<Contact> getAllContacts()
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = getWritableDatabase().rawQuery("SELECT  * FROM contacts", null);
    if (localCursor.moveToFirst()) {
      do
      {
        Contact localContact = new Contact();
        localContact.setINF(localCursor.getString(1));
        localContact.setURL(localCursor.getString(2));
        localContact.setFROM(localCursor.getString(3));
        localContact.setTOO(localCursor.getString(4));


        localArrayList.add(localContact);
      } while (localCursor.moveToNext());
    }
    localCursor.close();
    return localArrayList;
  }
  
  Contact getContact(int paramInt)
  {
	  SQLiteDatabase db = this.getReadableDatabase();
	  
      Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
              KEY_INFO, KEY_URL, KEY_FRO, KEY_TOO }, KEY_INFO + "=?",
              new String[] { String.valueOf(paramInt) }, null, null, null, null);
      if (cursor != null)
          cursor.moveToFirst();

      Contact contact = new Contact(
              cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
      // return contact
      return contact;
  }
  
  public int getContactsCount()
  {
    Cursor localCursor = getReadableDatabase().rawQuery("SELECT  * FROM contacts", null);
    int i = localCursor.getCount();
    localCursor.close();
    return i;
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE contacts(id INTEGER PRIMARY KEY,info TEXT,url TEXT,fro TEXT,too TEXT)");
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
    onCreate(paramSQLiteDatabase);
  }
  
  public int updateContact(Contact paramContact)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("info", paramContact.getINF());
    localContentValues.put("url", paramContact.getURL());
    localContentValues.put("fro", paramContact.getFROM());
    localContentValues.put("too", paramContact.getTOO());

    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramContact.getINF();
    return localSQLiteDatabase.update("contacts", localContentValues, "info = ?", arrayOfString);
  }
}

/* Location:           C:\Users\GHANSHYAM\Downloads\dex2jar-0.0.9.15\GCM-dex2jar.jar * Qualified Name:     com.javapapers.android.DatabaseHandler * JD-Core Version:    0.7.0.1 */