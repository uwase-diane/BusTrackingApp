package com.example.bustrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bustrackingapp.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import DriverActivities.DriverActivity;
import SwitchLanguage.LanguageManager;
import busActivitites.RouteDetailsActivity;

public class MainActivity extends AppCompatActivity {

    private TextView signUpLink;
    private EditText email, password;
    private Button loginBtn;
    private FirebaseAuth fAuth;
    private TextView switchLang;
    private TextView switchLang2;


    private LanguageManager lang = new LanguageManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginbutton);
        fAuth = FirebaseAuth.getInstance();

        switchLang=findViewById(R.id.switchbutton);
        switchLang2=findViewById(R.id.switchbutton2);

        switchLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang.updateResource("fr");
                recreate();
            }
        });
        switchLang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang.updateResource("en");
                recreate();
            }
        });

        signUpLink = findViewById(R.id.signUpLink);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString().trim();
                String npasswrd = password.getText().toString().trim();

                User user = new User();
                user.setEmail(emails);
                user.setPassword(npasswrd);

                if (TextUtils.isEmpty(emails)) {
                    email.setError(getString(R.string.email_is_required));
                }
                if (TextUtils.isEmpty(npasswrd)) {
                    password.setError(getString(R.string.password_is_required));
                }
                if (npasswrd.length() < 6) {
                    password.setError(getString(R.string.invalid_password));
                }
                fAuth.signInWithEmailAndPassword(emails,npasswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = getIntent();
                          if(intent.getStringExtra("user").equals("busdriver"))
                          {
                              startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                              return;
                          }
                           if(intent.getStringExtra("user").equals("bustudent"))
                            {
                                startActivity(new Intent(getApplicationContext(), RouteDetailsActivity.class));
                                return;

                            }

                            Toast.makeText(MainActivity.this, R.string.logged_in_successfully, Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, R.string.login_failed + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }


        });
    }



    public void signUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}