package com.company;

import java.util.ArrayList;
import java.util.List;

public class Hand implements GameConstants {

    //Player Card
    private List<UNOCard> playerCards;
    private int point = 0;
    private String playerName;


    // Initialize Empty Array List for store player card
    public Hand(String playerName){
        playerCards = new ArrayList<>();
        this.playerName = playerName; // Set Player Name
    }


    //Is the player able to play from his/her cards
    public boolean isAbleToPlay(UNOCard discordPile){
        try{
            for (UNOCard card : playerCards){
                if(discordPile.isMatch(card)) {
                    return true;
                }
            }
            //If Number or Color not Matched the Search for WILD/WILD_DRAW_4 card
            for (UNOCard card : playerCards){
                if (card.getCardType().equals(WILD) || card.getCardType().equals(WILD_DRAW_4)){
                    return true;
                }
            }
        }catch (Exception e){}

        return false;
    }


    //Is the player able to play from his/her cards (Match by Color) :: Method Overloading
    public boolean isAbleToPlay(String COLOR){
        try{
            for (UNOCard card : playerCards){
                if(card.getColor().equals(COLOR)) {
                    return true;
                }
            }
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
        return false;
    }

    // If can play :: Find the card can be play
    public UNOCard play(UNOCard discordPile){
        //Is Matched Card with Number or Color
        for (UNOCard card : playerCards){
            if(discordPile.isMatch(card)) {
                return card;
            }
        }
        //If Number or Color not Matched the Search for WILD/WILD_DRAW_4 card
        for (UNOCard card : playerCards){
            if (card.getCardType().equals(WILD) || card.getCardType().equals(WILD_DRAW_4)){
                return card;
            }
        }
        //If Not found any Match
        return null;
    }

    // If can play :: Find the card can be play
    public UNOCard play(String COLOR){
        //Is Matched Card with Number or Color
        for (UNOCard card : playerCards){
            if(card.getColor().equals(COLOR)) {
                return card;
            }
        }
        //If Number or Color not Matched the Search for WILD/WILD_DRAW_4 card
        for (UNOCard card : playerCards){
            if (card.getCardType().equals(WILD) || card.getCardType().equals(WILD_DRAW_4)){
                return card;
            }
        }
        //If Not found any Match
        return null;
    }


    //Remove Card :: If found
    public boolean removeCard(UNOCard card){
        return playerCards.remove(card);
    }


    //Add Card in Player Hand
    public void addCard(UNOCard card){
        playerCards.add(card);
    }


    //Get Score/Point
    public int getPoint() {
        return point;
    }


    //Set Score/Point
    public void setPoint(int point) {
        this.point = point;
    }


    //Get Player Name
    public String getPlayerName(){
        return playerName;
    }

    //is Empty
    public boolean isEmpty(){ return playerCards.isEmpty();}

    //Remains Card in Hand
    public int remainsCard(){return playerCards.size();}

    //Calculate Total Point
    public int getTotalPoint(){
        int point = 0;

        for (UNOCard card:playerCards){
            if(card != null){
                switch (card.getCardType()){
                    case NUMBER:
                        point +=Integer.parseInt(card.getValue());
                    case DRAW_2:
                        point += 20;
                    case REVERSE:
                        point += 20;
                    case SKIP:
                        point += 20;
                    case WILD:
                        point += 50;
                    case WILD_DRAW_4:
                        point += 50;
                }
            }
        }
        return point;
    }
}
