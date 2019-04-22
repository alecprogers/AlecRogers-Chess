package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class Knight extends Piece {

    public Knight() {
        setType(4);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public Knight(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(4);
        setHasMoved(false);
        setIsEmpty(false);
    }
}