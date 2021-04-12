import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A simulation of the two player card game Trash versus an automatic opponent.
 */
public class KhanCards extends Application {
    private Deck deck;
    private Pile discard;
    private Pile active;
    private Pile[] playerScoringArea;
    private Pile[] opponentScoringArea;

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
        startGame.setOnAction( actionEvent -> {
            initializeCards();
            System.out.println("Game Started.");
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(startGame);

        bp.setRight(buttons);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}