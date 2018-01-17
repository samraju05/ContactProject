package com.samboy.contactproject.model;

/**
 * Created by Hari Group Unity on 02-01-2018.
 */

public class ContactModel {
    String id;
    String name;
    String email;
    String address;
    String gender;
    PhoneModel phone;

    public ContactModel(String id, String name, String email,String address,String gender,PhoneModel phone){
        this.id=id;
        this.name=name;
        this.email=email;
        this.address=address;
        this.gender=gender;
        this.phone=phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PhoneModel getPhone() {
        return phone;
    }

    public void setPhone(PhoneModel phone) {
        this.phone = phone;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
