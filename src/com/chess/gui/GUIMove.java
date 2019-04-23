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

    private String[] allMoves;

    public GUIMove() {
        this.fromCoords = "";
        this.toCoords = "";
        this.fullMove = false;
    }

    public void clickCoords(String coords, Game game, GUI.BoardPanel boardPanel) {
        boolean whiteToMove = game.isWhiteToMove();
        Piece curPiece = game.getChessBoard().getGame()[Util.rankToRow(coords)][Util.fileToCol(coords)];

        if (fromCoords.equals("")) {
            if (curPiece.isWhite() == whiteToMove && !curPiece.isEmpty()) {
                System.out.print("Selecting fromCoords: " + coords + "\n");
                fromCoords = coords;

                boardPanel.selectTile(coords, true);

                this.allMoves = game.getChessBoard().getAllMoves(curPiece);

                boardPanel.highlightTiles(allMoves, true);
            }
        }
        // If selected piece has been clicked on again, need to deselect
        else if (fromCoords.equals(coords)) {
            System.out.print("De Selecting fromCoords: " + coords + "\n");
            fromCoords = "";

            boardPanel.selectTile(coords, false);

            boardPanel.highlightTiles(allMoves, false);
        }
        // If a piece to move has been selected, and next click is on another piece
        else {
            if ((curPiece.isWhite() != whiteToMove && !curPiece.isEmpty()) || curPiece.isEmpty()) {
                System.out.print("Selecting toCoords: " + coords + "\n");
                toCoords = coords;
                fullMove = true;
                boardPanel.selectTile(fromCoords, false);
                boardPanel.highlightTiles(allMoves, false);
            }
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
