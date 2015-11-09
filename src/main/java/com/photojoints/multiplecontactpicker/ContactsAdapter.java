package com.photojoints.multiplecontactpicker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by polis on 2015-11-07.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageView pictureView;
        public TextView nameView;
        public TextView emailView;


        public ViewHolder(View v) {
            super(v);


            nameView = (TextView) v.findViewById(R.id.contact_name);
            emailView = (TextView) v.findViewById(R.id.contact_email);
            pictureView = (ImageView) v.findViewById(R.id.contact_picture);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item_view, parent, false);
        // set the view's size, margins, paddings and layout parameters



        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.nameView.setText(contacts.get(position).getName());
        holder.emailView.setText(contacts.get(position).getEmail());
        holder.pictureView.setImageBitmap(contacts.get(position).getPicture());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return contacts.size();
    }
}
