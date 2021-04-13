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
    private final String DEFAULT_CARD_COLL_IMG = "resources/cardBack.png";

    private Deck deck = new Deck(true);
    private Pile discard;
    private Pile active;
    private Pile[] playerScoringArea;
    private Pile[] opponentScoringArea;
    private boolean playersTurn = true;

    private GridPane masterPane = new GridPane();
    private VBox rightpane = new VBox();

    private TextArea textLog = new TextArea("Welcome to the game!\n");
    private VBox textPane = new VBox(textLog);

    private HBox buttons = new HBox();
    private Button startGameButton = new Button("Start the game!");
    private Button drawCardButton = new Button("Draw a card.");

    private ImageView activeCardView;
    private VBox cardsPane = new VBox();

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

        textLog.setEditable(false);
        textLog.setWrapText(true);
        textPane.setAlignment(Pos.BOTTOM_CENTER);

        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(startGameButton);
        buttons.getChildren().add(drawCardButton);

        rightpane.getChildren().add(buttons);
        rightpane.getChildren().add(activeCardView);
        rightpane.getChildren().add(textPane);

        activeCardView = new ImageView(new Image(new FileInputStream(DEFAULT_CARD_COLL_IMG)));
        activeCardView.setFitWidth(75);
        activeCardView.setPreserveRatio(true);
        cardsPane.getChildren().add(activeCardView);

        masterPane.add(rightpane, 7, 0, 8, 9); // side panels positioning
        // masterPane.add(discardPane, 2, 3, 3, 4); // discard pile positioning
        // masterPane.add(deckPane, 4, 3, 5, 4); // draw pile positioning
        // masterPane.add(enemyScoringAreaPane, 1, 1, 6, 2);
        // masterPane.add(playerScoringAreaPane, 1, 5, 6, 6);
    }

    private void initializeCards(){
        deck  = new Deck(true);
        discard = new Pile();
        active = new Pile(1);

        playerScoringArea = new Pile[]{new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1), new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1)};

        opponentScoringArea = new Pile[]{new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1), new Pile(1), new Pile(1), new Pile(1),
                new Pile(1), new Pile(1)};

        for (Pile area : playerScoringArea) {
            area.add(deck.draw());
            area.topCard().hide();
        }

        for (Pile area : opponentScoringArea) {
            area.add(deck.draw());
            area.topCard().hide();
        }
    }

    private void pressDrawCard(){
        try {
            if (active.size() > 0) discard.add(active.draw());
            active.add(deck.draw());
            textLog.appendText("Drew the " + active.topCard().toString() + "\n");
            activeCardView.setImage(new Image(new FileInputStream(active.topCard().getImageDir())));
        }
        catch (ArrayIndexOutOfBoundsException e){
            textLog.appendText("The Khan furrows their brow in confusion as you try to draw from an empty deck.\n");
        }
        catch (NullPointerException e){
            textLog.appendText("The Khan smacks you for tampering with the cards before the game has started.\n");
        }
        catch (FileNotFoundException e){
            textLog.appendText("Somehow, the visible nature of this card has been wiped from existence.  The Khan is furious.\n");
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialSetup();

        startGameButton.setOnAction( actionEvent -> {
            initializeCards();
            textLog.appendText("Game started.\n");
        });

        drawCardButton.setOnAction(actionEvent -> pressDrawCard());



        primaryStage.setScene(new Scene(masterPane));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}