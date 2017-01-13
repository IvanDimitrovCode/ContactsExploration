package com.example.ivandimitrov.contactsexploration;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final  int CONTACT_LOADER_ID                    = 78;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private AdapterView.OnItemClickListener listener;
    private ContactsLoader                  mContactsLoader;
    private SimpleCursorAdapter             mAdapter;
    private Activity                        mActivity;
    private boolean isRunning = false;
    // Defines the asynchronous callback for the contacts data loader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkForPermission()) {
            Log.d("asd", "asd");
            askPermission();
        } else {
            runActivity();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRunning) {
            mContactsLoader.clearResources();
        }
    }

    private void runActivity() {
        isRunning = true;
        mActivity = this;
        setupCursorAdapter();
        mContactsLoader = new ContactsLoader(mAdapter, this, null);
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), mContactsLoader);

        listener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long rowID) {
                ContactNode currentContact = new ContactNode();
                Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
                cursor.moveToPosition(position);


                int idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(idx);

                idx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String id = cursor.getString(idx);

                currentContact.addMobileNumber(getContactNumbers(id));
                currentContact.setName(name);

                Intent myIntent = new Intent(mActivity, ContactInfoActivity.class);
                myIntent.putExtra("MobileNumbers", currentContact.getMobileList());
                myIntent.putExtra("ContactName", currentContact.getName());
                myIntent.putExtra("ContactUri", getPhotoUri(id));

                startActivity(myIntent);
            }
        };

        ListView contacts = (ListView) findViewById(R.id.lvContacts);
        contacts.setAdapter(mAdapter);
        contacts.setOnItemClickListener(listener);
    }

    private ArrayList<String> getContactNumbers(String id) {
        ArrayList<String> list = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
        phones.moveToFirst();
        do {
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
            switch (type) {
                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                    list.add(number + " (Home Number)");
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    list.add(number + " (Mobile Number)");
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                    list.add(number + " (Work Number)");
                    break;
            }
        } while (phones.moveToNext());
        phones.close();
        return list;
    }

    public Uri getPhotoUri(String id) {
        ContentResolver cr = getContentResolver();

        try {
            Cursor cur = cr.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }


    private void setupCursorAdapter() {
        String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};
        int[] uiBindTo = {R.id.contact_name, R.id.contact_image};

        mAdapter = new SimpleCursorAdapter(
                this, R.layout.list_element,
                null, uiBindFrom, uiBindTo,
                0);
    }

    private void askPermission() {
        Log.d("PERMISSION", "a");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    private boolean checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runActivity();
                }
            }
        }
    }
}
