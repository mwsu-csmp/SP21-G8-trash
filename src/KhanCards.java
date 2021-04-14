import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Timer.*;
import java.util.concurrent.TimeUnit;

/**
 * A simulation of the two player card game Trash versus an automatic opponent.
 */
public class KhanCards extends Application {
    private final int BOARD_CARD_WIDTH = 70;

    private Deck deck = new Deck(true);
    private Pile discard;
    private Pile active;
    private Pile[] playerScoringArea;
    private Pile[] enemyScoringArea;
    private boolean playersTurn = true;

    private final GridPane masterPane = new GridPane();
    private final VBox rightPane = new VBox();
    private final FlowPane playerScoringAreaPane = new FlowPane();
    private final FlowPane enemyScoringAreaPane = new FlowPane();
    private final FlowPane deckPane = new FlowPane();
    private final FlowPane discardPane = new FlowPane();

    private final TextArea textLog = new TextArea("Welcome to the game!\n\n");
    private final VBox textPane = new VBox(textLog);

    private final HBox buttons = new HBox();
    private final Button startGameButton = new Button("Start the game!");

    private ImageView activeCardView;
    private ImageView discardView;
    private ImageView deckView;
    private final ArrayList<ImageView> playerScoringViews = new ArrayList(10);
    private final ArrayList<ImageView> enemyScoringViews = new ArrayList(10);
    private ImageView enemyScoringView1;
    private ImageView enemyScoringView2;
    private ImageView enemyScoringView3;
    private ImageView enemyScoringView4;
    private ImageView enemyScoringView5;
    private ImageView enemyScoringView6;
    private ImageView enemyScoringView7;
    private ImageView enemyScoringView8;
    private ImageView enemyScoringView9;
    private ImageView enemyScoringView10;

    /** Initial setup for the viewing space.
     *
     * @throws FileNotFoundException if an image is not in the proper directory
     */
    private void initialSetup() throws FileNotFoundException{
        masterPane.setGridLinesVisible(true); //FOR DEBUGGING, COMMENT OUT IN FINAL CODE
        masterPane.setPrefSize(1080, 768);
        masterPane.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints scoringLeft = new ColumnConstraints();
        ColumnConstraints discardLeft = new ColumnConstraints();
        ColumnConstraints discardRight = new ColumnConstraints();
        ColumnConstraints deckLeft = new ColumnConstraints();
        ColumnConstraints deckRight = new ColumnConstraints();
        ColumnConstraints scoringRight = new ColumnConstraints();
        ColumnConstraints panesLeft = new ColumnConstraints();

        scoringLeft.setPercentWidth(18.0);
        discardLeft.setPercentWidth(10.7);
        discardRight.setPercentWidth(7.5);
        deckLeft.setPercentWidth(13);
        deckRight.setPercentWidth(7.5);
        scoringRight.setPercentWidth(10.7);
        panesLeft.setPercentWidth(14.4);
        masterPane.getColumnConstraints().addAll(scoringLeft, discardLeft, discardRight, deckLeft, deckRight, scoringRight, panesLeft);

        RowConstraints enemyScoringTop = new RowConstraints();
        RowConstraints enemyScoringBottom = new RowConstraints();
        RowConstraints deckDiscardTop = new RowConstraints();
        RowConstraints deckDiscardBottom = new RowConstraints();
        RowConstraints playerScoringTop = new RowConstraints();
        RowConstraints playerScoringBottom = new RowConstraints();
        RowConstraints bottomSpace = new RowConstraints();

        enemyScoringTop.setPercentHeight(57);
        enemyScoringBottom.setPercentHeight(218);
        deckDiscardTop.setPercentHeight(56);
        deckDiscardBottom.setPercentHeight(102);
        playerScoringTop.setPercentHeight(56);
        playerScoringBottom.setPercentHeight(218);
        bottomSpace.setPercentHeight(57);
        masterPane.getRowConstraints().addAll(enemyScoringTop, enemyScoringBottom, deckDiscardTop, deckDiscardBottom, playerScoringTop, playerScoringBottom, bottomSpace);

        for (int i = 0; i<10; i++) {
            playerScoringViews.add( new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA))) );
            playerScoringViews.get(i).setPreserveRatio(true);
            playerScoringViews.get(i).setFitWidth(BOARD_CARD_WIDTH);
        }

        for (int i = 0; i<10; i++) {
            enemyScoringViews.add( new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA))) );
            enemyScoringViews.get(i).setPreserveRatio(true);
            enemyScoringViews.get(i).setFitWidth(BOARD_CARD_WIDTH);
        }

        playerScoringAreaPane.getChildren().addAll(playerScoringViews);

        playerScoringAreaPane.setVgap(13);
        playerScoringAreaPane.setHgap(40.25);
        playerScoringAreaPane.setAlignment(Pos.TOP_CENTER);

        enemyScoringAreaPane.getChildren().addAll(enemyScoringViews);

        enemyScoringAreaPane.setVgap(13);
        enemyScoringAreaPane.setHgap(40.25);
        enemyScoringAreaPane.setAlignment(Pos.TOP_CENTER);

        activeCardView = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        activeCardView.setPreserveRatio(true);
        activeCardView.setFitWidth(150);

        textLog.setEditable(false);
        textLog.setWrapText(true);
        textPane.setAlignment(Pos.BOTTOM_CENTER);

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(startGameButton);

        rightPane.getChildren().addAll(buttons, activeCardView, textPane);
        rightPane.setAlignment(Pos.CENTER);

        deckView = new ImageView(new Image(new FileInputStream(FilePaths.CARD_BACK)));
        deckView.setPreserveRatio(true);
        deckView.setFitWidth(BOARD_CARD_WIDTH);

        deckPane.getChildren().add(deckView);
        // deckPane.setAlignment(Pos.TOP_CENTER);

        discardView = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        discardView.setPreserveRatio(true);
        discardView.setFitWidth(BOARD_CARD_WIDTH);

        discardPane.getChildren().add(discardView);
        // discardPane.setAlignment(Pos.TOP_CENTER);

        masterPane.add(rightPane, 7, 0, 8, 9); // side panels positioning
        masterPane.add(discardPane, 2, 3, 3, 4); // discard pile positioning
        masterPane.add(deckPane, 4, 3, 5, 4); // draw pile positioning
        masterPane.add(enemyScoringAreaPane, 1, 1, 5, 1);
        masterPane.add(playerScoringAreaPane, 1, 5, 5, 5); // player scoring area positioning
    }

    /** helper function for printing to the textField
     *
     * @param arg text to be printed
     */
    private void printNewLine(String arg){
        textLog.appendText(arg + "\n\n");
    }

    /** logic and object setup for the game pieces
     *
     */
    private void initializeCards(){
        if(!playersTurn){
            printNewLine("The Khan doesn't like quitters; especially on their turn.  They let you know with a spinning roundhouse kick.");
            return;
        }
        playersTurn = true;

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
        updateImages();
        printNewLine("Game Started.");
    }

    /** logic for clicking the deck
     *
     */
    private void pressDrawCard(){
        if(!playersTurn){
            printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            return;
        }
        try {
            if (active.size() > 0){
                printNewLine("As you try to draw a second card, the Khan snatches your hand, squeezes forcefully, and stares at you sternly");
                return;
            }
            active.add(deck.draw());
            printNewLine("Drew the " + active.topCard().toString());
        }
        catch (ArrayIndexOutOfBoundsException e){
            printNewLine("The Khan furrows their brow in confusion as you try to draw from an empty deck.");
        }
        catch (NullPointerException e){
            printNewLine("The Khan smacks you for tampering with the cards before the game has started.");
        }
        updateImages();
    }

    /** logic for clicking the active card
     *
     */
    private void pressActiveCard(){
        try {
            if(playersTurn) printNewLine("You are holding " + active.topCard().toString());
            else printNewLine("The Khan is holding " + active.topCard().toString());
        }
        catch (NullPointerException e){
            printNewLine("The game has not started; no-one is holding any cards.");
        }
        catch (ArrayIndexOutOfBoundsException e){
            printNewLine("No-one is holding any cards.");
        }
    }

    /** logic for clicking the discard.
     *
     */
    private void pressDiscard(){
        if(!playersTurn){
            printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            return;
        }
        try {
            if(active.size() == 0){
                printNewLine("Searching the Discard pile will be added later.");
            }
            else
                //TODO check for win
                discard.add(active.draw());
                playersTurn = false;
                enemyTurn();
        }
        catch (NullPointerException e){
            printNewLine("The game has not yet started.");
        }
        updateImages();
    }

    /** logic for clicking one of the player's scoring areas
     *
     * @param index for which area got clicked
     */
    private synchronized void pressPlayerScoringArea(int index){
        if(!playersTurn){
            printNewLine("The Khan catches you trying to act out of turn and judo chops you.");
            return;
        }
        try{
            if ((active.topCard().getValue() == index+1 || active.topCard().getValue() == 13    // if the player is holding a matching card or wildcard...
                    || (index == 0 && active.topCard().getValue() == 14))                       // (aces match space 1)
                    && !playerScoringArea[index].topCard().isFaceUp()){                         // ...and the card in that space has not already been revealed...
                playerScoringArea[index].topCard().reveal();                                    // ...then reveal that card and swap it with what's in hand
                active.add(playerScoringArea[index].replaceTop(active.draw()));
                printNewLine("Pulled " + active.topCard().toString() + " from your scoring area.");
            }
            else {
                printNewLine("The Khan clobbers you for making an illegal move.");
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            printNewLine("The Khan clobbers you for making an illegal move.");
        }
        catch (NullPointerException e){
            printNewLine("The game has not yet started.");
        }
        updateImages();
    }

    /** logic for clicking any of the enemy's scoring areas
     *
     */
    private void pressEnemyScoringArea(){
        printNewLine("As you reach for the Khan's scoring area, they smack your hand.");
    }

    private void enemyTurn(){
        //  delay(() -> {
        //      printNewLine("some action happens.");
        //  }, 500);

        playersTurn = false;
        printNewLine("The Khan starts to think...");

        boolean outOfOptions = false;
        while (!playersTurn) {
            if(active.size() <= 0 && deck.size() > 0){
                active.add(deck.draw());
                updateImages();
            }
            else if (!outOfOptions){
                boolean optionsUpdate = true;
                for(int i = 0; i < enemyScoringArea.length; i++){
                    if(!enemyScoringArea[i].topCard().isFaceUp() && (active.topCard().getValue() == i+1
                            || active.topCard().getValue() == 13 || (i == 0 && active.topCard().getValue() == 14))){
                        enemyScoringArea[i].topCard().reveal();
                        active.add(enemyScoringArea[i].replaceTop(active.draw()));
                        updateImages();
                        optionsUpdate = false;
                        break;
                    }
                }
                outOfOptions = optionsUpdate;
            }
            else {
                // TODO check for win
                discard.add(active.draw());
                updateImages();
                playersTurn = true;
            }
        }

        printNewLine("The Khan ends their turn.");
        updateImages();
        playersTurn = true;
    }

    private void delay( Runnable future, long time ){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                future.run();
            }
        }, time);
    }

    /** updates all ImageViews to correctly reflect content
     *
     */
    private void updateImages(){
        try {
            if (active != null)
                activeCardView.setImage(new Image(new FileInputStream(active.getImageDir())));

            if (deck != null)
                deckView.setImage(new Image(new FileInputStream(deck.getImageDir())));

            if (discard != null)
                discardView.setImage(new Image(new FileInputStream(discard.getImageDir())));

            if (playerScoringArea != null)
                for (int i = 0; (i<playerScoringArea.length && i<playerScoringViews.size()); i++)
                    if(playerScoringArea[i] != null)
                        playerScoringViews.get(i).setImage(new Image(new FileInputStream(playerScoringArea[i].getImageDir())));

            if (enemyScoringArea != null)
                for (int i = 0; (i<enemyScoringArea.length && i<enemyScoringViews.size()); i++)
                    if(enemyScoringArea[i] != null)
                        enemyScoringViews.get(i).setImage(new Image(new FileInputStream(enemyScoringArea[i].getImageDir())));
        }
        catch (FileNotFoundException e) {
            printNewLine("Somehow, the visible nature of some cards have been wiped from existence.  The Khan is furious.");
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialSetup();

        startGameButton.setOnAction(actionEvent -> initializeCards());

        deckView.setOnMouseClicked(mouseEvent -> pressDrawCard());
        discardView.setOnMouseClicked(mouseEvent -> pressDiscard());
        activeCardView.setOnMouseClicked(mouseEvent -> pressActiveCard());

        playerScoringViews.get(0).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(0));
        playerScoringViews.get(1).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(1));
        playerScoringViews.get(2).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(2));
        playerScoringViews.get(3).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(3));
        playerScoringViews.get(4).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(4));
        playerScoringViews.get(5).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(5));
        playerScoringViews.get(6).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(6));
        playerScoringViews.get(7).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(7));
        playerScoringViews.get(8).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(8));
        playerScoringViews.get(9).setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(9));

        enemyScoringViews.get(0).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(1).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(2).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(3).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(4).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(5).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(6).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(7).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(8).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringViews.get(9).setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());

        primaryStage.setScene(new Scene(masterPane));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}