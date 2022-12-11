package RetrieveFacadePattern;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusStop implements Retrieves{


    ValueEventListener listener;
    DatabaseReference databaseReference;
    @Override
    public void retrieveData(ArrayAdapter<String> arrayAdapter, ArrayList<String> bustStop) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Route1");
        databaseReference = FirebaseDatabase.getInstance().getReference("Route2");
        databaseReference = FirebaseDatabase.getInstance().getReference("Route3");

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bustStop.add(item.getValue().toString());
                }
                System.out.println("-------------------------->>>>>" + bustStop.size());

//                arrayAdapter.notifyDataSetChanged();
            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

    }
}
