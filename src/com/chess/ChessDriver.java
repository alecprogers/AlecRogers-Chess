/* Created by Alec Rogers as part of the MIS 220 Pariveda Competition
 * aprogers2@crimson.ua.edu
 *
 * GRAPHICS COURTESY OF WIKIMEDIA FOUNDATION
 */

package com.chess;

import com.chess.logic.*;
import com.chess.gui.*;

import javax.swing.*;

public class ChessDriver {

    public static void main(String[] args) {
        System.out.print("Hello, Pariveda\n\n");

        LoadGame inGame = new LoadGame();
        String ipNamePGN = inGame.getFileName();

        boolean importPGN = inGame.getImportPgn();

        Game game = new Game(importPGN, ipNamePGN);

        GUI gui = new GUI(game);

        game.setGUI(gui);

        int gameStatus = 0;

        /* gameStatus key:
         *
         * -1 if stalemate
         *  0 if normal
         *  1 if white wins by checkmate
         *  2 if black wins by checkmate
         *  3 if black is in check
         *  4 if white is in check
         */

        while (gameStatus == 0 || gameStatus == 3 || gameStatus == 4) { // game is still going
            //game.printGame(); // prints board to console

            game.playTurn();

            gameStatus = game.getGameStatus();

            if (gameStatus == 3)
                System.out.print("BLACK IN CHECK\n");
            else if (gameStatus == 4)
                System.out.print("WHITE IN CHECK\n");

            if (gameStatus == 3) {
                gui.updateTitle("BLACK IN CHECK");
            }
            else if (gameStatus == 4) {
                gui.updateTitle("WHITE IN CHECK");
            }
            else if (game.isWhiteToMove()) {
                gui.updateTitle("WHITE TO MOVE");
            }
            else {
                gui.updateTitle("BLACK TO MOVE");
            }

            gui.updateBoard(game.getChessBoard());
        }



        if (gameStatus == 1) {
            System.out.print("CHECKMATE: WHITE WINS\n");
            JOptionPane.showMessageDialog(null, "CHECKMATE: WHITE WINS!");
            gui.updateTitle("CHECKMATE: WHITE WINS");
        }
        else if (gameStatus == 2) {
            System.out.print("CHECKMATE: BLACK WINS\n");
            JOptionPane.showMessageDialog(null, "CHECKMATE: BLACK WINS!");
            gui.updateTitle("CHECKMATE: BLACK WINS");
        }
        else {
            System.out.print("STALEMATE: DRAW\n");
            JOptionPane.showMessageDialog(null, "STALEMATE: DRAW");
            gui.updateTitle("STALEMATE: DRAW");
        }
    }
}
