package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// in deze activity is het mogelijk voor een gebuiker om zich te registreren
public class RegisterActivity extends AppCompatActivity {

    // initialiseren authenticatie
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }

    // gebuiker wordt geregistreerd (indien voldaan aan voorwaarden)
    public void registerNow(View view) {
        EditText emaill = findViewById(R.id.email);
        String email = String.valueOf(emaill.getText());
        EditText passwordd = findViewById(R.id.password);
        String password = String.valueOf(passwordd.getText());
        EditText passwordd2 = findViewById(R.id.passwordConfirm);
        String password2 = String.valueOf(passwordd2.getText());

        // indien de twee wachtwoorden niet matchen
        if (!password2.equals(password)){
            Toast.makeText(RegisterActivity.this, "passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }

        // indien email of wachtwoord niet ingevuld
        else if ((password.equals("") || email.equals(""))){
            Toast.makeText(RegisterActivity.this, "e-mail or password incomplete.",
                    Toast.LENGTH_SHORT).show();
        }

        // proberen te registreren
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // succesvol ingelogd
                                FirebaseUser user = mAuth.getCurrentUser();

                                // database maken voor gebuiker
                                addToDB(user);

                                // naar activity_main
                                toMain();
                            }

                            else {

                                // als registrere mislukt
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    //  navigeren naar activity_main
    public void toMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // database voor nieuwe user maken
    public void addToDB(FirebaseUser user) {

        // id user verkrijgen
        String UID = "unknown";
        if (user != null) {
            UID = user.getUid();
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");

        // correcte, doute opgaven en totale tijd worden op nul gezet
        Score score = new Score("0","0", "0");

        // scores toevoegen voor de gebuiker met id
        myRef.child(UID).setValue(score);
    }

}
