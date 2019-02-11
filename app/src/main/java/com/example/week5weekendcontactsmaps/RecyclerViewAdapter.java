package com.example.week5weekendcontactsmaps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Contact> contactArrayList;
    Toast context;
    public RecyclerViewAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @NonNull

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Contact contact = contactArrayList.get(position);

        if(contact != null){
            String name = contact.getName();
            String phone = contact.getNumber();
            String location = contact.getLocation();
            viewHolder.setcontact(contact);
            viewHolder.tvPhone.setText(phone);
            viewHolder.tvName.setText(name);
            viewHolder.tvLocation.setText(location);
        }
    }


    @Override
    public int getItemCount() {
        return contactArrayList != null ? contactArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhone;
        TextView tvLocation;
        Contact contact;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            final Context context = itemView.getContext();
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvLocation = itemView.findViewById(R.id.tvLocation);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
//                    String event = contact.getDbn();
                      //String fire = "ready";
                      EventBus.getDefault().post(contact);

                    //System.out.println("pressed");
                }
            });

        }
        public void setcontact(Contact contact){ this.contact = contact;}

    }

}