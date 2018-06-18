package com.example.melle.chesslessons;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Character.isUpperCase;
import static java.lang.Integer.parseInt;
import static java.lang.StrictMath.abs;

//{"title":"Chapais, 1780","url":"https:\/\/www.chess.com\/forum\/view\/daily-puzzles\/1-2-2018-chapais-1780","publish_time":1514880000,"fen":"8\/8\/1n6\/8\/1K5k\/3P4\/B7\/8 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"8\/8\/1n6\/8\/1K5k\/3P4\/B7\/8 w - - 0 1\"]\r\n\r\n1. Be6 Na8 2. Kc5 Nc7 3. Bf7 Na6+ 4. Kb5 Nb8 5. Be8 Kg5 6. Kb6 Kf4 7. Bb5\r\nKe3 8. Kc7\r\n*","image":"https:\/\/www.chess.com\/dynboard?fen=8\/8\/1n6\/8\/1K5k\/3P4\/B7\/8%20w%20-%20-%200%201&size=2"}
//{"title":"Mate in 4","comments":"","url":"https://www.chess.com/forum/view/daily-puzzles/692015---mate-in-4","publish_time":1433833200,"fen":"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1\"]\r\n\r\n1. Bg6+ Kg8 2. Qa2+ d5 3. Qxd5+ Qf7 4. Qxf7#\r\n*","image":"https://www.chess.com/dynboard?fen=1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1%20w%20-%20-%200%201&size=2"}

//black
//{"title":"Short and Decisive","comments":"","url":"https:\/\/www.chess.com\/forum\/view\/daily-puzzles\/9292009---short-and-decisive","publish_time":1254207600,"fen":"4Q3\/5p1k\/7p\/p1p3nq\/P2r2p1\/6P1\/3NRP2\/6K1 b - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"4Q3\/5p1k\/7p\/p1p3nq\/P2r2p1\/6P1\/3NRP2\/6K1 b - - 0 1\"]\r\n\r\n1...Rxd2 2. Rxd2 Nf3+\r\n*","image":"https:\/\/www.chess.com\/dynboard?fen=4Q3\/5p1k\/7p\/p1p3nq\/P2r2p1\/6P1\/3NRP2\/6K1%20b%20-%20-%200%201&size=2"}


public class ChessExercise extends AppCompatActivity implements GetPuzzle.Callback , View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
        getPuzzleNow();
        initialiseClickers();

    }

    // {"title":"The Quiet Moves are the Hardest","comments":"","url":"https:\/\/www.chess.com\/forum\/view\/daily-puzzles\/7192007---the-quiet-moves-are-the-hardest","publish_time":1184828400,"fen":"8\/2N2k1p\/5pp1\/4p3\/p5PP\/1Pn2Q2\/3q1PK1\/8 w - - 0 1","pgn":"[FEN \"8\/2N2k1p\/5pp1\/4p3\/p5PP\/1Pn2Q2\/3q1PK1\/8 w - - 0 1\"]\r\n[PlyCount \"13\"]\r\n1. Qc6 {threatens Qe8+} Kg7 (1... Qd8 2. Qxc3 a3 3. Qc4+ Ke7 4.\r\nNd5+ Kf8 5. b4 {W has a N for a P}) 2. Qe8 Qd6 3. Ne6+ Kh6 4. Qf7 {\r\nand mate on g7} Qc6+ 5. Kh2 Qd7 6. Qxd7 axb3 7. Qg7# *","image":"https:\/\/www.chess.com\/dynboard?fen=8\/2N2k1p\/5pp1\/4p3\/p5PP\/1Pn2Q2\/3q1PK1\/8%20w%20-%20-%200%201&size=2"}
    String fen = "8/5pNk/2K5/6P1/2r5/8/6B1/8 w - - 0 1";
    String pgn = "[Date \"????.??.??\"]\r\n" +
            "[Result \"*\"]\r\n" +
            "[FEN \"8/5pNk/2K5/6P1/2r5/8/6B1/8 w - - 0 1\"]\r\n\r" +
            "\n" +
            "1. Kd5 Rg4 2. Bh3 Rg3 3. Ne6 Rxh3 (3... fxe6+ 4. Bxe6) 4. g6+ Kxg6 (4... fxg6 5. Ng5+) 5. Nf4+";

    public void verdwijn1() {

        for (int i = 0; i < 64; i++) {
            int column = i % 8 + 1;
            int row = 8 - (i - i % 8) / 8;
            char c = (char) (column + 96);

            String buttonID = String.valueOf(c) + String.valueOf(row);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            ImageView img = (ImageView) findViewById(resID);
            img.setVisibility(View.VISIBLE);
            verdwijn2(img);
        }

        for (int i = 0; i < 12; i++) {
            int column = i % 6 + 1;
            int row = 1 + i / 6;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setVisibility(View.INVISIBLE);
            tevoorschijn(img);
        }

        for (int i = 0; i < 16; i++) {
            int column = i % 8 + 1;
            int row = 3 + i / 8;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView img = (TextView) findViewById(resID);
            img.setVisibility(View.INVISIBLE);
            tevoorschijn2(img);
        }
    }

    public void verdwijn2(final ImageView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    public void tevoorschijn(final ImageView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    public void tevoorschijn2(final TextView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }


    public void confirmMove(View view) {
        EditText edit  = (EditText)findViewById(R.id.editText2);
        String editt = edit.getText().toString();

        Log.d("jochem23", chessMoves[zettenindex]);
        Log.d("jochem24", editt);

        if (chessMoves[zettenindex].equals(editt)) {
            Log.d("erin", "erin");
            int colour = ((colourOriginal + zettenindex) % 2);
            String zet = chessMoves[zettenindex];
            Log.d("chessmoves", chessMoves[zettenindex]);
            Log.d("chessmoves2", coordinates);
            coordinates = doezett(coordinates, colour, zet);
            zettenindex++;
        }

        else{

            // fout gemaakt
        }

    }

    public void nieuwe(View view) {
        getPuzzleNow();
    }

    @Override
    public void gotQuestions(JSONObject questions) {
        Log.d("blij", String.valueOf(questions));
        try {

            pgn = questions.getString("pgn");
            fen = questions.getString("fen");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotQuestionsError(String message) {
        Log.d("boosheid", message);
    }


    String coordinates;
    int colourOriginal;
    int zettenindex;
    String chessMoves[];

    public void getPuzzleNow() {

        //verdwijn1();
        GetPuzzle request = new GetPuzzle(getApplicationContext());
        request.GetPuzzle(this);

        Log.d("test3421312", fen);
        String[] fenStringAndColour = fenStringSplit(fen);
        String fenString = fenStringAndColour[0];

        coordinates = fedToString(fenString);
        createbord(coordinates);

        zettenindex = 0;
        colourOriginal = 1;
        if (fenStringAndColour[1].equals("w")) {
            colourOriginal = 0;
        }


        chessMoves = zettenn(pgn);
    }

    // zetten doen


    public void doezet(View view) {
        if (zettenindex == chessMoves.length) {
            getPuzzleNow();
        } else {
            int colour = ((colourOriginal + zettenindex) % 2);
            String zet = chessMoves[zettenindex];
            Log.d("chessmoves", chessMoves[zettenindex]);
            Log.d("chessmoves2", coordinates);
            coordinates = doezett(coordinates, colour, zet);
            zettenindex++;
        }
    }

    public String[] fenStringSplit(String fen) {
        String[] fenSplitted = fen.split(" ");

        String fenPosition = fenSplitted[0];
        String colour = fenSplitted[1];

        String fenStringAndColour[] = new String[2];
        fenStringAndColour[0] = fenPosition;
        fenStringAndColour[1] = colour;
        return fenStringAndColour;
    }


    public String[] zettenn(String string) {
        //String string = "[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1\"]\r\n\r\n1. Bg6+ Kg8 2. Qa2+ d5 3. Qxd5+ Qf7 4. Qxf7#\r\n*";
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
        arrayint[index2] = zettenstring.length();


        for (int j = 0; j < index2; j = j + 2) {
            dejuistestring = dejuistestring + zettenstring.substring(arrayint[j], arrayint[j + 1]);
            Log.d("handig", zettenstring.substring(arrayint[j], arrayint[j + 1]));
        }

        Log.d("tag3", dejuistestring);

        // soms geen spatie maar \r dus extra spatie erbij zetten
        for (int j = 0; j < dejuistestring.length(); j++) {
            if (Character.isUpperCase(dejuistestring.charAt(j)) && j > 1) {
                if (!String.valueOf(dejuistestring.charAt(j - 1)).equals("=") && !String.valueOf(dejuistestring.charAt(j - 1)).equals(" ")
                        && !String.valueOf(dejuistestring.charAt(j - 1)).equals("-")) {
                    Log.d("jochem", "pvda");
                    dejuistestring = dejuistestring.substring(0, j) + " " + dejuistestring.substring(j, dejuistestring.length());
                }
            }
        }

        Log.d("tag4", dejuistestring);
        String[] array3 = dejuistestring.split(" ");
        String[] array4 = new String[array3.length];
        Log.d("melle", dejuistestring);

        int countOfMoves = 0;
        int indexxx = 0;
        for (int j = 1; j < array3.length; j++) {
            Log.d("belangrijk", array3[j]);
            if (Character.isDigit(array3[j].charAt(0))) {
                Log.d("123", "123");
            } else {
                int indexxxx = 1;
                String[] array5 = array3[j].split("");
                String moveString = "";
                if (indexxxx == 1 && !isUpperCase(array5[indexxxx].charAt(0))) {
                    moveString = moveString + "P";
                }
                for (int h = 1; h < array5.length; h++) {
                    Log.d("sss", array5[h]);
                    if (!array5[h].equals("x") && !array5[h].equals("+") && !array5[h].equals("#")
                            && !array5[h].equals("r") && !array5[h].equals("\\")) {
                        moveString = moveString + array5[h];
                    }

                }

                moveString = moveString.replaceAll("(\\r|\\n)", "");
                array4[indexxx] = moveString;
                Log.d("hoi", array4[indexxx]);
                Log.d("move", moveString);
                indexxx++;
                countOfMoves++;
            }
        }

        String[] arrayWithMoves = new String[countOfMoves];

        Log.d("count", String.valueOf(countOfMoves));
        for (int j = 0; j < countOfMoves; j++) {
            arrayWithMoves[j] = array4[j];
            Log.d("yesss", arrayWithMoves[j]);
        }
        return arrayWithMoves;
    }


    // doet een enkele zet
    public String doezett(String currentNotation, int colour, String move) {

        // zetten vrijmaken van eventuele \r of \n
        move = move.replaceAll("(\\r|\\n)", "");

        // lege array maken om posities stuk in op te slaan
        List<Integer> myList = new ArrayList<Integer>();

        // initilaiseert variabele die telt hoeveel stukken er van bepaalde soort zijn
        int counter = 0;

        // selecteer het stuk
        Character pieceSort = move.charAt(0);

        // kijken of er een promotie is:
        if (move.length() > 3 && String.valueOf(move.charAt(3)).equals("=")) {
            pieceSort = move.charAt(4);
            counter = -1;
        }

        String piece;

        if (String.valueOf(pieceSort).equals("B")) {
            // geeft loper de juiste kleur
            Log.d("lopertje", move);
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

        if (counter == -1 || String.valueOf(move.charAt(0)).equals("O")) {
            if (move.length() > 3 && String.valueOf(move.charAt(move.length() - 2)).equals("=")) {
                Log.d("Gelok", "jaja");
                currentNotation = promotion(currentNotation, move, piece, colour);
            }

            else if (move.equals("O-O-O")){
                currentNotation = castling(currentNotation, colour, 1);
                counter = -1;

            }
            else if (move.equals("O-O")){
                currentNotation = castling(currentNotation, colour, 0);
                counter = -1;
            }

        }

        else {
            // gaat erin als zetnotatie gelijk aan 3 is
            if (move.length() == 3) {
                // itereren over hele schaakbord
                Log.d("lopertje2", move);
                for (int i = 0; i < currentNotation.length(); i++) {
                    if (currentNotation.charAt(i) == piece.charAt(0)) {
                        counter++;
                        myList.add(i);
                    }
                }
            } else if (move.length() == 4 && Character.isLetter(move.charAt(1))) {
                Log.d("hier moet ik zitten", "jaa");
                // itereren over hele schaakbord
                int ascii = (int) move.charAt(1);
                int column = ascii - 97;

                for (int i = 0; i < 8; i++) {
                    int index = 8 * i + column;
                    if (currentNotation.charAt(index) == piece.charAt(0)) {
                        counter++;
                        myList.add(index);
                    }
                }
            } else if (move.length() == 4 && Character.isDigit(move.charAt(1))) {
                // itereren over hele schaakbord
                int row = Integer.parseInt(String.valueOf(move.charAt(1)));

                for (int i = 0; i < 8; i++) {
                    int index = (8 - row) * 8 + i;
                    if (currentNotation.charAt(index) == piece.charAt(0)) {
                        counter++;
                        myList.add(index);
                    }
                }
            }


            // als er maar 1 loper is van bepaalde kleur
            if (counter == 1) {
                currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);

            }

            // als er meerdere lopers zijn van bepaalde kleur
            else if (counter > 1){
                currentNotation = movePieceWithTwins(counter, move, currentNotation, myList, piece);

            }
        }
        return currentNotation;
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
            }
        }
    }


    // verplaatst een enkel stuk over het bord
    public String moveSinglePiece(String currentNotation, String move, String position, String piece) {


        // de postitie waar het stuk vandaan komt verkijgen en laten verdwijnen
        int positionToCome = Integer.parseInt(position);
        currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome + 1);

        // positie waar stuk naartoe moet initialiseren
        char character = move.charAt(move.length()-2);
        int columnToGo = (int) Character.toLowerCase(character) - 97;
        int rowToGo = 8 - Integer.parseInt(String.valueOf(move.charAt(move.length()-1)));
        int positionToGo = 8 * rowToGo + columnToGo;

        // stuk op juiste positie zichtbaar maken
        currentNotation = currentNotation.substring(0, positionToGo) + piece + currentNotation.substring(positionToGo + 1);

        // bord aanpassen
        createbord(currentNotation);
        return currentNotation;

    }



    // verplaatst een stuk als er meerdere van op het bord staan
    public String movePieceWithTwins(int counter, String zet, String huidigenotatie, List<Integer> myList, String piece) {

        int index = 0;
        int i = 0;
        while (index != 1 && i != counter) {
            int position = myList.get(i);
            int column1 = position % 8;
            int row1 = 8 - (position / 8);


            char character = zet.charAt(zet.length()-2);
            int column2 = (int) Character.toLowerCase(character) - 97;
            int row2 = Integer.parseInt(String.valueOf(zet.charAt(zet.length()-1)));

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

                Log.d("lopertje", zet);
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


            // pion recht
            else if (pieceString.equals("P") && column2 == column1 && String.valueOf(huidigenotatie.charAt(8*(8-row2) + column2)).equals("0")
                    && (abs(row2 - row1) == 1  || abs(row2 - row1) == 2)) {
                indexvariabele = 1;
                if (row2 > row1) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
                    direction[0] = 0;
            }

            // pion slaan

            else if (pieceString.equals("P") && !String.valueOf(huidigenotatie.charAt(8*(8-row2) + column2)).equals("0")
                    && abs(column2 - column1) == 1  && abs(row2 - row1) == 1) {

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


            // paard
            else if (pieceString.equals("N") && (abs(column2 - column1) == 2 && abs(row2 - row1) == 1 ||
                    abs(column2 - column1) == 1 && abs(row2 - row1) == 2)){


                // verplaats het stuk door veld waar het vandaan kwam leeg te maken, en waar her naartoe gaat een paard te zetten
                int positionToGo = 8 * (8- row2) + column2;
                huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position+1);
                huidigenotatie = huidigenotatie.substring(0, positionToGo) + piece + huidigenotatie.substring(positionToGo+1);
                createbord(huidigenotatie);
                index = 1;

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

                    int column = (int) Character.toLowerCase(character) - 97;
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(zet.length()-1)));
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


    public String promotion(String huidigenotatie, String move, String piece, int colour) {

        char character = move.charAt(move.length()-4);
        int position;
        int position2;
        if (colour == 1) {

            position = (int) Character.toLowerCase(character) - 97 + 8*6;
            position2 = position +8;
        }

        else{
            position = (int) Character.toLowerCase(character) - 97 + 8;
            position2 = position - 8;
        }

        Log.d("position", String.valueOf(position));
        huidigenotatie = huidigenotatie.substring(0, position) + '0' + huidigenotatie.substring(position + 1);
        huidigenotatie = huidigenotatie.substring(0, position2) + piece + huidigenotatie.substring(position2 + 1);
        createbord(huidigenotatie);
        return huidigenotatie;

    }


    private String castling(String huidigenotatie, int colour, int i) {

        String pieceRook;
        String pieceKing;
        int positionRook;
        int positionRook2;
        int positionKing;
        int positionKing2;

        // geeft toren de juiste kleur
        pieceRook = "t";
        if (colour == 1) {
            pieceRook = "r";
        }

        // geeft koning juiste kleur
        pieceKing = "l";
        if (colour == 1) {
            pieceKing = "k";
        }

        if (i == 1) {
            positionRook = 56 * (1 -colour);
            positionRook2 = 3 + 56 * (1 -colour);

            positionKing = 4 + 56 * (1 -colour);
            positionKing2 = 2 + 56 * (1 -colour);
        }

        else {
            positionRook = 7 + 56 * (1 -colour);
            positionRook2 = 5 + 56 * (1 -colour);

            positionKing = 4 + 56 * (1 -colour);
            positionKing2 = 6 + 56 * (1 -colour);
        }


        huidigenotatie = huidigenotatie.substring(0, positionRook) + '0' + huidigenotatie.substring(positionRook + 1);
        huidigenotatie = huidigenotatie.substring(0, positionKing) + '0' + huidigenotatie.substring(positionKing + 1);

        huidigenotatie = huidigenotatie.substring(0, positionRook2) + pieceRook + huidigenotatie.substring(positionRook2 + 1);
        huidigenotatie = huidigenotatie.substring(0, positionKing2) + pieceKing + huidigenotatie.substring(positionKing2 + 1);

        createbord(huidigenotatie);

        return huidigenotatie;

    }


    public void changeEditText(View view) {
    }

    @Override
    public void onClick(View v) {

        EditText edit  = (EditText)findViewById(R.id.editText2);
        String editt = edit.getText().toString();

        switch (v.getId()) {

            case R.id.aa:
                editt = editt + "P";
                break;
            case R.id.ab:
                editt = editt + "R";
                break;
            case R.id.ac:
                editt = editt + "N";
                break;
            case R.id.ad:
                editt = editt + "B";
                break;
            case R.id.ae:
                editt = editt + "K";
                break;
            case R.id.af:
                editt = editt + "Q";
                break;

            case R.id.ba:
                editt = editt + "R";
                break;
            case R.id.bb:
                editt = editt + "N";
                break;
            case R.id.bc:
                editt = editt + "B";
                break;
            case R.id.bd:
                editt = editt + "K";
                break;
            case R.id.be:
                editt = editt + "Q";
                break;
            case R.id.bf:
                editt = editt + "P";
                break;

            case R.id.ca:
                editt = editt + "a";
                break;
            case R.id.cb:
                editt = editt + "b";
                break;
            case R.id.cc:
                editt = editt + "c";
                break;
            case R.id.cd:
                editt = editt + "d";
                break;
            case R.id.ce:
                editt = editt + "e";
                break;
            case R.id.cf:
                editt = editt + "f";
                break;
            case R.id.cg:
                editt = editt + "g";
                break;
            case R.id.ch:
                editt = editt + "h";
                break;

            case R.id.da:
                editt = editt + "1";
                break;
            case R.id.db:
                editt = editt + "2";
                break;
            case R.id.dc:
                editt = editt + "3";
                break;
            case R.id.dd:
                editt = editt + "4";
                break;
            case R.id.de:
                editt = editt + "5";
                break;
            case R.id.df:
                editt = editt + "6";
                break;
            case R.id.dg:
                editt = editt + "7";
                break;
            case R.id.dh:
                editt = editt + "8";
                break;

        }

        edit.setText(editt);
    }

    public void initialiseClickers() {

        for (int i = 0; i < 12; i++) {
            int column = i % 6 + 1;
            int row = 1 + i / 6;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);

            img.setOnClickListener(this); // calling onClick() method

        }

        for (int i = 0; i < 16; i++) {
            int column = i % 8 + 1;
            int row = 3 + i / 8;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView img = (TextView) findViewById(resID);
            img.setOnClickListener(this); // calling onClick() method

        }
    }

}
