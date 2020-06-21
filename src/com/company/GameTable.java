package com.company;

public class GameTable extends Thread implements GameConstants {

    //Table Properties
    private Hand[] players;
    private Deck DrawPile;
    private UNOCard DiscardPile;
    private int[] playerScore = new int[]{0,0,0,0};;


    //Constructor
    public GameTable(int totalPlayer) {
        //Crate Player
        totalPlayer = 4;
        players = new Hand[totalPlayer];
        for (int i = 0; i < totalPlayer; i++) {
            players[i] = new Hand("Player " + (i+1));
        }
    }

    //Public Start Game Method
    public void StartGame() {

        //Start Play
        startPlay();
    }

    /**
     * After Deal Card to Player Lets start play by Gaming Roles
     */
    private void startPlay() {
        boolean scoreCheck = false;
        while (!scoreCheck) { // Score Checker Loop
            // Deal Cards to player :: Start Game
            System.out.println("Game Start ... ");
            System.out.println("Dealing 7 Cards to each player ... \n");
            dealCard();

            // Set DiscardPile
            System.out.println("Set DiscardPile from DrawPile ... ");
            setDiscardPile(DrawPile.getTopCardFromDrawPile());
            System.out.println("DiscardPile: "+DiscardPile+"\n");

            //Some Variable to To take action
            String COLOR = "";
            boolean firstTurn = true;
            boolean runningGame = true;
            boolean clockWise = true;
            boolean skipCardTurn = false;
            boolean draw2Turn = false;
            boolean wildDraw4 = false;

            while (runningGame) {// Continue Playing Until anyone Card Finished

                int index = 0; // Mange Dynamic index with while loop
                while (index < 4) {

                    //Manage Rules :: If first Card is Action Card
                    if (firstTurn) {
                        firstTurn = false;
                        switch (DiscardPile.getCardType()){
                            case REVERSE:
                                //Lets Reverse The GamePlay ;)
                                if (clockWise) {
                                    players = sortDc(players);
                                    clockWise = false;
                                } else {
                                    players = sortAc(players);
                                    clockWise = true;
                                }
                                //Reverse Algorithm
                                switch (index) {
                                    case 0:
                                        break;
                                    case 1:
                                        ++index;
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        index = 1;
                                        break;
                                }
                                break;
                            case SKIP:
                                skipCardTurn = true; //Set Skip to True to Skip Next Turn
                                break;
                            case DRAW_2:
                                draw2Turn = true;
                                break;
                            case WILD:
                                COLOR = getRandomColor();
                                String value = players[index].getPlayerName() + " ::: Set Bord Color == " + COLOR;
                                printColorString(value, COLOR);
                                break;
                            case WILD_DRAW_4:
                                setDiscardPile(DrawPile.getTopCardFromDrawPile());
                                System.out.println("DiscardPile: ::: New "+DiscardPile+"\n");
                                break;
                        }
                    } //End first Trun

                    // Manage Action Cards :: :: :: ::
                    if (skipCardTurn) {
                        if (index < 3) {
                            System.out.println(players[index].getPlayerName() + " :: Skip Turn");
                            ++index;
                            skipCardTurn = false;
                        }
                    } else if (draw2Turn) {
                        for (int i = 0; i < 2; i++) {
                            players[index].addCard(DrawPile.getTopCardFromDrawPile());
                        }
                        draw2Turn = false;
                        System.out.println(players[index].getPlayerName() + " ::: Added 2 card from DrawPile & Skip Turn !");
                        if (index < 3) {
                            ++index;
                        } else {
                            break;
                        }
                    } else if (wildDraw4) {
                        for (int i = 0; i < 4; i++) {
                            players[index].addCard(DrawPile.getTopCardFromDrawPile());
                        }
                        wildDraw4 = false;
                        System.out.println(players[index].getPlayerName() + " ::: Added 4 card from DrawPile & Skip Turn !");
                        if (index < 3) {
                            ++index;
                        } else {
                            break;
                        }
                    }//End Manage Action Card  :: :: :: ::

                    //if Player Cards not Empty lets play with Game rules
                    if (!players[index].isEmpty()) {

                        //Variable for handle first Trun

                        //Use Thread Sleep To Slow Down the Process
                        try {
                            //1000 = 1 Second :: By increasing or decreasing we can slowdown Code Execution Time
                            sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Check Running Player Able to play from His Card ! :: :: Matched with Card or Color
                        boolean ableToPlay = players[index].isAbleToPlay(DiscardPile);
                        boolean ableToPlay2 = false;
                        if (!COLOR.equals("")) {
                            ableToPlay2 = players[index].isAbleToPlay(COLOR);
                        }

                        //Take Action based on if card found for play
                        if (ableToPlay || ableToPlay2) {
                            UNOCard card;

                            //Select Card need to play
                            if (ableToPlay) {
                                card = players[index].play(DiscardPile);
                            } else {
                                card = players[index].play(COLOR);
                                COLOR = "";
                            }

                            //Print Played Card with Player Name
                            if (card.getColor().equals(GREEN) || card.getColor().equals(BLUE) ||
                                    card.getColor().equals(RED) || card.getColor().equals(YELLOW)) {
                                String value = players[index].getPlayerName() + " ::: Played " + card;
                                printColorString(value, card.getColor());
                            } else {
                                System.out.println(players[index].getPlayerName() + " ::: Played " + card);
                            }


                            //Break the loop if not found any card to play
                            if (card == null) {
                                System.out.println("Card is null: No Card Have for Play");
                                break;
                            }

                            // Take Action based on Card Type
                            switch (card.getCardType()) {
                                case WILD: {
                                    players[index].removeCard(card);
                                    setDiscardPile(card);
                                    COLOR = getRandomColor();
                                    String value = players[index].getPlayerName() + " ::: Set Bord Color == " + COLOR;
                                    printColorString(value, COLOR);
                                    break;
                                }
                                case WILD_DRAW_4: {
                                    players[index].removeCard(card);
                                    setDiscardPile(card);
                                    COLOR = getRandomColor();
                                    String value = players[index].getPlayerName() + " ::: Set Bord Color == " + COLOR;
                                    printColorString(value, COLOR);
                                    wildDraw4 = true;
                                    break;
                                }
                                case REVERSE: {
                                    players[index].removeCard(card);
                                    setDiscardPile(card);
                                    //Lets Reverse The GamePlay ;)
                                    if (clockWise) {
                                        players = sortDc(players);
                                        clockWise = false;
                                    } else {
                                        players = sortAc(players);
                                        clockWise = true;
                                    }
                                    //Reverse Algorithm
                                    switch (index) {
                                        case 0:
                                            --index;
                                            break;
                                        case 1:
                                            ++index;
                                            break;
                                        case 2:
                                            --index;
                                            break;
                                        case 3:
                                            index = 1;
                                            break;
                                    }
                                    break;
                                }
                                case SKIP: { // Skip Card
                                    players[index].removeCard(card);
                                    setDiscardPile(card); // Add to DiscardPile
                                    skipCardTurn = true; //Set Skip to True to Skip Next Turn
                                    break;
                                }
                                case DRAW_2: {
                                    players[index].removeCard(card);
                                    setDiscardPile(card);
                                    draw2Turn = true;
                                    break;
                                }
                                case NUMBER: {
                                    players[index].removeCard(card);
                                    setDiscardPile(card);
                                    break;
                                }
                            }

                            //Finish Game or Say UNO
                            if (index < 4 && index > -1) {
                                if (players[index].isEmpty()) {
                                    break;
                                } else if (players[index].remainsCard() == 1) {
                                    sayUNO(players[index].getPlayerName());
                                }
                            }

                        } else { // Draw Card
                            if (!DrawPile.isEmpty()) {
                                //set draw roles
                                UNOCard drawCard = DrawPile.getTopCardFromDrawPile();
                                //Print Draw Card
                                String value = players[index].getPlayerName() + " ::: Draw a Card :::  " + drawCard;
                                printColorString(value, drawCard.getColor());

                                // Check Card Match DiscardPile with COLOR & CARD
                                if (drawCard.isMatch(DiscardPile) || drawCard.getColor().equals(COLOR)) {

                                    //Print Played Card
                                    printColorString(players[index].getPlayerName() + " ::: Played " + drawCard, drawCard.getColor());

                                    setDiscardPile(drawCard);
                                    COLOR = "";

                                    //Take action if Draw card is "Action Card"
                                    switch (drawCard.getCardType()) {

                                        case SKIP:
                                            skipCardTurn = true; //Set Skip to True to Skip Next Turn
                                            break;
                                        case REVERSE:  //Lets Reverse The GamePlay
                                            // Set GamePlay clockWise or AntiClockWise by Sorting Player List
                                            if (clockWise) {
                                                players = sortDc(players);
                                                clockWise = false;
                                            } else {
                                                players = sortAc(players);
                                                clockWise = true;
                                            }
                                            //Reverse Algorithm :
                                            switch (index) {
                                                case 0:
                                                    --index;
                                                    break;
                                                case 1:
                                                    ++index;
                                                    break;
                                                case 2:
                                                    --index;
                                                    break;
                                                case 3:
                                                    index = 1;
                                                    break;
                                            }
                                            break;
                                        case DRAW_2:  // Take Action for WILD_2 Card
                                            draw2Turn = true;
                                            break;
                                    }
                                } else if (drawCard.getCardType().equals(WILD_DRAW_4)) {
                                    printColorString(players[index].getPlayerName() + " ::: Played " + drawCard, "");
                                    setDiscardPile(drawCard);
                                    COLOR = getRandomColor();
                                    String colortxt = players[index].getPlayerName() + " ::: Set Bord Color == " + COLOR;
                                    printColorString(colortxt, COLOR);

                                    wildDraw4 = true;
                                } else if (drawCard.getCardType().equals(WILD)) {
                                    printColorString(players[index].getPlayerName() + " ::: Played " + drawCard, "");
                                    setDiscardPile(drawCard);
                                    COLOR = getRandomColor();
                                    String colortxt = players[index].getPlayerName() + " ::: Set Bord Color == " + COLOR;
                                    printColorString(colortxt, COLOR);
                                } else {
                                    System.out.println(players[index].getPlayerName() + " ::: Added DrawCard in Player Hand's Deck");
                                    players[index].addCard(drawCard);
                                }
                            } else {
                                runningGame = false;
                                break;
                            }
                        }
                    } else { // If Any Player Cards is finished!
                        int totalPoint = 0;
                        for (Hand player : players) {
                            if (!player.getPlayerName().equals(players[index].getPlayerName())) {
                                totalPoint += player.getTotalPoint();
                            }
                        }
                        playerScore[index] =playerScore[index] + totalPoint;
                        runningGame = false;
                        System.out.println("\nBoard Winner " + players[index].getPlayerName() + ", Earn "+totalPoint+"\n");
                        break;
                    }

                    //increase Index
                    index++;
                } //End Board Cycle Check
            } // End Card Finished Checker

            //Check Score
            for (int i=0;i<3;i++){
                int score = playerScore[i];
                if(score>50000){ // Set winner score here 500-50000 :: Anything
                    System.out.println("!!! WIN !!! " + players[i].getPlayerName() +" !!! WIN !!! "+ "\n Game Over ... .. .");
                    System.out.println("Total Point Earn ::: " + score);
                    scoreCheck = true;
                    //return;
                }
            }
        } // End Score Checker
    }


    //Get Random Color for WILD Card
    private String getRandomColor() {
        int index = (int) ((Math.random() * 4));
        return UNO_COLORS[index];
    }


    //Deal 7 card to Players
    private void dealCard() {
        //Create New DrawPile for Each GamePlay
        DrawPile = new Deck();
        players = sortAc(players);
        for (Hand player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(DrawPile.getTopCardFromDrawPile());
            }
        }
    }


    //Set DiscardPile from DrawPile
    private void setDiscardPile(UNOCard card) {
        DiscardPile = card;
    }


    private void sayUNO(String name){
        System.out.println();
        System.out.println("*****************************************");
        System.out.println("********* Called UNO by "+name+" ********");
        System.out.println("*****************************************");
        System.out.println("");
    }



}
