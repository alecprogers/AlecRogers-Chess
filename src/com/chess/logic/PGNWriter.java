package com.chess.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PGNWriter {

    private ArrayList<String> pgnMoves;
    private int moveNum;

    public PGNWriter() {
        pgnMoves = new ArrayList<>();
        moveNum = 0;
    }

    public void addMove(String moveCoords) {
        pgnMoves.add(moveCoords);
        moveNum++;
    }

    public void printToFile(String fileName) {

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write("[Event JavaChess]\n[Site University of Alabama]\n[Date ??/??/2019]\n\n");

            for (int i = 0; i < moveNum; i++) {
                fw.write(pgnMoves.get(i) + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.err.print("Unable to import PGN. File not found.\n");
        }
    }
}
