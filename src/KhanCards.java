import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A simulation of the two player card game Trash versus an automatic opponent.
 */
public class KhanCards extends Application {
    private Deck deck;
    private Pile discard;
    private Pile active;
    private Pile[] playerScoringArea;
    private Pile[] opponentScoringArea;
    private boolean playersTurn = true;

    public void initializeCards(){
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

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane bp = new BorderPane();
        bp.setPrefSize(1080, 768);
        bp.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(bp);

        Button startGame = new Button("Start the game!");
        Button drawCard = new Button("Draw a card.");

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(startGame);
        buttons.getChildren().add(drawCard);

        TextArea textLog = new TextArea("Welcome to the game!\n");
        textLog.setEditable(false);
        textLog.setWrapText(true);
        VBox text = new VBox(textLog);
        text.setAlignment(Pos.BOTTOM_CENTER);

        ImageView activeCard = new ImageView(new Image(new FileInputStream("resources/cardBack.png")));
        VBox cards = new VBox(activeCard);

        startGame.setOnAction( actionEvent -> {
            initializeCards();
            textLog.appendText("Game started.\n");
        });

        drawCard.setOnAction(actionEvent -> {
            try {
                if (active.size() > 0) discard.add(active.draw());
                active.add(deck.draw());
                textLog.appendText("Drew the " + active.topCard().toString() + "\n");
                activeCard.setImage(new Image(new FileInputStream(active.topCard().getImageDir())));
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
        });

        bp.setTop(buttons);
        bp.setRight(text);
        bp.setCenter(cards);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}