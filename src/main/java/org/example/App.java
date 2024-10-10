package org.example;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
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
    int cardWidth = 90;
    int cardHeight = 120;

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

    void cardSetup(){
        cardset = new ArrayList<Card>();
        for (String cardName : cardList){

            Image cardimg = new ImageIcon(getClass().getResource("/"+cardName+".png")).getImage();
            ImageIcon cardimgincaon = new ImageIcon(cardimg.getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));

            Card card = new Card(cardName, cardimgincaon);
            cardset.add(card);
        }
        cardset.addAll(cardset);

        Image cardBackimg = new ImageIcon(getClass().getResource("/back.png")).getImage();
        cardBackimgicon = new ImageIcon(cardBackimg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
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
        for (int i = 0; i< board.size();i++){
            board.get(i).setIcon(cardBackimgicon);
        }
        gameready = true;
    }
}
