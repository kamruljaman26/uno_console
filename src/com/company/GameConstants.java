package com.company;

/**
 * To Store some helper Method & Constants Values for Game
 */
public interface GameConstants {

    //ANIS COLOR codes for Console
    String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";

    //Colors
    String RED = "RED";
    String BLUE = "BLUE";
    String GREEN = "GREEN";
    String YELLOW = "YELLOW";

    //Card Type
    String NUMBER = "NUMBER";
    String SKIP = "SKIP";
    String REVERSE = "REVERSE";
    String DRAW_2 = "DRAW_2";
    String WILD = "WILD";
    String WILD_DRAW_4 = "WILD_DRAW_4";

    //Card Colors
    String[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};

    //Action Card Types
    String[] ACTION_CARDS = {SKIP, REVERSE, DRAW_2};

    //Wild Card Types
    String[] WILD_CARDS = {WILD, WILD_DRAW_4};

    // Card Numbers
    int[] UNO_NUMBERS =  {0,1,2,3,4,5,6,7,8,9};

    //Sort Players by Ascending Order
    default Hand[] sortAc(Hand[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j].getPlayerName().compareTo(arr[j+1].getPlayerName())>=0)
                {
                    // swap arr[j+1] and arr[i]
                    Hand temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
        return arr;
    }

    //Sort Players by Ascending Order
    default Hand[] sortDc(Hand[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j].getPlayerName().compareTo(arr[j].getPlayerName())<=0)
                {
                    // swap arr[j+1] and arr[i]
                    Hand temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
        return arr;
    }

    //Print String with Color in console
    default void printColorString(String value,String color){
        switch (color) {
            case GREEN:
                System.out.println(ANSI_GREEN + value + ANSI_RESET);
                break;
            case BLUE:
                System.out.println(ANSI_BLUE + value + ANSI_RESET);

                break;
            case RED:
                System.out.println(ANSI_RED + value + ANSI_RESET);

                break;
            case YELLOW:
                System.out.println(ANSI_YELLOW + value + ANSI_RESET);
                break;
            default:
                System.out.println(value);
                break;
        }
    }


}
