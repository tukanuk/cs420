package ouda.dynamiclayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
    private Button createFragment1, createFragment2, createFragment3, createFragment4, createFragment5;
    private Button removeFragment1, removeFragment2, removeFragment3, removeFragment4, removeFragment5;

    Fragment_1 myFragment1;  // = new Fragment_1 ();
    Fragment_2 myFragment2;  // = new Fragment_2 ();
    Fragment_3 myFragment3;  // = new Fragment_3 ();
    Fragment_4 myFragment4;  // = new Fragment_4 ();
    Fragment_5 myFragment5;  // = new Fragment_4 ();


    int[] FragmentList = new int [5];
    //<MyFragment> myFragList = new ArrayList<MyFragment> ();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment1 = (Button) findViewById(R.id.createFragment1);
        createFragment2 = (Button) findViewById(R.id.createFragment2);
        createFragment3 = (Button) findViewById(R.id.createFragment3);
        createFragment4 = (Button) findViewById(R.id.createFragment4);
        createFragment5 = (Button) findViewById(R.id.createFragment5);
        removeFragment1 = (Button) findViewById(R.id.removeFragment1);
        removeFragment2 = (Button) findViewById(R.id.removeFragment2);
        removeFragment3 = (Button) findViewById(R.id.removeFragment3);
        removeFragment4 = (Button) findViewById(R.id.removeFragment4);
        removeFragment5 = (Button) findViewById(R.id.removeFragment5);


        createFragment1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((FragmentList[0]) == 0) {
                    // Begin the transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // determine which Fragment to create
                    myFragment1 = new Fragment_1();
                    myFragment1.setSpecialText("Fragment time:  " + System.currentTimeMillis());
                    fragmentTransaction.add(R.id.myFrame, myFragment1);
                    fragmentTransaction.addToBackStack("myFragment1");
                    fragmentTransaction.commit();
                    FragmentList[0] = 1;
                }
            }
        });

        createFragment2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((FragmentList[1]) == 0) {
                    // Begin the transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // determine which Fragment to create
                    myFragment2 = new Fragment_2();
                    myFragment2.setSpecialText("Fragment time:  " + System.currentTimeMillis());
                    fragmentTransaction.add(R.id.myFrame, myFragment2);
                    fragmentTransaction.addToBackStack("myFragment2");
                    fragmentTransaction.commit();
                    FragmentList[1] = 1;
                }
            }
        });

        createFragment3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((FragmentList[2]) == 0) {
                    // Begin the transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // determine which Fragment to create
                    myFragment3 = new Fragment_3();
                    myFragment3.setSpecialText("Fragment time:  " + System.currentTimeMillis());
                    fragmentTransaction.add(R.id.myFrame, myFragment3);
                    fragmentTransaction.addToBackStack("myFragment3");
                    fragmentTransaction.commit();
                    FragmentList[2] = 1;
                }
            }
        });

        createFragment4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((FragmentList[3]) == 0) {
                    // Begin the transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // determine which Fragment to create
                    myFragment4 = new Fragment_4();
                    myFragment4.setSpecialText("Fragment time:  " + System.currentTimeMillis());
                    fragmentTransaction.add(R.id.myFrame, myFragment4);
                    fragmentTransaction.addToBackStack("myFragment4");
                    fragmentTransaction.commit();
                    FragmentList[3] = 1;
                }
            }
        });

        createFragment5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((FragmentList[4]) == 0) {
                    // Begin the transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // determine which Fragment to create
                    myFragment5 = new Fragment_5();
                    myFragment5.setSpecialText("Fragment time:  " + System.currentTimeMillis());
                    fragmentTransaction.add(R.id.myFrame, myFragment5);
                    fragmentTransaction.addToBackStack("myFragment5");
                    fragmentTransaction.commit();
                    FragmentList[4] = 1;
                }
            }
        });

        removeFragment1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((FragmentList[0]) == 1)  {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.remove (myFragment1);
                    fragmentTransaction.commit ();
                    FragmentList[0] = 0;
                }
            }
        });

        removeFragment2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((FragmentList[1]) == 1)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.remove (myFragment2);
                    fragmentTransaction.commit ();
                    FragmentList[1] = 0;
                }
            }
        });

        removeFragment3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((FragmentList[2]) == 1)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.remove (myFragment3);
                    fragmentTransaction.commit ();
                    FragmentList[2] = 0;
                }
            }
        });

        removeFragment4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((FragmentList[3]) == 1)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.remove (myFragment4);
                    fragmentTransaction.commit ();
                    FragmentList[3] = 0;
                }
            }
        });

        removeFragment5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Begin the transaction
                if ((FragmentList[4]) == 1)
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                    fragmentTransaction.remove (myFragment5);
                    fragmentTransaction.commit ();
                    FragmentList[4] = 0;
                }
            }
        });

        /*
         createFragment.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                 // Begin the transaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // determine which Fragment to create

                switch (dyFragmentcount)
                {
                    case 1:
                        myFragment1 = new Fragment_1 ();
                        fragmentTransaction.add (R.id.myFrame, myFragment1);
                        fragmentTransaction.addToBackStack ("myFragment1");
                        fragmentTransaction.commit ();
                        break;
                    case 2:
                        myFragment2 = new Fragment_2 ();
                        fragmentTransaction.add (R.id.myFrame, myFragment2);
                        fragmentTransaction.addToBackStack ("myFragment2");
                        fragmentTransaction.commit ();
                        break;
                    case 3:
                        myFragment3 = new Fragment_3 ();
                        fragmentTransaction.add (R.id.myFrame, myFragment3);
                        fragmentTransaction.addToBackStack ("myFragment3");
                        fragmentTransaction.commit ();
                        break;
                    case 4:
                        myFragment4 = new Fragment_4 ();
                        fragmentTransaction.add (R.id.myFrame, myFragment4);
                        fragmentTransaction.addToBackStack ("myFragment4");
                        fragmentTransaction.commit ();
                        break;
                    // Replace the contents of the container with the new fragment
                }
                if (dyFragmentcount >= 3)
                    dyFragmentcount = dyFragmentcount+1;
            }
        });

       removeFragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Begin the transaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                // determine which Fragment to create
                switch (dyFragmentcount)
                {
                    case 1:
                        fragmentTransaction.remove (myFragment1);
                        fragmentTransaction.commit ();

                        break;
                    case 2:
                        fragmentTransaction.remove (myFragment2);
                        fragmentTransaction.commit ();

                        break;
                    case 3:
                        fragmentTransaction.remove (myFragment3);
                        fragmentTransaction.commit ();

                        break;
                    case 4:
                        fragmentTransaction.remove (myFragment4);
                        fragmentTransaction.commit ();

                        break;
                    // Replace the contents of the container with the new fragment
                }
                if (dyFragmentcount > 1)
                    dyFragmentcount = dyFragmentcount-1;

            }
        });
        */
    }
}
