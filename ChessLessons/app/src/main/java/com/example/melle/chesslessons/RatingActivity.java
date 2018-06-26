package com.example.melle.chesslessons;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        readFromDB();
    }

    public void addToDB() {

        String UID = "unknown";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");
        Score scoreee = new Score("4","8", "23");

        myRef.child(UID).setValue(scoreee);
    }

    public void readFromDB() {
        // Read from the database

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String UID = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("scores");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Score value = dataSnapshot.child(UID).getValue(Score.class);
                    setText(value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("failed", "Failed to read value.", error.toException());
                }
            });
        }
    }

    public void setText(Score score){
        TextView correct = (TextView) findViewById(R.id.correct);
        correct.setText("correct: " + score.correct);

        TextView wrong = (TextView) findViewById(R.id.wrong);
        wrong.setText("wrong: " + score.wrong);

        int timeInMinutes = Integer.parseInt(score.time)/60;
        TextView time = (TextView) findViewById(R.id.time);
        time.setText("time: " + timeInMinutes + " min");
    }


//    public String SharedPreferencesRetrieve() {
//
//        SharedPreferences sharedpreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
//
//        String email = sharedpreferences.getString("email",  "no email");
//        Log.d("email", email);
//        return email;
//    }
}
