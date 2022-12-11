package busActivitites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import RetrieveFactoryPattern.Retrieve;
import RetrieveFactoryPattern.RetrieveFactory;
import busActivitites.BusStopActivity;

public class RouteDetailsActivity extends AppCompatActivity    {



    Button btnContinue;
    Spinner spinnerRouteOne;
//    Spinner spinnerRouteTwo;
//    Spinner spinnerRouteThree;
    DatabaseReference databaseReferenceRoute;
    DatabaseReference databaseReferenceBusStop2;
    DatabaseReference databaseReferenceBusStop3;
    DatabaseReference databaseReferenceBusStop;
    ValueEventListener listener;
    ArrayList<String> busRoute;
    ArrayList<String> bustStop2;
    ArrayList<String> bustStop3;
    ArrayAdapter<String> arrayAdapterRoute;

    String selectedRoute;

    // bus stop

    Spinner route_item;
    ArrayList<String> bustStop;
    ArrayAdapter arrayAdapter_bustStop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        btnContinue = findViewById(R.id.continueDetails);
        spinnerRouteOne = findViewById(R.id.route1);
//        spinnerRouteTwo = findViewById(R.id.route2);
//        spinnerRouteThree = findViewById(R.id.route3);



        //route 1

        databaseReferenceRoute = FirebaseDatabase.getInstance().getReference("Routes");
        busRoute = new ArrayList<>();
        busRoute.add(getString(R.string.Select_your_route));
        RetrieveFactory shapeFactory = new RetrieveFactory();
        Retrieve retrieve1 = shapeFactory.getData("routeDetails");

        arrayAdapterRoute = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, busRoute);
        spinnerRouteOne.setAdapter(arrayAdapterRoute);
        retrieve1.retrieveDataFeedback(arrayAdapterRoute, busRoute);
        arrayAdapterRoute.notifyDataSetChanged();
        System.out.println("--------------" + busRoute);


        // bus stop


        spinnerRouteOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        System.out.println("busroute: " + busRoute.get(i));

                selectedRoute = busRoute.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), TimeBusActivity.class));
                Intent intent = new Intent(view.getContext(), BusStopActivity.class);
                intent.putExtra("selected route",selectedRoute);
                startActivity(intent);
            }
        });

    }



//    public void retrieveDataRouteOne() {
//
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop1.add(item.getValue().toString());
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    @Override
//    public void retrieveDataFeedback() {
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop1.add(item.getValue().toString());
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

  //  }

//    public void retrieveDataRouteTwo() {
//
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop2.add(item.getValue().toString());
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


//    public void retrieveDataRouteThree() {
//
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop3.add(item.getValue().toString());
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    public void retrieveDataBusStop() {

        listener = databaseReferenceBusStop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bustStop.add(item.getValue().toString());
                }
                System.out.println("-------------------------->>>>>" + bustStop.size());

                arrayAdapter_bustStop.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void retrieveDataBusStopTwo() {
//
//        listener = databaseReferenceBusStop2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop2.add(item.getValue().toString());
//                }
//                System.out.println("-------------------------->>>>>" + bustStop.size());
//
//                arrayAdapter_bustStop.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    public void retrieveDataBusStopThree() {
//
//        listener = databaseReferenceBusStop3.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop3.add(item.getValue().toString());
//                }
//                System.out.println("-------------------------->>>>>" + bustStop.size());
//
//                arrayAdapter_bustStop.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}
