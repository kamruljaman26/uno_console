package com.company;

import java.util.Objects;

public class UNOCard implements GameConstants {

    //Card Properties : Variables
    private String color = "";
    private String cardType = "";
    private String value = "";

    //Constructor: Set Card Values at the begnaning
    public UNOCard(String color, String cardType, String value) {
        this.color = color;
        this.cardType = cardType;
        this.value = value;
    }

    //Get Card Color
    public String getColor() {
        return color;
    }

    //Get Card Card Type
    public String getCardType() {
        return cardType;
    }

    //Get Card Value
    public String getValue() {
        return value;
    }

    /**
     * Match UNOCard to Another UNOCard
     */
    public boolean isMatch(UNOCard unoCard){
        try {
            //Check Condition for Action Card
            if(cardType.equals(SKIP) || cardType.equals(REVERSE) || cardType.equals(DRAW_2)){
                return Objects.equals(color,unoCard.color) || Objects.equals(cardType,unoCard.cardType);
            }
            //else If : Check Condition for Number Type Card
            else if(cardType.equals(NUMBER)){
                return Objects.equals(color,unoCard.color) || Objects.equals(value,unoCard.value);
            }
            /*
             * This "else" statement for Wild Card: We don't need to match WILD_DRAW_4 Cards, This
             * card can be if don't Match any Card. So no Match needed! : By default, we will return false
             */
            else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override // Convert Card object in a Single String.
    public String toString() {
        return "UnoCard{" +
                "color='" + color + '\'' +
                ", cardType='" + cardType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
