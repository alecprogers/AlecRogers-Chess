package com.chess.logic;

import com.chess.gui.GUI;

public class Game {

    private ChessBoard board;
    private Move curMove;

    private boolean whiteToMove;
    private boolean whiteInCheck;
    private boolean blackInCheck;

    private int gameStatus;


    public Game() {
        board = new ChessBoard();

        whiteToMove = true;
        whiteInCheck = false;
        blackInCheck = false;

        gameStatus = 0;
    }

    // GUI Constructor // FIXME
    public Game(ChessBoard board, String fromCoords, String toCoords) {
        this.board = board;

        whiteToMove = true;

        this.curMove = new Move(board, whiteToMove, fromCoords, toCoords);
    }

    public ChessBoard playTurn() {

        Interface intFace = new Interface(board, whiteToMove);
        Move curMove = intFace.getMoveFromConsole();

        boolean validMove = false;
        while (!validMove) {
            validMove = curMove.checkMove();
            if (!validMove) {
                System.out.print("Invalid move. Please enter a different move.\n");
                curMove = intFace.getMoveFromConsole();
            }
        }
        gameStatus = curMove.getStatus(board, whiteToMove);

        whiteToMove = !whiteToMove;

        return board;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void printGame() {
        if (whiteToMove)
            System.out.print("WHITE TO MOVE\n");
        else
            System.out.print("BLACK TO MOVE\n");
        board.printBoard();
    }

    public ChessBoard getChessBoard() {
        return board;
    }

}
