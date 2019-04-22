package com.chess.logic;

public class Piece {

    private String rank;
    private String file;
    private int type; // K:0, Q:1, R:2, B:3, N:4, P:5
    private boolean hasMoved;
    private boolean white; // whether piece is white or black
    private boolean empty;

    // Default constructor
    public Piece() {
        rank = "";
        file = "";
        type = -1;
        hasMoved = false;
        white = false;
        empty = true;
    }

    // Copy constructor
    public Piece(Piece p) {
        rank = p.rank;
        file = p.file;
        type = p.type;
        hasMoved = p.hasMoved;
        white = p.white;
        empty = p.empty;
    }

    // Used for empty spaces
    public Piece(String rank, String file) {
        this.rank = rank;
        this.file = file;
        type = -1;
        hasMoved = false;
        white = false; // technically, this should not be true or false
        empty = true;
    }

    public String toString(Piece p) {
        String outString;
        int type = p.getType();
        boolean isWhite = p.isWhite();
        switch (type) {
            case 0: // king
                if (isWhite)
                    outString = "K";
                else
                    outString = "k";
                break;

            case 1: // queen
                if (isWhite)
                    outString = "Q";
                else
                    outString = "q";
                break;

            case 2: // rook
                if (isWhite)
                    outString = "R";
                else
                    outString = "r";
                break;

            case 3: // bishop
                if (isWhite)
                    outString = "B";
                else
                    outString = "b";
                break;

            case 4: // knight
                if (isWhite)
                    outString = "N";
                else
                    outString = "n";
                break;

            case 5: // pawn
                if (isWhite)
                    outString = "P";
                else
                    outString = "p";
                break;

            default:
                outString = " ";
        }
        return outString;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isWhite() {
        return this.white;
    }

    public void setIsWhite(boolean isWhite) {
        this.white = isWhite;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.empty = isEmpty;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
