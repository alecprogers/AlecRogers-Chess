package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class Bishop extends Piece {

    public Bishop() {
        setType(3);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public Bishop(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(3);
        setHasMoved(false);
        setIsEmpty(false);
    }
}