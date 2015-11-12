package com.photojoints.multiplecontactpicker;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MultipleContactPickerActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public ArrayList<Contact> contacts = new ArrayList<Contact>();

    private RecyclerView contactsView;
    private RecyclerView.Adapter contactsAdapter;
    private RecyclerView.LayoutManager contactsLayoutManager;

    private static final String[] PROJECTION =
            {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,

            };

    private static final String FILTER = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";

    private static final String SORT_ORDER = "CASE WHEN "
            + ContactsContract.Contacts.DISPLAY_NAME
            + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
            + ContactsContract.Contacts.DISPLAY_NAME
            + ", "
            + ContactsContract.CommonDataKinds.Email.DATA
            + " COLLATE NOCASE";

    private Bitmap getPhotoById(int id){

        Bitmap bitmapPhoto = null;
        byte[] photo = null;

        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, id);
        Cursor cursorPhoto = getContentResolver().query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);

        try
        {
            if (cursorPhoto.moveToFirst())
                photo = cursorPhoto.getBlob(0);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        } finally {

            cursorPhoto.close();
        }

        if (photo != null) {
            bitmapPhoto = BitmapFactory.decodeByteArray(photo, 0, photo.length);
//                    Log.d("!!!!!!!!!!!!!!!PICTURE!!!!!!!!!!", "!!!!!!!!!!!!!!!!");
        }

        return bitmapPhoto;
    }

    private ArrayList<Contact> contactsFromCursor(Cursor cursor) {
        ArrayList<Contact> c = new ArrayList<Contact>();


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));

                if (email != null) {
                    c.add(new Contact(name, email, getPhotoById(id)));
                    Log.d("Name: ", name);
                    Log.d("email: ", email);

                }

            } while (cursor.moveToNext());
        }

        return c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_contact_picker);

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);

        contactsView = (RecyclerView) findViewById(R.id.contacts_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        contactsView.setHasFixedSize(true);

        // use a linear layout manager
        contactsLayoutManager = new LinearLayoutManager(this);
        contactsView.setLayoutManager(contactsLayoutManager);

        // specify an adapter (see also next example)
        contactsAdapter = new ContactsAdapter(contacts);
        contactsView.setAdapter(contactsAdapter);

    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
               /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
      //  mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                this,
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                PROJECTION,
                FILTER,
                null,
                SORT_ORDER
        );
    }




    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        contacts.addAll(contactsFromCursor(cursor));
        contactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
    //    mCursorAdapter.swapCursor(null);
    }
}
