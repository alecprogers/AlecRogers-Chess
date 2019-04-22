package com.chess;

import java.util.Scanner;

public class Util {

    // Gets chess coord, returns x value of corresponding array index
    public static int rankToRow(String coord) {
        if (coord.length() == 1) { // if it is a standalone value
            char rank = coord.charAt(0);
            int row = Character.getNumericValue(rank);
            row -= 8;
            row *= -1;
            return row;
        }
        // if it's part of coords
        char rank = coord.charAt(1);
        int row = Character.getNumericValue(rank);
        row -= 8;
        row *= -1;
        return row;
    }

    // Gets chess coord, returns y value of corresponding array index
    public static int fileToCol(String coord) {
        char file = coord.charAt(0);
        return (int)file - 97;
    }

    // Gets array x index, returns corresponding chess coord file
    public static String rowToRank(int row) {
        int rank = row - 8;
        rank *= -1;
        return rank + "";
    }

    // Gets array y index, returns corresponding chess coord rank
    public static String colToFile(int col) {
        char file = (char)col;
        file += 97;
        return file + "";
    }

    // Gets full coords, returns coords to move from
    public static String parseFromCoords(String inMove) {
        return inMove.substring(0, inMove.length() - 2);
    }

    // Gets full coords, returns coords to move to
    public static String parseToCoords(String inMove) {
        return inMove.substring(inMove.length() - 2);
    }

    // Helper method that gets String from user
    public static String getString() {
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextLine();
    }

    // Helper method that safely gets int from user
    public static int getInt() {
        Scanner keyboard = new Scanner(System.in);
        String strIp;
        int intIp = 0;
        boolean numeric = false;
        while (!numeric) {
            strIp = keyboard.next();
            if (strIp.matches("\\d+")) {
                intIp = Integer.parseInt(strIp);
                numeric = true;
            }
            else {
                System.out.print("Invalid input. Please try again.\n");
            }
        }
        return intIp;
    }

    // Helper method that determines whether coordinates valid
    public static String verifyCoord(String coord) {
        boolean isCoord = false;
        if (coord.matches("[a-h][1-8]")) isCoord = true;
        while (!isCoord) {
            System.out.print("Invalid coordinate. Please try again: ");
            coord = Util.getString();
            if (coord.matches("[a-h][1-8]")) isCoord = true;
        }
        return coord;
    }

    // Helper method that makes user press enter to continue
    public static void enterToContinue() {
        System.out.print("Press Enter to continue\n");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }
}
