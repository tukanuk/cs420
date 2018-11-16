// MainActivity.java
// Splash Screen for app
package com.david11n.memoryGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// MainActivity class for the Memory Game app
public class MainActivity extends AppCompatActivity {

    private Button enterButton;     // enter button
    private Button exitButton;      // exit button

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);             // call superclass version
        setContentView(R.layout.activity_main);         // inflate the GUI

        // get references to buttons
        enterButton = (Button) findViewById(R.id.enterButton);
        exitButton = (Button) findViewById(R.id.exitButton);

    }

    // Button to enter the CalculatorActivity
    public void enterMemoryGameActivity(View activity_main)  {
        Intent memoryGameIntent = new Intent(this, MemoryGameActivity.class);
        startActivity(memoryGameIntent);
    }

    // Button to exit the app
    public void exitApp(View activity_main) {
        finish();
        System.exit(0);
    }

}
