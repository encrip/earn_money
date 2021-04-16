package com.example.databasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    ProgressBar progressBar;

    TextView goToRegister,forgot;
    DatabaseReference reference;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.l_email);
        password = findViewById(R.id.l_password);
        goToRegister = findViewById(R.id.goToRegister);
        forgot = findViewById(R.id.forget);
        login = findViewById(R.id.loginBtn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        goToRegister.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent ( LoginActivity.this,RegisterActivity.class ) );
            }
        } );


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String l_email=email.getText().toString();
                //String l_Password=password.getText().toString();


                if (TextUtils.isEmpty ( l_email ))
                {
                    email.setError ( "Continue with email" );
                    return;
                }
                if (TextUtils.isEmpty ( l_Password ))
                {
                    password.setError ( "Enter password" );
                    return;
                }
                else {
                    signIn(l_email, l_Password);
                }
            }
        });
    }

    private void signIn(final String email, final String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){



                    FirebaseUser user=auth.getCurrentUser();
                    if (user.isEmailVerified())
                    {
                        startActivity(new Intent(LoginActivity.this, BoardActivity.class));
                        Toast.makeText(LoginActivity.this, "Login yayi", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(LoginActivity.this, "Email is not Verified", Toast.LENGTH_SHORT).show();
                    }








                }else {
                    Toast.makeText(LoginActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
