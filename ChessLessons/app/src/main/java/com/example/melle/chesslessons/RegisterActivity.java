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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerNow(View view) {
        EditText emaill = findViewById(R.id.email);
        String email = String.valueOf(emaill.getText());
        EditText passwordd = findViewById(R.id.password);
        String password = String.valueOf(passwordd.getText());
        EditText passwordd2 = findViewById(R.id.passwordConfirm);
        String password2 = String.valueOf(passwordd2.getText());

        if (!password2.equals(password)){
            Toast.makeText(RegisterActivity.this, "passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }

        else if ((password.equals("") || email.equals(""))){
            Toast.makeText(RegisterActivity.this, "e-mail or password incomplete.",
                    Toast.LENGTH_SHORT).show();
        }

        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("sign in", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                addToDB();
                                toMain();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("sign in failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }


    public void toMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addToDB() {

        String UID = "unknown";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");
        Score scoreee = new Score("0","0", "0");

        myRef.child(UID).setValue(scoreee);
    }

}
