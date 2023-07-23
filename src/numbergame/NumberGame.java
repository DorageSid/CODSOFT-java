package numbergame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Random;

public class NumberGame extends JFrame implements ActionListener {
    private JTextField guessField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private int randomNumber;
    private int maxAttempts;
    private int score;
    private int attempts;
    private boolean gameInProgress;

    public NumberGame() {
        setTitle("Number Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Number Game");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(120, 30, 150, 30);
        panel.add(titleLabel);

        JLabel guessLabel = new JLabel("Enter your guess:");
        guessLabel.setBounds(70, 80, 120, 20);
        panel.add(guessLabel);

        guessField = new JTextField();
        guessField.setBounds(190, 80, 50, 20);
        panel.add(guessField);
        guessField.setColumns(10);

        guessButton = new JButton("Guess");
        guessButton.setBounds(110, 120, 80, 25);
        panel.add(guessButton);
        guessButton.addActionListener(this);

        resultLabel = new JLabel(" ");
        resultLabel.setBounds(100, 160, 200, 20);
        panel.add(resultLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20, 200, 100, 20);
        panel.add(scoreLabel);

        attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setBounds(180, 200, 100, 20);
        panel.add(attemptsLabel);

        setBounds(100, 100, 300, 280);
        setResizable(false);
        setVisible(true);

        initializeGame();
    }

    private void initializeGame() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        maxAttempts = 5;
        score = 0;
        attempts = 0;
        resultLabel.setText(" ");
        scoreLabel.setText("Score: " + score);
        attemptsLabel.setText("Attempts: " + attempts);
        gameInProgress = true;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == guessButton) {
            int guess;
            try {
                guess = Integer.parseInt(guessField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gameInProgress) {
                attempts++;

                if (guess == randomNumber) {
                    resultLabel.setText("Congratulations! You guessed the correct number.");
                    score += maxAttempts - attempts + 1;
                    scoreLabel.setText("Score: " + score);
                    gameInProgress = false;
                    int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION) {
                        resetGame();
                        initializeGame();
                    } else {
                        System.exit(0);
                    }
                } else if (guess < randomNumber) {
                    resultLabel.setText("Too low! Guess a higher number.");
                } else {
                    resultLabel.setText("Too high! Guess a lower number.");
                }

                if (attempts == maxAttempts) {
                    JOptionPane.showMessageDialog(this, "Game over! You ran out of attempts.\nThe correct number was: " + randomNumber, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    gameInProgress = false;
                    int playAgain = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION) {
                        resetGame();
                        initializeGame();
                    } else {
                        System.exit(0);
                    }
                }

                attemptsLabel.setText("Attempts: " + attempts);
                if (gameInProgress) {
                    showHint(guess);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please start a new game.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void showHint(int guess) {
        String hint;

        int difference = Math.abs(randomNumber - guess);
        if (difference >= 20) {
            hint = "You're far away from the number.";
        } else if (difference >= 10) {
            hint = "You're getting closer but still a bit far.";
        } else if (difference >= 5) {
            hint = "You're very close now, just a few steps away.";
        } else {
            hint = "You're extremely close! You're almost there.";
        }

        JOptionPane.showMessageDialog(this, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        guessField.setText("");
        resultLabel.setText("");
        attempts = 0;
        gameInProgress = true;
    }

public static void main(String[] args) {
        new NumberGame();
    }
}
