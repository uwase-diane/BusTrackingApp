package com.example.bustrackingapp.bus_mapping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import busActivitites.BusNearNotification;
import com.example.bustrackingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Distance_Map extends AppCompatActivity {
    TextView textView;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_map);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Location A = new Location("locationA");
        A.setLatitude(17.372102);
        A.setLongitude(78.484196);

        Location B = new Location("locationB");
        A.setLatitude(18.372102);
        A.setLongitude(79.484196);
        notifyStudent(A, B, 5);
        textView2.setText(notifyStudent(A, B, 5));

    }

    public String notifyStudent(Location currentLocation, Location destination, double timeselected) {
        double distance, time;
        Location loc_A = currentLocation, loc_B = destination;
        do {


            //####################################3
            //getting location from database
            Query longitude_query = FirebaseDatabase.getInstance().getReference().child(getString(R.string.bus_location))
                    .child(getString(R.string.route_1))
                    .child(getString(R.string.location))
                    .orderByKey().equalTo(getString(R.string.longitude));;
            Query latitude_query = FirebaseDatabase.getInstance().getReference().child(getString(R.string.bus_location))
                    .child(getString(R.string.route_1))
                    .child(getString(R.string.location))
                    .orderByKey().equalTo(getString(R.string.latitude));
//        System.out.println("location latitude: "+latitude_query);
            longitude_query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
//                    location = (Location)dataSnapshot.getValue();
                        System.out.println("location longitude: "+dataSnapshot.getValue());

                        loc_A.setLongitude(Double.parseDouble(dataSnapshot.getValue().toString()));
//                        Log.d(TAG, "onDataChange: (location retrieval from db)"+location.toString());
                        /////////////////////you need to finish this data reading form firebase
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            latitude_query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
//                    location = (Location)dataSnapshot.getValue();
                        System.out.println("location latitude: "+dataSnapshot.getValue());

                        loc_A.setLatitude(Double.parseDouble(dataSnapshot.getValue().toString()));
//                        Log.d(TAG, "onDataChange: (location retrieval from db)"+location.toString());
                        /////////////////////you need to finish this data reading form firebase
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //##########################################3
            distance = currentLocation.distanceTo(destination);
//        textView.setText("Distance between two Geographic Locations: " + distance + " KMS");

             time = 60 * distance / 60000;
            textView2.setText("time taken  is" + time);
//        if(time>timeselected)
//        {
//
//        }


        }
        while (time > timeselected);
        BusNearNotification.showNotification(this,"bus tracking reminder"," the bus is "+time+" minutes away!");
return "notification sent";
    }
}