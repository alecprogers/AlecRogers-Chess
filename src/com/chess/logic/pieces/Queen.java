package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class Queen extends Piece {

    public Queen() {
        setType(1);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public Queen(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(1);
        setHasMoved(false);
        setIsEmpty(false);
    }
}