package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static java.lang.Integer.parseInt;

// deze activity maakt het mogelijk schaakpuzzels te krijgen en deze op te lossen
public class ChessExercise extends AppCompatActivity implements GetPuzzle.Callback , View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);

        getPuzzleNow();
        initialiseClickers();

    }

    String fen = "r5k1/5p1p/p1q2p2/1pb4N/8/P1Q2PP1/5P1P/3R2K1 b - - 0 1";
    String pgn= "[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"r5k1/5p1p/p1q2p2/1pb4N/8/P1Q2PP1/5P1P/3R2K1 b - - 0 1\"]\r\n\r\n1...Bxf2+ 2. Kxf2 Qxc3\r\n*";

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


    // initialiseren variabelen
    String coordinates;
    int colourOriginal;
    int moveIndex;
    String chessMoves[];
    long startTime;
    int correctPuzzleIndex;
    int wrongindex;

    int puzzleFinished = 3;
    int puzzleFirstWrongNoworrect = 2;
    int wrong = 1;
    int correct = 0;

    int white = 0;
    int black = 1;

    int ignoreThis = -1;

    // er wordt een schaakpuzzel verkregen
    public void getPuzzleNow() {

        // resultaat leegmaken
        ImageView img = (ImageView) findViewById(R.id.result);
        img.setImageResource(0);

        // index waarde voor correcte puzzel
        correctPuzzleIndex = correct;

        // een puzzel kan slechts eenmaal fout gerekend worden en niet meerdere malen
        wrongindex = correct;
        startTime = System.currentTimeMillis();


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

        colourOriginal = black;
        if (fenStringAndColour[1].equals("w")) {
            colourOriginal = white;
        }

        // textvak met tegenzetten / resultaat vorige puzzel leegmaken
        changeText2(colourOriginal);

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
        for (int i = 0; i < 7; i++) {
            int column = i % 7 + 1;
            int row = 1 + i / 7;
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

        TextView img2 = (TextView) findViewById(R.id.displayMoves);
        img2.setVisibility(View.INVISIBLE);
        emerge2(img2);

        TextView img3 = (TextView) findViewById(R.id.displayMoves2);
        img3.setVisibility(View.INVISIBLE);
        emerge2(img3);
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


    // verkijgt notatie ingevuld door gebruiker en doet er eventueel een zet mee
    public void confirmMove(View view) {

        // notatie verkrijgen
        TextView notation = (TextView) findViewById(R.id.notationInText);
        String notationUser = notation.getText().toString();
        notation.setText("");

        //puzzel nog niet klaar
        if (correctPuzzleIndex != puzzleFinished) {


            // komt zet overeen met antwoord
            if (chessMoves[moveIndex].equals(notationUser)) {


                // kleur aanpassen
                int colour = ((colourOriginal + moveIndex) % 2);
                String move = chessMoves[moveIndex];

                changeText(move, colour);

                // coodinaten verkrijgen na schaakzet
                coordinates = makeChessMove(coordinates, colour, move);
                moveIndex++;


                makeChessMove2();
            } else {
                wrongindex++;
                correctPuzzleIndex = wrong;
                if (correctPuzzleIndex == wrong) {

                    int estimatedTime = (int) (System.currentTimeMillis() - startTime) / 1000;

                    // een puzzel kan slechts eenmaal fout gerekend worden
                    if (wrongindex == wrong){
                        readFromDB(estimatedTime, "wrong");
                    }
                    else{
                        readFromDB(estimatedTime, "none");
                    }

                    // startime wordt aangepast zodat tijd van fout to eventueel goed wordt gemeten
                    startTime = System.currentTimeMillis();

                    // resultaat wordt fout gecommuniceerd via rood kruis
                    ImageView img = (ImageView) findViewById(R.id.result);
                    img.setImageResource(R.drawable.wrong);

                    correctPuzzleIndex = puzzleFirstWrongNoworrect;


                }
                // puzzel is incorrect


            }
        }

    }

    // nieuwe puzzel wordt aangeroepen
    public void nieuwe(View view) {

        getPuzzleNow();
    }

    // tekst van zetten/resultaat puzzel veranderen
    public void changeText(String text, int colour) {

        // de notatie moet ik het juiste vakje geplaatst worden

        // notatie in gebruikers zetvakje
        if (colour - colourOriginal == 0) {
            TextView view = (TextView) findViewById(R.id.displayMoves);
            text =  view.getText()+ text  +"\r\n";
            view.setText(text);
        }

        // notatie in computers zetvakje
        else {
            TextView view = (TextView) findViewById(R.id.displayMoves2);
            text = view.getText()+ text  + "\r\n";
            view.setText(text);
        }
    }

    // tekst van zetten/resultaat leegmaken
    public void changeText2(int colourOriginal) {
        if (colourOriginal == white) {
            TextView view = (TextView) findViewById(R.id.displayMoves);
            String text = "white (you)" + "\r\n";
            view.setText(text);

            TextView view2 = (TextView) findViewById(R.id.displayMoves2);
            String text2 = "black" + "\r\n";
            view2.setText(text2);
        }
        else{

            TextView view = (TextView) findViewById(R.id.displayMoves);
            String text = "black (you)" + '\n';
            view.setText(text);

            TextView view2 = (TextView) findViewById(R.id.displayMoves2);
            String text2 = "white" + '\n';
            view2.setText(text2);
        }

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
        text.setText("White: "+ white +"\n" + "Black: "+ black);
    }



    // schaakzet doen
    public void makeChessMove2() {

        // alle zetten zijn uitgevoerd
        if (moveIndex == chessMoves.length) {

            // indien geen fout gemaakt
            if (correctPuzzleIndex == correct) {

                int estimatedTime = (int) (System.currentTimeMillis() - startTime) / 1000;
                readFromDB(estimatedTime, "correct");

                ImageView img = (ImageView) findViewById(R.id.result);
                img.setImageResource(R.drawable.correct);

                // breng de index naar getal waar niks mee gebeurd
                correctPuzzleIndex = puzzleFinished;
            }

            // indien wel een fout gemaakt
            else if (correctPuzzleIndex == puzzleFirstWrongNoworrect){

                int estimatedTime = (int) (System.currentTimeMillis() - startTime) / 1000;
                readFromDB(estimatedTime, "none");

                ImageView img = (ImageView) findViewById(R.id.result);
                img.setImageResource(R.drawable.correctandwrong);

                // breng de index naar getal waar niks mee gebeurd
                correctPuzzleIndex = puzzleFinished;

            }
        }

        else {

            // selecteer juiste kleur en zet
            int colour = ((colourOriginal + moveIndex) % 2);
            String move = chessMoves[moveIndex];

            // coordinates aanpassen nadat zet gemaakt
            coordinates = makeChessMove(coordinates, colour, move);

            changeText(move, colour);
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

        // maak een lege string im fen in op te slaan
        String coordinates = "";

        // split fed in array
        String[] FedArray = fed.split("");
        int arrayLength = FedArray.length;

        // itereren over array elementen
        for (int i = 1; i < arrayLength; i++) {

            // kijken of arrayelement gelijk is aan stuk
            if (FedArray[i].equals("r")) {
                coordinates = coordinates + "r";
            } else if (FedArray[i].equals("n")) {

                coordinates = coordinates + "n";
            } else if (FedArray[i].equals("b")) {
                coordinates = coordinates + "b";
            } else if (FedArray[i].equals("q")) {
                coordinates = coordinates + "q";
            } else if (FedArray[i].equals("k")) {
                coordinates = coordinates + "k";
            } else if (FedArray[i].equals("p")) {
                coordinates = coordinates + "p";
            } else if (FedArray[i].equals("R")) {
                coordinates = coordinates + "t";
            } else if (FedArray[i].equals("N")) {
                coordinates = coordinates + "m";
            } else if (FedArray[i].equals("B")) {
                coordinates = coordinates + "c";
            } else if (FedArray[i].equals("Q")) {
                coordinates = coordinates + "s";
            } else if (FedArray[i].equals("K")) {
                coordinates = coordinates + "l";
            } else if (FedArray[i].equals("P")) {
                coordinates = coordinates + "o";

                // als er cijfer staat komen er zoveel nullen bij
            } else if (FedArray[i].equals("1") || FedArray[i].equals("2") || FedArray[i].equals("3") || FedArray[i].equals("4") ||
                    FedArray[i].equals("5") || FedArray[i].equals("6") || FedArray[i].equals("7") || FedArray[i].equals("8"))

            {
                for (int j = 0; j < parseInt(FedArray[i]); j++) {
                    coordinates = coordinates + "0";
                }
            }
        }
        return coordinates;
    }


    // maak bord naar notatie
    public void createbord(String coordinates) {

        // split de coordinates in een array
        String[] arrayCoordinates = coordinates.split("");

        // itereer over het hele bord
        for (int i = 0; i < 64; i++) {
            int column = i % 8 + 1;
            int row = 8 - (i - i % 8) / 8;
            char columnChar = (char) (column + 96);

            String buttonID = String.valueOf(columnChar) + String.valueOf(row);
            int ID = getResources().getIdentifier(buttonID, "id", getPackageName());

            ImageView img = (ImageView) findViewById(ID);
            img.setVisibility(View.INVISIBLE);

            // alle velden schaakbord juiste plaatje geven
            if (arrayCoordinates[i + 1].equals("r")) {
                img.setImageResource(R.drawable.blackrook);
            } else if (arrayCoordinates[i + 1].equals("n")) {

                img.setImageResource(R.drawable.blackknight);
            } else if (arrayCoordinates[i + 1].equals("b")) {
                img.setImageResource(R.drawable.blackbishop);
            } else if (arrayCoordinates[i + 1].equals("q")) {
                img.setImageResource(R.drawable.blackqueen);
            } else if (arrayCoordinates[i + 1].equals("k")) {
                img.setImageResource(R.drawable.blackking);
            } else if (arrayCoordinates[i + 1].equals("p")) {
                img.setImageResource(R.drawable.blackpawn);
            } else if (arrayCoordinates[i + 1].equals("t")) {
                img.setImageResource(R.drawable.whiterook);
            } else if (arrayCoordinates[i + 1].equals("m")) {
                img.setImageResource(R.drawable.whiteknight);
            } else if (arrayCoordinates[i + 1].equals("c")) {
                img.setImageResource(R.drawable.whitebishop);
            } else if (arrayCoordinates[i + 1].equals("s")) {
                img.setImageResource(R.drawable.whitequeen);
            } else if (arrayCoordinates[i + 1].equals("l")) {
                img.setImageResource(R.drawable.whiteking);
            } else if (arrayCoordinates[i + 1].equals("o")) {
                img.setImageResource(R.drawable.whitepawn);
            } else if (arrayCoordinates[i + 1].equals("0")) {
                img.setImageResource(0);
            }
        }
    }




    public void changeEditText(View view) {
    }

    // clickers voor stukken
    @Override
    public void onClick(View v) {

        // de notatieinvuller wordt aangepast
        TextView edit  = (TextView)findViewById(R.id.notationInText);
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
            case R.id.ag:
                if (notation != null && notation.length() > 0) {
                    notation = notation.substring(0, notation.length() - 1);
                }
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

        // tekst wordt neergezet in notatie EditText veld
        edit.setText(notation);
    }


    // er worden clickers gezet
    public void initialiseClickers() {

        // er wordt een clicker gezet op de imageviews
        for (int i = 0; i < 7; i++) {
            int column = i % 7 + 1;
            int row = 1 + i / 7;
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

            // verander de stukken naar wit
            if (colour == white){
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

            // verander de stukken naar zwart
            else if (colour == black){
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

    // er wordt een hint toegepast: er wordt een zet gedaan met een tegenzet
    public void hintForChessMove(View view) {
        makeChessMove2();
        makeChessMove2();
    }

    // het schaakbord wordt even zichtbaar
    public void showBoard(View view) {
        showBoard();
    }


    // er wordt gelezen van de database, ook wordt deze aangepast
    public void readFromDB(final int time, final String result) {

        // gebruiker wordt geselecteerd
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            final String UID = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("scores");

            myRef.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override

                // de waardes worden veranderd
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Score value = dataSnapshot.child(UID).getValue(Score.class);
                    String valueCorrect = String.valueOf(Integer.parseInt(value.correct) + 1);
                    String totalTime = String.valueOf(Integer.parseInt(value.time) + time);
                    String totalWrong = String.valueOf(Integer.parseInt(value.wrong) + 1);

                    // indien correct, correct++
                    if (result.equals("correct")) {
                        try {
                            myRef.child(UID).child("correct").setValue(valueCorrect);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // indien fout, wrong++
                    else if (result.equals("wrong")) {
                        try {
                            myRef.child(UID).child("wrong").setValue(totalWrong);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // totale tijd besteed wordt aangepast
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



    // doet een zet
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
            counter = ignoreThis;
        }

        String piece;

        if (String.valueOf(pieceSort).equals("B")) {

            // / geeft loper de juiste kleur
            piece = "c";
            if (colour == black) {
                piece = "b";
            }
        } else if (String.valueOf(pieceSort).equals("R")) {

            // geeft toren de juiste kleur
            piece = "t";
            if (colour == black) {
                piece = "r";
            }
        } else if (String.valueOf(pieceSort).equals("Q")) {

            // geeft dame de juiste kleur
            piece = "s";
            if (colour == black) {
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
            if (colour == black) {
                piece = "n";
            }
        } else {

            // geeft koning juiste kleur
            piece = "l";
            if (colour == black) {
                piece = "k";
            }
        }

        // promotie of rokade
        if (counter == ignoreThis || String.valueOf(move.charAt(0)).equals("O")) {

            // voer promotie uit
            if (move.length() > 3 && String.valueOf(move.charAt(move.length() - 2)).equals("=")) {
                currentNotation = ChessExerciseFunctions.promotion(currentNotation, move, piece, colour);
            }

            // voer lange rokade uit
            else if (move.equals("O-O-O")){
                currentNotation = ChessExerciseFunctions.castling(currentNotation, colour, 1);

            }

            // voer korte rokade uit
            else if (move.equals("O-O")){
                currentNotation = ChessExerciseFunctions.castling(currentNotation, colour, 0);
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
                currentNotation = ChessExerciseFunctions.moveSinglePiece(currentNotation, move, String.valueOf(myList.get(0)), piece);

            }

            // als er meerdere stukken zijn van bepaalde kleur
            else if (counter > 1){
                currentNotation = ChessExerciseFunctions.movePieceWithTwins(counter, move, currentNotation, myList, piece);

            }
        }

        createbord(currentNotation);

        // notatie wordt teruggegeven
        return currentNotation;
    }

}
