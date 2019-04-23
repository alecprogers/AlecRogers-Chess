package com.chess.gui;

import javax.swing.*;

public class PawnPromotion {

    private int choice;

    public PawnPromotion() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        choice = JOptionPane.showOptionDialog(null, "What type of piece would you like to" +
                        "promote your pawn to?", "Select Promotion Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    public int getChoice() {
        return choice;
    }


}
