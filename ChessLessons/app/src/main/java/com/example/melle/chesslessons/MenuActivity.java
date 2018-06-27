package com.example.melle.chesslessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// in deze activity is het mogelijk voor de gebuiker om door te gaan naar puzzel, statistieken en instructie
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // de gebuiker wordt genavigeerd naar de schaakpuzzel
    public void puzzel(View view) {
        Intent intent = new Intent(MenuActivity.this, ChessExercise.class);
        startActivity(intent);
    }

    // de gebuiker wordt genavigeerd naar zijn statistieken
    public void statistics(View view) {
        Intent intent = new Intent(MenuActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    // de gebuiker wordt genavigeerd naar de instructie
    public void instruction(View view) {
        Intent intent = new Intent(MenuActivity.this, InstructionActivity.class);
        startActivity(intent);
    }
}
