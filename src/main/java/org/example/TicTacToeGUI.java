package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameOver;
    private int playerXScore;
    private int playerOScore;
    private JLabel playerXLabel;
    private JLabel playerOLabel;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        add(boardPanel, BorderLayout.CENTER);

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameOver = false;

        initializeButtons(boardPanel);
        initializeScorePanel();

        setVisible(true);
    }

    private void initializeButtons(JPanel boardPanel) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }
    }

    private void initializeScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 2));

        JLabel playerXText = new JLabel("Player X:");
        playerXLabel = new JLabel("0");
        JLabel playerOText = new JLabel("Player O:");
        playerOLabel = new JLabel("0");

        scorePanel.add(playerXText);
        scorePanel.add(playerXLabel);
        scorePanel.add(playerOText);
        scorePanel.add(playerOLabel);

        add(scorePanel, BorderLayout.SOUTH);
    }

    private void makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            buttons[row][col].setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                updateScore();
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a tie!");
                resetGame();
            } else {
                currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move. Try again.");
        }
    }

    private boolean isValidMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        return buttons[row][col].getText().isEmpty();
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (!buttons[i][0].getText().isEmpty() && buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }
            // Check columns
            if (!buttons[0][i].getText().isEmpty() && buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }

        // Check diagonals
        if (!buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())) {
            return true;
        }
        if (!buttons[0][2].getText().isEmpty() && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateScore() {
        if (currentPlayer == 'X') {
            playerXScore++;
            playerXLabel.setText(String.valueOf(playerXScore));
        } else {
            playerOScore++;
            playerOLabel.setText(String.valueOf(playerOScore));
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver) {
                makeMove(row, col);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
