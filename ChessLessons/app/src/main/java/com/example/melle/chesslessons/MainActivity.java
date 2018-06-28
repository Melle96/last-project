package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// in deze activity kan de gebuiker inloggen

public class MainActivity extends AppCompatActivity{

    // authenticatie initialiseren
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();

        // Check of de gebuiker is ingelogd
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    // indien sign in aangeklikt wordt er ingelogd
    public void logIn(View view) {

        // wachtwoord en email worden verkegen
        EditText emailUser = (EditText) findViewById(R.id.emailUser);
        String email = emailUser.getText().toString();

        EditText passwordUser = (EditText) findViewById(R.id.passwordUser);
        String password = passwordUser.getText().toString();
//
//        final String email = "hoi@j.nl";
//        String password = "qwerty";

        // als wachtwoord en email ingevuld
        if (!password.equals("") && !email.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // succesvol ingelogd
                                FirebaseUser user = mAuth.getCurrentUser();

                                // ga naar  het menu
                                goToMenu();

                            } else {

                                // inloggen mislukt
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

        //indien wachtwoord of email niet ingevuld
        else{
            Toast.makeText(MainActivity.this, "e-mail or password incomplete.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // activity_menu wordt aangroepen
    public void goToMenu() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    // activity_register wordt aangeroepen
    public void goToRegister(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
