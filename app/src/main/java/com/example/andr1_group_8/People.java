package com.example.andr1_group_8;

public class People {

    private String firstName;
    private String lastName;
    private String email;
    private String photo;

    public People(String firstname, String lastname, String email, String photo){
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
