package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void puzzel(View view) {
        Intent intent = new Intent(MenuActivity.this, ChessExercise.class);
        startActivity(intent);
    }

    public void rating(View view) {
        Intent intent = new Intent(MenuActivity.this, RatingActivity.class);
        startActivity(intent);
    }
}
