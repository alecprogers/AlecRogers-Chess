package com.chess.logic;

import com.chess.Util;
import com.chess.gui.GUIMove;

public class Interface {

    private ChessBoard board;
    private boolean whiteToMove;

    private String fromCoords;
    private String toCoords;

    private GUIMove guiMove;

    private PGNImporter pgn;

    public Interface(ChessBoard board, boolean whiteToMove, PGNImporter pgn) {
        this.board = board;
        this.whiteToMove = whiteToMove;
        this.pgn = pgn;
    }

    public Move getMoveFromConsole() {
        System.out.print("Enter the coordinate to move FROM: ");
        fromCoords = Util.getString();
        fromCoords = Util.verifyCoord(fromCoords);

        System.out.print("Enter the coordinates to move TO: ");
        toCoords = Util.getString();
        toCoords = Util.verifyCoord(toCoords);

        return new Move(board, whiteToMove, fromCoords, toCoords);
    }

    public Move getMoveFromPGN() {
        String coords = pgn.getNextMove();

        fromCoords = Util.parseFromCoords(coords);
        toCoords = Util.parseToCoords(coords);

        return new Move(board, whiteToMove, fromCoords, toCoords);
    }

    public boolean isEndOfPGN() {
        return pgn.isEndOfFile();
    }

    public Move getMoveFromGUI() {

        guiMove = new GUIMove();

        String coords = guiMove.getMove();
        while (!guiMove.hasFullMove()) {
            try {
                Thread.sleep(250);
            }
            catch(InterruptedException ex){

            }

            coords = guiMove.getMove();
        }

        fromCoords = Util.parseFromCoords(coords);
        toCoords = Util.parseToCoords(coords);

        System.out.print("\nMOVE: " + coords + "\n\n");

        return new Move(board, whiteToMove, fromCoords, toCoords);
        //return new Move();
    }

    public void resetGUIMove() {
        guiMove.reset();
    }

}
