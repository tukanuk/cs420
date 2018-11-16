// EraseImageDialogFragment.java
// Allows user to erase image
package com.david11n.art420;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment; //******************************
import android.content.DialogInterface;
import android.os.Bundle;

// class for the Erase Image dialog
public class EraseImageDialogFragment extends DialogFragment {
   // create an AlertDialog and return it
   @Override
   public Dialog onCreateDialog(Bundle bundle) {
      AlertDialog.Builder builder =
         new AlertDialog.Builder(getActivity());

      // set the AlertDialog's message
      builder.setMessage(R.string.message_erase);

      // add Erase Button
      builder.setPositiveButton(R.string.button_erase,
         new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               getArt420Fragment().getDavid11nArt().clear(); // clear image
            }
         }
      );

      // add cancel Button
      builder.setNegativeButton(android.R.string.cancel, null);
      return builder.create(); // return dialog
   }

   // gets a reference to the MainActivityFragment
   private MainActivityFragment getArt420Fragment() {
      return (MainActivityFragment) getFragmentManager().findFragmentById(
         R.id.art240Fragment);
   }

  /*
   // tell MainActivityFragment that dialog is now displayed

   @Override
   public void onAttach(Activity activity) {
      super.onAttach(activity);
      MainActivityFragment fragment = getArt420Fragment();

      if (fragment != null)
         fragment.setDialogOnScreen(true);
   }
*/
   // tell MainActivityFragment that dialog is no longer displayed
   @Override
   public void onDetach() {
      super.onDetach();
      MainActivityFragment fragment = getArt420Fragment();

      if (fragment != null)
         fragment.setDialogOnScreen(false);
   }
}

