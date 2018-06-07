package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetPuzzle.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetPuzzle request = new GetPuzzle(getApplicationContext());
        request.GetPuzzle(this);
    }

    @Override
    public void gotQuestions(JSONObject questions) {
        Log.d("blij", String.valueOf(questions));
    }

    @Override
    public void gotQuestionsError(String message) {
        Log.d("boosheid", message);
    }

    public void chessExercise(View view) {
        Intent intent = new Intent(MainActivity.this, ChessExercise.class);
        startActivity(intent);
    }
}