package com.chess.logic;

import com.chess.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PGNWriter {

    ArrayList<String> pgnMoves;
    String fileName = "";
    int moveNum;

    public PGNWriter() {
        pgnMoves = new ArrayList<>();
        System.out.print("Enter the name the file to save to: ");
        this.fileName = Util.getString();
        moveNum = 0;
    }

    public void addMove(String moveCoords) {
        pgnMoves.add(moveCoords);
        moveNum++;
    }

    public void printToFile() {

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write("[game info]\n\n"); // todo add actual game data

            for (int i = 0; i < moveNum; i++) {
                fw.write(pgnMoves.get(i) + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.err.print("Unable to import PGN. File not found.\n");
        }
    }
}
