package com.example.mycontactsroomdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontactsroomdb.ContactDialog;
import com.example.mycontactsroomdb.MainActivity;
import com.example.mycontactsroomdb.R;
import com.example.mycontactsroomdb.db.entity.Contact;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private FragmentManager fragment;

    private List <Contact> contacts;

    public CustomAdapter(Context context, List <Contact> contacts, FragmentManager fragment){
        this.context = context;
        this.contacts = contacts;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_item_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(contacts.get(position).getUserName());
        holder.txtEmail.setText(contacts.get(position).getEmail());

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts( Contact contact ) {
        contacts.add(contact);
        notifyDataSetChanged();
    }

    public void deleteContacts( Contact contact ) {
        contacts.remove(contact);
        notifyDataSetChanged();
    }

    public void updateContact( Contact contact ) {
        contacts.get(contacts.indexOf(contact)).setUserName(contact.getUserName());
        contacts.get(contacts.indexOf(contact)).setEmail(contact.getEmail());
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create alert dialog to add new contact
                    ContactDialog contactDialog = new ContactDialog( context , true, CustomAdapter.this, contacts.get(getAdapterPosition()) );
                    contactDialog.show(fragment, "add contact");
                    notifyDataSetChanged();
                }
            });
        }
    }
}
