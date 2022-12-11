package busActivitites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.R;
import com.example.bustrackingapp.TimeBusActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import RetrieveFacadePattern.RetrieveFacade;

public class BusStopActivity extends AppCompatActivity {

    Button btnBusStop;
    Spinner route_item;
    ArrayList<String> bustStop;
    ValueEventListener listener;
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_item);

        btnBusStop = findViewById(R.id.btnBusStop);
        route_item = findViewById(R.id.route_item);

        // BUS stop

        Intent intent = getIntent();
        if(intent.getStringExtra("selected route").equals("Route1"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Route1");

        }
        if(intent.getStringExtra("selected route").equals("Route2"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Route2");

        }

        if(intent.getStringExtra("selected route").equals("Route3"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Route3");

        }

        bustStop = new ArrayList<>();
        bustStop.add("Select your bus stop");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bustStop);
        route_item.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        RetrieveFacade retrieveFacade = new RetrieveFacade(arrayAdapter,bustStop);
        retrieveFacade.retrieveDataBusStop();
//        retrieveDataBusStop();
        System.out.println("----------------------------");
        btnBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TimeBusActivity.class));
            }
        });

    }

//    public void retrieveDataBusStop() {
//
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop.add(item.getValue().toString());
//                }
//                System.out.println("-------------------------->>>>>" + bustStop.size());
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


}
