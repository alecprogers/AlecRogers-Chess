package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class King extends Piece {

    public King() {
        setType(0);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public King(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(0);
        setHasMoved(false);
        setIsEmpty(false);
    }
}