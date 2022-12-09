package RetrieveFactoryPattern;

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

public class StudentFeedback implements Retrieve{
    DatabaseReference databaseReference;
    ValueEventListener listener;
    @Override
    public void retrieveDataFeedback(ArrayAdapter<String> arrayAdapter, ArrayList<String> student_feedback) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    student_feedback.add(item.getValue().toString());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
