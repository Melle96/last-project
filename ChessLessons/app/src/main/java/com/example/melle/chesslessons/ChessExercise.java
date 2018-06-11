package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.StrictMath.abs;


//{"title":"Mate in 4","comments":"","url":"https://www.chess.com/forum/view/daily-puzzles/692015---mate-in-4","publish_time":1433833200,"fen":"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1\"]\r\n\r\n1. Bg6+ Kg8 2. Qa2+ d5 3. Qxd5+ Qf7 4. Qxf7#\r\n*","image":"https://www.chess.com/dynboard?fen=1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1%20w%20-%20-%200%201&size=2"}
public class ChessExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
    }

    public void getPuzzleNow(View view) {
        String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
        ;
        String coordinates = fedToString(fed);
        Log.d("coordinates", coordinates);
        Log.d("coordinates_length", String.valueOf(coordinates.length()));
        createbord(coordinates);
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
        //String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
        String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
        String huidigenotatie = fedToString(fed);
        String zet = "Rf1";


        if (zet.length() == 3) {
            // Loper
            if (String.valueOf(zet.charAt(0)).equals("B")) {

                List<Integer> myList = new ArrayList<Integer>();
                int counter = 0;

                // itereren over hele schaakbord
                for (int i = 0; i < huidigenotatie.length(); i++) {
                    if (huidigenotatie.charAt(i) == 'c') {
                        counter++;
                        myList.add(i);
                    }
                }

                if (counter == 1) {
                    moveSinglePiece(huidigenotatie, zet, String.valueOf(myList.get(0)), "c");

                } else {
                    moveBishopWithTwins(counter, zet, huidigenotatie, myList);

                }

            }


            // Toren
            if (String.valueOf(zet.charAt(0)).equals("R")) {

                List<Integer> myList = new ArrayList<Integer>();
                int counter = 0;


                for (int i = 0; i < huidigenotatie.length(); i++) {
                    if (huidigenotatie.charAt(i) == 't') {
                        counter++;
                        myList.add(i);
                    }
                }

                if (counter == 1) {
                    moveSinglePiece(huidigenotatie, zet, String.valueOf(myList.get(0)), "t");

                } else {
                    moveRookWithTwins(counter, zet, huidigenotatie, myList);

                }
            }

            // Dame
            if (String.valueOf(zet.charAt(0)).equals("Q")) {

                List<Integer> myList = new ArrayList<Integer>();
                int counter = 0;


                for (int i = 0; i < huidigenotatie.length(); i++) {
                    if (huidigenotatie.charAt(i) == 's') {
                        counter++;
                        myList.add(i);
                    }
                }

                if (counter == 1) {
                    moveSinglePiece(huidigenotatie, zet, String.valueOf(myList.get(0)), "s");

                }
                else {
                    moveQueenWithTwins(counter, zet, huidigenotatie, myList);

                }
            }
        }
    }

    // maakt een overzichtelijkere string van FED-notatie
    public String fedToString(String fed) {
        String coordinates = "";
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
        return coordinates;
    }

    // maak bord naar notatie
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

    // verplaatst een enkel stuk over het bord
    public void moveSinglePiece(String huidigenotatie, String zet, String list, String piece){
        int position = Integer.parseInt(list);
        huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
        char character = zet.charAt(1);
        int column = (int) Character.toLowerCase(character) - 97;
        Log.d("column", String.valueOf(column));
        int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
        Log.d("row", String.valueOf(row));
        int counter2 = 8 * row + column;
        Log.d("counter", String.valueOf(counter2));
        huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2+1);
        createbord(huidigenotatie);

    }

    // verplaatst een loper
    public void moveBishopWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList){
        int index = 0;
        int i = 0;
        while (index != 1 && i != counter){
            Log.d("hier1", "hier1");
            int position = myList.get(i);
            int column1 = position % 8;
            int row1 = 8 - (position / 8);
            Log.d("row1", String.valueOf(row1));
            char character = zet.charAt(1);
            int column2 = (int) Character.toLowerCase(character) - 97;
            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));
            Log.d("row2", String.valueOf(row2));

            Log.d("test", String.valueOf(abs(column2 - column1)));
            Log.d("test2", String.valueOf(abs(row2 - row1)));
            if (abs(column2 - column1) == abs(row2 - row1)){
                int countt = abs(column2 - column1) - 1;
                int index2 = 0;
                int direction[] = new int[2];

                // richting loper bepalen
                if (column2 > column1) {
                    direction[0] = 1;
                }
                else{
                    direction[0] = -1;
                }
                if (row2 > row1){
                    direction[1] = 1;
                }

                else {
                    direction[1] = -1;
                }


                for (int h = 0; h < countt; h++) {
                    Log.d("yeah", String.valueOf(h));
                    int element1 = column1 + direction[0]*(h+1);
                    Log.d("element1", String.valueOf(element1));
                    int element2 = row1 + direction[1]*(h+1);
                    Log.d("element2", String.valueOf(element2));

                    int positionn = 8*(8-element2) + element1;
                    Log.d("nu~!pos", String.valueOf(positionn));
                    Log.d("jochem2", String.valueOf(huidigenotatie.charAt(positionn)));
                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
                        Log.d("hier1234", "hier1234");
                        index2++;

                    }

                }

                Log.d("index2", String.valueOf(index2));
                Log.d("countt", String.valueOf(countt));
                // alle velden leeg
                if (index2 == countt){
                    // move is legaal moet gemaakt worden
                    index = 1;
                    Log.d("jajaja", "jjJj");


                    int column = (int) Character.toLowerCase(character) - 97;
                    Log.d("column", String.valueOf(column));
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
                    Log.d("row", String.valueOf(row));
                    int counter2 = 8 * row + column;

                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
                    huidigenotatie = huidigenotatie.substring(0, counter2) + 'c' + huidigenotatie.substring(counter2+1);
                    createbord(huidigenotatie);
                    index = 1;
                }
            }
            i++;


        }
    }

    // verplaatst een toren
    public void moveRookWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList){
        int index = 0;
        int i = 0;
        while (index != 1 && i != counter){
            int position = myList.get(i);
            int column1 = position % 8;
            int row1 = 8 - (position / 8);

            char character = zet.charAt(1);
            int column2 = (int) Character.toLowerCase(character) - 97;
            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));

            if (column2 == column1 || row2 == row1){
                int countt;
                if (column2 == column1){
                    countt = abs(row2 - row1) - 1;
                }
                else {
                    countt = abs(column2 - column1) - 1;
                }

                int index2 = 0;
                int direction[] = new int[2];

                // richting loper bepalen
                if (column2 > column1) {
                    direction[0] = 1;
                }

                else if (column2 < column1) {
                    direction[0] = -1;
                }

                else{
                    direction[0] = 0;
                }

                if (row2 > row1){
                    direction[1] = 1;
                }

                else if (row2 < row1){
                    direction[1] = -1;
                }
                else{
                    direction[1] = 0;
                }


                for (int h = 0; h < countt; h++) {
                    int element1 = column1 + direction[0]*(h+1);
                    int element2 = row1 + direction[1]*(h+1);

                    int positionn = 8*(8-element2) + element1;
                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
                        index2++;

                    }

                }

                // alle velden leeg
                if (index2 == countt){
                    // move is legaal moet gemaakt worden
                    index = 1;

                    int column = (int) Character.toLowerCase(character) - 97;
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
                    int counter2 = 8 * row + column;

                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
                    huidigenotatie = huidigenotatie.substring(0, counter2) + 't' + huidigenotatie.substring(counter2+1);
                    createbord(huidigenotatie);
                }
            }
            i++;


        }
    }

    // verplaatst een toren
    public void moveQueenWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList){
        int index = 0;
        int i = 0;
        while (index != 1 && i != counter){
            int position = myList.get(i);
            int column1 = position % 8;
            int row1 = 8 - (position / 8);

            char character = zet.charAt(1);
            int column2 = (int) Character.toLowerCase(character) - 97;
            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));

            if (column2 == column1 || row2 == row1){
                int countt;
                if (column2 == column1){
                    countt = abs(row2 - row1) - 1;
                }
                else {
                    countt = abs(column2 - column1) - 1;
                }

                int index2 = 0;
                int direction[] = new int[2];

                // richting dame bepalen
                if (column2 > column1) {
                    direction[0] = 1;
                }

                else if (column2 < column1) {
                    direction[0] = -1;
                }

                else{
                    direction[0] = 0;
                }

                if (row2 > row1){
                    direction[1] = 1;
                }

                else if (row2 < row1){
                    direction[1] = -1;
                }
                else{
                    direction[1] = 0;
                }


                for (int h = 0; h < countt; h++) {
                    int element1 = column1 + direction[0]*(h+1);
                    int element2 = row1 + direction[1]*(h+1);

                    int positionn = 8*(8-element2) + element1;
                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
                        index2++;

                    }

                }

                // alle velden leeg
                if (index2 == countt){
                    // move is legaal moet gemaakt worden
                    index = 1;

                    int column = (int) Character.toLowerCase(character) - 97;
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
                    int counter2 = 8 * row + column;

                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
                    huidigenotatie = huidigenotatie.substring(0, counter2) + 't' + huidigenotatie.substring(counter2+1);
                    createbord(huidigenotatie);
                }
            }
            i++;


        }
    }
}
