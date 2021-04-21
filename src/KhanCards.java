import javafx.application.Application;
import javafx.stage.Stage;


/**
 * A simulation of the two player card game Trash versus an automatic opponent.
 */
public class KhanCards extends Application {



    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        PlayerRecord currentRecord = new PlayerRecord();
        TrashBoard currentBoard = new TrashBoard(currentRecord);
        TrashScene currentScene = new TrashScene(currentBoard, currentRecord);
        currentBoard.setPartner(currentScene);

        primaryStage.setScene(currentScene.getScene());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khan Cards");
        primaryStage.show();
    }
}