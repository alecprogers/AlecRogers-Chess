package com.chess.logic;

import com.chess.Util;

public class Move {

    private String fromCoord;
    private String toCoord;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    // No arg constructor -- gets all its own info from user
    public Move() {

    }

    // Gets input from user, returns true if move was successful
    public boolean getMove(ChessBoard curGame, boolean whiteToMove) {
        System.out.print("Enter the coordinate to move FROM: ");
        String fromCoord = Util.getString();
        fromCoord = verifyCoord(fromCoord);


        System.out.print("Enter the coordinates to move TO: ");
        String toCoord = Util.getString();
        toCoord = verifyCoord(toCoord);

        this.fromCoord = fromCoord;
        this.fromX = Util.rankToRow(fromCoord);
        this.fromY = Util.fileToCol(fromCoord);
        this.toCoord = toCoord;
        this.toX = Util.rankToRow(toCoord);
        this.toY = Util.fileToCol(toCoord);

        // Check if move is legal from physics standpoint
        if (!curGame.checkMove(whiteToMove, fromX, fromY, toX, toY)) {
            return false;
        }

        // Check if move is castle that goes from or through check
        if (!curGame.checkCastle(fromX, fromY, toX, toY)) {
            return false;
        }

        // Make copy of curGame
        ChessBoard dupGame = new ChessBoard(curGame);

        // Make move on copy
        dupGame.makeMove(fromX, fromY, toX, toY);

        // Check if move puts king in check
        if (dupGame.isInCheck(dupGame.getKing(whiteToMove))) {
            return false;
        }

        // Make move on original
        curGame.makeMove(fromX, fromY, toX, toY);

        // Update en passant variables
        curGame.checkEnPassant(whiteToMove);

        // Check to see if any pawns need to be promoted
        curGame.checkPromotion();

        return true;
    }

    // Determines if game is in checkmate or stalemate
    public int getStatus(ChessBoard curGame, boolean whiteToMove) {

        /* Notes:
         *  -Team that just played can never be in check after their move, so can't be checkmate for them
         *  -If other player is in check, can't be stalemate
         *  -Only difference in checkmate and stalemate is whether or not king is currently in check
         *
         *  Returns -1 if stalemate
         *          0 if normal
         *          1 if white wins by checkmate
         *          2 if black wins by checkmate
         *          3 if black is in check
         *          4 if white is in check
         */

        // Check if other player is in check
        if (curGame.isInCheck(curGame.getKing(!whiteToMove))) {
            // check for checkmate, if true return 1 or 2
            boolean canMove = false; // goal is to make this true; find a legal move that does not result in check

            for (int r = 0; r < 8 && !canMove; r++) {
                for (int c = 0; c < 8 && !canMove; c++) {
                    Piece curPiece = curGame.getGame()[r][c];
                    if (!curPiece.isEmpty() && curPiece.isWhite() != whiteToMove) {
                        String[] allMoves = curGame.getAllMoves(curPiece);
                        for (int i = 0; i < allMoves.length && !canMove; i++) {
                            ChessBoard dupGame = new ChessBoard(curGame);
                            dupGame.makeMove(r, c, Util.rankToRow(allMoves[i]), Util.fileToCol(allMoves[i]));
                            if (!dupGame.isInCheck(dupGame.getKing(!whiteToMove))) {
                                canMove = true;
                            }
                        }
                    }
                }
            }

            if (!canMove) {
                if (whiteToMove) {
                    return 1; // white's turn, black must be checkmated
                }
                return 2; // black's turn, white must be checkmated
            }

            // if not checkmate, return 3 or 4
            if (whiteToMove) { // if it's white's turn, black must be the one in check
                return 3;
            }
            else { // if black isn't in check, it must be white in check
                return 4;
            }

        }
        else { // check for stalemate
            boolean canMove = false; // goal is to make this true; find a legal move that does not result in check

            for (int r = 0; r < 8 && !canMove; r++) {
                for (int c = 0; c < 8 && !canMove; c++) {
                    Piece curPiece = curGame.getGame()[r][c];
                    if (!curPiece.isEmpty() && curPiece.isWhite() != whiteToMove) {
                        String[] allMoves = curGame.getAllMoves(curPiece);
                        for (int i = 0; i < allMoves.length && !canMove; i++) {
                            ChessBoard dupGame = new ChessBoard(curGame);
                            dupGame.makeMove(r, c, Util.rankToRow(allMoves[i]), Util.fileToCol(allMoves[i]));
                            if (!dupGame.isInCheck(dupGame.getKing(!whiteToMove))) {
                                canMove = true;
                            }
                        }
                    }
                }
            }
            if (!canMove) {
                return -1;
            }
        }

        // If not in check and not stalemate, return 0
        return 0;
    }

    // Helper method that determines whether coordinates valid
    public String verifyCoord(String coord) {
        boolean isCoord = false;
        if (coord.matches("[a-h][1-8]")) isCoord = true;
        while (!isCoord) {
            System.out.print("Invalid coordinate. Please try again: ");
            coord = Util.getString();
            if (coord.matches("[a-h][1-8]")) isCoord = true;
        }
        return coord;
    }

}
