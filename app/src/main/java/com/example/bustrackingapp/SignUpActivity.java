package com.example.bustrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName,email,password;
    Button registerButton;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullName=findViewById(R.id.namesInput);
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.passwordInput);
        registerButton=findViewById(R.id.registerButton);

        fAuth=FirebaseAuth.getInstance();
//        if(fAuth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//
//        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails =email.getText().toString().trim();
                String npasswrd=password.getText().toString().trim();

                if(TextUtils.isEmpty(emails))
                {
                    email.setError("email is required");
                }
                if(TextUtils.isEmpty(npasswrd))
                {
                    password.setError("password is required");
                }
                if(npasswrd.length()<6)
                {
                    password.setError(("password must be >= 6characters"));
                }
                fAuth.createUserWithEmailAndPassword(emails,npasswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this,"User created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else
                        {
                            Toast.makeText(SignUpActivity.this,"ERROR!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        signUpBtn =  findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpActivity();
            }
        });
    }

    public void signUpActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
