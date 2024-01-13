package com.example.mycontactsroomdb.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mycontactsroomdb.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactsDAO {

    @Insert
    public long insertContact (Contact contact);

    @Update
    public int updateContact ( Contact contact );

    @Delete
    public void deleteContact ( Contact contact );

    @Query("SELECT * FROM contacts")
    public List<Contact> getAllContacts();
}
