// AddEditFragment.java
// Fragment for adding a new contact or editing an existing one
package com.david11n.medicationlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.david11n.medicationlist.data.DatabaseDescription.Medication;

import com.david11n.medicationlist.data.DatabaseDescription;

public class AddEditFragment extends Fragment
   implements LoaderManager.LoaderCallbacks<Cursor> {

   // defines callback method implemented by MainActivity
   public interface AddEditFragmentListener {
      // called when contact is saved
      void onAddEditCompleted(Uri contactUri);
   }

   // constant used to identify the Loader
   private static final int CONTACT_LOADER = 0;

   private AddEditFragmentListener listener; // MainActivity
   private Uri contactUri; // Uri of selected contact
   private boolean addingNewContact = true; // adding (true) or editing

   // EditTexts for contact information
   private TextInputLayout medicationNameTextInputLayout;
   private TextInputLayout medicationGenericTextInputLayout;
   private TextInputLayout dinTextInputLayout;
   private TextInputLayout doesTextInputLayout;
   private TextInputLayout timeOfDayTextInputLayout;
   private TextInputLayout physicianTextInputLayout;
   private TextInputLayout refillTextInputLayout;
   private FloatingActionButton saveContactFAB;

   private CoordinatorLayout coordinatorLayout; // used with SnackBars

   // set AddEditFragmentListener when Fragment attached
   @Override
   public void onAttach(Context context) {
      super.onAttach(context);
      listener = (AddEditFragmentListener) context;
   }

   // remove AddEditFragmentListener when Fragment detached
   @Override
   public void onDetach() {
      super.onDetach();
      listener = null;
   }

   // called when Fragment's view needs to be created
   @Override
   public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      setHasOptionsMenu(true); // fragment has menu items to display

      // inflate GUI and get references to EditTexts
      View view =
         inflater.inflate(R.layout.fragment_add_edit, container, false);
      medicationNameTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.nameTextInputLayout);
      medicationNameTextInputLayout.getEditText().addTextChangedListener(
         nameChangedListener);
      medicationGenericTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.phoneTextInputLayout);
      dinTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.emailTextInputLayout);
      doesTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.streetTextInputLayout);
      timeOfDayTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.cityTextInputLayout);
      physicianTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.provinceTextInputLayout);
      refillTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.pcodeTextInputLayout);

      // set FloatingActionButton's event listener
      saveContactFAB = (FloatingActionButton) view.findViewById(
         R.id.saveFloatingActionButton);
      saveContactFAB.setOnClickListener(saveContactButtonClicked);
      updateSaveButtonFAB();

      // used to display SnackBars with brief messages
      coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(
         R.id.coordinatorLayout);

      Bundle arguments = getArguments(); // null if creating new contact

      if (arguments != null) {
         addingNewContact = false;
         contactUri = arguments.getParcelable(MainActivity.CONTACT_URI);
      }

      // if editing an existing contact, create Loader to get the contact
      if (contactUri != null)
         getLoaderManager().initLoader(CONTACT_LOADER, null, this);

      return view;
   }



   // display this fragment's menu items
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.fragment_exit_menu, menu);
   }

   // handle menu item selections
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_exit:
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return true;
      }

      return super.onOptionsItemSelected(item);
   }



   // detects when the text in the nameTextInputLayout's EditText changes
   // to hide or show saveButtonFAB
   private final TextWatcher nameChangedListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) {}

      // called when the text in nameTextInputLayout changes
      @Override
      public void onTextChanged(CharSequence s, int start, int before,
         int count) {
         updateSaveButtonFAB();
      }

      @Override
      public void afterTextChanged(Editable s) { }
   };

   // shows saveButtonFAB only if the name is not empty
   private void updateSaveButtonFAB() {
      String input =
         medicationNameTextInputLayout.getEditText().getText().toString();

      // if there is a name for the contact, show the FloatingActionButton
      if (input.trim().length() != 0)
         saveContactFAB.show();
      else
         saveContactFAB.hide();
   }

   // responds to event generated when user saves a contact
   private final View.OnClickListener saveContactButtonClicked =
      new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // hide the virtual keyboard
            ((InputMethodManager) getActivity().getSystemService(
               Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
               getView().getWindowToken(), 0);
            saveContact(); // save contact to the database
         }
      };

   // saves contact information to the database
   private void saveContact() {
      // create ContentValues object containing contact's key-value pairs
      ContentValues contentValues = new ContentValues();
      contentValues.put(Medication.COLUMN_NAME_BRAND,
         medicationNameTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_NAME_GENERIC,
         medicationGenericTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_DIN,
         dinTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_DOES,
         doesTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_TIME_OF_DAY,
         timeOfDayTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_PHYSICIAN,
         physicianTextInputLayout.getEditText().getText().toString());
      contentValues.put(Medication.COLUMN_REFILL,
         refillTextInputLayout.getEditText().getText().toString());

      if (addingNewContact) {
         // use Activity's ContentResolver to invoke
         // insert on the MedicationListContentProvider
         Uri newContactUri = getActivity().getContentResolver().insert(
            Medication.CONTENT_URI, contentValues);

         if (newContactUri != null) {
            Snackbar.make(coordinatorLayout,
               R.string.contact_added, Snackbar.LENGTH_LONG).show();
            listener.onAddEditCompleted(newContactUri);
         }
         else {
            Snackbar.make(coordinatorLayout,
               R.string.contact_not_added, Snackbar.LENGTH_LONG).show();
         }
      }
      else {
         // use Activity's ContentResolver to invoke
         // insert on the MedicationListContentProvider
         int updatedRows = getActivity().getContentResolver().update(
            contactUri, contentValues, null, null);

         if (updatedRows > 0) {
            listener.onAddEditCompleted(contactUri);
            Snackbar.make(coordinatorLayout,
               R.string.contact_updated, Snackbar.LENGTH_LONG).show();
         }
         else {
            Snackbar.make(coordinatorLayout,
               R.string.contact_not_updated, Snackbar.LENGTH_LONG).show();
         }
      }
   }

   // called by LoaderManager to create a Loader
   @Override
   public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      // create an appropriate CursorLoader based on the id argument;
      // only one Loader in this fragment, so the switch is unnecessary
      switch (id) {
         case CONTACT_LOADER:
            return new CursorLoader(getActivity(),
               contactUri, // Uri of contact to display
               null, // null projection returns all columns
               null, // null selection returns all rows
               null, // no selection arguments
               null); // sort order
         default:
            return null;
      }
   }

   // called by LoaderManager when loading completes
   @Override
   public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      // if the contact exists in the database, display its data
      if (data != null && data.moveToFirst()) {
         // get the column index for each data item
         int nameBrandIndex = data.getColumnIndex(Medication.COLUMN_NAME_BRAND);
         int nameGenericIndex = data.getColumnIndex(Medication.COLUMN_NAME_GENERIC);
         int dinIndex = data.getColumnIndex(Medication.COLUMN_DIN);
         int doesIndex = data.getColumnIndex(Medication.COLUMN_DOES);
         int timeOfDayIndex = data.getColumnIndex(Medication.COLUMN_TIME_OF_DAY);
         int physicianIndex = data.getColumnIndex(Medication.COLUMN_PHYSICIAN);
         int refillIndex = data.getColumnIndex(Medication.COLUMN_REFILL);

         // fill EditTexts with the retrieved data
         medicationNameTextInputLayout.getEditText().setText(
            data.getString(nameBrandIndex));
         medicationGenericTextInputLayout.getEditText().setText(
            data.getString(nameGenericIndex));
         dinTextInputLayout.getEditText().setText(
            data.getString(dinIndex));
         doesTextInputLayout.getEditText().setText(
            data.getString(doesIndex));
         timeOfDayTextInputLayout.getEditText().setText(
            data.getString(timeOfDayIndex));
         physicianTextInputLayout.getEditText().setText(
            data.getString(physicianIndex));
         refillTextInputLayout.getEditText().setText(
            data.getString(refillIndex));

         updateSaveButtonFAB();
      }
   }

   // called by LoaderManager when the Loader is being reset
   @Override
   public void onLoaderReset(Loader<Cursor> loader) { }


   //public int getContactId() {
   //   return nameTextInputLayout.getEditText().getText().toString();)
   //}
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
