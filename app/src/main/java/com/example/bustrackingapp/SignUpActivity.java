package com.example.bustrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText fullName, email, password;
    private Button registerButton;
//    private Button signUpBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private   String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullName = findViewById(R.id.namesInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.signUp);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
//        if(fAuth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//
//        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString().trim();
                String npasswrd = password.getText().toString().trim();
                String fullnames =fullName.getText().toString().trim();
                User user = new User();
                user.setFullnames(fullnames);
                user.setEmail(emails);

                if (TextUtils.isEmpty(emails)) {
                    email.setError(getString(R.string.email_is_required));
                }
                if (TextUtils.isEmpty(npasswrd)) {
                    password.setError(getString(R.string.password_is_required));
                }
                if (npasswrd.length() < 6) {
                    password.setError((getString(R.string.invalid_password)));
                }
                //creating a user
                fAuth.createUserWithEmailAndPassword(emails, npasswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //sending verification email
                            FirebaseUser users =fAuth.getCurrentUser();
                            users.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignUpActivity.this,getString(R.string.verification_email_has_been_sent),Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure:Email not sent"+e.getMessage());

                                }
                            });

                            Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fname",fullnames);
                            user.put("email",emails);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"Onsuccess: user profile is created for"+userID);
                               }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "ERROR!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}
