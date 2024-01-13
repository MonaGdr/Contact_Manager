package com.example.mycontactsroomdb.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "contacts")
public class Contact {

    @ColumnInfo(name = "column_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "column_name")
    private String userName;

    @ColumnInfo(name = "column_email")
    private String email;

    public Contact(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    @Ignore
    public Contact(){}

    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
}
