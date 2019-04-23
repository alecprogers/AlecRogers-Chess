package com.chess.gui;

import javax.swing.*;
import java.awt.*;

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

        /*
        frame = new JFrame("Load Game?");
        frame.setSize(WINDOW_SIZE);

        JLabel label = new JLabel("Would you like to load a saved game?");
        frame.add(label);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Would you like to save this game?", "Save Game?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    int status = game.getGameStatus();
                    if (status == 0 || status == 3 || status == 4) {
                        game.saveOnExit();
                    }
                }
                System.exit(0);
            }
        });


        fileName = JOptionPane.showInputDialog("Enter the name of the file you would like to load, or leave" +
                " field blank to start a new game:");
        */
    }

    public boolean getImportPgn() {
        return importPGN;
    }

    public String getFileName() {
        return fileName;
    }

}
