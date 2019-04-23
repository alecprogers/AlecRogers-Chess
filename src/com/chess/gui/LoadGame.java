package com.chess.gui;

import javax.swing.*;

public class LoadGame {

    private String fileName;
    private boolean importPGN;

    public LoadGame() {
        JFrame frame = new JFrame();
        if (JOptionPane.showConfirmDialog(frame, "Would you like to load a saved game?", "Load Game?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            fileName = JOptionPane.showInputDialog("Enter the name of the file you would like to load: ");
            importPGN = true;
        }
        else {
            importPGN = false;
        }
    }

    public boolean getImportPgn() {
        return importPGN;
    }

    public String getFileName() {
        return fileName;
    }

}
