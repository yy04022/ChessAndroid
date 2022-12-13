package com.example.chessandroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordedGameModel implements Serializable {
    String gameTitle;
    String date;
    ArrayList<String> moveList;

    public RecordedGameModel(String gameTitle, String date, ArrayList<String> moveList) {
        this.gameTitle = gameTitle;
        this.date = date;
        this.moveList = moveList;
    }

    public String getDateAndTitle() {
        return date + gameTitle;
    }
}
