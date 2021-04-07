import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
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

    public void initialize(){
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
        initialize();

        Box b1 = new Box(50, 50, 20);
        Box b2 = new Box(80, 30, 20);
        b1.setMaterial(new PhongMaterial(Color.INDIANRED));
        b2.setMaterial(new PhongMaterial(Color.DARKGREEN));
        FlowPane shapes = new FlowPane();
        shapes.getChildren().add(b1);
        shapes.getChildren().add(b2);
        Scene scene = new Scene(shapes);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World!");
        primaryStage.show();
    }
}