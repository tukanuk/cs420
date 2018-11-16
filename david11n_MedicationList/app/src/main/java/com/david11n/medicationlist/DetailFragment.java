// DetailFragment.java
// Fragment subclass that displays one contact's details
package com.david11n.medicationlist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.david11n.medicationlist.data.DatabaseDescription.Medication;

public class DetailFragment extends Fragment
   implements LoaderManager.LoaderCallbacks<Cursor> {

   // callback methods implemented by MainActivity
   public interface DetailFragmentListener {
      void onContactDeleted(); // called when a contact is deleted

      // pass Uri of contact to edit to the DetailFragmentListener
      void onEditContact(Uri contactUri);
   }

   private static final int CONTACT_LOADER = 0; // identifies the Loader

   public static DetailFragmentListener listener; // MainActivity
   private Uri contactUri; // Uri of selected contact

   private TextView nameBrandTextView; // displays contact's name
   private TextView nameGenericTextView; // displays contact's phone
   private TextView dinTextView; // displays contact's email
   private TextView doesTextView; // displays contact's street
   private TextView timeOfDayTextView; // displays contact's city
   private TextView physicianTextView; // displays contact's province
   private TextView refillTextView; // displays contact's pcode

   // set DetailFragmentListener when fragment attached
   @Override
   public void onAttach(Context context) {
      super.onAttach(context);
      listener = (DetailFragmentListener) context;
   }

   // remove DetailFragmentListener when fragment detached
   @Override
   public void onDetach() {
      super.onDetach();
      listener = null;
   }

   // called when DetailFragmentListener's view needs to be created
   @Override
   public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      setHasOptionsMenu(true); // this fragment has menu items to display

      // get Bundle of arguments then extract the contact's Uri
      Bundle arguments = getArguments();

      if (arguments != null)
         contactUri = arguments.getParcelable(MainActivity.CONTACT_URI);

      // inflate DetailFragment's layout
      View view =
         inflater.inflate(R.layout.fragment_detail, container, false);

      // get the EditTexts
      nameBrandTextView = (TextView) view.findViewById(R.id.nameTextView);
      nameGenericTextView = (TextView) view.findViewById(R.id.phoneTextView);
      dinTextView = (TextView) view.findViewById(R.id.emailTextView);
      doesTextView = (TextView) view.findViewById(R.id.streetTextView);
      timeOfDayTextView = (TextView) view.findViewById(R.id.cityTextView);
      physicianTextView = (TextView) view.findViewById(R.id.provinceTextView);
      refillTextView = (TextView) view.findViewById(R.id.pcodeTextView);

      // load the contact
      getLoaderManager().initLoader(CONTACT_LOADER, null, this);
      return view;
   }

   // display this fragment's menu items
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.menu.fragment_details_menu, menu);
   }

   // handle menu item selections
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_edit:
            listener.onEditContact(contactUri); // pass Uri to listener
            return true;
         case R.id.action_delete:
            deleteContact();
            return true;
         case R.id.action_exit:
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return true;
      }

      return super.onOptionsItemSelected(item);
   }

   // delete a contact
   private void deleteContact() {
      // use FragmentManager to display the confirmDelete DialogFragment
/*      confirmDelete.show(getFragmentManager(), "confirm delete");
 //  }

   // DialogFragment to confirm deletion of contact
   private final DialogFragment confirmDelete =
      new DialogFragment() {
         // create an AlertDialog and return it
         @Override
         public Dialog onCreateDialog(Bundle bundle) {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder =
               new AlertDialog.Builder(getActivity());

            builder.setTitle(R.string.confirm_title);
            builder.setMessage(R.string.confirm_message);

            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.button_delete,
               new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(
                     DialogInterface dialog, int button) {

                     // use Activity's ContentResolver to invoke
                     // delete on the MedicationListContentProvider
                     getActivity().getContentResolver().delete(
                        contactUri, null, null);
                     listener.onContactDeleted(); // notify listener
                  }
               }
            );

            builder.setNegativeButton(R.string.button_cancel, null);
            return builder.create(); // return the AlertDialog
         }
      };
*/
      // DeleteFragment to confirm deletion of contact
      DeleteFragment confirmDelete = new DeleteFragment();
      Bundle arguments = new Bundle();
      arguments.putParcelable("CONTACT_URI", contactUri);
      confirmDelete.setArguments(arguments);

      confirmDelete.show(getFragmentManager(), "Confirm Delete Contact");
   }

   // called by LoaderManager to create a Loader
   @Override
   public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      // create an appropriate CursorLoader based on the id argument;
      // only one Loader in this fragment, so the switch is unnecessary
      CursorLoader cursorLoader;

      switch (id) {
         case CONTACT_LOADER:
            cursorLoader = new CursorLoader(getActivity(),
               contactUri, // Uri of contact to display
               null, // null projection returns all columns
               null, // null selection returns all rows
               null, // no selection arguments
               null); // sort order
            break;
         default:
            cursorLoader = null;
            break;
      }

      return cursorLoader;
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

         // fill TextViews with the retrieved data
         nameBrandTextView.setText(data.getString(nameBrandIndex));
         nameGenericTextView.setText(data.getString(nameGenericIndex));
         dinTextView.setText(data.getString(dinIndex));
         doesTextView.setText(data.getString(doesIndex));
         timeOfDayTextView.setText(data.getString(timeOfDayIndex));
         physicianTextView.setText(data.getString(physicianIndex));
         refillTextView.setText(data.getString(refillIndex));
      }
   }

   // called by LoaderManager when the Loader is being reset
   @Override
   public void onLoaderReset(Loader<Cursor> loader) { }
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
