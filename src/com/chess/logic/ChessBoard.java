package com.chess.logic;

import com.chess.Util;
import com.chess.gui.PawnPromotion;
import com.chess.logic.pieces.*;

public class ChessBoard {

    private Piece[][] game;
    private String whiteEnPassant; // stores coords of pawn that can be en passanted, can never be more than one
    private String blackEnPassant;

    // Standard constructor for new game
    public ChessBoard() {

        // Declare board
        game = new Piece[8][8];

        // Set en passant variables to blank
        whiteEnPassant = "";
        blackEnPassant = "";

        // Declare black's back row
        game[0][0] = new Rook("8", "a", false);
        game[0][1] = new Knight("8", "b", false);
        game[0][2] = new Bishop("8", "c", false);
        game[0][3] = new Queen("8", "d", false);
        game[0][4] = new King("8", "e", false);
        game[0][5] = new Bishop("8", "f", false);
        game[0][6] = new Knight("8", "g", false);
        game[0][7] = new Rook("8", "h", false);

        // Declare black's pawns
        for (int col = 0; col < 8; col++) {
            game[1][col] = new Pawn("7", Util.colToFile(col), false);
        }

        // Declare empty spaces in middle
        for (int r = 2; r < 6; r++) {
            for (int c = 0; c < 8; c++) {
                game[r][c] = new Piece(Util.rowToRank(r), Util.colToFile(c));

            }
        }

        // Declare white's back row
        game[7][0] = new Rook("1", "a", true);
        game[7][1] = new Knight("1", "b", true);
        game[7][2] = new Bishop("1", "c", true);
        game[7][3] = new Queen("1", "d", true);
        game[7][4] = new King("1", "e", true);
        game[7][5] = new Bishop("1", "f", true);
        game[7][6] = new Knight("1", "g", true);
        game[7][7] = new Rook("1", "h", true);

        // Declare white's pawns
        for (int col = 0; col < 8; col++) {
            game[6][col] = new Pawn("2", Util.colToFile(col), true);
        }
    }

    // Copy constructor
    public ChessBoard(ChessBoard cb) {
        game = new Piece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                game[r][c] = new Piece(cb.game[r][c]);
            }
        }
        whiteEnPassant = cb.whiteEnPassant;
        blackEnPassant = cb.blackEnPassant;
    }

    // Receives (X, Y) coords to move from/to, does not return anything -- makes actual move
    public void makeMove(int fromX, int fromY, int toX, int toY) {

        // Check to see if piece is pawn that moves two spaces
        if (game[fromX][fromY].getType() == 5) {
            int xDiff = fromX - toX;
            if (xDiff == 2 || xDiff * -1 == 2) {
                if (game[fromX][fromY].isWhite()) { // pawn is white
                    whiteEnPassant = game[toX][toY].getFile() + game[toX][toY].getRank();
                }
                else { // pawn is black
                    blackEnPassant = game[toX][toY].getFile() + game[toX][toY].getRank();
                }
            }
        }

        // Check to see if move is en passant
        if (game[fromX][fromY].getType() == 5) { // piece is pawn
            if (fromY != toY && game[toX][toY].isEmpty()) { // moves diagonally to a empty spot
                // Must be en passant
                if (fromX == 3) { // if pawn is white
                    // Write over captured pawn
                    game[3][toY] = new Piece(Util.colToFile(3), Util.rowToRank(toY));
                }
                else { // pawn must be black
                    // Write over captured pawn
                    game[4][toY] = new Piece(Util.colToFile(4), Util.rowToRank(toY));
                }
            }
        }

        // Check to see if move is castle
        else if (game[fromX][fromY].getType() == 0) {
            if (fromX == 7 && fromY == 4) { // white king
                if (toX == 7 && toY == 6) { // kingside castle
                    // Move rook
                    game[7][5] = game[7][7];
                    game[7][5].setHasMoved(true);
                    game[7][5].setRank("1");
                    game[7][5].setFile("f");
                    game[7][7] = new Piece("1", "h");
                }
                else if (toX == 7 && toY == 2) { // queenside castle
                    // Move rook
                    game[7][3] = game[7][0];
                    game[7][3].setHasMoved(true);
                    game[7][3].setRank("1");
                    game[7][3].setFile("d");
                    game[7][0] = new Piece("1", "a");
                }
            }
            else if (fromX == 0 && fromY == 4) { // black king
                if (toX == 0 && toY == 6) { // kingside castle
                    // Move rook
                    game[0][5] = game[0][7];
                    game[0][5].setHasMoved(true);
                    game[0][5].setRank("8");
                    game[0][5].setFile("f");
                    game[0][7] = new Piece("8", "h");
                }
                else if (toX == 0 && toY == 2) { // queenside castle
                    // Move rook
                    game[0][3] = game[0][0];
                    game[0][3].setHasMoved(true);
                    game[0][3].setRank("8");
                    game[0][3].setFile("d");
                    game[0][0] = new Piece("8", "a");
                }
            }
        }

        // Make move like normal, update hasMoved
        game[toX][toY] = game[fromX][fromY];
        game[toX][toY].setHasMoved(true);

        // Update rank/file variables in moved piece
        game[toX][toY].setRank(Util.rowToRank(toX));
        game[toX][toY].setFile(Util.colToFile(toY));

        // Overwrite the square moved from with a blank piece
        game[fromX][fromY] = new Piece(Util.rowToRank(fromX), Util.colToFile(fromY));
    }

    // Receives (X, Y) coords to move from/to, returns true if move is physically legal
    public boolean checkPhysics(boolean whiteToMove, int fromX, int fromY, int toX, int toY) {

        // Check to make sure moved piece is correct color
        if (game[fromX][fromY].isWhite() != whiteToMove) {
            if (whiteToMove) {
                System.out.print("White to move. You may not move a black piece.\n\n");
            }
            else {
                System.out.print("Black to move. You may not move a white piece.\n\n");
            }
            return false;
        }

        // Get all possible moves for the selected piece
        String[] allMoves = getAllMoves(game[fromX][fromY]);

        // Get coordinates for destination
        String dest = Util.colToFile(toY) + Util.rowToRank(toX);

        // Check to see if dest is in array of valid moves
        boolean valid = false;
        for (int i = 0; i < allMoves.length && !valid; i++) {
            if (allMoves[i].equals(dest)) valid = true;
        }
        if (!valid) {
            return false;
        }

        // All tests have passed, move is valid from direction standpoint
        return true;
    }

    // Receives (X, Y) coords to move from/to, returns false if move is castle from or through check
    public boolean checkCastle(int fromX, int fromY, int toX, int toY) {

        boolean checkWhite = !game[fromX][fromY].isWhite();

        if (game[fromX][fromY].getType() == 0) {
            if (fromX == 7 && fromY == 4) { // white king
                if (toX == 7 && toY == 6) { // kingside castle
                    // returns false if in check
                    return !(isInCheck("e1", checkWhite) || isInCheck("f1", checkWhite));
                }
                else if (toX == 7 && toY == 2) { // queenside castle
                    return !(isInCheck("e1", checkWhite) || isInCheck("d1", checkWhite));
                }
            }
            else if (fromX == 0 && fromY == 4) { // black king
                if (toX == 0 && toY == 6) { // kingside castle
                    return !(isInCheck("e8", checkWhite) || isInCheck("f8", checkWhite));
                }
                else if (toX == 0 && toY == 2) { // queenside castle
                    return !(isInCheck("e8", checkWhite) || isInCheck("d8", checkWhite));
                }
            }
        }
        return true;
    }

    // Receives coords, returns true if specified location is in check
    public boolean isInCheck(String coords, boolean checkWhiteAttack) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece curPiece = game[r][c];

                // if curPiece exists and is of opposing color
                if (curPiece.isWhite() == checkWhiteAttack && !curPiece.isEmpty()) {
                    String[] allMoves = getAllMoves(curPiece);
                    for (int i = 0; i < allMoves.length; i++) {

                        // if curPiece has line of sight on king
                        if (allMoves[i].equals(coords)) {

                            // King must be in check
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Receives the (X, Y) coords to move from/to, returns true if no pieces are obstructing move
    public boolean checkObstruction(int fromX, int fromY, int toX, int toY) {

        // Check to see if move was castle
        if (game[fromX][fromY].getType() == 0) {
            if (fromX == 7 && fromY == 4) { // white king
                if (toX == 7 && toY == 6) { // kingside castle
                    return true;
                }
                else if (toX == 7 && toY == 2) { // queenside castle
                    return true;
                }
            }
            else if (fromX == 0 && fromY == 4) { // black king
                if (toX == 0 && toY == 6) {
                    return true;
                }
                else if (toX == 0 && toY == 2) {
                    return true;
                }
            }
        }

        // Create the "path" between the start and destination
        int minX = fromX;
        int maxX = toX;
        if (toX < fromX) {
            minX = toX;
            maxX = fromX;
        }
        int minY = fromY;
        int maxY = toY;
        if (toY < fromY) {
            minY = toY;
            maxY = fromY;
        }
        int xDiff = fromX - toX;
        int yDiff = fromY - toY;

        if (maxX - minX < 2 && maxY - minY < 2) { // only moved one square, can't have obstruction
            return true;
        }

        if (game[fromX][fromY].getType() == 4) { // piece is a knight, which can jump other pieces
            return true;
        }

        if (fromX == toX) { // if row doesn't change
            for (int c = minY + 1; c < maxY; c++) {
                if (!game[fromX][c].isEmpty()) {
                    return false;
                }
            }
        }
        else if (fromY == toY) { // if column doesn't change
            for (int r = minX + 1; r < maxX; r++) {
                if (!game[r][fromY].isEmpty()) {
                    return false;
                }
            }
        }
        else if (xDiff == yDiff) { // move is on positive diagonal
            for (int i = 1; i < maxX - minX; i++) {
                if (!game[minX + i][minY + i].isEmpty()) {
                    return false;
                }
            }
        }
        else if ((xDiff) * -1 == yDiff) { // move is on negative diagonal
            for (int i = 1; i < maxX - minX; i++) {
                if (!game[minX + i][maxY - i].isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    // Receives piece that user wants to move, returns an ArrayList with coords of every possible destination
    public String[] getAllMoves(Piece mover) {
        String[] allMoves = new String[27];
        int count = 0;

        int type = mover.getType();
        boolean hasMoved = mover.hasMoved();
        boolean isWhite = mover.isWhite();

        int mX = Util.rankToRow(mover.getRank());
        int mY = Util.fileToCol(mover.getFile());

        switch (type) {
            case 0: // king
                for (int r = -1; r < 2; r++) {
                    if (mX + r > -1 && mX + r < 8) { // if coords are within rows of ChessBoard
                        for (int c = -1; c < 2; c++) {
                            if (mY + c > -1 && mY + c < 8) { // if coords are within cols of ChessBoard
                                Piece curP = game[mX + r][mY + c];
                                if (curP.isEmpty() || curP.isWhite() != isWhite) {
                                    String move = curP.getFile() + curP.getRank();
                                    allMoves[count] = move;
                                    count++;
                                }
                            }
                        }
                    }
                }
                // Check to see if castle is possible

                // White kingside castle
                if (!game[7][4].hasMoved() && !game[7][7].hasMoved() && game[7][7].getType() == 2 &&
                        game[7][7].isWhite() && game[7][5].isEmpty() && game[7][6].isEmpty()) {

                    allMoves[count] = "g1"; // castle must be legal
                    count++;
                }
                // White queenside castle
                if (!game[7][4].hasMoved() && !game[7][0].hasMoved() && game[7][0].getType() == 2
                        && game[7][0].isWhite() && game[7][1].isEmpty() && game[7][2].isEmpty()
                        && game[7][3].isEmpty()) {

                    allMoves[count] = "c1"; // castle must be legal
                    count++;
                }
                // Black kingside castle
                if (!game[0][4].hasMoved() && !game[0][7].hasMoved() && game[0][7].getType() == 2
                        && !game[0][7].isWhite() && game[0][5].isEmpty() && game[0][6].isEmpty()) {

                    allMoves[count] = "g8"; // castle must be legal
                    count++;
                }
                // Black queenside castle
                if (!game[0][4].hasMoved() && !game[0][0].hasMoved() && game[0][0].getType() == 2
                        && !game[0][0].isWhite() && game[0][1].isEmpty() && game[0][2].isEmpty()
                        && game[0][3].isEmpty()) {

                    allMoves[count] = "c8"; // castle must be legal
                    count++;
                }
                break;

            case 1: // queen
                for (int i = 0; i < 8; i++) {
                    Piece curP = game[mX][i];
                    if (curP.isEmpty() || curP.isWhite() != isWhite) { // same row
                        if (checkObstruction(mX, mY, mX, i)) {
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    curP = game[i][mY];
                    if (curP.isEmpty() || curP.isWhite() != isWhite) { // same col
                        if (checkObstruction(mX, mY, i, mY)) {
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                }
                for (int i = -7; i < 8; i++) {
                    if (mX + i > -1 && mX + i < 8 && mY + i > -1 && mY + i < 8 ) { // positive diagonal
                        Piece curP = game[mX + i][mY + i];
                        if (curP.isEmpty() || curP.isWhite() != isWhite) {
                            if (checkObstruction(mX, mY, mX + i, mY + i)) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                    }
                    if (mX + i > -1 && mX + i < 8 && mY - i > -1 && mY - i < 8 ) { // negative diagonal
                        Piece curP = game[mX + i][mY - i];
                        if (curP.isEmpty() || curP.isWhite() != isWhite) {
                            if (checkObstruction(mX, mY, mX + i, mY - i)) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                    }
                }
                break;

            case 2: // rook
                for (int i = 0; i < 8; i++) {
                    Piece curP = game[mX][i];
                    if (curP.isEmpty() || curP.isWhite() != isWhite) { // same row
                        if (checkObstruction(mX, mY, mX, i)) {
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    curP = game[i][mY];
                    if (curP.isEmpty() || curP.isWhite() != isWhite) { // same col
                        if (checkObstruction(mX, mY, i, mY)) {
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                }
                break;

            case 3: // bishop
                for (int i = -7; i < 8; i++) {
                    if (mX + i > -1 && mX + i < 8 && mY + i > -1 && mY + i < 8 ) { // positive diagonal
                        Piece curP = game[mX + i][mY + i];
                        if (curP.isEmpty() || curP.isWhite() != isWhite) {
                            if (checkObstruction(mX, mY, mX + i, mY + i)) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                    }
                    if (mX + i > -1 && mX + i < 8 && mY - i > -1 && mY - i < 8 ) { // negative diagonal
                        Piece curP = game[mX + i][mY - i];
                        if (curP.isEmpty() || curP.isWhite() != isWhite) {
                            if (checkObstruction(mX, mY, mX + i, mY - i)) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                    }
                }
                break;

            case 4: // knight
                for (int r = -2; r < 3; r += 4) {
                    for (int c = -1; c < 2; c += 2) {
                        if (mX + r < 8 && mX + r > -1 && mY + c < 8 && mY + c > -1) {
                            Piece curP = game[mX + r][mY + c];
                            if (curP.isEmpty() || curP.isWhite() != isWhite) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                        if (mX + c < 8 && mX + c > -1 && mY + r < 8 && mY + r > -1) {
                            Piece curP = game[mX + c][mY + r];
                            if (curP.isEmpty() || curP.isWhite() != isWhite) {
                                String move = curP.getFile() + curP.getRank();
                                allMoves[count] = move;
                                count++;
                            }
                        }
                    }
                }
                break;

            case 5: // pawn
                if (isWhite) {
                    if (!blackEnPassant.equals("")) { // check to see if black has en passantable pawn
                        int enPassantCol = Util.fileToCol(blackEnPassant);
                        if (mX == 3 && (mY + 1 == enPassantCol || mY - 1 == enPassantCol)) {
                            String move = Util.colToFile(enPassantCol) + "6";
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    if (game[mX - 1][mY].isEmpty()) { // straight forward
                        Piece curP = game[mX - 1][mY];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                    if (!hasMoved && game[mX - 1][mY].isEmpty() && game[mX - 2][mY].isEmpty()) { // forward two
                        Piece curP = game[mX - 2][mY];
                        if (checkObstruction(mX, mY, mX - 2, mY)) { // have to check obstruction here
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    if (mY != 0 && !game[mX - 1][mY - 1].isEmpty() && !game[mX - 1][mY - 1].isWhite()) {
                        Piece curP = game[mX - 1][mY - 1];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                    if (mY != 7 && !game[mX - 1][mY + 1].isEmpty() && !game[mX - 1][mY + 1].isWhite()) {
                        Piece curP = game[mX - 1][mY + 1];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                }
                else { // pawn is black
                    if (!whiteEnPassant.equals("")) { // check to see if white has en passantable pawn
                        int enPassantCol = Util.fileToCol(whiteEnPassant);
                        if (mX == 4 && (mY + 1 == enPassantCol || mY - 1 == enPassantCol)) {
                            String move = Util.colToFile(enPassantCol) + "3";
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    if (game[mX + 1][mY].isEmpty()) { // straight forward
                        Piece curP = game[mX + 1][mY];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                    if (!hasMoved && game[mX + 1][mY].isEmpty() && game[mX + 2][mY].isEmpty()) { // forward two
                        Piece curP = game[mX + 2][mY];
                        if (checkObstruction(mX, mY, mX + 2, mY)) { // have to check obstruction here
                            String move = curP.getFile() + curP.getRank();
                            allMoves[count] = move;
                            count++;
                        }
                    }
                    if (mY != 0 && !game[mX + 1][mY - 1].isEmpty() && game[mX + 1][mY - 1].isWhite()) {
                        Piece curP = game[mX + 1][mY - 1];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                    if (mY != 7 && !game[mX + 1][mY + 1].isEmpty() && game[mX + 1][mY + 1].isWhite()) {
                        Piece curP = game[mX + 1][mY + 1];
                        String move = curP.getFile() + curP.getRank();
                        allMoves[count] = move;
                        count++;
                    }
                }
                break;
        }
        // Create new String array of exact size now that size is known
        String[] moves = new String[count];

        // Copy data into new array
        for (int i = 0; i < count; i++) {
            moves[i] = allMoves[i];
        }

        return moves;
    }

    // Checks if there are any pawns on back rows and gets promo choice from user if there are
    public void checkPromotion() {
        for (int r = 0; r < 8; r += 7) { // check just the back rows
            for (int c = 0; c < 8; c++) {
                Piece curPiece = game[r][c];
                boolean white = curPiece.isWhite();
                if (curPiece.getType() == 5) {
                    /*
                    System.out.print("\nPawn Promotion (Choose which piece you would like to promote your pawn to):" +
                            "\n\t1. Queen\n\t2. Rook\n\t3. Bishop\n\t4. Knight\n");
                    boolean valid = false;
                    int pType = 0;
                    while (!valid) {
                        pType = Util.getInt();
                        if (pType > 0 && pType < 5) {
                            valid = true;
                        }
                    }
                    */

                    PawnPromotion promo = new PawnPromotion();
                    int pType = promo.getChoice();

                    switch (pType) {
                        case 0:
                            game[r][c] = new Queen(Util.rowToRank(r), Util.colToFile(c), white);
                            break;
                        case 1:
                            game[r][c] = new Rook(Util.rowToRank(r), Util.colToFile(c), white);
                            break;
                        case 2:
                            game[r][c] = new Bishop(Util.rowToRank(r), Util.colToFile(c), white);
                            break;
                        case 3:
                            game[r][c] = new Knight(Util.rowToRank(r), Util.colToFile(c), white);
                            break;
                    }
                    // Update variables
                    game[r][c].setHasMoved(true);
                }
            }
        }
    }

    // Checks and updates en passant variables
    public void checkEnPassant(boolean whiteToMove) {
        // branches of if will be called in alternating turns, so current en passant is never overwritten
        if (whiteToMove) {
            blackEnPassant = "";
        }
        else {
            whiteEnPassant = "";
        }
    }

    // Receives whiteToMove, returns the coordinates of the corresponding king
    public String getKing(boolean whiteToMove) {

        // Find location of king
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece curPiece = game[r][c];
                // Check if curPiece is the king
                if (curPiece.getType() == 0 && curPiece.isWhite() == whiteToMove) {
                    return Util.colToFile(c) + Util.rowToRank(r);
                }
            }
        }
        return "";
    }

    // Prints board to console
    public void printBoard() {

        System.out.print("    _______________________________________________   \n");
        System.out.print("   |     |/////|     |/////|     |/////|     |/////|  \n");

        for (int r = 0; r < 8; r++) {
            int rank = (r - 8) * -1;
            System.out.print(" " + rank + " |");
            for (int c = 0; c < 8; c++) {
                boolean isBlackSquare;
                // if (r index is odd and c is even, or if c is odd and r is even)
                if (((r + 1) % 2 == 0 && (c + 1) % 2 == 1) || ((r + 1) % 2 == 1 && (c + 1) % 2 == 0)) {
                    isBlackSquare = true;
                }
                else isBlackSquare = false;
                if (isBlackSquare) System.out.print("/ ");
                else System.out.print("  ");
                System.out.print(game[r][c].toString());
                if (isBlackSquare) System.out.print(" /|");
                else System.out.print("  |");
            }
            System.out.print("  \n");
            if (r == 7) {
                System.out.print("   |/////|_____|/////|_____|/////|_____|/////|_____|  \n");
                System.out.print("      a     b     c     d     e     f     g      h    \n");
            }
            else if ((r + 1) % 2 == 1) {
                System.out.print("   |_____|/////|_____|/////|_____|/////|_____|/////|  \n");
                System.out.print("   |/////|     |/////|     |/////|     |/////|     |  \n");
            }
            else {
                System.out.print("   |/////|_____|/////|_____|/////|_____|/////|_____|  \n");
                System.out.print("   |     |/////|     |/////|     |/////|     |/////|  \n");
            }
        }
    }

    // Getter for main array
    public Piece[][] getGame() {
        return game;
    }
}
