package com.ouda.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ouda.addressbook.data.AddressBookDatabaseHelper;

public class UserActivity extends AppCompatActivity {

    EditText userNameTextView, userPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userNameTextView = (EditText) findViewById(R.id.userName);
        userPasswordTextView = (EditText) findViewById(R.id.userPassword);
    }

    public void exitApp (View view)
    {
        //Closes the app
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void login (View view)
    {
        String username = userNameTextView.getText().toString();
        String userpassword= userPasswordTextView.getText().toString();

        AddressBookDatabaseHelper dbHelper = new AddressBookDatabaseHelper(this);

        if(!dbHelper.userExists(username))
        {
            dbHelper.addUser(username, userpassword);
            Toast.makeText(this, "New user "+username +" has been added.", Toast.LENGTH_LONG).show();
            MainActivity.USERNAME = username;
            Intent addBook = new Intent (this, MainActivity.class);
            //System.out.println(" at login in UserActivity username = "+username);
            startActivity(addBook);
        }
         else
        {
            if (dbHelper.Authenticate(username, userpassword))

            {
                Intent addBook = new Intent (this, MainActivity.class);
                MainActivity.USERNAME = username;
                startActivity(addBook);
            }
            else
            {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show();
            }
        }

    }
}
