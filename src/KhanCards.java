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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    private final TextArea textLog = new TextArea("Welcome to the game!\n");
    private final VBox textPane = new VBox(textLog);

    private final HBox buttons = new HBox();
    private final Button startGameButton = new Button("Start the game!");

    private ImageView activeCardView;
    private ImageView discardView;
    private ImageView deckView;
    private ImageView playerScoringView1;
    private ImageView playerScoringView2;
    private ImageView playerScoringView3;
    private ImageView playerScoringView4;
    private ImageView playerScoringView5;
    private ImageView playerScoringView6;
    private ImageView playerScoringView7;
    private ImageView playerScoringView8;
    private ImageView playerScoringView9;
    private ImageView playerScoringView10;
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

        playerScoringView1 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView1.setPreserveRatio(true);
        playerScoringView1.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView2 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView2.setPreserveRatio(true);
        playerScoringView2.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView3 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView3.setPreserveRatio(true);
        playerScoringView3.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView4 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView4.setPreserveRatio(true);
        playerScoringView4.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView5 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView5.setPreserveRatio(true);
        playerScoringView5.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView6 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView6.setPreserveRatio(true);
        playerScoringView6.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView7 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView7.setPreserveRatio(true);
        playerScoringView7.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView8 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView8.setPreserveRatio(true);
        playerScoringView8.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView9 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView9.setPreserveRatio(true);
        playerScoringView9.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringView10 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        playerScoringView10.setPreserveRatio(true);
        playerScoringView10.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView1 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView1.setPreserveRatio(true);
        enemyScoringView1.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView2 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView2.setPreserveRatio(true);
        enemyScoringView2.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView3 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView3.setPreserveRatio(true);
        enemyScoringView3.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView4 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView4.setPreserveRatio(true);
        enemyScoringView4.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView5 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView5.setPreserveRatio(true);
        enemyScoringView5.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView6 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView6.setPreserveRatio(true);
        enemyScoringView6.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView7 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView7.setPreserveRatio(true);
        enemyScoringView7.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView8 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView8.setPreserveRatio(true);
        enemyScoringView8.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView9 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView9.setPreserveRatio(true);
        enemyScoringView9.setFitWidth(BOARD_CARD_WIDTH);

        enemyScoringView10 = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        enemyScoringView10.setPreserveRatio(true);
        enemyScoringView10.setFitWidth(BOARD_CARD_WIDTH);

        playerScoringAreaPane.getChildren().addAll(playerScoringView1, playerScoringView2, playerScoringView3,
                playerScoringView4, playerScoringView5, playerScoringView6, playerScoringView7, playerScoringView8,
                playerScoringView9, playerScoringView10);

        playerScoringAreaPane.setVgap(13);
        playerScoringAreaPane.setHgap(40.25);
        playerScoringAreaPane.setAlignment(Pos.TOP_CENTER);

        enemyScoringAreaPane.getChildren().addAll(enemyScoringView1, enemyScoringView2, enemyScoringView3,
                enemyScoringView4, enemyScoringView5, enemyScoringView6, enemyScoringView7, enemyScoringView8,
                enemyScoringView9, enemyScoringView10);

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

        deckView = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
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

    private void printNewLine(String arg){
        textLog.appendText(arg + "\n\n");
    }

    private void initializeCards(){
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
        try {
            updateImages();
        }
        catch (FileNotFoundException e){
            printNewLine("Somehow, the visible nature of some cards have been wiped from existence.  The Khan is furious.");
        }
        printNewLine("Game Started.");
    }

    private void pressDrawCard(){
        try {
            if (active.size() > 0) discard.add(active.draw());
            active.add(deck.draw());
            printNewLine("Drew the " + active.topCard().toString());
            updateImages();
        }
        catch (ArrayIndexOutOfBoundsException e){
            printNewLine("The Khan furrows their brow in confusion as you try to draw from an empty deck.");
        }
        catch (NullPointerException e){
            printNewLine("The Khan smacks you for tampering with the cards before the game has started.");
        }
        catch (FileNotFoundException e){
            printNewLine("Somehow, the visible nature of this card has been wiped from existence.  The Khan is furious.");
        }
    }

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

    private void pressDiscard(){
        printNewLine("That's the discard pile.");
    }

    private void pressPlayerScoringArea(int index){
        try{
            playerScoringArea[index].topCard().reveal();
            updateImages();
        }
        catch (Exception e){
            printNewLine("something wrong fix later");
        }
    }

    private void pressEnemyScoringArea(){
        printNewLine("As you reach for the Khan's scoring area, they smack your hand.");
    }

    private void updateImages() throws FileNotFoundException{
        if(active != null)
            activeCardView.setImage(new Image(new FileInputStream(active.getImageDir())));

        if(deck != null)
            deckView.setImage(new Image(new FileInputStream(deck.getImageDir())));

        if(discard != null)
            discardView.setImage(new Image(new FileInputStream(discard.getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[0] != null)
                playerScoringView1.setImage(new Image(new FileInputStream(playerScoringArea[0].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[1] != null)
                playerScoringView2.setImage(new Image(new FileInputStream(playerScoringArea[1].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[2] != null)
                playerScoringView3.setImage(new Image(new FileInputStream(playerScoringArea[2].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[3] != null)
                playerScoringView4.setImage(new Image(new FileInputStream(playerScoringArea[3].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[4] != null)
                playerScoringView5.setImage(new Image(new FileInputStream(playerScoringArea[4].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[5] != null)
                playerScoringView6.setImage(new Image(new FileInputStream(playerScoringArea[5].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[6] != null)
                playerScoringView7.setImage(new Image(new FileInputStream(playerScoringArea[6].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[7] != null)
                playerScoringView8.setImage(new Image(new FileInputStream(playerScoringArea[7].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[8] != null)
                playerScoringView9.setImage(new Image(new FileInputStream(playerScoringArea[8].getImageDir())));

        if(playerScoringArea != null)
            if(playerScoringArea[9] != null)
                playerScoringView10.setImage(new Image(new FileInputStream(playerScoringArea[9].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[0] != null)
                enemyScoringView1.setImage(new Image(new FileInputStream(enemyScoringArea[0].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[1] != null)
                enemyScoringView2.setImage(new Image(new FileInputStream(enemyScoringArea[1].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[2] != null)
                enemyScoringView3.setImage(new Image(new FileInputStream(enemyScoringArea[2].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[3] != null)
                enemyScoringView4.setImage(new Image(new FileInputStream(enemyScoringArea[3].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[4] != null)
                enemyScoringView5.setImage(new Image(new FileInputStream(enemyScoringArea[4].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[5] != null)
                enemyScoringView6.setImage(new Image(new FileInputStream(enemyScoringArea[5].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[6] != null)
                enemyScoringView7.setImage(new Image(new FileInputStream(enemyScoringArea[6].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[7] != null)
                enemyScoringView8.setImage(new Image(new FileInputStream(enemyScoringArea[7].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[8] != null)
                enemyScoringView9.setImage(new Image(new FileInputStream(enemyScoringArea[8].getImageDir())));

        if(enemyScoringArea != null)
            if(enemyScoringArea[9] != null)
                enemyScoringView10.setImage(new Image(new FileInputStream(enemyScoringArea[9].getImageDir())));
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

        playerScoringView1.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(0));
        playerScoringView2.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(1));
        playerScoringView3.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(2));
        playerScoringView4.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(3));
        playerScoringView5.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(4));
        playerScoringView6.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(5));
        playerScoringView7.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(6));
        playerScoringView8.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(7));
        playerScoringView9.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(8));
        playerScoringView10.setOnMouseClicked(mouseEvent -> pressPlayerScoringArea(9));

        enemyScoringView1.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView2.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView3.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView4.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView5.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView6.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView7.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView8.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView9.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());
        enemyScoringView10.setOnMouseClicked(mouseEvent -> pressEnemyScoringArea());

        primaryStage.setScene(new Scene(masterPane));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}