package RetrieveFactoryPattern;

import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RouteDetails implements Retrieve {
    DatabaseReference databaseReference ;

    @Override
    public void retrieveDataFeedback(ArrayAdapter<String> arrayAdapter,ArrayList<String> bustStop1) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Route1");
        ValueEventListener  listener = databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    bustStop1 = new ArrayList<>();
                    bustStop1.add(item.getValue().toString());
                }


//                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
