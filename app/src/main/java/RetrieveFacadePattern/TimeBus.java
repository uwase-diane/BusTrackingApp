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

public class TimeBus implements Retrieves{

    DatabaseReference databaseReference;
    ValueEventListener listener;
    ;


    @Override
    public void retrieveData(ArrayAdapter<String> arrayAdapter,ArrayList<String> time) {
        databaseReference = FirebaseDatabase.getInstance().getReference("PreferredTime");
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    time.add(item.getValue().toString());
                }

//                arrayAdapter.notifyDataSetChanged();
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
    }
}
