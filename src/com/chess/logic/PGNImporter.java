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
        String toCoords = Util.parseToCoords(pgnMoves.get(curMove));

        String fromCoords = Util.parseFromCoords(pgnMoves.get(curMove));

        System.out.print("Move " + curMove + ": " + fromCoords + toCoords + "\n");

        curMove++;

        return new Move();
    }
}
