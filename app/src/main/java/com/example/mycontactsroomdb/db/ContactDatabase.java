package com.example.mycontactsroomdb.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mycontactsroomdb.db.entity.Contact;

@Database(entities = {Contact.class} , version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactsDAO getContactDAO();
}
