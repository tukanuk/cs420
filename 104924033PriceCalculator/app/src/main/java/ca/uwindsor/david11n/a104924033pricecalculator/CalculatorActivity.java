// CalculateActivity.java
// Calculates a bill total based on tip percentage
package ca.uwindsor.david11n.a104924033pricecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;  // SeekBar Listener
import android.widget.TextView;
import android.text.TextWatcher;                        // EditText listener
import android.text.Editable;

import java.text.NumberFormat;

// MainActivity class for the Tip Calculator app
public class CalculatorActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;    // bill amount entered by the user
    private double taxPercent = 0.13;   // initial tax percentage
    private double shipPercent = 0.10;  // initial ship percentage
    private TextView amountTextView;    // shows formatted bill amount
    private TextView taxPercentTextView;       // shows tax percentage
    private TextView shipPercentTextView;      // shows ship percentage
    private TextView taxTextView;       // shows calcualted tax amount
    private TextView shipTextView;      // shows calcualted ship amount
    private TextView totalTextView;     // shows calculated total bill amount

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         // call superclass' version
        setContentView(R.layout.activity_calculator);     // inflate the GUI

        // get references to programmatically manipulated textViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        taxPercentTextView = (TextView) findViewById(R.id.taxPercentTextView);
        shipPercentTextView = (TextView) findViewById(R.id.shipPercentTextView);
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        shipTextView = (TextView) findViewById(R.id.shipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        taxTextView.setText(currencyFormat.format(0));                  // set text to 0
        shipTextView.setText(currencyFormat.format(0));                 // set text to 0
        totalTextView.setText(currencyFormat.format(0));                // set text to 0

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set taxPercentSeekBar's OnSeekBarChangeListener
        SeekBar taxPercentSeekBar = (SeekBar) findViewById(R.id.taxPercentSeekBar);
        taxPercentSeekBar.setOnSeekBarChangeListener(taxSeekBarChangeListener);

        // set shipPercentSeekBar's OnSeekBarChangeListener
        SeekBar shipPercentSeekBar = (SeekBar) findViewById(R.id.shipPercentSeekBar);
        shipPercentSeekBar.setOnSeekBarChangeListener(shipSeekBarChangeListener);
    }

    //  calculate and display tax, ship and total amounts
    private void calculate() {
        // format percent and display in taxPercentTextView
        taxPercentTextView.setText(percentFormat.format(taxPercent));

        // format percent and display in shipPercentTextView
        shipPercentTextView.setText(percentFormat.format(shipPercent));

        // calculate the tax, ship and total
        double tax = billAmount * taxPercent;

        double ship = billAmount * shipPercent;
        if (ship < 5 && billAmount > 0) ship = 5;                 // $5 minimum shipping charge

        double total = billAmount + tax + ship;

        // display tax, ship and total formatted as currency
        taxTextView.setText(currencyFormat.format(tax));
        shipTextView.setText(currencyFormat.format(ship));
        totalTextView.setText(currencyFormat.format(total));
    }

    // listen object for the taxPercentSeekBar's progress changed events
    private final OnSeekBarChangeListener taxSeekBarChangeListener = new OnSeekBarChangeListener() {
        // update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            taxPercent = progress / 100.0;     // set percent based on progress
            calculate();                    // calculate and display the tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // listen object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener shipSeekBarChangeListener = new OnSeekBarChangeListener() {
        // update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            shipPercent = progress / 100.0;     // set percent based on progress
            calculate();                    // calculate and display the tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // listener object for the amountEditText text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try { // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
};
