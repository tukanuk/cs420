// MainActivity.java
// Splash Screen for app
package ca.uwindsor.david11n.a104924033pricecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;  // SeekBar Listener
import android.widget.TextView;
import android.text.TextWatcher;                        // EditText listener
import android.text.Editable;

import java.text.NumberFormat;

// MainActivity class for the Tip Calculator app
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
    public void enterCalculatorActivity(View activity_main)  {
        Intent calculatorIntent = new Intent(this, CalculatorActivity.class);
        startActivity(calculatorIntent);
    }

    // Button to exit the app
    public void exitApp(View activity_main) {
        finish();
        System.exit(0);
    }

}
