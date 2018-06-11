package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Integer.parseInt;


    //{"title":"Saving The Draw","comments":"","url":"https://www.chess.com/forum/view/daily-puzzles/7-5-2016-saving-the-draw","publish_time":1467702000,"fen":"8/4K3/8/3k4/8/1N1B4/6p1/8 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"8/4K3/8/3k4/8/1N1B4/6p1/8 w - - 0 1\"]\r\n\r\n1. Bc4+ Kxc4 2. Nd2+ Kd3 3. Nf3\r\n*","image":"https://www.chess.com/dynboard?fen=8/4K3/8/3k4/8/1N1B4/6p1/8%20w%20-%20-%200%201&size=2"}

public class ChessExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
    }

    public void getPuzzleNow(View view) {
        String coordinates = "";
        String fed = "8/4K3/8/3k4/8/1N1B4/6p1/8";
        String[] array = fed.split("");
        int arrayLength = array.length;

        for (int i = 1; i < arrayLength; i++) {

            if (array[i].equals("r")) {
                coordinates = coordinates + "r";
            } else if (array[i].equals("n")) {

                coordinates = coordinates + "n";
            } else if (array[i].equals("b")) {
                coordinates = coordinates + "b";
            } else if (array[i].equals("q")) {
                coordinates = coordinates + "q";
            } else if (array[i].equals("k")) {
                coordinates = coordinates + "k";
            } else if (array[i].equals("p")) {
                coordinates = coordinates + "p";
            } else if (array[i].equals("R")) {
                coordinates = coordinates + "t";
            } else if (array[i].equals("N")) {
                coordinates = coordinates + "m";
            } else if (array[i].equals("B")) {
                coordinates = coordinates + "c";
            } else if (array[i].equals("Q")) {
                coordinates = coordinates + "s";
            } else if (array[i].equals("K")) {
                coordinates = coordinates + "l";
            } else if (array[i].equals("P")) {
                coordinates = coordinates + "o";
            } else if (array[i].equals("1") || array[i].equals("2") || array[i].equals("3") || array[i].equals("4") ||
                    array[i].equals("5") || array[i].equals("6") || array[i].equals("7") || array[i].equals("8"))

            {
                for (int j = 0; j < parseInt(array[i]); j++) {
                    coordinates = coordinates + "0";
                }
            }

        }
        Log.d("coordinates", coordinates);
        Log.d("coordinates_length", String.valueOf(coordinates.length()));
        String[] array_coordinates = coordinates.split("");

        for (int i = 0; i < 64; i++) {
            int column = i % 8 + 1;
            int row = 8 - (i - i % 8) / 8;
            char c = (char) (column + 96);

            String buttonID = String.valueOf(c) + String.valueOf(row);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            ImageView img = (ImageView) findViewById(resID);

            if (array_coordinates[i + 1].equals("r")) {
                img.setImageResource(R.drawable.blackrook);
            } else if (array_coordinates[i + 1].equals("n")) {

                img.setImageResource(R.drawable.blackknight);
            } else if (array_coordinates[i + 1].equals("b")) {
                img.setImageResource(R.drawable.blackbishop);
            } else if (array_coordinates[i + 1].equals("q")) {
                img.setImageResource(R.drawable.blackqueen);
            } else if (array_coordinates[i + 1].equals("k")) {
                img.setImageResource(R.drawable.blackking);
            } else if (array_coordinates[i + 1].equals("p")) {
                img.setImageResource(R.drawable.blackpawn);
            } else if (array_coordinates[i + 1].equals("t")) {
                img.setImageResource(R.drawable.whiterook);
            } else if (array_coordinates[i + 1].equals("m")) {
                img.setImageResource(R.drawable.whiteknight);
            } else if (array_coordinates[i + 1].equals("c")) {
                img.setImageResource(R.drawable.whitebishop);
            } else if (array_coordinates[i + 1].equals("s")) {
                img.setImageResource(R.drawable.whitequeen);
            } else if (array_coordinates[i + 1].equals("l")) {
                img.setImageResource(R.drawable.whiteking);
            } else if (array_coordinates[i + 1].equals("o")) {
                img.setImageResource(R.drawable.whitepawn);
            } else if (array_coordinates[i + 1].equals("0")) {
                img.setImageResource(0);
                ;
            }

        }
    }

    public void zetten(View view) {
        String string = "[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"6k1/5p2/p4npQ/4p1N1/5n2/2r3R1/P2q2PP/5R1K w - - 0 1\"]\r\n\r\n1. Ne6 fxe6 (1... Nxe6 2. Qxd2 Ne4 3. Qxc3 Nxc3 4. Rxc3) (1... N4h5 2. Qf8+ Kh7 3. Rxc3) (1... N6h5 2. Qf8+ Kh7 3. Ng5#) 2. Rxg6+ Nxg6 3. Qxg6+ Kf8 4. Qxf6+ Ke8 5. Qxe6+ Kd8 6. Rf8+ Kc7 7. Rf7+ Kd8 8. Qe7+ Kc8 9. Qb7+ Kd8 10. Rf8#\r\n*";
        String[] array = string.split("");
        int index = 0;
        int arrayindex = 1;
        int arrayLength = array.length;

        while (index != 3) {
            if (array[arrayindex].equals("]")) {
                index = index + 1;
            }
            arrayindex = arrayindex + 1;
        }

        String zettenstring = string.substring(arrayindex - 1, string.length() - 1);

        Log.d("tag", string);
        Log.d("tag2", zettenstring);

        String dejuistestring = "";
        int index2 = 1;
        int[] arrayint = new int[zettenstring.length()];
        String[] array2 = zettenstring.split("");

        for (int i = 1; i < 1 + zettenstring.length(); i++) {
            if (array2[i].equals("(")) {
                arrayint[index2] = i - 2;
                index2++;
            } else if (array2[i].equals(")")) {
                arrayint[index2] = i;
                index2++;
            }
        }
        arrayint[0] = 0;
        arrayint[index2] = zettenstring.length() - 2;


        for (int j = 0; j < index2; j = j + 2) {
            dejuistestring = dejuistestring + zettenstring.substring(arrayint[j], arrayint[j + 1]);
        }

        Log.d("tag3", dejuistestring);

    }

    public void doezet(View view) {
        String huidigenotatie = "000000000000l00000000000000k0000000000000m0c0000000000p000000000";
        String zet = "Bc4";

        Log.d("tag4", String.valueOf(zet.charAt(0)));

        if (zet.length() == 3) {
            if (String.valueOf(zet.charAt(0)).equals("B")) {

                String positions = "";
                int counter = 0;
                for (int i = 0; i < huidigenotatie.length(); i++) {
                    if (huidigenotatie.charAt(i) == 'c') {
                        counter++;
                        positions = positions + i;
                    }
                }

                if (counter == 1) {
                    int position = Integer.parseInt(String.valueOf(positions));
                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
                    char character = zet.charAt(1);
                    int column = (int) Character.toLowerCase(character) - 97;
                    Log.d("column", String.valueOf(column));
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
                    Log.d("row", String.valueOf(row));
                    int counter2 = 8 * row + column;
                    Log.d("counter", String.valueOf(counter2));
                    huidigenotatie = huidigenotatie.substring(0, counter2) + 'c' + huidigenotatie.substring(counter2+1);
                    createbord(huidigenotatie);

                }
                Log.d("positions", positions);
                Log.d("counter", String.valueOf(counter));
                huidigenotatie.indexOf('c');

            }
        }

        if (zet.length() == 4) {

        }
//            String[] array = fed.split("");
    }

    public void createbord(String coordinates) {
        String[] array_coordinates = coordinates.split("");

        for (int i = 0; i < 64; i++) {
            int column = i % 8 + 1;
            int row = 8 - (i - i % 8) / 8;
            char c = (char) (column + 96);

            String buttonID = String.valueOf(c) + String.valueOf(row);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            ImageView img = (ImageView) findViewById(resID);

            if (array_coordinates[i + 1].equals("r")) {
                img.setImageResource(R.drawable.blackrook);
            } else if (array_coordinates[i + 1].equals("n")) {

                img.setImageResource(R.drawable.blackknight);
            } else if (array_coordinates[i + 1].equals("b")) {
                img.setImageResource(R.drawable.blackbishop);
            } else if (array_coordinates[i + 1].equals("q")) {
                img.setImageResource(R.drawable.blackqueen);
            } else if (array_coordinates[i + 1].equals("k")) {
                img.setImageResource(R.drawable.blackking);
            } else if (array_coordinates[i + 1].equals("p")) {
                img.setImageResource(R.drawable.blackpawn);
            } else if (array_coordinates[i + 1].equals("t")) {
                img.setImageResource(R.drawable.whiterook);
            } else if (array_coordinates[i + 1].equals("m")) {
                img.setImageResource(R.drawable.whiteknight);
            } else if (array_coordinates[i + 1].equals("c")) {
                img.setImageResource(R.drawable.whitebishop);
            } else if (array_coordinates[i + 1].equals("s")) {
                img.setImageResource(R.drawable.whitequeen);
            } else if (array_coordinates[i + 1].equals("l")) {
                img.setImageResource(R.drawable.whiteking);
            } else if (array_coordinates[i + 1].equals("o")) {
                img.setImageResource(R.drawable.whitepawn);
            } else if (array_coordinates[i + 1].equals("0")) {
                img.setImageResource(0);
                ;
            }
        }
    }
}
