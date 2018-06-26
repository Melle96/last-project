package com.example.melle.chesslessons;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.lang.StrictMath.abs;

public class ChessExerciseFunctions {

    // schaakzetten verkijgen vanuit pgn
    public static String[] obtainChessMoves(String pgn) {

        String[] array = pgn.split("");

        // index variabele aanmaken
        int index = 0;
        int arrayindex = 1;

        // de zetten string begint na de derde ]
        while (index != 3) {
            if (array[arrayindex].equals("]")) {
                index++;
            }
            arrayindex++;
        }

        // selecteer gedeelte van de string met alleen de zetten
        String moveString = pgn.substring(arrayindex - 1, pgn.length() - 1);

        // initialiseer zetten string zonder haakjes
        String MoveStringWithoutBraces = "";


        int index2 = 1;
        int[] arrayPositions = new int[moveString.length()];
        String[] moveArray = moveString.split("");

        // itereren over lengte string met zetten
        for (int i = 1; i < 1 + moveString.length(); i++) {

            // sla positie van haakjes op in moveArray
            if (moveArray[i].equals("(")) {
                arrayPositions[index2] = i - 2;
                index2++;
            } else if (moveArray[i].equals(")")) {
                arrayPositions[index2] = i;
                index2++;
            }
        }
        // geeft begin moceString en het einde mee aan de array met posities
        arrayPositions[0] = 0;
        arrayPositions[index2] = moveString.length();


        // maak de zettenstring zonder haakjes en zetten tussen de haakjes
        for (int j = 0; j < index2; j = j + 2) {
            MoveStringWithoutBraces = MoveStringWithoutBraces + moveString.substring(arrayPositions[j], arrayPositions[j + 1]);
        }


        // soms geen spatie maar \r dus extra spatie erbij zetten

        //itereren over zettenstring zonder haakjes en wat daartussen staat
        for (int j = 0; j < MoveStringWithoutBraces.length(); j++) {

            // als de char hoofletter is en het element plaats neemt op positie groter dan 1
            if (Character.isUpperCase(MoveStringWithoutBraces.charAt(j)) && j > 1) {

                // als elemnt ervoor geen = is en ook niet gelijk aan spatie en ook niet gelijk aan -
                if (!String.valueOf(MoveStringWithoutBraces.charAt(j - 1)).equals("=") &&
                        !String.valueOf(MoveStringWithoutBraces.charAt(j - 1)).equals(" ")
                        && !String.valueOf(MoveStringWithoutBraces.charAt(j - 1)).equals("-"))
                {
                    // voeg spatie toe
                    MoveStringWithoutBraces = MoveStringWithoutBraces.substring(0, j) + " "
                            + MoveStringWithoutBraces.substring(j, MoveStringWithoutBraces.length());
                }
            }
        }

        // split string in een array
        String[] MoveArrayWithoutBraces = MoveStringWithoutBraces.split(" ");
        String[] emmptyArray = new String[MoveArrayWithoutBraces.length];


        int countOfMoves = 0;
        int index3 = 0;


        // itereren over lengte zettenarray zonder haakjes
        for (int j = 1; j < MoveArrayWithoutBraces.length; j++) {

            // als character is ongelijk aan cijfer
            if (!Character.isDigit(MoveArrayWithoutBraces[j].charAt(0)))
            {
                String[] arrayWithSymbols = MoveArrayWithoutBraces[j].split("");
                String chessMoveString = "";
                if (!isUpperCase(arrayWithSymbols[1].charAt(0))) {
                    chessMoveString = chessMoveString + "P";
                }

                // itereren over de array
                for (int h = 1; h < arrayWithSymbols.length; h++) {

                    if (!arrayWithSymbols[h].equals("x") && !arrayWithSymbols[h].equals("+") && !arrayWithSymbols[h].equals("#")
                            && !arrayWithSymbols[h].equals("r") && !arrayWithSymbols[h].equals("\\")) {

                        // voeg notatie toe aan zettenstring
                        chessMoveString = chessMoveString + arrayWithSymbols[h];
                    }

                }

                // verwijder alle \r\n
                chessMoveString = chessMoveString.replaceAll("(\\r|\\n)", "");

                // vul de move in in de array
                emmptyArray[index3] = chessMoveString;

                index3++;
                countOfMoves++;
            }
        }

        // laatste element is soms leeg en wordt dus gelijk aan P,dit element niet meenemen
        if (emmptyArray[countOfMoves-1].equals("P")){
            countOfMoves--;
        }

        // mak een finale array met juiste lengte en moves erin
        String[] FinalArrayWithMoves = new String[countOfMoves];

        for (int j = 0; j < countOfMoves; j++) {
            FinalArrayWithMoves[j] = emmptyArray[j];
        }
        
        return FinalArrayWithMoves;
    }



    // verplaatst een enkel stuk over het bord
    public static String moveSinglePiece(String currentNotation, String move, String position, String piece) {


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

        return currentNotation;

    }





    // verplaatst een stuk als er meerdere van op het bord staan
    public static String movePieceWithTwins(int counter, String zet, String currentNotation, List<Integer> piecePositions, String piece) {

        // index variabelen om zetten te checken
        int moveIsFinished = 0;
        int i = 0;

        // zoland zet nog niet gedaan kan worden door een stuk en er niet over alle mogelijke stukken is geitereerd
        while (moveIsFinished != 1 && i != counter) {


            int positionToCome = piecePositions.get(i);
            int columnToCome = positionToCome % 8;
            int rowToCome = 8 - (positionToCome / 8);


            char columnChar = zet.charAt(zet.length() - 2);
            int columnToGo = (int) Character.toLowerCase(columnChar) - 97;
            int rowToGo = Integer.parseInt(String.valueOf(zet.charAt(zet.length() - 1)));

            // kijken hoeveel velden er tussen stuk vertrek en bestemming liggen
            int countChessFieldsBetweenDesAndGo = 0;
            int indexChessFieldsEmpty = 0;

            // richting van stuk initialiseren
            int direction[] = new int[2];

            // index variabele als er rekening moet worden gehouden met of er niks tussen staat
            int canIPass = 0;
            String pieceString = String.valueOf(zet.charAt(0));

            // schuin
            if ((pieceString.equals("Q") || pieceString.equals("B")) && abs(columnToGo - columnToCome) == abs(rowToGo - rowToCome)) {

                canIPass = 1;
                countChessFieldsBetweenDesAndGo = abs(columnToGo - columnToCome) - 1;

                // richting loper bepalen
                if (columnToGo > columnToCome) {
                    direction[0] = 1;
                } else {
                    direction[0] = -1;
                }
                if (rowToGo > rowToCome) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
            }

            //recht
            else if ((pieceString.equals("Q") || pieceString.equals("R")) && (columnToGo == columnToCome || rowToGo == rowToCome)) {

                canIPass = 1;
                if (columnToGo == columnToCome)
                {
                    countChessFieldsBetweenDesAndGo = abs(rowToGo - rowToCome) - 1;
                } else {
                    countChessFieldsBetweenDesAndGo = abs(columnToGo - columnToCome) - 1;
                }


                if (columnToGo > columnToCome) {
                    direction[0] = 1;
                } else if (columnToGo < columnToCome) {
                    direction[0] = -1;
                } else {
                    direction[0] = 0;
                }

                if (rowToGo > rowToCome) {
                    direction[1] = 1;
                } else if (rowToGo < rowToCome) {
                    direction[1] = -1;
                } else {
                    direction[1] = 0;
                }
            }


            // pion recht
            else if (pieceString.equals("P") && columnToGo == columnToCome && String.valueOf(currentNotation.charAt(8*(8-rowToGo) + columnToGo)).equals("0")
                    && (abs(rowToGo - rowToCome) == 1  || abs(rowToGo - rowToCome) == 2)) {

                canIPass = 1;
                if (rowToGo > rowToCome) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
                direction[0] = 0;
            }

            // pion slaan

            else if (pieceString.equals("P") && !String.valueOf(currentNotation.charAt(8*(8-rowToGo) + columnToGo)).equals("0")
                    && abs(columnToGo - columnToCome) == 1  && abs(rowToGo - rowToCome) == 1) {

                canIPass = 1;
                if (columnToGo > columnToCome) {
                    direction[0] = 1;
                } else {
                    direction[0] = -1;
                }
                if (rowToGo > rowToCome) {
                    direction[1] = 1;
                } else {
                    direction[1] = -1;
                }
            }


            // paard
            else if (pieceString.equals("N") && (abs(columnToGo - columnToCome) == 2 && abs(rowToGo - rowToCome) == 1 ||
                    abs(columnToGo - columnToCome) == 1 && abs(rowToGo - rowToCome) == 2)){


                // verplaats het stuk door veld waar het vandaan kwam leeg te maken, en waar her naartoe gaat een paard te zetten
                int positionToGo = 8 * (8- rowToGo) + columnToGo;
                currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome+1);
                currentNotation = currentNotation.substring(0, positionToGo) + piece + currentNotation.substring(positionToGo+1);

                moveIsFinished = 1;

            }


            // kijken of er geen obstakel is tussen het vertrekpunt en bestemming van het stuk
            if (canIPass == 1) {
                for (int h = 0; h < countChessFieldsBetweenDesAndGo; h++) {
                    int columnObstacle = columnToCome + direction[0] * (h + 1);
                    int rowObstacle = rowToCome + direction[1] * (h + 1);

                    int obstaclePosition = 8 * (8 - rowObstacle) + columnObstacle;

                    if (String.valueOf(currentNotation.charAt(obstaclePosition)).equals("0")) {
                        indexChessFieldsEmpty++;

                    }

                }

                // alle velden leeg
                if (indexChessFieldsEmpty == countChessFieldsBetweenDesAndGo) {

                    // move is legaal moet gemaakt worden
                    int column = (int) Character.toLowerCase(columnChar) - 97;
                    int row = 8 - Integer.parseInt(String.valueOf(zet.charAt(zet.length()-1)));
                    int positionToGo = 8 * row + column;

                    // notatie van positie aanpassen
                    currentNotation = currentNotation.substring(0, positionToCome) + '0' + currentNotation.substring(positionToCome + 1);
                    currentNotation = currentNotation.substring(0, positionToGo) + piece + currentNotation.substring(positionToGo + 1);
                    moveIsFinished = 1;
                }
            }
            i++;
        }
        return currentNotation;
    }


    // er wordt een promotie gemaakt
    public static String promotion(String currentnotation, String move, String piece, int colour) {

        // krijg de positie van de pion
        char character = move.charAt(move.length()-4);
        int position;
        int position2;

        // wit
        if (colour == 1) {

            // hier komt pion vandaan
            position = (int) Character.toLowerCase(character) - 97 + 8*6;

            // hier gaat de pion naartoe
            position2 = position +8;
        }

        //zwart
        else{
            position = (int) Character.toLowerCase(character) - 97 + 8;
            position2 = position - 8;
        }

        // pas de notatie aan
        currentnotation = currentnotation.substring(0, position) + '0' + currentnotation.substring(position + 1);
        currentnotation = currentnotation.substring(0, position2) + piece + currentnotation.substring(position2 + 1);

        // maak het bord aan met de nieuwe notatie
        return currentnotation;

    }

    // er wordt gerokeerd
    static String castling(String currentNotation, int colour, int i) {

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

        // er wordt lang gerokeerd
        if (i == 1) {
            positionRook = 56 * (1 -colour);
            positionRook2 = 3 + 56 * (1 -colour);

            positionKing = 4 + 56 * (1 -colour);
            positionKing2 = 2 + 56 * (1 -colour);
        }

        // er wordt kort gerokeerd
        else {
            positionRook = 7 + 56 * (1 -colour);
            positionRook2 = 5 + 56 * (1 -colour);

            positionKing = 4 + 56 * (1 -colour);
            positionKing2 = 6 + 56 * (1 -colour);
        }

        // notatie wordt aangepast door toren en koning naar de juiste plek te verplaatsen
        currentNotation = currentNotation.substring(0, positionRook) + '0' + currentNotation.substring(positionRook + 1);
        currentNotation = currentNotation.substring(0, positionKing) + '0' + currentNotation.substring(positionKing + 1);

        currentNotation = currentNotation.substring(0, positionRook2) + pieceRook + currentNotation.substring(positionRook2 + 1);
        currentNotation = currentNotation.substring(0, positionKing2) + pieceKing + currentNotation.substring(positionKing2 + 1);

        return currentNotation;

    }

}
