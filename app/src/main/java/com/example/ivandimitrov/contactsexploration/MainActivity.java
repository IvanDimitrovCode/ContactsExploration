package com.example.ivandimitrov.contactsexploration;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public static final int CONTACT_LOADER_ID = 78;

    private AdapterView.OnItemClickListener listener;
    private ContactsLoader                  mContactsLoader;
    private SimpleCursorAdapter             mAdapter;
    // Defines the asynchronous callback for the contacts data loader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCursorAdapter();
        mContactsLoader = new ContactsLoader(mAdapter, this);
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(), mContactsLoader);

        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        lvContacts.setAdapter(mAdapter);
//        lvContacts.setOnClickListener(list);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mContactsLoader.clearResources();
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


}
