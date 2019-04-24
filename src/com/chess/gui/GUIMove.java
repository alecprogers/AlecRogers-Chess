package com.chess.gui;

import com.chess.Util;
import com.chess.logic.ChessBoard;
import com.chess.logic.Game;
import com.chess.logic.Move;
import com.chess.logic.Piece;


public class GUIMove {

    private static String fromCoords;
    private static String toCoords;
    private static boolean fullMove;

    private String[] allMoves;
    private String[] validMoves;

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

                this.allMoves = game.getChessBoard().getAllMoves(curPiece);

                int count = allMoves.length;
                for (int i = 0; i < allMoves.length; i++) {
                    ChessBoard dupGame = new ChessBoard(game.getChessBoard());
                    boolean valid = new Move(dupGame, whiteToMove, coords, allMoves[i]).checkMove(false);
                    if (!valid) {
                        allMoves[i] = "";
                        count--;
                    }
                }

                validMoves = new String[count];
                int numCopied = 0;

                for (int i = 0; i < allMoves.length; i++) {
                    if (!allMoves[i].equals("")) {
                        validMoves[numCopied] = allMoves[i];
                        numCopied++;
                    }
                }

                if (numCopied == 0) {
                    boardPanel.selectTileRed(coords, true);
                }
                else {
                    boardPanel.selectTile(coords, true);
                    boardPanel.highlightTiles(validMoves, true);
                }
            }
        }
        // If selected piece has been clicked on again, need to deselect
        else if (fromCoords.equals(coords)) {
            System.out.print("De Selecting fromCoords: " + coords + "\n");
            fromCoords = "";

            boardPanel.selectTile(coords, false);

            boardPanel.highlightTiles(validMoves, false);
        }
        // If a piece to move has been selected, and next click is on another piece
        else {
            if ((curPiece.isWhite() != whiteToMove && !curPiece.isEmpty()) || curPiece.isEmpty()) {
                System.out.print("Selecting toCoords: " + coords + "\n");
                toCoords = coords;
                fullMove = true;
                boardPanel.selectTile(fromCoords, false);
                boardPanel.highlightTiles(validMoves, false);
                //boardPanel.highlightTiles(allMoves, false);
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
