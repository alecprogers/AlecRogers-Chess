package com.chess.logic;

import com.chess.Util;
import com.chess.gui.GUIMove;

public class Interface {

    private ChessBoard board;
    private boolean whiteToMove;

    private String fromCoords;
    private String toCoords;


    public Interface(PGNWriter gameLog, ChessBoard board, boolean whiteToMove) {
        this.board = board;
        this.whiteToMove = whiteToMove;
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

    public Move getMoveFromPGN() { // FIXME
        PGNImporter pgn = new PGNImporter();
        pgn.getNextMove();

        return new Move();
    }

    public Move getMoveFromGUI() {

        GUIMove guiMove = new GUIMove();

        String coords = guiMove.getMove();
        while (!guiMove.hasFullMove()) {

            //Util.enterToContinue();

            coords = guiMove.getMove();
        }

        fromCoords = Util.parseFromCoords(coords);
        toCoords = Util.parseToCoords(coords);

        System.out.print("\nMOVE: " + coords + "\n\n");

        return new Move(board, whiteToMove, fromCoords, toCoords);
        //return new Move();
    }

}
