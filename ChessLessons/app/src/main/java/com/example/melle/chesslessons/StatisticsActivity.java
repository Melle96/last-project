package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// in deze activity worden de statistieken van een gebruiker gepresenteerd
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // de database wordt gelezen
        readFromDB();
    }

    // database lezen
    public void readFromDB() {

        // gebuiker wordt verkegen
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            // id van de user verkijgen
            final String UID = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("scores");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // scores van de user wordt verkijgen bij juiste id
                    Score value = dataSnapshot.child(UID).getValue(Score.class);

                    // textviews met scores aanpassen
                    setText(value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }

    // Textviews aanpassen
    public void setText(Score score){

        // aantal correcte puzzels in textview
        TextView correct = (TextView) findViewById(R.id.correct);
        correct.setText("correct: " + score.correct);

        // aantal foute puzzels in textview
        TextView wrong = (TextView) findViewById(R.id.wrong);
        wrong.setText("wrong: " + score.wrong);

        // aantal minuten besteed aan puzzels in textview
        int timeInMinutes = Integer.parseInt(score.time)/60;
        TextView time = (TextView) findViewById(R.id.time);
        time.setText("time: " + timeInMinutes + " min");
    }
}
