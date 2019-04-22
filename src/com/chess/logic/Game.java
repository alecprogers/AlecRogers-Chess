package com.chess.logic;

public class Game {

    private ChessBoard game;

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

    public ChessBoard playTurn() {

        Move curMove = new Move();

        boolean validMove = false;
        while (!validMove) {
            validMove = curMove.getMove(game, whiteToMove);
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

    public ChessBoard getGame() {
        return game;
    }

}
