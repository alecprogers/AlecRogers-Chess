package com.chess.logic.pieces;

import com.chess.logic.Piece;

public class Rook extends Piece {

    public Rook() {
        setType(2);
        setHasMoved(false);
        setIsEmpty(false);
    }

    public Rook(String rank, String file, boolean isWhite) {
        setRank(rank);
        setFile(file);
        setIsWhite(isWhite);
        setType(2);
        setHasMoved(false);
        setIsEmpty(false);
    }

}