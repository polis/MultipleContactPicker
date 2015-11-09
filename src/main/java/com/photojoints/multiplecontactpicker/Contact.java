package com.photojoints.multiplecontactpicker;

import android.graphics.Bitmap;

/**
 * Created by polis on 2015-11-07.
 */
public class Contact {
    private String name;
    private String email;
    private Bitmap picture;

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

}
