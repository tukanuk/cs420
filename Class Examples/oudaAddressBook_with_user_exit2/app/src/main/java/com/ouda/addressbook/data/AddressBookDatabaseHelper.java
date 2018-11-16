// AddressBookDatabaseHelper.java
// SQLiteOpenHelper subclass that defines the app's database
package com.ouda.addressbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ouda.addressbook.data.DatabaseDescription.Contact;
import com.ouda.addressbook.data.DatabaseDescription.User; //new


public class AddressBookDatabaseHelper extends SQLiteOpenHelper {
   private static final String DATABASE_NAME = "AddressBook.db";
   private static final int DATABASE_VERSION = 1;

   // constructor
   public AddressBookDatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   // creates the contacts table when the database is created
   @Override
   public void onCreate(SQLiteDatabase db) {
      // SQL for creating the contacts table
      final String CREATE_CONTACTS_TABLE =
              "CREATE TABLE " + Contact.TABLE_NAME + "(" +
                      Contact._ID + " integer primary key, " +
                      Contact.COLUMN_NAME + " TEXT, " +
                      Contact.COLUMN_PHONE + " TEXT, " +
                      Contact.COLUMN_EMAIL + " TEXT, " +
                      Contact.COLUMN_STREET + " TEXT, " +
                      Contact.COLUMN_CITY + " TEXT, " +
                      Contact.COLUMN_STATE + " TEXT, " +
                      Contact.COLUMN_PCODE + " TEXT," +

                      //new
                      Contact.COLUMN_USER + " TEXT, " +
                      "FOREIGN KEY (" + Contact.COLUMN_USER + ") REFERENCES " +
                      User.TABLE_NAME + "(" + User.COLUMN_USERNAME + "));";

      db.execSQL(CREATE_CONTACTS_TABLE); // create the contacts table

      // SQL for creating users table
      final String CREATE_USERS_TABLE =
              "CREATE TABLE " + DatabaseDescription.User.TABLE_NAME + "(" +
                      DatabaseDescription.User.COLUMN_USERNAME + " TEXT primary key not null, " +
                      DatabaseDescription.User.COLUMN_USERPASSWORD + " TEXT not null);";

      db.execSQL(CREATE_USERS_TABLE); // create the users table
   }

   // normally defines how to upgrade the database when the schema changes
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion,
                         int newVersion) {
   }




   public void addUser (String userName, String userPassword)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(DatabaseDescription.User.COLUMN_USERNAME, userName);
      values.put(DatabaseDescription.User.COLUMN_USERPASSWORD, userPassword);
      long idValue = db.insert (DatabaseDescription.User.TABLE_NAME, null, values);
   }

   public boolean Authenticate(String userName, String userPassword)
   {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query(DatabaseDescription.User.TABLE_NAME,
              new String[] {DatabaseDescription.User.COLUMN_USERNAME, DatabaseDescription.User.COLUMN_USERPASSWORD},
              DatabaseDescription.User.COLUMN_USERNAME + "=?",
              new String[]{userName},
              null, null, null);

      if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
      {
         String userPassword1 = cursor.getString(1);
         if (userPassword.equals(userPassword1))
            return true;
      }
      return false;
   }

   public boolean userExists(String user)
   {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query(DatabaseDescription.User.TABLE_NAME,
              new String[] {DatabaseDescription.User.COLUMN_USERNAME, DatabaseDescription.User.COLUMN_USERPASSWORD},
              DatabaseDescription.User.COLUMN_USERNAME + "=?",
              new String[]{user},
              null, null, null);
      if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
      {
          return true;
      }
      return false;
   }
}



/**************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
