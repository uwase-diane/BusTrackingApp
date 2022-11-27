package com.example.bustrackingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TimeBusActivty extends AppCompatActivity {

    Spinner pickUpTimeSpinner;
    Spinner notifyTimeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time);
        pickUpTimeSpinner = findViewById(R.id.backup_time);
        List<String> time = new ArrayList<>();
        time.add("Select your prefered time ");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, time);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickUpTimeSpinner.setAdapter(arrayAdapter);
        pickUpTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose from lis")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        pickUpTimeSpinner = findViewById(R.id.notify_time);
        List<String> notifyTime = new ArrayList<>();
        notifyTime.add("When to notify you ");
        ArrayAdapter<String> arrayAdapterNotifyTime = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notifyTime);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickUpTimeSpinner.setAdapter(arrayAdapter);
        pickUpTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose from lis")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
