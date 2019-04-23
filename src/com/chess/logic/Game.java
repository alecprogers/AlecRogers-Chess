package com.chess.logic;

import javax.swing.*;

public class Game {

    private ChessBoard board;
    private Move curMove;

    private boolean whiteToMove;
    private boolean whiteInCheck;
    private boolean blackInCheck;

    private int gameStatus;

    private boolean importPGN;
    private String ipNamePGN;

    private PGNImporter inputPGN;
    private PGNWriter gameLog;


    public Game(boolean importPgn, String ipNamePGN) {
        this.importPGN = importPgn;
        this.ipNamePGN = ipNamePGN;

        board = new ChessBoard();


        if (importPgn) {
            inputPGN = new PGNImporter(ipNamePGN);
        }

        gameLog = new PGNWriter();

        whiteToMove = true;
        whiteInCheck = false;
        blackInCheck = false;

        gameStatus = 0;
    }

    public ChessBoard playTurn() {

        Interface intFace = new Interface(board, whiteToMove, inputPGN);

        Move curMove;

        if (importPGN && !intFace.isEndOfPGN()) {
            curMove = intFace.getMoveFromPGN();
        }
        else {
            curMove = intFace.getMoveFromGUI();
            intFace.resetGUIMove();
        }

        //Move curMove = intFace.getMoveFromConsole();

        boolean validMove = false;
        while (!validMove) {
            validMove = curMove.checkMove();
            if (!validMove) {
                System.out.print("Invalid move. Please enter a different move.\n");

                if (importPGN && !intFace.isEndOfPGN()) {
                    curMove = intFace.getMoveFromPGN();
                }
                else {
                    curMove = intFace.getMoveFromGUI();
                    intFace.resetGUIMove();
                }

                //curMove = intFace.getMoveFromConsole();
            }
        }
        gameStatus = curMove.getStatus(board, whiteToMove);

        gameLog.addMove(curMove.getFromCoord() + curMove.getToCoord());

        whiteToMove = !whiteToMove;

        return board;
    }

    // Prints board to console
    public void printGame() {
        if (whiteToMove)
            System.out.print("WHITE TO MOVE\n");
        else
            System.out.print("BLACK TO MOVE\n");
        board.printBoard();
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public ChessBoard getChessBoard() {
        return board;
    }

    public boolean isWhiteToMove() {
        return whiteToMove;
    }

    public void saveOnExit() {
        String fileName = JOptionPane.showInputDialog("What would you like to name your save file?");
        gameLog.printToFile(fileName);
    }

}
