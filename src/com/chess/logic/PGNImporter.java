package com.chess.logic;

import com.chess.Util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PGNImporter {

    private String fileName;
    ArrayList<String> pgnMoves;
    private int curMove;
    private boolean endOfFile;

    public PGNImporter(String fileName) {
        this.fileName = fileName;

        /*
        System.out.print("Enter the name of the PGN file you would like to import: ");
        fileName = Util.getString();
        */

        File pgnFile = new File(fileName);
        if (!pgnFile.isFile()) {
            System.err.print("Unable to import PGN. File not found.\n");
            JOptionPane.showMessageDialog(null, "Error: Unable to import PGN. File not found.");
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
        curMove = -1;
    }

    public String getNextMove() {
        curMove++;
        endOfFile = (curMove == pgnMoves.size() - 1);

        System.out.print(pgnMoves.get(curMove) + "\n");
        return pgnMoves.get(curMove);
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }
}
