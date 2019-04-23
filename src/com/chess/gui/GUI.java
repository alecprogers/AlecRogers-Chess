package com.chess.gui;

import com.chess.Util;
import com.chess.logic.ChessBoard;
import com.chess.logic.Game;
import com.chess.logic.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private Game game;
    private ChessBoard chessBoard;

    private GUIMove guiMove;

    private Color borderColor = Color.decode("#424242");
    private Color borderFlashColor = Color.decode("#DF013A");

    private Color lightTileColor = Color.decode("#E6E6E6");
    private Color darkTileColor = Color.decode("#848484");
    private Color redTileColor = Color.decode("#F19A88");
    private Color selectedTileColor = Color.decode("#A9BCF5");
    private Color highlightLightTile = Color.decode("#CAD9F8");
    private Color highlightDarkTile = Color.decode("#8295BE");

    //private static Dimension WINDOW_SIZE = new Dimension(600, 600);
    private static Dimension WINDOW_SIZE = new Dimension(650, 650);
    private static Dimension BOARD_SIZE = new Dimension(400, 350);
    private static Dimension TILE_SIZE = new Dimension(10, 10);

    public GUI(Game game) {
        this.game = game;
        this.chessBoard = game.getChessBoard();

        guiMove = new GUIMove();

        gameFrame = new JFrame("WHITE TO MOVE");
        gameFrame.setSize(WINDOW_SIZE);
        gameFrame.setResizable(false);
        gameFrame.setLayout(new BorderLayout());

        gameFrame.getRootPane().setBorder(BorderFactory.
                createMatteBorder(5, 5, 5, 5, borderColor));

        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(gameFrame,
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

        gameFrame.setVisible(true);
    }

    public void flashBorder() {
        gameFrame.getRootPane().setBorder(BorderFactory.
                createMatteBorder(5, 5, 5, 5, borderFlashColor));
        try {
            Thread.sleep(250);
        }
        catch(InterruptedException ex){

        }
        gameFrame.getRootPane().setBorder(BorderFactory.
                createMatteBorder(5, 5, 5, 5, borderColor));
    }

    public void updateBoard(ChessBoard game) {
        this.chessBoard = game;
        boardPanel.drawBoard(game);
    }

    public void updateTitle(String newTitle) {
        gameFrame.setTitle(newTitle);
    }

    public class BoardPanel extends JPanel {

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

            int i = -1;
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    i++;
                    Piece curPiece = board.getGame()[r][c];
                    tiles.set(i, new TilePanel(boardPanel, Util.colToFile(c) + Util.rowToRank(r)));
                    add(tiles.get(i));
                }
            }
            this.validate();
        }

        public void selectTile(String coords, boolean select) {
            int row = Util.rankToRow(coords);
            int col = Util.fileToCol(coords);

            int index = (row * 8) + col;

            if (select) {
                tiles.get(index).select(selectedTileColor);
            }
            else {
                tiles.get(index).deselect();
            }
        }

        public void selectTileRed(String coords, boolean select) {
            int row = Util.rankToRow(coords);
            int col = Util.fileToCol(coords);

            int index = (row * 8) + col;

            if (select) {
                tiles.get(index).select(redTileColor);
            }
            else {
                tiles.get(index).deselect();
            }
        }

        public void highlightTiles(String[] allMoves, boolean highlight) {
            for (int i = 0; i < allMoves.length; i++) {
                int row = Util.rankToRow(allMoves[i]);
                int col = Util.fileToCol(allMoves[i]);

                int index = (row * 8) + col;

                if (highlight) {
                    if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
                        tiles.get(index).select(highlightDarkTile);
                    }
                    else {
                        tiles.get(index).select(highlightLightTile);
                    }
                } else {
                    tiles.get(index).deselect();
                }

            }
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

            this.row = Util.rankToRow(coords);
            this.col = Util.fileToCol(coords);

            this.curPiece = chessBoard.getGame()[row][col];

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
                    guiMove.clickCoords(coords, game, boardPanel);
                }
            });

            validate();
        }

        private void select(Color color) {
            setBackground(color);
            validate();
        }

        private void deselect() {
            setTileColor();
            validate();
        }

        private String getTilePiece() {
            String colorLetter = "";
            String pieceLetter = "";

            if (!curPiece.isEmpty()) {
                colorLetter = curPiece.isWhite() ? "w" : "b";
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
