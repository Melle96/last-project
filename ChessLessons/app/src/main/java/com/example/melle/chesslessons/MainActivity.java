package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GetPuzzle request = new GetPuzzle(getApplicationContext());
//        request.GetPuzzle(this);
    }

//    @Override
//    public String gotQuestions(JSONObject questions) {
//
//        Log.d("blij", String.valueOf(questions));
//        try {
//            Log.d("blij2", questions.getString("pgn"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void gotQuestionsError(String message) {
//        Log.d("boosheid", message);
//    }

    public void chessExercise(View view) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void registreer(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
