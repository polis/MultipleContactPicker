package com.photojoints.multiplecontactpicker;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MultipleContactPickerActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView contactsView;
    private RecyclerView.Adapter contactsAdapter;
    private RecyclerView.LayoutManager contactsLayoutManager;

    private static final String[] PROJECTION =
            {
//                    ContactsContract.CommonDataKinds.Photo.DISPLAY_NAME_PRIMARY,
////                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
////                    Contacts.DISPLAY_NAME_PRIMARY,
//                    ContactsContract.CommonDataKinds.Email.ADDRESS,
//                    ContactsContract.CommonDataKinds.Photo.PHOTO

                    Contacts._ID,
                    Contacts.DISPLAY_NAME,
                    Contacts.CONTACT_STATUS,
                    Contacts.CONTACT_PRESENCE,
                    Contacts.PHOTO_ID,
                    Contacts.LOOKUP_KEY,
            };

    private static final String SORT_ORDER = Contacts.DISPLAY_NAME;

    /*
 * Constructs search criteria from the search string
 * and email MIME type
 */
//    private static final String SELECTION =
//            /*
//             * Searches for an email address
//             * that matches the search string
//             */
//            ContactsContract.CommonDataKinds.Email.ADDRESS + " LIKE ? " + "AND " +
//            /*
//             * Searches for a MIME type that matches
//             * the value of the constant
//             * Email.CONTENT_ITEM_TYPE. Note the
//             * single quotes surrounding Email.CONTENT_ITEM_TYPE.
//             */
//                    Data.MIMETYPE + " = '" + Email.CONTENT_ITEM_TYPE + "'";


    public ArrayList<Contact> contacts = new ArrayList<Contact>();


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
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );
    }

    private ArrayList<Contact> contactsFromCursor(Cursor cursor) {
        ArrayList<Contact> c = new ArrayList<Contact>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));

                String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                Bitmap photo = null;

                byte[] data = cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                if (data != null) {
                    photo = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Log.d("!!!!!!!!!!!!!!!PICTURE!!!!!!!!!!", "!!!!!!!!!!!!!!!!");
                    }

                if (email != null) {
                    c.add(new Contact(name, email, photo));
                    Log.d("Name: ", name);
                    Log.d("email: ", email);

                }

            } while (cursor.moveToNext());
        }

        return c;
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
