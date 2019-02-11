package com.example.week5weekendcontactsmaps;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactsManager {
    Context context;
    IContactsManager iContactsManager;


    public ContactsManager(Context context) {
        this.context = context;
        this.iContactsManager = (IContactsManager) context;
    }

    public void getContacts() {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri locationUri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        Cursor contactsCursor = context.getContentResolver().query(
                contactsUri, null, null, null, null);

        List<Contact> contactList = new ArrayList<>();
        while (contactsCursor.moveToNext()) {
            String contactName = contactsCursor.getString(contactsCursor.getColumnIndex(DISPLAY_NAME));
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
            String LOCATION = ContactsContract.CommonDataKinds.StructuredPostal.STREET;
            String contactID = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contID = ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID;
            Cursor phoneCursor = context.getContentResolver().query(
                    phoneUri,
                    new String[]{NUMBER},
                    DISPLAY_NAME + "=?",
                    new String[]{contactName},
                    NUMBER +" ASC"
            );

            Cursor locationCursor = context.getContentResolver().query(
                    locationUri,
                    null,
                     contID + " = " + contactID,
                    null,
                    null
            );
            String phoneNumber = " ";
            String locationAddress = " ";
            while (phoneCursor.moveToNext()){
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                //locationAddress = locationCursor.getString(locationCursor.getColumnIndex(LOCATION));
            }
            while(locationCursor.moveToNext()){
                locationAddress = locationCursor.getString(locationCursor.getColumnIndex(LOCATION));
                //System.out.println(locationAddress);
            }
            //System.out.println(locationAddress);


                    //locationCursor.getString(locationCursor.getColumnIndex(LOCATION)));

            if(locationAddress != null && !locationAddress.equals(" ")){
                Contact contact = new Contact(contactName, phoneNumber, locationAddress);
                contactList.add(contact);
            }

        }

        iContactsManager.getContacts(contactList);
    }

    public interface IContactsManager {
        void getContacts(List<Contact> contact);
    }
}

