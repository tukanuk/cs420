package ouda.dynamiclayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Abdelnasser on 3/24/2018.
 */

public class Fragment_2 extends Fragment {
    //FragmentActivity listener;
    String specialText;
    public String getSpecialText ()
    {
        return specialText;
    }

    public void setSpecialText (String specialText)
    {
        this.specialText = specialText;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_2, parent,
                false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_2_toolbar);
        toolbar.setTitle("Fragment 2 ..... ");
        toolbar.inflateMenu(R.menu.fragment_2_menu);

        TextView textView= (TextView) view.findViewById (R.id.textView2);
        textView.setText (specialText);

        return view;//inflater.inflate(R.layout.fragment_1, parent, false);
        //return inflater.inflate(R.layout.fragment_2, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etF2 = (EditText) view.findViewById(R.id.etF2);
        // -------
    }
}
