package com.samboy.contactproject.rectroresponce;

import com.samboy.contactproject.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hari Group Unity on 05-01-2018.
 */

public class ContactList {

    private List<ContactModel> contacts=new ArrayList<>();

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }
}
