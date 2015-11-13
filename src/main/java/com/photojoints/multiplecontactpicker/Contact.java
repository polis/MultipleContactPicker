package com.photojoints.multiplecontactpicker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by polis on 2015-11-07.
 */
public class Contact implements Parcelable {
    private String name;
    private String email;
    private Bitmap picture;
    private boolean isSelected = false;

    public Contact(String name, String email, Bitmap picture){


        this.name = name;
        this.email = email;
        this.picture = picture;

    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public Bitmap getPicture(){
        return picture;
    }

    public boolean isSelected(){
        return isSelected ;
    }
    public void setSelected(boolean b){
        isSelected = b;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
