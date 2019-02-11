package com.example.week5weekendcontactsmaps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsManager.IPermissionManager, ContactsManager.IContactsManager{
    PermissionsManager permissionsManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView tvName;
    TextView tvPhone;
    TextView tvLocation;
    List<Contact> contactList;
    //MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvView);
        tvName = findViewById(R.id.tvDisplayName);
        tvPhone = findViewById(R.id.tvDisplayPhone);
        tvLocation = findViewById(R.id.tvDisplayLocation);
        permissionsManager = new PermissionsManager(this, this);
        permissionsManager.checkPermission();

        //mainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPerissionResult(boolean isGranted) {
        if(isGranted){
            getContacts();
            //refreshData();
        }else{
            Log.d("TAG", "onPerissionResult: " + (isGranted ? "Granted" : "Denied!!!"));
        }

    }

    public  void getContacts(){
        ContactsManager contactsManager = new ContactsManager(this);
        contactsManager.getContacts();
    }

    @Override
    public void getContacts(List<Contact> contacts) {
        contactList = contacts;
        for (Contact contact : contacts){
            Log.d("TAG", "OnContactsReceived: " + contact.toString());
        }
        ArrayList<Contact> contactArrayList = new ArrayList<>(contactList);
        recyclerViewAdapter = new RecyclerViewAdapter(contactArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recyclerClickEvent(Contact contact){
        tvName.setText("Name: " + contact.getName());
        tvPhone.setText("Phone: " + contact.getNumber());
        tvLocation.setText("Location: " + contact.getLocation());

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("location", tvLocation.getText().toString());
        startActivity(intent);
    }
}
