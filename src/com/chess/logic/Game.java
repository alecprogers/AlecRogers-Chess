package com.chess.logic;

import com.chess.gui.GUI;

public class Game {

    private ChessBoard game;
    private Move curMove;

    private boolean whiteToMove;
    private boolean whiteInCheck;
    private boolean blackInCheck;

    private int gameStatus;


    public Game() {
        game = new ChessBoard();

        whiteToMove = true;
        whiteInCheck = false;
        blackInCheck = false;

        gameStatus = 0;
    }

    // GUI Constructor // FIXME
    public Game(ChessBoard game, String fromCoords, String toCoords) {
        this.game = game;

        whiteToMove = true;

        this.curMove = new Move(game, whiteToMove, fromCoords, toCoords);
    }

    public ChessBoard playTurn() {

        Move curMove = new Move(game, whiteToMove);

        boolean validMove = false;
        while (!validMove) {
            validMove = curMove.checkMove();
            if (!validMove) {
                System.out.print("Invalid move. Please enter a different move.\n");
            }
        }
        gameStatus = curMove.getStatus(game, whiteToMove);

        whiteToMove = !whiteToMove;

        return game;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void printGame() {
        if (whiteToMove)
            System.out.print("WHITE TO MOVE\n");
        else
            System.out.print("BLACK TO MOVE\n");
        game.printBoard();
    }

    public ChessBoard getChessBoard() {
        return game;
    }

}
