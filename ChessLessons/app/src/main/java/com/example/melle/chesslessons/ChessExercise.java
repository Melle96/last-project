package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.lang.Integer.parseInt;
import static java.lang.StrictMath.abs;

public class ChessExercise extends AppCompatActivity implements GetPuzzle.Callback , View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
        getPuzzleNow();
        initialiseClickers();

    }
    // initialiseer fen: startpositie schaakbord
    String fen = "3r4/p2q2k1/3P1p1p/2B2Pp1/6P1/4Q2P/b7/4R1K1 w - - 0 1";

    // pgn weergeeft onder andere antwoorden puzzel
    String pgn = "[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"3r4/p2q2k1/3P1p1p/2B2Pp1/6P1/4Q2P/b7/4R1K1 w - - 0 1\"]\r\n\r\n1. Qe7+ Bf7 2. Qxf6+ Kxf6 3. Bd4# \r\n*";

    // initialiseren variabelen
    String coordinates;
    int colourOriginal;
    int moveIndex;
    String chessMoves[];
    long startTime;
    int correctPuzzleIndex;

    // er wordt een schaakpuzzel verkregen
    public void getPuzzleNow() {

        // index waarde voor correcte puzzel
        correctPuzzleIndex = 0;
        startTime = System.currentTimeMillis();

        // textvak met tegenzetten / resultaat vorige puzzel leegmaken
        changeText("");

        // nieuwe puzzel via api verkrijgen
        GetPuzzle request = new GetPuzzle(getApplicationContext());
        request.GetPuzzle(this);

        // fen splitten naar beginstelling en of de puzzel voor wit of zwart is
        String[] fenStringAndColour = fenStringSplit(fen);
        String fenString = fenStringAndColour[0];

        // functie om clickers van de schaakstukken naar de juiste kleur te zetten

        // fenstring wordt aangepast
        coordinates = fenToString(fenString);

        // bord returnen in letters
        returnBoardInText(fenString);

        // bord wordt aangemaakt met coordinaten
        createbord(coordinates);

        moveIndex = 0;
        colourOriginal = 1;
        if (fenStringAndColour[1].equals("w")) {
            colourOriginal = 0;
        }

        // kleur van de stukken klikkers wordt aangepast naar kleur
        changeColourPieceClickers(colourOriginal);

        // verkrijg de zetten vanuit de pgn
        chessMoves = ChessExerciseFunctions.obtainChessMoves(pgn);
    }



    public void showBoard() {

        for (int i = 0; i < 64; i++) {

            // initialiseren velden schaakbord
            int column = i % 8 + 1;
            int row = 8 - (i - i % 8) / 8;
            char columnChar = (char) (column + 96);

            String buttonID = String.valueOf(columnChar) + String.valueOf(row);
            int ID = getResources().getIdentifier(buttonID, "id", getPackageName());

            // schaakbord velden zichtbaar maken
            ImageView img = (ImageView) findViewById(ID);
            img.setVisibility(View.VISIBLE);
            disappear(img);
        }

        // initialiseren schaakstukklikkers
        for (int i = 0; i < 6; i++) {
            int column = i % 6 + 1;
            int row = 1 + i / 6;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            ImageView img = (ImageView) findViewById(resID);
            img.setVisibility(View.INVISIBLE);

            // laat de schaakstukklikkers tevoorschijn komen
            emerge1(img);
        }

        // initialiseren 2e rij tekstvakken
        for (int i = 0; i < 3; i++) {
            int column = i % 8 + 1;
            int row = 2 + i / 8;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView img = (TextView) findViewById(resID);
            img.setVisibility(View.INVISIBLE);

            // laat de cijfers en letters tevoorschijn komen
            emerge2(img);
        }

        // initialiseren 3e en 4e  rij tekstvakken
        for (int i = 0; i < 16; i++) {
            int column = i % 8 + 1;
            int row = 3 + i / 8;
            char c = (char) (column + 96);
            char d = (char) (row + 96);

            String buttonID = String.valueOf(d) + String.valueOf(c);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView img = (TextView) findViewById(resID);
            img.setVisibility(View.INVISIBLE);

            // laat de cijfers en letters tevoorschijn komen
            emerge2(img);
        }

        TextView img = (TextView) findViewById(R.id.notationStartPostition);
        img.setVisibility(View.INVISIBLE);
        emerge2(img);
    }

    // laat imageview verdwijnen voor aantal seconden
    public void disappear(final ImageView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    // laat imageview tevoorschijn komen voor aantal seconden
    public void emerge1(final ImageView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    // laat textview tevoorschijn komen voor aantal seconden
    public void emerge2(final TextView img) {
        img.postDelayed(new Runnable() {
            public void run() {
                img.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }


    public void confirmMove(View view) {
        EditText edit  = (EditText)findViewById(R.id.notationInText);
        String editt = edit.getText().toString();


        if (chessMoves[moveIndex].equals(editt)) {
            Log.d("erin", "erin");
            int colour = ((colourOriginal + moveIndex) % 2);
            String zet = chessMoves[moveIndex];
            Log.d("chessmoves", chessMoves[moveIndex]);
            Log.d("chessmoves2", coordinates);
            coordinates = makeChessMove(coordinates, colour, zet);
            moveIndex++;
            makeChessMove();
        }

        else{
            correctPuzzleIndex++;
            if (correctPuzzleIndex == 1) {
                changeText("Jammer!");
                int estimatedTime = (int) (System.currentTimeMillis() - startTime) / 1000;
                readFromDB(estimatedTime, "wrong");
                correctPuzzleIndex++;
            }
            // puzzel is incorrect



        }

    }

    // nieuwe puzzel wordt aangeroepen
    public void nieuwe(View view) {

        getPuzzleNow();
    }

    // schaakpuzzel verkrijgen
    @Override
    public void gotChessPuzzle(JSONObject puzzle) {
        try {

            pgn = puzzle.getString("pgn");
            fen = puzzle.getString("fen");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // schaakpuzzel verkrijgen
    @Override
    public void gotChessPuzzleError(String message)
    {
        Log.d("Error", message);
    }


    // tekst van zetten/resultaat puzzel veranderen
    public void changeText(String text) {
        TextView view = (TextView) findViewById(R.id.displayMoves);
        view.setText(text);
    }


    // startpositie schaakpuzzel in tekst weergeven
    public void returnBoardInText(String fenString){

        // strings voor witte stukken en zwarte stukken weergeven
        String white = "";
        String black = "";

        // index variabele maken om juiste element in fenString te selecteren
        int j = 0;

        // initialiseren oer het hele bord
        for (int i = 0; i < 64;) {

            // als element in fen/string getal is
            if (Character.isDigit(fenString.charAt(j))) {
                // getal in fen/string bij i optellen
                int index = Integer.parseInt(String.valueOf(fenString.charAt(j)));
                i = i + index;
                j++;
            }


            else {
                int column = i % 8 + 1;
                int row = 8 - (i - i % 8) / 8;
                char columnChar = (char) (column + 96);

                // selecteer char en voeg het met kolom en rij toe aan de string
                if (Character.isUpperCase(fenString.charAt(j))){
                    white = white + String.valueOf(fenString.charAt(j)) + columnChar + row + " ";
                    i++;
                }
                else if (Character.isLowerCase(fenString.charAt(j))){
                    black = black + String.valueOf(Character.toUpperCase(fenString.charAt(j))) + columnChar + row + " ";
                    i++;
                }
                j++;
            }
        }

        // verander de startpositie in de textview
        TextView text = (TextView) findViewById(R.id.notationStartPostition);
        text.setText("Wit: "+ white +"\n" + "Zwart: "+ black);
    }



    // schaakzet doen
    public void makeChessMove() {

        // alle zetten zijn uitgevoerd
        if (moveIndex == chessMoves.length) {

            // indien geen fout gemaakt
            if (correctPuzzleIndex == 0) {
                changeText("Great job!");
                int estimatedTime = (int) (System.currentTimeMillis() - startTime) / 1000;
                readFromDB(estimatedTime, "correct");
                correctPuzzleIndex =+ 2;
            }

            // indien wel een fout gemaakt
            else if (correctPuzzleIndex == 1){
                changeText("Now it's correct");
            }
        }

        else {

            // selecteer juiste kleur en zet
            int colour = ((colourOriginal + moveIndex) % 2);
            String move = chessMoves[moveIndex];

            // coordinates aanpassen nadat zet gemaakt
            coordinates = makeChessMove(coordinates, colour, move);
            changeText(move);
            moveIndex++;
        }
    }

    // fen String spliiten
    public String[] fenStringSplit(String fen) {
        String[] fenSplitted = fen.split(" ");

        // startpositie verkrijgen
        String fenPosition = fenSplitted[0];

        // startkleur verkrijgen
        String colour = fenSplitted[1];

        String fenStringAndColour[] = new String[2];
        fenStringAndColour[0] = fenPosition;
        fenStringAndColour[1] = colour;
        return fenStringAndColour;
    }

    // maakt een overzichtelijkere string van FED-notatie
    public String fenToString(String fed) {
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
            img.setVisibility(View.INVISIBLE);

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




    public void changeEditText(View view) {
    }

    @Override
    public void onClick(View v) {

        // de notatieinvuller wordt aangepast
        EditText edit  = (EditText)findViewById(R.id.notationInText);
        String notation = edit.getText().toString();

        // voor elke klik wordt tekst bij notatie weergeven
        switch (v.getId()) {

            case R.id.aa:
                notation = notation + "P";
                break;
            case R.id.ab:
                notation = notation + "R";
                break;
            case R.id.ac:
                notation = notation + "N";
                break;
            case R.id.ad:
                notation = notation + "B";
                break;
            case R.id.ae:
                notation = notation + "K";
                break;
            case R.id.af:
                notation = notation + "Q";
                break;

            case R.id.ba:
                notation = notation + "O-O";
                break;
            case R.id.bb:
                notation = notation + "=";
                break;
            case R.id.bc:
                notation = notation + "O-O-O";
                break;

            case R.id.ca:
                notation = notation + "a";
                break;
            case R.id.cb:
                notation = notation + "b";
                break;
            case R.id.cc:
                notation = notation + "c";
                break;
            case R.id.cd:
                notation = notation + "d";
                break;
            case R.id.ce:
                notation = notation + "e";
                break;
            case R.id.cf:
                notation = notation + "f";
                break;
            case R.id.cg:
                notation = notation + "g";
                break;
            case R.id.ch:
                notation = notation + "h";
                break;

            case R.id.da:
                notation = notation + "1";
                break;
            case R.id.db:
                notation = notation + "2";
                break;
            case R.id.dc:
                notation = notation + "3";
                break;
            case R.id.dd:
                notation = notation + "4";
                break;
            case R.id.de:
                notation = notation + "5";
                break;
            case R.id.df:
                notation = notation + "6";
                break;
            case R.id.dg:
                notation = notation + "7";
                break;
            case R.id.dh:
                notation = notation + "8";
                break;

        }

        edit.setText(notation);
    }


    // er worden clickers gezet
    public void initialiseClickers() {

        // er wordt een clicker gezet op de imageviews
        for (int i = 0; i < 6; i++) {
            int column = i % 6 + 1;
            int row = 1 + i / 6;
            char columnChar = (char) (column + 96);
            char rowChar = (char) (row + 96);

            // id selecteren
            String buttonID = String.valueOf(rowChar) + String.valueOf(columnChar);
            int ID = getResources().getIdentifier(buttonID, "id", getPackageName());
            ImageView img = (ImageView) findViewById(ID);
            img.setOnClickListener(this);

        }

        // tweede rij met 3 textviews clickers zetten
        for (int i = 0; i < 3; i++) {
            int column = i % 8 + 1;
            int row = 2 + i / 8;
            char columnChar = (char) (column + 96);
            char rowChar = (char) (row + 96);

            // bijbehorende id selecteren
            String buttonID = String.valueOf(rowChar) + String.valueOf(columnChar);
            int ID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView text = (TextView) findViewById(ID);

            // clicker zetten op textview
            text.setOnClickListener(this);

        }


        // 3e en 4e  rij met allebei 8 textviews clickers zetten
        for (int i = 0; i < 16; i++) {
            int column = i % 8 + 1;
            int row = 3 + i / 8;
            char columnChar = (char) (column + 96);
            char rowChar = (char) (row + 96);

            // bijbehorende id selecteren
            String buttonID = String.valueOf(rowChar) + String.valueOf(columnChar);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            TextView text = (TextView) findViewById(resID);

            // clicker zetten op textview
            text.setOnClickListener(this);

        }
    }

    // indien de puzzel voor zwart is worden de clickers gewijzigd naar juiste kleur

    public void changeColourPieceClickers(int colour) {

        // er wordt een clicker gezet op de imageviews
        for (int i = 0; i < 6; i++) {
            int column = i % 6 + 1;
            int row = 1 + i / 6;
            char columnChar = (char) (column + 96);
            char rowChar = (char) (row + 96);

            // id selecteren
            String buttonID = String.valueOf(rowChar) + String.valueOf(columnChar);
            int ID = getResources().getIdentifier(buttonID, "id", getPackageName());
            ImageView img = (ImageView) findViewById(ID);

            if (colour == 0){
                switch (ID) {

                    case R.id.aa:
                        img.setImageResource(R.drawable.whitepawn);
                        break;
                    case R.id.ab:
                        img.setImageResource(R.drawable.whiterook);
                        break;
                    case R.id.ac:
                        img.setImageResource(R.drawable.whiteknight);
                        break;
                    case R.id.ad:
                        img.setImageResource(R.drawable.whitebishop);
                        break;
                    case R.id.ae:
                        img.setImageResource(R.drawable.whiteking);
                        break;
                    case R.id.af:
                        img.setImageResource(R.drawable.whitequeen);
                        break;
                }
            }

            else if (colour == 1){
                switch (ID) {

                    case R.id.aa:
                        img.setImageResource(R.drawable.blackpawn);
                        break;
                    case R.id.ab:
                        img.setImageResource(R.drawable.blackrook);
                        break;
                    case R.id.ac:
                        img.setImageResource(R.drawable.blackknight);
                        break;
                    case R.id.ad:
                        img.setImageResource(R.drawable.blackbishop);
                        break;
                    case R.id.ae:
                        img.setImageResource(R.drawable.blackking);
                        break;
                    case R.id.af:
                        img.setImageResource(R.drawable.blackqueen);
                        break;
                }
            }

        }
    }

    public void doezetttt(View view) {
        makeChessMove();
        makeChessMove();
    }

    public void showBoard(View view) {
        showBoard();
    }

    public void addToDB(Score value) {

        String UID = "unknown";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
        }

        String correct = String.valueOf(Integer.parseInt(value.correct) + 1);
        Log.d("jochemXI","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");
        Score scoreee = new Score(correct,value.wrong, value.time);

        myRef.child(UID).setValue(scoreee);
    }

        // addListenerForSingleValueEvent(new ValueEventListener()
//    public void readFromDB(final int time) {
//        // Read from the database
//        final int[] index = {0,0};
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            final String UID = user.getUid();
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            final DatabaseReference myRef = database.getReference("scores");
//
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Score value = dataSnapshot.child(UID).getValue(Score.class);
//                    String valueCorrect = String.valueOf(Integer.parseInt(value.correct) + 1);
//                    String totalTime = String.valueOf(Integer.parseInt(value.time) +
//                            time);
//                    Log.d("time", String.valueOf(time));
//                    Log.d("time_total", String.valueOf(totalTime));
//
//                    if (index[0]== 0) {
//                        try {
//                            myRef.child(UID).child("correct").setValue(valueCorrect);
//                            index[0]++;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (index[1]== 0) {
//                        try {
//                            myRef.child(UID).child("time").setValue(totalTime);
//                            index[1]++;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w("failed", "Failed to read value.", error.toException());
//                }
//            });
//        }
//    }

    public void readFromDB(final int time, final String result) {
        // Read from the database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String UID = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("scores");

            myRef.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Score value = dataSnapshot.child(UID).getValue(Score.class);
                    String valueCorrect = String.valueOf(Integer.parseInt(value.correct) + 1);
                    String totalTime = String.valueOf(Integer.parseInt(value.time) + time);
                    String totalWrong = String.valueOf(Integer.parseInt(value.wrong) + 1);

                    if (result.equals("correct")) {
                        try {
                            myRef.child(UID).child("correct").setValue(valueCorrect);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    else if (result.equals("wrong")) {
                        try {
                            myRef.child(UID).child("wrong").setValue(totalWrong);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        myRef.child(UID).child("time").setValue(totalTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("failed", "Failed to read value.", error.toException());
                }
            });
        }
    }



    // doet een enkele zet
    public String makeChessMove(String currentNotation, int colour, String move) {

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

        // promotie of rokade
        if (counter == -1 || String.valueOf(move.charAt(0)).equals("O")) {

            // voer promotie uit
            if (move.length() > 3 && String.valueOf(move.charAt(move.length() - 2)).equals("=")) {
                currentNotation = promotion(currentNotation, move, piece, colour);
            }

            // voer lange rokade uit
            else if (move.equals("O-O-O")){
                currentNotation = castling(currentNotation, colour, 1);

            }

            // voer korte rokade uit
            else if (move.equals("O-O")){
                currentNotation = castling(currentNotation, colour, 0);
            }

        }

        else {

            // gaat erin als zetnotatie gelijk aan 3 is
            if (move.length() == 3) {

                // itereren over hele schaakbord
                for (int i = 0; i < currentNotation.length(); i++) {
                    if (currentNotation.charAt(i) == piece.charAt(0)) {
                        counter++;
                        myList.add(i);
                    }
                }
            }

            // gaat erin  als zetlengte 4 is en tweede symbool letter (vb: Rad8)
            else if (move.length() == 4 && Character.isLetter(move.charAt(1))) {

                // itereren over hele schaakbord
                int ascii = (int) move.charAt(1);
                int column = ascii - 97;

                // itereren over rij
                for (int i = 0; i < 8; i++) {
                    int index = 8 * i + column;
                    if (currentNotation.charAt(index) == piece.charAt(0)) {
                        counter++;
                        myList.add(index);
                    }
                }
            }

            // gaat erin  als zetlengte 4 is en tweede symbool cijfer (vb: R3d8)
            else if (move.length() == 4 && Character.isDigit(move.charAt(1))) {

                // itereren over hele schaakbord
                int row = Integer.parseInt(String.valueOf(move.charAt(1)));

                // itereren over kolom
                for (int i = 0; i < 8; i++) {
                    int index = (8 - row) * 8 + i;
                    if (currentNotation.charAt(index) == piece.charAt(0)) {
                        counter++;
                        myList.add(index);
                    }
                }
            }


            // als er maar 1 stuk is van bepaalde kleur
            if (counter == 1) {
                currentNotation = moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);

            }

            // als er meerdere stukken zijn van bepaalde kleur
            else if (counter > 1){
                currentNotation = movePieceWithTwins(counter, move, currentNotation, myList, piece);

            }
        }
        return currentNotation;
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
}
