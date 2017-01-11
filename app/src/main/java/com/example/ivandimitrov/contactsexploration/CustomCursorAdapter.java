package com.example.ivandimitrov.contactsexploration;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ivan Dimitrov on 1/11/2017.
 */

public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;

    public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.list_element, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
