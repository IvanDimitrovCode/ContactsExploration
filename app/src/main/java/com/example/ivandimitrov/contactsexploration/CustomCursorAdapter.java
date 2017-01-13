package com.example.ivandimitrov.contactsexploration;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ivan Dimitrov on 1/11/2017.
 */

public class CustomCursorAdapter extends SimpleCursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context        mContext;
    private int            mLayout;

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.mContext = context;
        this.mLayout = layout;
        mLayoutInflater = LayoutInflater.from(context);
    }


//    public CustomCursorAdapter(Context context, Cursor c, int flags) {
//        super(context, c, flags);
//        mContext = context;
//        mLayoutInflater = LayoutInflater.from(context);
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vView = mLayoutInflater.inflate(mLayout, parent, false);
        vView.setTag(new ViewHolder(vView));
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor c) {
//        int iCol_Text = c.getColumnIndex(DBCOL_TEXT);

    }

    private class ViewHolder {
        TextView  textView;
        ImageView imageView;

        ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.contactName);
            imageView = (ImageView) v.findViewById(R.id.contactImage);
        }
    }
}
