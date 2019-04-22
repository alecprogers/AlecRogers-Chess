/* Tips to improve readability:
 *  1. When using for-loops in 2D array contexts, I typically call the index variables r (rows) and c (columns). So
 *      if you ever see an r or a c, it is likely iterating across the board in some form or another. I use i for
 *      all other applications.
 *
 *
 * GRAPHICS COURTESY OF WIKIMEDIA FOUNDATION
 */

package com.chess;

import com.chess.logic.*;
import com.chess.gui.*;

public class ChessDriver {

    public static void main(String[] args) {
        System.out.print("Hello, Pariveda\n\n");

        Game game = new Game();

        GUI gui = new GUI();

        int gameStatus = 0;

        /* gameStatus meaning:
         *
         * -1 if stalemate
         *  0 if normal
         *  1 if white wins by checkmate
         *  2 if black wins by checkmate
         *  3 if black is in check
         *  4 if white is in check
         */

        while (gameStatus == 0 || gameStatus == 3 || gameStatus == 4) { // game is still going
            game.printGame();

            // gui.update(game.getGame());

            game.playTurn();

            gameStatus = game.getGameStatus();

            if (gameStatus == 3)
                System.out.print("BLACK IN CHECK\n");
            else if (gameStatus == 4)
                System.out.print("WHITE IN CHECK\n");
        }

        if (gameStatus == 1)
            System.out.print("CHECKMATE: WHITE WINS\n");
        else if (gameStatus == 2)
            System.out.print("CHECKMATE: BLACK WINS");
        else
            System.out.print("STALEMATE: DRAW\n");


    }
}
