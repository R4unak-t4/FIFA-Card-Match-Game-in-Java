package org.example;
import java.awt.*;
import java.awt.event.*;
import java.security.PublicKey;
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
    ImageIcon cardBack;

    public void MatchCard() {
        cardSetup();
        ShuffelCard();
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
        cardBackimg = new ImageIcon(cardBackimg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)).getImage();
    }
    void ShuffelCard(){
        System.out.println(cardset);
         for (int i = 0; i < cardset.size(); i++){
             int j = (int) Math.random() * cardset.size();

         }
    }
}
