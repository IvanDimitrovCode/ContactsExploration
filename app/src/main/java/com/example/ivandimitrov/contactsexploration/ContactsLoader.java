package com.example.ivandimitrov.contactsexploration;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by Ivan Dimitrov on 1/11/2017.
 */

public class ContactsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    private Activity            activity;
    private Uri                 uri;

    public ContactsLoader(SimpleCursorAdapter adapter, Activity activity, Uri uri) {
        this.adapter = adapter;
        this.activity = activity;
        this.uri = uri;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define the columns to retrieve
        String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};
        CursorLoader cursorLoader;
        if (uri == null) {
            // Construct the loader
            cursorLoader = new CursorLoader(activity,
                    ContactsContract.Contacts.CONTENT_URI, // URI
                    projectionFields, // projection fields
                    null, // the selection criteria
                    null, // the selection args
                    null // the sort order
            );
        } else {
            // Construct the loader
            cursorLoader = new CursorLoader(activity,
                    uri, // URI
                    projectionFields, // projection fields
                    null, // the selection criteria
                    null, // the selection args
                    null // the sort order
            );
        }

        // Return the loader for use
        return cursorLoader;
    }

    // When the system finishes retrieving the Cursor through the CursorLoader,
    // a call to the onLoadFinished() method takes place.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // The swapCursor() method assigns the new Cursor to the adapter
        adapter.swapCursor(cursor);
    }

    // This method is triggered when the loader is being reset
    // and the loader data is no longer available. Called if the data
    // in the provider changes and the Cursor becomes stale.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Clear the Cursor we were using with another call to the swapCursor()
        adapter.swapCursor(null);
    }

    public void clearResources() {
        activity = null;
    }
}
