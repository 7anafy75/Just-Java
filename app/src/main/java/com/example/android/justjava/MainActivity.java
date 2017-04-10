package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox ChoclateCheckBox = (CheckBox) findViewById(R.id.Choclate_checkbox);
        boolean hasChoclate = ChoclateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChoclate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChoclate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);


    }


    /**
     * Calculates the price of the order.
     *
     * @return total price is the number of cups of coffee ordered
     */


    private int calculatePrice(boolean addWhippedCream, boolean addChoclate) {

        int basePrice = 5; //price of 1 cup

        if (addWhippedCream) {        // add 1$ for whippedCream

            basePrice = basePrice + 1;
        }

        if (addChoclate) {         //add 2$ for Chocolate
            basePrice = basePrice + 2;
        }


        return quantity * basePrice;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChoclate, String name) {
        String priceMessage  =   getString(R.string.order_summary_name, name);
        priceMessage += "\n" +  getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" +  getString(R.string.order_summary_chocolate, addChoclate);
        priceMessage += "\n" +  getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" +  getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" +  getString(R.string.thank_you);
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     */


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}