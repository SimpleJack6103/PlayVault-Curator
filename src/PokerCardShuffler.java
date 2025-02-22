import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PokerCardShuffler extends JFrame {
    private final JPanel cardPanel;
    private final List<ImageIcon> cardImages = new ArrayList<>();
    private final String cardPath = "C:/Users/antbu/IdeaProjects/Lab 5 Pair Programming/src/cards/";
    private final String[] suits = {"hearts", "diamonds", "clubs", "spades"};
    private final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};

    public PokerCardShuffler() {
        setTitle("Poker Card Shuffler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 100, 0)); // Dark Green Background

        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(4, 13, 10, 10)); // 4 rows, 13 cols, with spacing
        cardPanel.setBackground(new Color(0, 100, 0));
        cardPanel.setPreferredSize(new Dimension(1000, 600)); // Set default size

        loadCards();

        JButton shuffleButton = new JButton("Shuffle Cards");
        shuffleButton.addActionListener(e -> shuffleAndDisplayCards());

        add(cardPanel, BorderLayout.CENTER);
        add(shuffleButton, BorderLayout.SOUTH);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        SwingUtilities.invokeLater(this::shuffleAndDisplayCards); // Ensure proper scaling
    }

    private void loadCards() {
        for (String suit : suits) {
            for (String rank : ranks) {
                String cardName = rank + "_of_" + suit + ".png";
                File file = new File(cardPath + cardName);
                if (file.exists()) {
                    cardImages.add(new ImageIcon(cardPath + cardName));
                } else {
                    System.err.println("Missing file: " + cardName);
                }
            }
        }
    }

    private void shuffleAndDisplayCards() {
        cardPanel.removeAll();
        Collections.shuffle(cardImages);
        for (ImageIcon cardImage : cardImages) {
            JLabel cardLabel = new JLabel(scaleImage(cardImage));
            cardPanel.add(cardLabel);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private ImageIcon scaleImage(ImageIcon icon) {
        int panelWidth = Math.max(cardPanel.getWidth(), 1000); // Prevent 0 width
        int panelHeight = Math.max(cardPanel.getHeight(), 600); // Prevent 0 height
        int width = panelWidth / 14;
        int height = panelHeight / 5;
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}


