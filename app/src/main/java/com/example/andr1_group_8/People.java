package com.example.andr1_group_8;

import android.graphics.drawable.Icon;
import android.media.Image;
import android.widget.ImageView;

public class People {

    private String firstName;
    private String lastName;
    private Icon photo;
    private String email;

    public People(String firstname, String lastname, Icon photo){
        this.firstName = firstname;
        this.lastName = lastname;
        this.photo = photo;
        this.email = "wow@gmail.com";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // TODO: Check for valid email address
        this.email = email;
    }
}
