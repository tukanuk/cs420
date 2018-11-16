// DatabaseDescription.java
// Describes the table name and column names for this app's database,
// and other information required by the ContentProvider
package com.david11n.medicationlist.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
   // ContentProvider's name: typically the package name
   public static final String AUTHORITY =
      "com.david11n.medicationlist.data";

   // base URI used to interact with the ContentProvider
   private static final Uri BASE_CONTENT_URI =
      Uri.parse("content://" + AUTHORITY);

   // nested class defines contents of the contacts table
   public static final class Medication implements BaseColumns {
      public static final String TABLE_NAME = "medication"; // table's name

      // Uri for the contacts table
      public static final Uri CONTENT_URI =
         BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

      // column names for contacts table's columns
      public static final String COLUMN_NAME_BRAND = "name_brand";
      public static final String COLUMN_NAME_GENERIC = "name_generic";
      public static final String COLUMN_DIN = "din";
      public static final String COLUMN_DOES = "does";
      public static final String COLUMN_TIME_OF_DAY = "time_of_day";
      public static final String COLUMN_PHYSICIAN = "physician";
      public static final String COLUMN_REFILL = "refill_date";

      // creates a Uri for a specific medication
      public static Uri buildContactUri(long id) {
         return ContentUris.withAppendedId(CONTENT_URI, id);
      }
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
