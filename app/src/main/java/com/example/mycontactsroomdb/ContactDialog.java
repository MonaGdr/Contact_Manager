package com.example.mycontactsroomdb;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mycontactsroomdb.adapter.CustomAdapter;
import com.example.mycontactsroomdb.db.ContactDatabase;
import com.example.mycontactsroomdb.db.ContactsDAO;
import com.example.mycontactsroomdb.db.entity.Contact;

import java.util.List;

public class ContactDialog extends DialogFragment {

    private Context context;
    private boolean contactExist;
    private View addContactView;

    private CustomAdapter adapter;

    private EditText editTxtName, editTxtEmail;

    private Contact contact = null;

    private ContactDatabase contactDatabase;

    public ContactDialog(Context context, boolean contactExist, CustomAdapter adapter){
        this.context = context;
        this.contactExist = contactExist;
        this.adapter = adapter;
    }

    public ContactDialog(Context context, boolean contactExist, CustomAdapter adapter, Contact contact){
        this.context = context;
        this.contactExist = contactExist;
        this.adapter = adapter;
        this.contact = contact;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //inflate the addContactView
        LayoutInflater inflater = LayoutInflater.from(context);
        addContactView = inflater.inflate(R.layout.add_user_layout, null, false);

        //initialize the editTexts
        editTxtName = addContactView.findViewById(R.id.editTxtName);
        editTxtEmail = addContactView.findViewById(R.id.editTxtEmail);

        //build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(addContactView);

        if ( contactExist ){
            TextView txtTitle = addContactView.findViewById(R.id.txtTitle);
            txtTitle.setText(contact.getUserName());
            editTxtName.setText(contact.getUserName());
            editTxtEmail.setText(contact.getEmail());

            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateContact(contact);
                }
            });
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteContact( contact );


                }
            });


        }else{
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addContact();
                }
            });
            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });



        }

        return builder.create();

    }

    private void deleteContact(Contact contact) {

        ContactDatabase contactDatabase = Room.databaseBuilder(
                context,
                ContactDatabase.class,
                "db.contact"
        ).allowMainThreadQueries().build();

        contactDatabase.getContactDAO().deleteContact(contact);
        adapter.deleteContacts(contact);
    }

    public void addContact(){

        ContactDatabase contactDatabase = Room.databaseBuilder(
                context,
                ContactDatabase.class,
                "db.contact"
        ).allowMainThreadQueries().build();

        Contact contact = new Contact();
        contact.setUserName(editTxtName.getText().toString());
        contact.setEmail(editTxtEmail.getText().toString());


        adapter.setContacts(contact);
        boolean success = contactDatabase.getContactDAO().insertContact(contact) != -1;

        if(success)
            Toast.makeText(context, "Contact added successfully", Toast.LENGTH_SHORT).show();

    }

    public void updateContact( Contact contact ){
        contactDatabase = Room.databaseBuilder(
                context,
                ContactDatabase.class,
                "db.contact"
        ).allowMainThreadQueries().build();

        contact.setUserName(editTxtName.getText().toString());
        contact.setEmail(editTxtEmail.getText().toString());

        boolean success = contactDatabase.getContactDAO().updateContact(contact) != -1;
        adapter.updateContact(contact);

        if(success)
            Toast.makeText(context, "Contact updated successfully", Toast.LENGTH_SHORT).show();

    }
}
