import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/** Game logic handler
 *
 */
public class TrashBoard {
    private TrashScene partnerScene;
    private final PlayerRecord record;

    private Deck deck = new Deck(true);
    private Pile discard;
    private Pile active;
    private Pile[] playerScoringArea;
    private Pile[] enemyScoringArea;
    private boolean playersTurn = true;
    private int playerLives = 3;


    public TrashBoard(PlayerRecord record){
        this.record = record;
    }


    /** To be called immediately after construction.  Sets the partner for proper functioning of board and scene
     *
     * @param partnerScene
     */
    public void setPartner(TrashScene partnerScene){
        this.partnerScene = partnerScene;
    }


    /** logic and object setup for the game pieces
     *
     */
    public void initializeCards(){
        playersTurn = true;
        playerLives = 3;

        deck  = new Deck(true);
        discard = new Pile();
        active = new Pile(1);

        playerScoringArea = new Pile[]{new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1), new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1)};

        enemyScoringArea = new Pile[]{new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1), new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1)};

        for (Pile area : playerScoringArea) {
            area.add(deck.draw());
            area.topCard().hide();
        }

        for (Pile area : enemyScoringArea) {
            area.add(deck.draw());
            area.topCard().hide();
        }
        partnerScene.updateImages();
        partnerScene.printNewLine("Game Started.");
    }

    /** logic for pressing the start game button
     *
     */
    public void pressStartGame(){
        if(!playersTurn){
            partnerScene.printNewLine("The Khan doesn't like quitters; especially on their turn.  They let you know with a spinning roundhouse kick.");
            takeDamage();
            return;
        }
        else initializeCards();
    }


    /** logic for clicking the deck
     *
     */
    public void pressDrawCard(){
        if(!playersTurn){
            partnerScene.printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            takeDamage();
            return;
        }
        try {
            if (active.size() > 0){
                partnerScene.printNewLine("As you try to draw a second card, the Khan snatches your hand, squeezes forcefully, and stares at you sternly");
                takeDamage();
                return;
            }
            active.add(deck.draw());
            partnerScene.printNewLine("Drew the " + active.topCard().toString());
        }
        catch (ArrayIndexOutOfBoundsException e){
            partnerScene.printNewLine("The Khan furrows their brow in confusion as you try to draw from an empty deck.");
        }
        catch (NullPointerException e){
            partnerScene.printNewLine("The Khan smacks you for tampering with the cards before the game has started.");
            takeDamage();
        }
        partnerScene.updateImages();
    }


    /** logic for clicking the active card
     *
     */
    public void pressActiveCard(){
        try {
            if(playersTurn) partnerScene.printNewLine("You are holding " + active.topCard().toString());
            else partnerScene.printNewLine("The Khan is holding " + active.topCard().toString());
        }
        catch (NullPointerException e){
            partnerScene.printNewLine("The game has not started; no-one is holding any cards.");
        }
        catch (ArrayIndexOutOfBoundsException e){
            partnerScene.printNewLine("No-one is holding any cards.");
        }
    }


    /** logic for clicking the discard.
     *
     */
    public void pressDiscard() throws IOException{
        int winCode = 0;

        if(!playersTurn){
            partnerScene.printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            takeDamage();
            return;
        }
        try {
            if(active.size() == 0){
                if(discard.size() > 0) {
                    active.add(discard.draw());
                    partnerScene.printNewLine("Drew the " + active.topCard().toString());
                }
                else
                    partnerScene.printNewLine("There are no cards to draw from the discard.");
            }
            else {
                if (deck.size() > 0) {
                    discard.add(active.draw());
                    partnerScene.printNewLine("Discarded the " + discard.topCard().toString());
                    winCode = checkWin();
                }
                else {
                    endGame(2);
                    return;
                }

                playersTurn = false;
                if (winCode == 1){
                    endGame(winCode);
                    return;
                }
                enemyTurn();
            }
        }
        catch (NullPointerException e){
            partnerScene.printNewLine("The game has not yet started.");
        }

        partnerScene.updateImages();
    }


    /** logic for clicking one of the player's scoring areas
     *
     * @param index for which area got clicked
     */
    public synchronized void pressPlayerScoringArea(int index){
        if(!playersTurn){
            partnerScene.printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            takeDamage();
            return;
        }
        try{
            if ((active.topCard().getValue() == index+1 || active.topCard().getValue() == 13    // if the player is holding a matching card or wildcard...
                    || (index == 0 && active.topCard().getValue() == 14))                       // (aces match space 1)
                    && !playerScoringArea[index].topCard().isFaceUp()){                         // ...and the card in that space has not already been revealed...
                playerScoringArea[index].topCard().reveal();                                    // ...then reveal that card and swap it with what's in hand
                active.add(playerScoringArea[index].replaceTop(active.draw()));
                partnerScene.printNewLine("Pulled " + active.topCard().toString() + " from your scoring area.");
            }
            else {
                partnerScene.printNewLine("The Khan clobbers you for making an illegal move.");
                takeDamage();
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            partnerScene.printNewLine("The Khan clobbers you for making an illegal move.");
            takeDamage();
        }
        catch (NullPointerException e){
            partnerScene.printNewLine("The game has not yet started.");
        }
        partnerScene.updateImages();
    }


    /** logic for clicking any of the enemy's scoring areas
     *
     */
    public void pressEnemyScoringArea(){
        partnerScene.printNewLine("As you reach for the Khan's scoring area, they smack your hand.");
        takeDamage();
    }

    /** logic for an enemy turn
     *
     */
    private void enemyTurn() throws IOException{
        //  delay(() -> {
        //      printNewLine("some action happens.");
        //  }, 500);
        int winCode = 0;
        playersTurn = false;
        partnerScene.printNewLine("The Khan starts to think...");

        boolean outOfOptions = (deck.size()==0);

        while (!playersTurn) {
            if(active.size() <= 0 && deck.size() > 0){
                active.add(deck.draw());
                partnerScene.updateImages();
            }
            else if (!outOfOptions){
                boolean optionsUpdate = true;
                for(int i = 0; i < enemyScoringArea.length; i++){
                    if(!enemyScoringArea[i].topCard().isFaceUp() && (active.topCard().getValue() == i+1
                            || active.topCard().getValue() == 13 || (i == 0 && active.topCard().getValue() == 14))){
                        enemyScoringArea[i].topCard().reveal();
                        active.add(enemyScoringArea[i].replaceTop(active.draw()));
                        partnerScene.updateImages();
                        optionsUpdate = false;
                        break;
                    }
                }
                outOfOptions = optionsUpdate;
            }
            else {
                winCode = checkWin();
                discard.add(active.draw());
                partnerScene.printNewLine("Discarded the " + discard.topCard().toString());
                partnerScene.updateImages();
                playersTurn = true;
            }
        }

        partnerScene.printNewLine("The Khan ends their turn.");
        partnerScene.updateImages();
        endGame(winCode);
        playersTurn = true;
    }

    /** Helper function to enable waiting time for enemy actions
     * currently unused
     *
     * @param future task to be run
     * @param time millisecond delay
     */
    private void delay( Runnable future, long time ){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                future.run();
            }
        }, time);
    }


    private void takeDamage(){
        playerLives--;
        partnerScene.printNewLine(playerLives + " lives remaining.");
        if(playerLives == 0){
            partnerScene.printNewLine("Oh dear!  You are dead.");
            try {
                endGame(2);
            }
            catch (IOException e){
                partnerScene.printNewLine("Game data failed to save.");
            }
        }
    }


    /** Logic to check for a win
     *
     */
    private int checkWin(){
        boolean playerWin = true;
        for (Pile scoringArea : playerScoringArea) {
            if(!scoringArea.topCard().isFaceUp()) {
                playerWin = false;
                break;
            }
        }
        if(playerWin) return 1;

        boolean enemyWin = true;
        for (Pile scoringArea : enemyScoringArea) {
            if(!scoringArea.topCard().isFaceUp()) {
                enemyWin = false;
                break;
            }
        }
        if(enemyWin) return 2;

        return 0;
    }


    private void endGame(int endCode) throws IOException {
        switch(endCode){
            case 1:
                playersTurn = false;
                record.addWin();
                partnerScene.printNewLine("Player wins.");

                if(record.isUnnamed()) partnerScene.endScreen(0);
                else partnerScene.endScreen(1);

                break;
            case 2:
                playersTurn = false;
                partnerScene.printNewLine("The Khan wins.");

                if(playerLives == 3){
                    record.addLoss(false);
                    partnerScene.endScreen(2);
                }
                else if(playerLives == 0){
                    record.addLoss(true);
                    partnerScene.endScreen(4);
                }
                else{
                    record.addLoss(true);
                    partnerScene.endScreen(3);
                }

                break;
            default:
                break;
        }

        record.save();
    }

    public Pile getActive() {
        return active;
    }


    public Deck getDeck() {
        return deck;
    }

    public Pile getDiscard() {
        return discard;
    }

    public Pile[] getPlayerScoringArea() {
        return playerScoringArea;
    }

    public Pile[] getEnemyScoringArea() {
        return enemyScoringArea;
    }
}
