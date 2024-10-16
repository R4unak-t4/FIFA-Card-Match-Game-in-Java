package org.example;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class App {
    class Card{
        String cardNme;
        ImageIcon cardIcon;

        Card(String cardNme, ImageIcon cardIcon) {
            this.cardNme = cardNme;
            this.cardIcon = cardIcon;
        }

        public String toString(){
            return cardNme;
        }
    }
    String [] cardList = {
            "Dimaria","Dybala","fornlan","griezman","Haland","maldini",
            "Messi","Neymr","Ronldo","Vanbasten"
    };
    int rows = 4;
    int col = 5;
    int cardWidth = 180;
    int cardHeight = 240;

    ArrayList<Card> cardset;
    ImageIcon cardBackimgicon;

    int boardWidth = col*cardWidth;
    int boardHeight = rows*cardHeight;

    JFrame Frame = new JFrame("FIFA CARD MATCH");
    JLabel textLable = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel BoardPanel = new JPanel();
    JPanel RestartPanel = new JPanel();
    JButton restartButton = new JButton();
    int errorcount = 0;

    ArrayList<JButton> board;
    Timer HideCardTimer;
    boolean gameready = false;
    JButton card1Selection;
    JButton card2Selection;




    public void MatchCard() {
        cardSetup();
        ShuffleCard();



        Frame.setLayout(new BorderLayout());
        Frame.setSize(boardWidth,boardHeight);
        Frame.setLocationRelativeTo(null);
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLable.setFont(new Font("Arial",Font.PLAIN, 20));
        textLable.setHorizontalAlignment(JLabel.CENTER);
        textLable.setText("Error: "+ Integer.toString(errorcount));

        textPanel.setPreferredSize(new Dimension(boardWidth,30));
        textPanel.add(textLable);
        Frame.add(textPanel, BorderLayout.NORTH);

        board = new ArrayList<JButton>();
        BoardPanel.setLayout(new GridLayout(rows, col));
        for (int i =0; i <cardset.size();i++){
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth,cardHeight));
            tile.setOpaque(true);
            tile.setIcon(cardset.get(i).cardIcon);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gameready){
                        return;
                    }
                    JButton tile = (JButton) e.getSource();
                    if (tile.getIcon() == cardBackimgicon){
                        if(card1Selection == null){
                            card1Selection = tile;
                            int index = board.indexOf(card1Selection);
                            card1Selection.setIcon(cardset.get(index).cardIcon);
                        } else if (card2Selection == null) {
                            card2Selection = tile;
                            int index = board.indexOf(card2Selection);
                            card2Selection.setIcon(cardset.get(index).cardIcon);

                            if (card1Selection.getIcon() != card2Selection.getIcon()){
                                errorcount += 1;
                                textLable.setText("Errors: "+ Integer.toString(errorcount));
                                HideCardTimer.start();
                            }
                            else {
                                card1Selection = null;
                                card2Selection = null;
                            }
                        }
                    }
                }
            });
            board.add(tile);
            BoardPanel.add(tile);

        }
        Frame.add(BoardPanel);

        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        restartButton.setText("Restart");
        restartButton.setPreferredSize(new Dimension(boardWidth,30));
        restartButton.setFocusable(false);
        restartButton.setEnabled(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameready){
                    return;
                }
                gameready = false;
                restartButton.setEnabled(false);
                card1Selection = null;
                card2Selection = null;
                ShuffleCard();

                for (int i = 0; i <board.size();i++){
                    board.get(i).setIcon(cardset.get(i).cardIcon);
                }

                errorcount = 0;
                textLable.setText("Errors: "+ Integer.toString(errorcount));
                HideCardTimer.start();
            }
        });
        RestartPanel.add(restartButton);
        Frame.add(RestartPanel, BorderLayout.SOUTH);


    Frame.pack();
    Frame.setVisible(true);

    HideCardTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            HideCards();
        }
    });
    HideCardTimer.setRepeats(false);
    HideCardTimer.start();



    }
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // Set rendering hints for better image quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    void cardSetup() {
        cardset = new ArrayList<Card>();
        try {
            for (String cardName : cardList) {
                // Load the original image from resources
                BufferedImage originalImage = ImageIO.read(getClass().getResource("/" + cardName + ".png"));

                // Resize using the custom resize method
                BufferedImage resizedImage = resizeImage(originalImage, cardWidth, cardHeight);
                ImageIcon cardimgicon = new ImageIcon(resizedImage);

                Card card = new Card(cardName, cardimgicon);
                cardset.add(card);
            }
            cardset.addAll(cardset);

            // Similarly scale the back image
            BufferedImage backImage = ImageIO.read(getClass().getResource("/back.png"));
            BufferedImage resizedBackImage = resizeImage(backImage, cardWidth, cardHeight);
            cardBackimgicon = new ImageIcon(resizedBackImage);
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception (logging, showing error message, etc.)
        }
    }
    void ShuffleCard() {
        for (int i = 0; i < cardset.size(); i++) {
            int j = (int) (Math.random() * cardset.size());


            Card temp = cardset.get(i);
            cardset.set(i, cardset.get(j));
            cardset.set(j, temp);
        }

    }
    void HideCards(){
        if(gameready && card1Selection != null && card2Selection !=null){
            card1Selection.setIcon(cardBackimgicon);
            card1Selection = null;
            card2Selection.setIcon(cardBackimgicon);
            card2Selection = null;
        }else {
            for (int i = 0; i< board.size();i++){
                board.get(i).setIcon(cardBackimgicon);
            }
        }

        gameready = true;
        restartButton.setEnabled(true);
    }
}
