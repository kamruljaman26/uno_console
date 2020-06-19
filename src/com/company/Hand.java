package com.company;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    //Player Card
    private List<UNOCard> playerCards;
    private int point;

    public Hand(){
        playerCards = new ArrayList<>(); // Initialize Empty Array List for store player card
    }

    //Add Card in Player Hand
    public void addCard(UNOCard card){
        playerCards.add(card);
    }

    public boolean isAbleToPlay(UNOCard discordPile){
        for (UNOCard card : playerCards){
            if(discordPile.isMatch(card)) {
                return true;
            }
        }
        return false;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
