package com.example.ivandimitrov.contactsexploration;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ivan Dimitrov on 1/12/2017.
 */

public class ContactInfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Bundle bundle = getIntent().getExtras();

        ArrayList<String> phoneNumbers = bundle.getStringArrayList("MobileNumbers");
        String name = bundle.getString("ContactName");
        Uri uri = bundle.getParcelable("ContactUri");

        TextView nameTextView = (TextView) findViewById(R.id.contactName);
        TextView phoneNumbersTextView = (TextView) findViewById(R.id.contactPhoneNumbers);
        ImageView contactImage = (ImageView) findViewById(R.id.contactImage);

        nameTextView.setText(name);

        String phoneNumbersData = "";
        for (int i = 0; i < phoneNumbers.size(); i++) {
            phoneNumbersData = phoneNumbersData + phoneNumbers.get(i) + "\n";
        }
        phoneNumbersTextView.setText(phoneNumbersData);

        contactImage.setImageURI(uri);

    }
}
