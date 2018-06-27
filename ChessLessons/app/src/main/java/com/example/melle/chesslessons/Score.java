package com.example.melle.chesslessons;

// deze class bezit strings van correct, wrong en time
public class Score {

    public String correct;
    public String wrong;
    public String time;

    // deze wordt aangemaakt angezien anders Firebase niet werkt
    public Score(){

    }

    // correct, wrong en time aanpassen
    public Score(String correct, String wrong, String time) {
        this.correct = correct;
        this.wrong = wrong;
        this.time = time;
    }

    // correct, wrong en time verkrijgen
    public String getCorrect() {
        return correct;
    }

    public String getWrong() {
        return wrong;
    }

    public String getTime() {
        return time;
    }

}