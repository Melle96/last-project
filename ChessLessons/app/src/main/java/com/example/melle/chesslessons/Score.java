package com.example.melle.chesslessons;

public class Score {

    public String correct;
    public String wrong;
    public String time;

    public Score(){

    }

    public Score(String correct, String wrong, String time) {
        this.correct = correct;
        this.wrong = wrong;
        this.time = time;
    }

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