package com.example.melle.chesslessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.StrictMath.abs;


//{"title":"Mate in 4","comments":"","url":"https://www.chess.com/forum/view/daily-puzzles/692015---mate-in-4","publish_time":1433833200,"fen":"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1","pgn":"[Date \"????.??.??\"]\r\n[Result \"*\"]\r\n[FEN \"1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1\"]\r\n\r\n1. Bg6+ Kg8 2. Qa2+ d5 3. Qxd5+ Qf7 4. Qxf7#\r\n*","image":"https://www.chess.com/dynboard?fen=1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1%20w%20-%20-%200%201&size=2"}
public class ChessExercise extends AppCompatActivity implements GetPuzzle.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_exercise);
        getPuzzleNow();

    }

    public void nieuwe(View view) {
        getPuzzleNow();
    }

    @Override
    public void gotQuestions(JSONObject questions) {

        Log.d("blij", String.valueOf(questions));
        try {
            Log.d("blij2", questions.getString("pgn"));
            String test = questions.getString("pgn");
            String[] testarray = zettenn(test);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotQuestionsError(String message) {
        Log.d("boosheid", message);
    }

    public void getPuzzleNow() {

        GetPuzzle request = new GetPuzzle(getApplicationContext());
        request.GetPuzzle(this);
        
        // hier json opvragen

        String fen = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1 w - - 0 1";
        String[] fenStringAndColour = fenStringSplit(fen);
        String fenString = fenStringAndColour[0];

        String coordinates = fedToString(fenString);
        createbord(coordinates);
    }

    public String[] fenStringSplit(String fen){
        String[] fenSplitted = fen.split(" ");

        String fenPosition = fenSplitted[0];
        String colour = fenSplitted[1];

        String fenStringAndColour[] = new String[2];
        fenStringAndColour[0]= fenPosition;
        fenStringAndColour[1] =  colour;
        return fenStringAndColour;
    }



    public String [] zettenn(String string) {
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
        arrayint[index2] = zettenstring.length() - 2;


        for (int j = 0; j < index2; j = j + 2) {
            dejuistestring = dejuistestring + zettenstring.substring(arrayint[j], arrayint[j + 1]);
        }

        Log.d("tag3", dejuistestring);
        String[] array3 = dejuistestring.split(" ");
        String[] array4 = new String[array3.length];

        int countOfMoves = 0;
        int indexxx = 0;
        for (int j = 1; j < array3.length; j++) {
            Log.d("belangrijk", array3[j]);
            if (Character.isDigit(array3[j].charAt(0))){
                Log.d("123","123");
            }
            else {
                int indexxxx =1;
                String[] array5 = array3[j].split("");
                String moveString = "";
                if (indexxxx ==1 && !Character.isUpperCase(array5[indexxxx].charAt(0))){
                    moveString = moveString + "P";
                }
                for (int h = 1; h < array5.length; h++){
                    Log.d("sss", array5[h]);
                    if (!array5[h].equals("x") && !array5[h].equals("+") && !array5[h].equals("#") && !array5[h].equals("=")){
                        moveString = moveString + array5[h];
                    }
                }

                array4[indexxx] = moveString;
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


    // zetten doen

    int zettenindex = 0;
    String fed = "1r3q1r/1bpn2bk/2np1p1p/1p2pPpB/pP1PP1N1/P1PRB1NP/5QP1/3R2K1";
    String huidigenotatie = fedToString(fed);

    public void doezet(View view) {
        //String myArray[] = {"Bg6", "Kg8", "Qa2", "Ra8", "Ra1", "Kf7", "Pc4", "Pd5", "Pe5", "Pe4", "Nh1"};
        String myArray[] = {"R3d2", "Qf7", "Kh1", "Rhc8", "Pa8Q"};
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

        else if (move.length() == 4 && Character.isLetter(move.charAt(1))) {
            Log.d("hier moet ik zitten", "jaa");
            // itereren over hele schaakbord
            int ascii = (int) move.charAt(1);
            int column = ascii - 97;

            for (int i = 0; i < 8; i++) {
                int index = 8*i + column;
                if (currentNotation.charAt(index) == piece.charAt(0)) {
                    counter++;
                    myList.add(index);
                }
            }
        }

        else if (move.length() == 4 && Character.isDigit(move.charAt(1))) {
            // itereren over hele schaakbord
            int row = Integer.parseInt(String.valueOf(move.charAt(1)));

            for (int i = 0; i < 8; i++) {
                int index = (8 - row)*8 + i;
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
        else {
            currentNotation = movePieceWithTwins(counter, move, currentNotation, myList, piece);

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


}
