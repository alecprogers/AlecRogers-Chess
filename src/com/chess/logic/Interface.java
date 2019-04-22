package com.chess.logic;

import com.chess.Util;

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

        // TODO Not sure what needs to go here


        // return new Move(board, whiteToMove, fromCoords, toCoords) // FIXME this should be the return
        return new Move();
    }

}
