package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Integer.parseInt;

public class ChessExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
    }

    public void getPuzzleNow(View view) {
        String coordinates = "";
        String fed = "3r3k/1b2rpp1/p2qpN1p/1p6/4pP1Q/P5R1/1PP3PP/5R1K";
        String[] array = fed.split("");
        Log.d("array", array[2]);
        int arrayLength = array.length;
        Log.d("length", String.valueOf(arrayLength));

        for (int i = 1; i < arrayLength; i++) {
            if (array[i] == "r"){
                coordinates = coordinates + "blackrook";
            }

            else if (array[i] == "n"){
                coordinates = coordinates + "blackknight";
            }

            else if (array[i] == "b"){
                coordinates = coordinates + "blackbishop";
            }

            else if (array[i] == "q"){
                coordinates = coordinates + "blackqueen";
            }

            else if (array[i] == "k"){
                coordinates = coordinates + "blackking";
            }

            else if (array[i] == "p"){
                coordinates = coordinates + "blackpawn";
            }

            else if (array[i] == "R"){
                coordinates = coordinates + "whiterook";
            }

            else if (array[i] == "N"){
                coordinates = coordinates + "whiteknight";
            }

            else if (array[i] == "B"){
                coordinates = coordinates + "whitebisshop";
            }

            else if (array[i] == "Q"){
                coordinates = coordinates + "whitequeen";
            }

            else if (array[i] == "K"){
                coordinates = coordinates + "whiteking";
            }

            else if (array[i] == "P"){
                coordinates = coordinates + "whitepawn";
            }

            else if (array[i] == "1") {

                for (int j = 0; j < parseInt(array[i]); j++){
                    coordinates = coordinates + "0";
                }
            }

        }

        Log.d("coordinates", coordinates);


//        for (int i = 0; i < 64; i++) {
//            int column = i%8 + 1;
//            int row = 8- (i - i%8)/8;
//            char c = (char) (column + 96);
//            Log.d("rij", String.valueOf(row));
//            Log.d("kolom", String.valueOf(c));
//
//
//            String buttonID = String.valueOf(c) + String.valueOf(row);
//            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
//
//            ImageView img = (ImageView) findViewById(resID);
//            img.setImageResource(0);
//        }
    }
}
