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
        System.out.println("Card Match");
    }
}
