package com.chess.gui;

import com.chess.Util;
import com.chess.logic.ChessBoard;
import com.chess.logic.Game;
import com.chess.logic.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private ChessBoard game;

    private String fromCoords;
    private String toCoords;

    private Color lightTileColor = Color.decode("#E6E6E6");
    private Color darkTileColor = Color.decode("#848484");

    private static Dimension WINDOW_SIZE = new Dimension(600, 600);
    private static Dimension BOARD_SIZE = new Dimension(400, 350); // fixme change these values ?
    private static Dimension TILE_SIZE = new Dimension(10, 10);

    public GUI(ChessBoard game) {
        this.game = game;

        fromCoords = "";
        toCoords = "";

        gameFrame = new JFrame("Chess");
        gameFrame.setSize(WINDOW_SIZE);
        gameFrame.setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.setVisible(true);
    }

    public void updateTitle(String newTitle) {
        gameFrame.setTitle(newTitle);
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

        public void drawBoard(ChessBoard board) {
            removeAll();
            for (TilePanel tilePanel : tiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {

        JButton btn;
        private Piece curPiece;
        private String coords;
        private int row;
        private int col;

        TilePanel(BoardPanel boardPanel, String coords) {
            super(new GridBagLayout());

            this.coords = coords;

            row = Util.rankToRow(coords);
            col = Util.fileToCol(coords);

            curPiece = game.getGame()[row][col];

            setPreferredSize(TILE_SIZE);
            setTileColor();





            setLayout(new GridLayout());

            if (curPiece.isEmpty()) {
                btn = addButton();
            }
            else {
                btn = addPieceImg(getTilePiece());
            }
            this.add(btn);


            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.print("\nButton at " + coords + " has been pressed\n");

                    if (fromCoords.equals("")) {
                        fromCoords = coords;
                    }
                    else if (fromCoords.equals(coords)) {
                        fromCoords = "";
                    }
                    else {
                        toCoords = coords;
                    }
                    if (!fromCoords.equals("") && !toCoords.equals("")) {
                       // Game thisMove = new Game(game, fromCoords, toCoords);
                        // thisMove.playTurn();
                    }
                }
            });

            validate();
        }

        public void drawTile(ChessBoard board) {
            setTileColor();
            if (curPiece.isEmpty()) {
                btn = addButton();
            }
            else {
                btn = addPieceImg(getTilePiece());
            }
            validate();
            repaint();
        }

        private String getTilePiece() {
            String colorLetter = "";
            String pieceLetter = "";

            if (!curPiece.isEmpty()) {
                colorLetter = curPiece.isWhite() ? "w" : "b"; // fixme not certain about this...
                pieceLetter = curPiece.toString().toLowerCase();
            }
            return colorLetter + pieceLetter;
        }

        private JButton addButton() {
            JButton btn = new JButton();
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);

            return btn;
        }

        private JButton addPieceImg(String fileName) {
            removeAll();

            String imgPath = "src/graphics/";

            ImageIcon pieceImg = new ImageIcon(imgPath + fileName + ".png");
            JButton btn = new JButton(pieceImg);

            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);

            return btn;
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
