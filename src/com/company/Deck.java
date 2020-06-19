package com.company;

import com.company.values.GameConstants;

import java.util.*;

/**
 * We used the Stack Data Structure to manipulate UNO Cards Draw Pile.
 * To make the Class Clean we implements GameConstants class for some Constants Values
 */
public class Deck implements GameConstants {

    // Card Deck / UNO DrawPile
    private Stack<UNOCard> Deck;

    //Constructor
    public Deck() {
        Deck = new Stack<>(); //init Deck
        initDeckStack(); // Add data to Deck
    }

    // Draw top Card from Draw Pile
    public UNOCard getTopCardFromDrawPile(){
        return Deck.pop();
    }

    // Is The Duck is Empty ?
    public boolean isEmpty(){
        return Deck.isEmpty();
    }

    //Create UNO Cards & Add Cards to Stuck
    private void initDeckStack(){
        //Create & shuffle UNOCards
        List<UNOCard> cardDeck = new ArrayList<>();
        cardDeck = addCards(); // Add Cards
        Collections.shuffle(cardDeck); //Shuffle Card with Help of Collections Class

        //Add to UNOCard Stuck : To access data from top one by one
        for(UNOCard card: cardDeck){
            Deck.push(card);
        }
    }

    /**
     * Create & Add Cards to CardDeck
     */
    private List<UNOCard> addCards() {
        List<UNOCard> cards = new ArrayList<>(); // Create an temporary Array List

        //Crate All Colors Cards
        for (String color : UNO_COLORS) {

            //Create 76 NumberCards --
            for (int num : UNO_NUMBERS) {
                int i = 0;
                do {
                    UNOCard card = new UNOCard(color, NUMBER, Integer.toString(num)); // Create Card
                    cards.add(card);
                    i++;
                } while (num != 0 && i < 2);
            }

            //Create 24 ActionCards --> everything twice
            for (String type : ACTION_CARDS) {
                for (int i = 0; i < 2; i++) {
                    UNOCard card = new UNOCard(color, type, null); // Create Card
                    cards.add(card);
                }
            }
        }
        //Create 8 WILD cards
        for (String type : WILD_CARDS) {
            for (int i = 0; i < 4; i++) {
                UNOCard card = new UNOCard(null, type, null); // Create Card
                cards.add(card); // Add to Deck
            }
        }
        return cards;
    } // End Add Card Method
}
