package com.chess.logic;

import com.chess.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PGNHandler {

    private String fileName;
    ArrayList<String> pgnMoves;

    public PGNHandler() throws IOException {
        System.out.print("Enter the name of the PGN file you would like to import: ");
        fileName = Util.getString();

        File pgnFile = new File(fileName);
        if (!pgnFile.isFile()) {
            System.err.print("Unable to import PGN. File not found.\n");
        }
        else {
            Scanner inFile = new Scanner(new File(fileName));
            pgnMoves = new ArrayList<>();
            String curLine = inFile.nextLine();

            // Skip past header
            while (curLine.charAt(0) == '[' && inFile.hasNext()) {
                curLine = inFile.nextLine();
            }
            curLine = inFile.nextLine(); // skip past blank line

            while (inFile.hasNext()) {
                curLine = inFile.nextLine();

                String[] lineMoves = curLine.split(" ");

                // TODO
            }
        }
    }

    public void getNextMove() {

    }

}
