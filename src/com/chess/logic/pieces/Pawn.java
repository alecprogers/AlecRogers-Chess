package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class Pawn extends Piece {

    public Pawn() {
        setType(5);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public Pawn(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(5);
        setHasMoved(false);
        setIsEmpty(false);
    }
}