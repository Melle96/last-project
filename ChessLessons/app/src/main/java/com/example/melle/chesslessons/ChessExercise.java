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

        getPuzzleNow();

    }

    public void getPuzzleNow() {
        String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
        String coordinates = fedToString(fed);
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


    // zetten doen

    int zettenindex = 0;
    String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
    String huidigenotatie = fedToString(fed);

    public void doezet(View view) {
        String myArray[] = {"Bg6", "Kg8", "Qa2", "Ra8", "Ra1", "Kf7", "Pc4", "Pd5", "Pe5", "Pe4"};
        int colourOriginal = 0;
        int colour = ((colourOriginal + zettenindex) % 2);
        String zet = myArray[zettenindex];
        huidigenotatie = doezett(huidigenotatie, colour, zet);
        zettenindex++;

    }

    // doet een enkele zet
    public String doezett(String currentNotation, int colour, String move) {

        // lege array maken om posities stuk in op te slaan
        List<Integer> myList = new ArrayList<Integer>();

        // initilaiseert variabele die telt hoeveel stukken er van bepaalde soort zijn
        int counter = 0;

        // gaat erin als zetnotatie gelijk aan 3 is
        if (move.length() == 3) {


            // loper
            Character pieceSort = move.charAt(0);
            String piece;

            if (String.valueOf(pieceSort).equals("B")) {
                // geeft loper de juiste kleur
                piece = "c";
                if (colour == 1) {
                    piece = "b";
                }
            } else if (String.valueOf(pieceSort).equals("R")) {

                // geeft toren de juiste kleur
                piece = "t";
                if (colour == 1) {
                    piece = "r";
                }
            } else if (String.valueOf(pieceSort).equals("Q")) {

                // geeft dame de juiste kleur
                piece = "s";
                if (colour == 1) {
                    piece = "q";
                }
            } else if (String.valueOf(pieceSort).equals("P")) {

                // geeft pion de juiste kleur
                piece = "o";
                if (colour == 1) {
                    piece = "p";
                }
            } else if (String.valueOf(pieceSort).equals("N")) {

                // geeft paard juiste kleur
                piece = "m";
                if (colour == 1) {
                    piece = "n";
                }
            } else {

                // geeft koning juiste kleur
                piece = "l";
                if (colour == 1) {
                    piece = "k";
                }
            }


            // itereren over hele schaakbord
            for (int i = 0; i < currentNotation.length(); i++) {
                if (currentNotation.charAt(i) == piece.charAt(0)) {
                    counter++;
                    myList.add(i);
                }
            }

            Log.d("counter", String.valueOf(counter));
            Log.d("move", move);
            Log.d("piece", piece);


            // als er maar 1 loper is van bepaalde kleur
            if (counter == 1) {
                currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);

            }

            // als er meerdere lopers zijn van bepaalde kleur
            else {
                currentNotation = moveBishopWithTwins(counter, move, currentNotation, myList, piece);

            }
        }
        return currentNotation;
    }

//            // loper
//            if (String.valueOf(move.charAt(0)).equals("B")) {
//
//                // geeft loper de juiste kleur
//                String piece = "c";
//                if (colour == 1){
//                    piece = "b";
//                }
//
//                // itereren over hele schaakbord
//                for (int i = 0; i < currentNotation.length(); i++) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        counter++;
//                        myList.add(i);
//                    }
//                }
//
//                // als er maar 1 loper is van bepaalde kleur
//                if (counter == 1) {
//                    currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);
//
//                }
//
//                // als er meerdere lopers zijn van bepaalde kleur
//                else {
//                    currentNotation = moveBishopWithTwins(counter, move, currentNotation, myList, piece);
//
//                }
//            }
//
//
//            // Toren
//            if (String.valueOf(move.charAt(0)).equals("R")) {
//
//                // geeft toren de juiste kleur
//                String piece = "t";
//                if (colour == 1){
//                    piece = "r";
//                }
//
//                // itereren over het hele bord
//                for (int i = 0; i < currentNotation.length(); i++) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        counter++;
//                        myList.add(i);
//                    }
//                }
//
//                // als slechts 1 toren van bepaalde kleur
//                if (counter == 1) {
//                    currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);
//
//                }
//
//                // als meerdere torens van bepaalde kleur
//                else {
//                    currentNotation = moveRookWithTwins(counter, move, currentNotation, myList, piece);
//
//                }
//            }
//
//            // dame
//            if (String.valueOf(move.charAt(0)).equals("Q")) {
//
//                // geeft dame de juiste kleur
//                String piece = "s";
//                if (colour == 1){
//                    piece = "q";
//                }
//
//                // itereert over het hele bord
//                for (int i = 0; i < currentNotation.length(); i++) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        counter++;
//                        myList.add(i);
//                    }
//                }
//
//                // als slechts 1 dame op bord van bepaalde kleur
//                if (counter == 1) {
//                    currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);
//
//                }
//
//                // als meerdere dames op bord van bepaalde kleur
//                else {
//                    currentNotation = moveQueenWithTwins(counter, move, currentNotation, myList, piece);
//
//                }
//            }
//
//            // pion
//            if (String.valueOf(move.charAt(0)).equals("P")) {
//
//                // geeft pion de juiste kleur
//                String piece = "o";
//                if (colour == 1){
//                    piece = "p";
//                }
//
//                // itereert over het hele bord
//                for (int i = 0; i < currentNotation.length(); i++) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        counter++;
//                        myList.add(i);
//                    }
//                }
//
//                // als slechts 1 pion op het bord van bepaalde kleur
//                if (counter == 1) {
//                    currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);
//
//                }
//
//                // als meerdere pionnen op het bord van bepaalde kleur
//                else {
//                    currentNotation = movePawnWithTwins(counter, move, currentNotation, myList, piece);
//
//                }
//            }
//
//            // paard
//            if (String.valueOf(move.charAt(0)).equals("N")) {
//
//                // geeft paard juiste kleur
//                String piece = "m";
//                if (colour == 1){
//                    piece = "n";
//                }
//
//                // itereert over hele bord
//                for (int i = 0; i < currentNotation.length(); i++) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        counter++;
//                        myList.add(i);
//                    }
//                }
//
//                // als 1 paard op bord van bepaalde kleur
//                if (counter == 1) {
//                    currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);
//
//                }
//
//                // als meerdere paarden op bord van bepaalde kleur
//                else {
//                    currentNotation = moveKnightWithTwins(counter, move, currentNotation, myList, piece);
//
//                }
//            }
//
//            // koning
//            if (String.valueOf(move.charAt(0)).equals("K")) {
//
//                // geeft koning juiste kleur
//                String piece = "l";
//                if (colour == 1){
//                    piece = "k";
//                }
//
//                int i = 0;
//                int index = 0;
//                int position = 0;
//
//                // zoekt naar koning juiste kleur op het bord
//                while (i != currentNotation.length() && index != 1) {
//                    if (currentNotation.charAt(i) == piece.charAt(0)) {
//                        position = i;
//                        index = 1;
//                    }
//                    i++;
//                }
//
//                // verplaatst de koning
//                currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(position), piece);
//            }
//        }
//
//        // returned nieuwe bord in string
//        return currentNotation;
//    }


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
            }
        }
    }


    // verplaatst een enkel stuk over het bord
    public String moveSinglePiece(String currentNotation, String move, String position, String piece) {

        // de postitie waar het stuk vandaan komt verkijgen en laten verdwijnen
        int positionToCome = Integer.parseInt(position);
        currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome + 1);

        // positie waar stuk naartoe moet initialiseren
        char character = move.charAt(1);
        int columnToGo = (int) Character.toLowerCase(character) - 97;
        int rowToGo = 8 - Integer.parseInt(String.valueOf(move.charAt(2)));
        int positionToGo = 8 * rowToGo + columnToGo;

        // stuk op juiste positie zichtbaar maken
        currentNotation = currentNotation.substring(0, positionToGo) + piece + currentNotation.substring(positionToGo + 1);

        // bord aanpassen
        createbord(currentNotation);
        return currentNotation;

    }

    // verplaatst een loper
    public String moveBishopWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList, String piece) {

        int index = 0;
        int i = 0;
        while (index != 1 && i != counter) {
            int position = myList.get(i);
            int column1 = position % 8;
            int row1 = 8 - (position / 8);


            char character = zet.charAt(1);
            int column2 = (int) Character.toLowerCase(character) - 97;
            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));

            // richting bepalen

            int countt = 0;
            int index2 = 0;
            int direction[] = new int[2];
            int indexvariabele = 0;
            String pieceString = String.valueOf(zet.charAt(0));

            // schuin
            if ((pieceString.equals("Q") || pieceString.equals("B")) && abs(column2 - column1) == abs(row2 - row1)) {
                indexvariabele = 1;
                countt = abs(column2 - column1) - 1;

                // richting loper bepalen
                if (column2 > column1) {
                    direction[0] = 1;
                } else {
                    direction[0] = -1;
                }
                if (row2 > row1) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
            }

            //recht
            else if ((pieceString.equals("Q") || pieceString.equals("R")) && (column2 == column1 || row2 == row1)) {

                indexvariabele = 1;
                if (column2 == column1)
                {
                    countt = abs(row2 - row1) - 1;
                } else {
                    countt = abs(column2 - column1) - 1;
                }


                if (column2 > column1) {
                    direction[0] = 1;
                } else if (column2 < column1) {
                    direction[0] = -1;
                } else {
                    direction[0] = 0;
                }

                if (row2 > row1) {
                    direction[1] = 1;
                } else if (row2 < row1) {
                    direction[1] = -1;
                } else {
                    direction[1] = 0;
                }
            }

            // pion

            if (pieceString.equals("P") && column2 == column1 && String.valueOf(huidigenotatie.charAt(8*(8-row2) + column2)).equals("0")
             && (abs(column2 - column1) == 1  || abs(row2 - row1) == 2)) {

                indexvariabele = 1;
                if (column2 > column1) {
                    direction[0] = 1;
                } else {
                    direction[0] = -1;
                }
                if (row2 > row1) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
            }

            if (indexvariabele == 1) {
                for (int h = 0; h < countt; h++) {
                    int element1 = column1 + direction[0] * (h + 1);
                    int element2 = row1 + direction[1] * (h + 1);

                    int positionn = 8 * (8 - element2) + element1;

                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")) {
                        index2++;

                    }

                }

                // alle velden leeg
                if (index2 == countt) {
                    // move is legaal moet gemaakt worden
                    index = 1;


                    int column = (int) Character.toLowerCase(character) - 97;
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
                    int counter2 = 8 * row + column;

                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position + 1);
                    huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2 + 1);
                    createbord(huidigenotatie);
                    index = 1;
                }
            }
            i++;
        }
        return huidigenotatie;
    }
}


//
//    //verplaatst een loper
//    public String moveBishopWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList, String piece){
//        int index = 0;
//        int i = 0;
//        while (index != 1 && i != counter){
//            int position = myList.get(i);
//            int column1 = position % 8;
//            int row1 = 8 - (position / 8);
//
//            char character = zet.charAt(1);
//            int column2 = (int) Character.toLowerCase(character) - 97;
//            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));
//
//            if (abs(column2 - column1) == abs(row2 - row1)){
//                int countt = abs(column2 - column1) - 1;
//                int index2 = 0;
//                int direction[] = new int[2];
//
//                // richting loper bepalen
//                if (column2 > column1) {
//                    direction[0] = 1;
//                }
//                else{
//                    direction[0] = -1;
//                }
//                if (row2 > row1){
//                    direction[1] = 1;
//                }
//
//                else {
//                    direction[1] = -1;
//                }
//
//
//                for (int h = 0; h < countt; h++) {
//                    int element1 = column1 + direction[0]*(h+1);
//                    int element2 = row1 + direction[1]*(h+1);
//
//                    int positionn = 8*(8-element2) + element1;
//
//                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
//                        index2++;
//
//                    }
//
//                }
//
//                // alle velden leeg
//                if (index2 == countt){
//                    // move is legaal moet gemaakt worden
//                    index = 1;
//
//
//                    int column = (int) Character.toLowerCase(character) - 97;
//                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
//                    int counter2 = 8 * row + column;
//
//                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
//                    huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2+1);
//                    createbord(huidigenotatie);
//                    index = 1;
//                }
//            }
//            i++;
//
//
//        }
//        return huidigenotatie;
//    }
//
//    // verplaatst een toren
//    public String moveRookWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList, String piece){
//        int index = 0;
//        int i = 0;
//        while (index != 1 && i != counter){
//            int position = myList.get(i);
//            int column1 = position % 8;
//            int row1 = 8 - (position / 8);
//
//            char character = zet.charAt(1);
//            int column2 = (int) Character.toLowerCase(character) - 97;
//            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));
//
//            if (column2 == column1 || row2 == row1){
//                int countt;
//                if (column2 == column1){
//                    countt = abs(row2 - row1) - 1;
//                }
//                else {
//                    countt = abs(column2 - column1) - 1;
//                }
//
//                int index2 = 0;
//                int direction[] = new int[2];
//
//                // richting loper bepalen
//                if (column2 > column1) {
//                    direction[0] = 1;
//                }
//
//                else if (column2 < column1) {
//                    direction[0] = -1;
//                }
//
//                else{
//                    direction[0] = 0;
//                }
//
//                if (row2 > row1){
//                    direction[1] = 1;
//                }
//
//                else if (row2 < row1){
//                    direction[1] = -1;
//                }
//                else{
//                    direction[1] = 0;
//                }
//
//
//                for (int h = 0; h < countt; h++) {
//                    int element1 = column1 + direction[0]*(h+1);
//                    int element2 = row1 + direction[1]*(h+1);
//
//                    int positionn = 8*(8-element2) + element1;
//                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
//                        index2++;
//
//                    }
//
//                }
//
//                // alle velden leeg
//                if (index2 == countt){
//                    // move is legaal moet gemaakt worden
//                    index = 1;
//
//                    int column = (int) Character.toLowerCase(character) - 97;
//                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
//                    int counter2 = 8 * row + column;
//
//                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
//                    huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2+1);
//                    createbord(huidigenotatie);
//                }
//            }
//            i++;
//
//
//        }
//        return huidigenotatie;
//    }
//
//    // verplaatst een dame
//    public String moveQueenWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList, String piece){
//        int index = 0;
//        int i = 0;
//        while (index != 1 && i != counter){
//            int position = myList.get(i);
//            int column1 = position % 8;
//            int row1 = 8 - (position / 8);
//
//            char character = zet.charAt(1);
//            int column2 = (int) Character.toLowerCase(character) - 97;
//            int row2 = Integer.parseInt(String.valueOf(zet.charAt(2)));
//
//            if (column2 == column1 || row2 == row1){
//                int countt;
//                if (column2 == column1){
//                    countt = abs(row2 - row1) - 1;
//                }
//                else {
//                    countt = abs(column2 - column1) - 1;
//                }
//
//                int index2 = 0;
//                int direction[] = new int[2];
//
//                // richting dame bepalen
//                if (column2 > column1) {
//                    direction[0] = 1;
//                }
//
//                else if (column2 < column1) {
//                    direction[0] = -1;
//                }
//
//                else{
//                    direction[0] = 0;
//                }
//
//                if (row2 > row1){
//                    direction[1] = 1;
//                }
//
//                else if (row2 < row1){
//                    direction[1] = -1;
//                }
//                else{
//                    direction[1] = 0;
//                }
//
//
//                for (int h = 0; h < countt; h++) {
//                    int element1 = column1 + direction[0]*(h+1);
//                    int element2 = row1 + direction[1]*(h+1);
//
//                    int positionn = 8*(8-element2) + element1;
//                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
//                        index2++;
//
//                    }
//
//                }
//
//                // alle velden leeg
//                if (index2 == countt){
//                    // move is legaal moet gemaakt worden
//                    index = 1;
//
//                    int column = (int) Character.toLowerCase(character) - 97;
//                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
//                    int counter2 = 8 * row + column;
//
//                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
//                    huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2+1);
//                    createbord(huidigenotatie);
//                }
//            }
//
//            else if (abs(column2 - column1) == abs(row2 - row1)){
//                int countt = abs(column2 - column1) - 1;
//                int index2 = 0;
//                int direction[] = new int[2];
//
//                // richting loper bepalen
//                if (column2 > column1) {
//                    direction[0] = 1;
//                }
//                else{
//                    direction[0] = -1;
//                }
//                if (row2 > row1){
//                    direction[1] = 1;
//                }
//
//                else {
//                    direction[1] = -1;
//                }
//
//
//                for (int h = 0; h < countt; h++) {
//                    int element1 = column1 + direction[0]*(h+1);
//                    int element2 = row1 + direction[1]*(h+1);
//
//                    int positionn = 8*(8-element2) + element1;
//                    if (String.valueOf(huidigenotatie.charAt(positionn)).equals("0")){
//                        index2++;
//
//                    }
//
//                }
//
//                // alle velden leeg
//                if (index2 == countt){
//                    // move is legaal moet gemaakt worden
//                    index = 1;
//
//                    int column = (int) Character.toLowerCase(character) - 97;
//                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(2)));
//                    int counter2 = 8 * row + column;
//
//                    huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
//                    huidigenotatie = huidigenotatie.substring(0, counter2) + piece + huidigenotatie.substring(counter2+1);
//                    createbord(huidigenotatie);
//                    index = 1;
//                }
//            }
//            i++;
//
//
//        }
//        return huidigenotatie;
//    }
//
//    // verplaatst een pion
//    public String movePawnWithTwins(int counter, String Move, String currentNotation, List<Integer> myList, String piece){
//
//        // maak index variabelen
//        int index = 0;
//        int i = 0;
//
//        // zolang het juiste stuk niet gevonden is of alle stukken onderzocht zijn
//        while (index != 1 && i != counter){
//
//            // positie waar pion nu staat bepalen
//            int positionToCome = myList.get(i);
//            int columnPieceToCome = positionToCome % 8;
//            int rowPieceToCome = 8 - (positionToCome / 8);
//
//            // positie waar pion naartoe moet bepalen
//            char character = Move.charAt(1);
//            int columnPieceToGo = (int) Character.toLowerCase(character) - 97;
//            int rowPieceToGo = Integer.parseInt(String.valueOf(Move.charAt(2)));
//
//            // als kolommen van bestemming en vertrekpunt hetzelfde zijn en de bestemming van de pion leeg is
//            if (columnPieceToGo == columnPieceToCome && String.valueOf(currentNotation.charAt(8*(8-rowPieceToGo) + columnPieceToGo)).equals("0")
//                    && (abs(rowPieceToGo - rowPieceToCome) == 1  || abs(rowPieceToGo - rowPieceToCome) == 2)){
//
//                // telt aantal tussengelegen velden tussen bestemming en vertrekpunt
//                int countt = abs(rowPieceToGo - rowPieceToCome) - 1;
//                int index2 = 0;
//
//                // richting bepalen van de pion
//                int direction;
//
//                // richting pion bepalen
//                if (rowPieceToGo > rowPieceToCome){
//                    direction = 1;
//                }
//
//                else{
//                    direction = -1;
//                }
//
//
//                for (int h = 0; h < countt; h++) {
//                    int column = columnPieceToCome;
//                    int row = rowPieceToCome + direction*(h+1);
//
//                    int position = 8*(8-row) + column;
//                    if (String.valueOf(currentNotation.charAt(position)).equals("0")){
//                        index2++;
//                    }
//
//                }
//
//                // alle velden leeg
//                if (index2 == countt){
//
//                    // move is legaal moet gemaakt worden
//                    index = 1;
//                    int PositionToGo = 8 * (8 - rowPieceToGo) + columnPieceToGo;
//
//                    currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome+1);
//                    currentNotation = currentNotation.substring(0, PositionToGo) + piece + currentNotation.substring(PositionToGo+1);
//                    createbord(currentNotation);
//                }
//            }
//
//            else if (abs(columnPieceToGo - columnPieceToCome) == 1 && abs(rowPieceToGo - rowPieceToCome) == 1
//                    && !String.valueOf(currentNotation.charAt(8*(8-rowPieceToGo) + columnPieceToGo)).equals("0")){
//
//                // move is legaal moet gemaakt worden
//                index = 1;
//                int PositionPieceToGo = 8 * (8 - rowPieceToGo) + columnPieceToGo;
//
//                currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome+1);
//                currentNotation = currentNotation.substring(0, PositionPieceToGo) + piece + currentNotation.substring(PositionPieceToGo+1);
//                createbord(currentNotation);
//            }
//            i++;
//
//
//        }
//        return currentNotation;
//    }
//
//
//
//    // verplaats het paard
//    public String moveKnightWithTwins(int counter, String move, String currentNotation, List<Integer> myList, String piece){
//
//        // aanmaken index variabelen
//        int index = 0;
//        int i = 0;
//
//        // zolang niet het juiste stuk is gevonden of alle stukken is afgegaan
//        while (index != 1 && i != counter){
//
//            // positie eerste paard
//            int positionToCome = myList.get(i);
//            int columnPieceToCome = positionToCome % 8;
//            int rowPieceToCome = 8 - (positionToCome / 8);
//
//            // bepaal waar het paard naartoe moet
//            char character = move.charAt(1);
//            int columnPieceToGo = (int) Character.toLowerCase(character) - 97;
//            int rowPieceToGo = Integer.parseInt(String.valueOf(move.charAt(2)));
//
//            // als er een paardzet mogelijk is van het stuk naar de eindpositie
//            if (abs(columnPieceToGo - columnPieceToCome) == 2 && abs(rowPieceToGo - rowPieceToCome) == 1 ||
//                    abs(columnPieceToGo - columnPieceToCome) == 1 && abs(rowPieceToGo - rowPieceToCome) == 2){
//
//
//                // verplaats het stuk door veld waar het vandaan kwam leeg te maken, en waar her naartoe gaat een paard te zetten
//                int positionToGo = 8 * (8- rowPieceToGo) + columnPieceToGo;
//                currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome+1);
//                currentNotation = currentNotation.substring(0, positionToGo) + piece + currentNotation.substring(positionToGo+1);
//                createbord(currentNotation);
//                index = 1;
//
//            }
//            i++;
//        }
//        // return het bord in de notatie string
//        return currentNotation;
//    }
