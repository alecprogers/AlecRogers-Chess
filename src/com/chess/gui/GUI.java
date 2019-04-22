package com.chess.gui;

import com.chess.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private JFrame gameFrame;
    private BoardPanel boardPanel;

    private static Dimension WINDOW_SIZE = new Dimension(600, 600);
    private static Dimension BOARD_SIZE = new Dimension(400, 350); // fixme change these values ?
    private static Dimension TILE_SIZE = new Dimension(10, 10);

    private Color lightTileColor = Color.decode("#E6E6E6");
    private Color darkTileColor = Color.decode("#848484");

    public GUI() {
        gameFrame = new JFrame("Chess");
        gameFrame.setSize(WINDOW_SIZE);
        gameFrame.setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.setVisible(true);
    }

    private class BoardPanel extends JPanel {


        List<TilePanel> tiles;

        BoardPanel() {

            super(new GridLayout(8, 8));
            tiles = new ArrayList<>();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    TilePanel tilePanel = new TilePanel(this, Util.colToFile(c) + Util.rowToRank(r));
                    tiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_SIZE);
            validate();

        }
    }

    private class TilePanel extends JPanel {

        private String coords;

        TilePanel(BoardPanel boardPanel, String coords) {
            super(new GridBagLayout());

            this.coords = coords;

            setPreferredSize(TILE_SIZE);
            setTileColor();
            validate();
        }

        private void setTileColor() {
            int x = Util.rankToRow(coords) + 1;
            int y = Util.fileToCol(coords) + 1;
            // if (r index is odd and c is even, or if c is odd and r is even)
            if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
                setBackground(darkTileColor);
            }
            else setBackground(lightTileColor);
        }
    }
}
