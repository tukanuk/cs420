package ouda.dynamiclayout;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
//import android.app.FragmentManager;

/**
 * Created by Abdelnasser on 3/24/2018.
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLinear;
    private Button createTextview, createEdittext, createButton, createFragment, removeFragment, hideFragment;
    private int dyTextcount=1, dyEditTextcount =1 , dyButtoncount=1 , dyFragmentcount=1;


    MyFragment myFragment;

    List<MyFragment> myFragList = new ArrayList<MyFragment> ();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainLinear = (LinearLayout) findViewById(R.id.HolderLayout);
        createTextview = (Button) findViewById(R.id.CreateTextView);
        createEdittext = (Button) findViewById(R.id.CreateEditText);
        createButton = (Button) findViewById(R.id.CreateButton);
        createFragment = (Button) findViewById(R.id.CreateFragment);
        removeFragment = (Button) findViewById(R.id.RemoveFragment);
        hideFragment = (Button) findViewById(R.id.HideFragment);

        createTextview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Starting a new Intent
                TextView surveytitle = new TextView(MainActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                // param.setMargins(int left,int top , int right,int bottom)
                params.setMargins(20, 10, 20, 10);
                //  params.weight = 1.0f;
                params.gravity = Gravity.CENTER_HORIZONTAL;  /// this is layout gravity of textview
                surveytitle.setLayoutParams(params);
                surveytitle.setBackgroundColor(Color.parseColor("#3F51B5"));
                surveytitle.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                surveytitle.setTextColor(Color.parseColor("#ffffff"));
                surveytitle.setTypeface(null, Typeface.BOLD);
                surveytitle.setTextSize(18);
                surveytitle.setText("TextView "+ dyTextcount);
                dyTextcount= dyTextcount+1;
                mainLinear.addView(surveytitle);
            }
        });

        createEdittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Starting a new Intent
                EditText editTextbox = new EditText(MainActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                // param.setMargins(int left,int top , int right,int bottom)
                params.setMargins(20, 10, 20, 10);
                //  params.weight = 1.0f;
                params.gravity = Gravity.CENTER_HORIZONTAL;  /// this is layout gravity of edittextview
                editTextbox.setLayoutParams(params);
                editTextbox.setBackgroundColor(Color.parseColor("#3F51B5"));
                editTextbox.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                editTextbox.setTextColor(Color.parseColor("#ffffff"));
                editTextbox.setTypeface(null, Typeface.BOLD);
                editTextbox.setTextSize(18);
                editTextbox.setHint("Edittext "+ dyEditTextcount);
                editTextbox.setMinimumWidth(140);
                dyEditTextcount = dyEditTextcount+1;
                mainLinear.addView(editTextbox);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Starting a new Intent
                Button dyButton = new Button(MainActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                // param.setMargins(int left,int top , int right,int bottom)
                params.setMargins(20, 10, 20, 10);
                //  params.weight = 1.0f;
                params.gravity = Gravity.CENTER_HORIZONTAL;  /// this is layout gravity of button view
                dyButton.setLayoutParams(params);
                dyButton.setBackgroundColor(Color.parseColor("#3F51B5"));
                dyButton.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                dyButton.setTextColor(Color.parseColor("#ffffff"));
                dyButton.setTypeface(null, Typeface.BOLD);
                dyButton.setTextSize(18);
                dyButton.setText("Dynamic Button " + dyButtoncount);
                dyButton.setMinimumWidth(140);
                dyButtoncount = dyButtoncount+1;
                mainLinear.addView(dyButton);
            }
        });

        createFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Begin the transaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // determine which Fragment to create
                myFragment = new MyFragment ();
                myFragment.setSpecialText ("Fragment time:  " + System.currentTimeMillis ());
                myFragList.add (myFragment);
                fragmentTransaction.add (R.id.myFrame, myFragment);
                fragmentTransaction.addToBackStack ("myFragment");
                fragmentTransaction.commit ();
            }
        });


        removeFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((myFragList.size () - 1) >= 0)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    MyFragment lastFrag = myFragList.get (myFragList.size () - 1);
                    fragmentTransaction.remove (lastFrag);
                    myFragList.remove (lastFrag);
                    fragmentTransaction.commit ();
                }
            }
        });

        hideFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((myFragList.size () - 1) >= 0)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    MyFragment lastFrag = myFragList.get (myFragList.size () - 1);
                    fragmentTransaction.hide(lastFrag);
                    //myFragList.remove (lastFrag);
                    fragmentTransaction.commit ();
                }
            }
        });


    }

}
