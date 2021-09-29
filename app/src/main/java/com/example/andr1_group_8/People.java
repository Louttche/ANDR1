package com.example.andr1_group_8;

import android.graphics.drawable.Icon;
import android.media.Image;
import android.widget.ImageView;

public class People {

    private String firstName;
    private String lastName;
    private Icon photo;

    public People(String firstname, String lastname, Icon photo){
        this.firstName = firstname;
        this.lastName = lastname;
        this.photo = photo;
    }

    public Icon getPhoto() {
        return photo;
    }

    public void setPhoto(Icon photo) {
        this.photo = photo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
