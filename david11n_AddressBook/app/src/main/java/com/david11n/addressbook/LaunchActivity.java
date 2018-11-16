// MainActivity.java
// Splash Screen for app
package com.david11n.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.david11n.addressbook.data.DatabaseDescription;

// MainActivity class for the Address Book app
public class LaunchActivity extends AppCompatActivity {

    private Button enterButton;      // login button
    private Button enterButtonTemp;  // direct entry to main activity
    private Button exitButton;       // exit button

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);             // call superclass version
        setContentView(R.layout.activity_launch);       // inflate the GUI

        // get references to buttons
        enterButton = (Button) findViewById(R.id.enterButton);
        enterButton.setOnClickListener(loginButtonClicked);

        exitButton = (Button) findViewById(R.id.exitButton);

        enterButtonTemp = (Button) findViewById(R.id.enterButton2);

        // set userNameEditText TextWatcher
        EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        userNameEditText.addTextChangedListener(userNameEditTextWatcher);


        // set passwordEditText TextWatcher
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.addTextChangedListener(passwordEditTextWatcher);
    }


    // listener for userNameEditText
    private final TextWatcher userNameEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            username = s.toString();
            Log.d("MSG", username);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // listener for userNameEditText
    private final TextWatcher passwordEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            password = s.toString();
            Log.d("MSG", password);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // responds to event generated when user saves a contact
    private final View.OnClickListener loginButtonClicked = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveUser(); // check/save user to the database
                }
            };

    // checks/saves user information to the database
    private void saveUser() {
        // create ContentValues object containing contact's key-value pairs
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDescription.User.COLUMN_USER, username);
        contentValues.put(DatabaseDescription.User.COLUMN_PASSWORD, password);

        if (true) {
//            query();
        }

    }



    // Button to enter the MainActivity
    public void enterMainActivity(View activity_launch)  {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    // Button to exit the app
    public void exitApp(View activity_launch) {
        finish();
        System.exit(0);
    }

}
