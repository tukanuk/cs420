// MainActivity.java
// Sets MainActivity's layout
package com.david11n.art420;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
   // configures the screen orientation for this app

   private ImageButton exitButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      // determine screen size
      int screenSize =
         getResources().getConfiguration().screenLayout &
            Configuration.SCREENLAYOUT_SIZE_MASK;

      // use landscape for extra large tablets; otherwise, use portrait
      if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
         setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      else
         setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      exitButton = findViewById(R.id.exitButton);
      exitButton.setOnClickListener(exitListener);
   }


   // called when the Exit Button is touched
   private View.OnClickListener exitListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         System.exit(1);

//         Button eButton = ( (Button) v);
//         String label = eButton.getText().toString();
//
//         if (label.equals("Exit"))
//         {
//            System.exit(1);
//         }
      }

   };

}

