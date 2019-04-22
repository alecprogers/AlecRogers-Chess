package com.chess.logic;

import com.chess.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PGNImporter {

    private ChessBoard board;
    private String fileName;
    ArrayList<String> pgnMoves;
    private int curMove;

    public PGNImporter() {
        System.out.print("Enter the name of the PGN file you would like to import: ");
        fileName = Util.getString();

        File pgnFile = new File(fileName);
        if (!pgnFile.isFile()) {
            System.err.print("Unable to import PGN. File not found.\n");
        }
        else {
            try {
                Scanner inFile = new Scanner(new File(fileName));
                pgnMoves = new ArrayList<>();
                String curLine = inFile.nextLine();

                // Skip past header
                while (curLine.length() != 0 && inFile.hasNext()) {
                    curLine = inFile.nextLine();
                }

                while (inFile.hasNext()) {
                    curLine = inFile.nextLine();

                    pgnMoves.add(curLine);
                }
                inFile.close();
            }
            catch (IOException e) {
                System.err.print("Unable to import PGN. File not found.\n");
            }
        }
        curMove = 0;
        board = new ChessBoard();
    }

    public Move getNextMove() {
        String toCoords = parseToCoords(pgnMoves.get(curMove));

        String fromCoords = parseFromCoords(pgnMoves.get(curMove));


        System.out.print("Move " + curMove + ": " + fromCoords + toCoords + "\n");

        curMove++;

        return new Move();
    }

    public String parseToCoords(String inMove) {
        return inMove.substring(inMove.length() - 2);
    }

    public String parseFromCoords(String inMove) {
        return inMove.substring(0, inMove.length() - 2);

        /*
        Piece curPiece = new Piece();

        // Iterate through board and see which piece is capable of moving to toCoords
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                curPiece = board.getGame()[r][c];
                String[] allMoves = board.getAllMoves(curPiece);
                for (int i = 0; i < allMoves.length; i++) {
                    if (allMoves[i].equals(toCoords)) {
                        return curPiece.getFile() + curPiece.getRank();
                    }
                }
            }
        }

        return "";

         */
    }

}
