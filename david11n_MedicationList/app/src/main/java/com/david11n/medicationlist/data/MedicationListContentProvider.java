// AddressBookContentProvider.java
// ContentProvider subclass for manipulating the app's database
package com.david11n.medicationlist.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.david11n.medicationlist.R;
import com.david11n.medicationlist.data.DatabaseDescription.Medication;

public class MedicationListContentProvider extends ContentProvider {
   // used to access the database
   private MedicationListDatabaseHelper dbHelper;

   // UriMatcher helps ContentProvider determine operation to perform
   private static final UriMatcher uriMatcher =
      new UriMatcher(UriMatcher.NO_MATCH);

   // constants used with UriMatcher to determine operation to perform
   private static final int ONE_MEDICATION = 1; // manipulate one contact
   private static final int MEDICATIONS = 2; // manipulate contacts table

   // static block to configure this ContentProvider's UriMatcher
   static {
      // Uri for Contact with the specified id (#)
      uriMatcher.addURI(DatabaseDescription.AUTHORITY,
              Medication.TABLE_NAME + "/#", ONE_MEDICATION);

      // Uri for Medicaiton table
      uriMatcher.addURI(DatabaseDescription.AUTHORITY,
         Medication.TABLE_NAME, MEDICATIONS);
   }

   // called when the MedicationListContentProvider is created
   @Override
   public boolean onCreate() {
      // create the MedicationListDatabaseHelper
      dbHelper = new MedicationListDatabaseHelper(getContext());
      return true; // ContentProvider successfully created
   }

   // required method: Not used in this app, so we return null
   @Override
   public String getType(Uri uri) {
      return null;
   }

   // query the database
   @Override
   public Cursor query(Uri uri, String[] projection,
      String selection, String[] selectionArgs, String sortOrder) {

      // create SQLiteQueryBuilder for querying contacts table
      SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
      queryBuilder.setTables(Medication.TABLE_NAME);

      switch (uriMatcher.match(uri)) {
         case ONE_MEDICATION: // contact with specified id will be selected
            queryBuilder.appendWhere(
                    Medication._ID + "=" + uri.getLastPathSegment());
            break;
         case MEDICATIONS: // all contacts will be selected
            break;
         default:
            throw new UnsupportedOperationException(
               getContext().getString(R.string.invalid_query_uri) + uri);
      }

      // execute the query to select one or all contacts
      Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
         projection, selection, selectionArgs, null, null, sortOrder);

      // configure to watch for content changes
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
      return cursor;
   }

   // insert a new contact in the database
   @Override
   public Uri insert(Uri uri, ContentValues values) {
      Uri newContactUri = null;

      switch (uriMatcher.match(uri)) {
         case MEDICATIONS:
            // insert the new contact--success yields new contact's row id
            long rowId = dbHelper.getWritableDatabase().insert(
                    Medication.TABLE_NAME, null, values);

            // if the contact was inserted, create an appropriate Uri;
            // otherwise, throw an exception
            if (rowId > 0) { // SQLite row IDs start at 1
               newContactUri = Medication.buildContactUri(rowId);

               // notify observers that the database changed
               getContext().getContentResolver().notifyChange(uri, null);
            }
            else
               throw new SQLException(
                  getContext().getString(R.string.insert_failed) + uri);
            break;
         default:
            throw new UnsupportedOperationException(
               getContext().getString(R.string.invalid_insert_uri) + uri);
      }

      return newContactUri;
   }

   // update an existing contact in the database
   @Override
   public int update(Uri uri, ContentValues values,
      String selection, String[] selectionArgs) {
      int numberOfRowsUpdated; // 1 if update successful; 0 otherwise

      switch (uriMatcher.match(uri)) {
         case ONE_MEDICATION:
            // get from the uri the id of contact to update
            String id = uri.getLastPathSegment();

            // update the contact
            numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                    Medication.TABLE_NAME, values, Medication._ID + "=" + id,
               selectionArgs);
            break;
         default:
            throw new UnsupportedOperationException(
               getContext().getString(R.string.invalid_update_uri) + uri);
      }

      // if changes were made, notify observers that the database changed
      if (numberOfRowsUpdated != 0) {
         getContext().getContentResolver().notifyChange(uri, null);
      }

      return numberOfRowsUpdated;
   }

   // delete an existing contact from the database
   @Override
   public int delete(Uri uri, String selection, String[] selectionArgs) {
      int numberOfRowsDeleted;

      switch (uriMatcher.match(uri)) {
         case ONE_MEDICATION:
            // get from the uri the id of contact to update
            String id = uri.getLastPathSegment();

            // delete the contact
            numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                    DatabaseDescription.Medication.TABLE_NAME, DatabaseDescription.Medication._ID + "=" + id, selectionArgs);
            break;
         default:
            throw new UnsupportedOperationException(
               getContext().getString(R.string.invalid_delete_uri) + uri);
      }

      // notify observers that the database changed
      if (numberOfRowsDeleted != 0) {
         getContext().getContentResolver().notifyChange(uri, null);
      }

      return numberOfRowsDeleted;
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
