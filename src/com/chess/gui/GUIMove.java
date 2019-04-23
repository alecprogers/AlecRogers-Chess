package com.chess.gui;

import com.chess.Util;
import com.chess.logic.Game;
import com.chess.logic.Piece;

import javax.swing.*;
import java.awt.*;

public class GUIMove {

    private static String fromCoords;
    private static String toCoords;
    private static boolean fullMove;

    private Color selectedTileColor = Color.decode("#A9BCF5");

    public GUIMove() {
        this.fromCoords = "";
        this.toCoords = "";
        this.fullMove = false;
    }

    public void clickCoords(String coords, JButton btn, Game game) {
        boolean whiteToMove = game.isWhiteToMove();
        Piece curPiece = game.getChessBoard().getGame()[Util.rankToRow(coords)][Util.fileToCol(coords)];

        if (fromCoords.equals("")) { // FIXME
            if (curPiece.isWhite() == whiteToMove && !curPiece.isEmpty()) {
                System.out.print("Selecting fromCoords: " + coords + "\n");
                fromCoords = coords;
                btn.setBackground(selectedTileColor);
                btn.validate();
            }
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
