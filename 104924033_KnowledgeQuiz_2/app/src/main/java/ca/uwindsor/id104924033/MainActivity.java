package ca.uwindsor.id104924033;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
   // keys for reading data from SharedPreferences
   public static final String CHOICES = "pref_numberOfChoices";
   public static final String GROUPS = "pref_elementGroupsToInclude";

   private boolean phoneDevice = true; // used to force portrait mode
   private boolean preferencesChanged = true; // did preferences change?

   // configure the MainActivity
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      // set default values in the app's SharedPreferences
      PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

      // determine screen size
      int screenSize = getResources().getConfiguration().screenLayout &
         Configuration.SCREENLAYOUT_SIZE_MASK;

      // if device is a tablet, set phoneDevice to false
      if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
         screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
         phoneDevice = false; // not a phone-sized device

      // if running on phone-sized device, allow only portrait orientation
      if (phoneDevice)
         setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
   }

   // called after onCreate completes execution
   @Override
   protected void onStart() {
      super.onStart();
   }
}


/*************************************************************************
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
 *************************************************************************/
