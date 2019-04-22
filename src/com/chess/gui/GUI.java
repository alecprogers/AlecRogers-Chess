package com.chess.gui;

import com.chess.Util;
import com.chess.logic.ChessBoard;
import com.chess.logic.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private ChessBoard chessBoard;

    private static Dimension WINDOW_SIZE = new Dimension(600, 600);
    private static Dimension BOARD_SIZE = new Dimension(400, 350); // fixme change these values ?
    private static Dimension TILE_SIZE = new Dimension(10, 10);

    private Color lightTileColor = Color.decode("#E6E6E6");
    private Color darkTileColor = Color.decode("#848484");

    public GUI() {
        chessBoard = new ChessBoard();

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
        private int row;
        private int col;

        TilePanel(BoardPanel boardPanel, String coords) {
            super(new GridBagLayout());

            this.coords = coords;

            row = Util.rankToRow(coords);
            col = Util.fileToCol(coords);

            setPreferredSize(TILE_SIZE);
            setTileColor();
            addPieceImg(chessBoard);
            validate();
        }

        private void addPieceImg(ChessBoard board) {
            removeAll();
            Piece curPiece = board.getGame()[row][col];

            if (curPiece.isEmpty())
                return;

            String imgPath = "src/graphics/"; // fixme
            String colorLetter = "w";

            if (!curPiece.isWhite()) {
                colorLetter = "b";
            }

            String pieceLetter = curPiece.toString().toLowerCase();

            try {
                BufferedImage img = ImageIO.read(new File(imgPath + colorLetter + pieceLetter + ".png"));
                add(new JLabel(new ImageIcon(img)));
            }
            catch (IOException e) {
                System.err.print("Unable to get piece image. Check graphics directory.\n");
                e.printStackTrace();
            }
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
