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

        // mak een finale array met juiste lengte en moves erin
        String[] FinalArrayWithMoves = new String[countOfMoves];

        for (int j = 0; j < countOfMoves; j++) {
            FinalArrayWithMoves[j] = emmptyArray[j];
        }
        return FinalArrayWithMoves;
    }

}
