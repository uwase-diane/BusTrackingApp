package studentActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.Confirmation;
import com.example.bustrackingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import RetrieveFactoryPattern.Retrieve;
import RetrieveFactoryPattern.RetrieveFactory;

public class StudentFeedbackActivity extends AppCompatActivity  {



    Spinner feedback;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> student_feedback;
    ArrayAdapter<String> arrayAdapter;
    Button save;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        save = findViewById(R.id.btnFeedback);

        feedback = findViewById(R.id.feedback);
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        student_feedback = new ArrayList<>();
        student_feedback.add(getString(R.string.choose_your_answer));
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, student_feedback);
        feedback.setAdapter(arrayAdapter);

        RetrieveFactory shapeFactory = new RetrieveFactory();
        Retrieve retrieve1 = shapeFactory.getData("studentfeedback");
        retrieve1.retrieveDataFeedback(arrayAdapter,student_feedback);
        arrayAdapter.notifyDataSetChanged();




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Confirmation.class));
            }
        });

    }

    public static class StudentsListActivity extends AppCompatActivity {
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_student_list);
        }
    }
}
