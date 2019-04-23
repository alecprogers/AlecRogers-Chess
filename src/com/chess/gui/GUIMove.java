package com.chess.gui;

import com.chess.Util;

public class GUIMove {

    private static String fromCoords;
    private static String toCoords;
    private static boolean fullMove;


    public GUIMove() {
        this.fromCoords = "";
        this.toCoords = "";
        this.fullMove = false;
    }

    public void clickCoords(String coords) {
        if (fromCoords.equals("")) {
            System.out.print("Selecting fromCoords: " + coords + "\n");
            fromCoords = coords;
        }
        // If selected piece has been clicked on again, need to deselect
        else if (fromCoords.equals(coords)) {
            System.out.print("De Selecting fromCoords: " + coords + "\n");
            fromCoords = "";
        }
        // If a piece to move has been selected, and next click is on another piece
        else {
            System.out.print("Selecting toCoords: " + coords + "\n");
            toCoords = coords;
            fullMove = true;
        }
    }

    public String getMove() {
        return fromCoords + toCoords;
    }

    public boolean hasFullMove() {
        return fullMove;
    }

    public void reset() {
        fromCoords = "";
        toCoords = "";
        fullMove = false;
    }

}
