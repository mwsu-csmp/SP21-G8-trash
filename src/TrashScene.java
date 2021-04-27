import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/** JavaFX handler
 *
 */
public class TrashScene {
    private final TrashBoard partnerBoard;
    private final PlayerRecord record;

    private final GridPane masterPane = new GridPane();

    private final TextArea textLog;

    private final Button startGameButton = new Button("Start the game!");

    private final ImageView activeCardView;
    private final ImageView discardView;
    private final ImageView deckView;
    private final ArrayList<ImageView> playerScoringViews = new ArrayList(10);
    private final ArrayList<ImageView> enemyScoringViews = new ArrayList(10);


    /** Initial setup for the viewing space of a game of Trash.
     *
     * @throws FileNotFoundException if an image is not in the proper directory
     */
    public TrashScene(TrashBoard partnerBoard, PlayerRecord record) throws IOException {
        final int CARD_WIDTH_IN_PX = 65;
        final double CARD_WIDTH_PERCENTAGE = 0.0846;

        this.partnerBoard = partnerBoard;
        this.record = record;

        textLog = new TextArea("Welcome to the game, " + record.getName() + ". \n\n");
        textLog.setPrefHeight(425);

        // masterPane.setGridLinesVisible(true); //FOR DEBUGGING, COMMENT OUT IN FINAL CODE
        masterPane.setPrefSize(1080, 768);
        masterPane.setPadding(new Insets(10, 10, 10, 10));
        masterPane.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY)));

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
            playerScoringViews.get(i).setFitWidth(CARD_WIDTH_IN_PX);
        }

        for (int i = 0; i<10; i++) {
            enemyScoringViews.add( new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA))) );
            enemyScoringViews.get(i).setPreserveRatio(true);
            enemyScoringViews.get(i).setFitWidth(CARD_WIDTH_IN_PX);
        }

        FlowPane playerScoringAreaPane = new FlowPane();
        playerScoringAreaPane.getChildren().addAll(playerScoringViews);

        playerScoringAreaPane.setVgap(13);
        playerScoringAreaPane.setHgap(40.25);
        playerScoringAreaPane.setAlignment(Pos.CENTER);

        FlowPane enemyScoringAreaPane = new FlowPane();
        enemyScoringAreaPane.getChildren().addAll(enemyScoringViews);

        enemyScoringAreaPane.setVgap(13);
        enemyScoringAreaPane.setHgap(40.25);
        enemyScoringAreaPane.setAlignment(Pos.CENTER);

        activeCardView = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        activeCardView.setPreserveRatio(true);
        activeCardView.setFitWidth(150);

        textLog.setEditable(false);
        textLog.setWrapText(true);
        VBox textPane = new VBox(textLog);
        textPane.setAlignment(Pos.BOTTOM_CENTER);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);

        buttons.getChildren().add(startGameButton);

        VBox rightPane = new VBox();
        rightPane.getChildren().addAll(buttons, activeCardView, textPane);
        rightPane.setAlignment(Pos.CENTER);

        deckView = new ImageView(new Image(new FileInputStream(FilePaths.CARD_BACK)));
        deckView.setPreserveRatio(true);
        deckView.setFitWidth(CARD_WIDTH_IN_PX);

        FlowPane deckPane = new FlowPane();
        deckPane.getChildren().add(deckView);
        deckPane.setAlignment(Pos.CENTER);

        discardView = new ImageView(new Image(new FileInputStream(FilePaths.EMPTY_CARD_AREA)));
        discardView.setPreserveRatio(true);
        discardView.setFitWidth(CARD_WIDTH_IN_PX);

        FlowPane discardPane = new FlowPane();
        discardPane.getChildren().add(discardView);
        discardPane.setAlignment(Pos.CENTER);

        masterPane.add(rightPane, 7, 0, 8, 9); // side panels positioning
        masterPane.add(discardPane, 2, 3); // discard pile positioning
        masterPane.add(deckPane, 4, 3); // draw pile positioning
        masterPane.add(enemyScoringAreaPane, 1, 1, 5, 1);
        masterPane.add(playerScoringAreaPane, 1, 5, 5, 1); // player scoring area positioning

        startGameButton.setOnAction(actionEvent -> {
            partnerBoard.pressStartGame();
            startGameButton.setVisible(false);
        });

        deckView.setOnMouseClicked(mouseEvent -> partnerBoard.pressDrawCard());
        discardView.setOnMouseClicked(mouseEvent -> {
            try {
                partnerBoard.pressDiscard();
            } catch (IOException e){
                printNewLine("Data could not be saved.");
            }
        });
        activeCardView.setOnMouseClicked(mouseEvent -> partnerBoard.pressActiveCard());

        playerScoringViews.get(0).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(0));
        playerScoringViews.get(1).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(1));
        playerScoringViews.get(2).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(2));
        playerScoringViews.get(3).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(3));
        playerScoringViews.get(4).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(4));
        playerScoringViews.get(5).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(5));
        playerScoringViews.get(6).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(6));
        playerScoringViews.get(7).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(7));
        playerScoringViews.get(8).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(8));
        playerScoringViews.get(9).setOnMouseClicked(mouseEvent -> partnerBoard.pressPlayerScoringArea(9));

        enemyScoringViews.get(0).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(1).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(2).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(3).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(4).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(5).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(6).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(7).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(8).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
        enemyScoringViews.get(9).setOnMouseClicked(mouseEvent -> partnerBoard.pressEnemyScoringArea());
    }


    /** pojo get for thisScene
     *
     * @return thisScene
     */
    public Scene getScene() {
        return new Scene(masterPane);
    }

    /** helper function for printing to the textField
     *
     * @param arg text to be printed
     */
    public void printNewLine(String arg){
        textLog.appendText(arg + "\n\n");
    }

    /** updates all ImageViews to correctly reflect content
     *
     */
    public void updateImages(){
        try {
            if (partnerBoard.getActive() != null)
                activeCardView.setImage(new Image(new FileInputStream(partnerBoard.getActive().getImageDir())));

            if (partnerBoard.getDeck() != null)
                deckView.setImage(new Image(new FileInputStream(partnerBoard.getDeck().getImageDir())));

            if (partnerBoard.getDiscard() != null)
                discardView.setImage(new Image(new FileInputStream(partnerBoard.getDiscard().getImageDir())));

            if (partnerBoard.getPlayerScoringArea() != null)
                for (int i = 0; (i < partnerBoard.getPlayerScoringArea().length && i < playerScoringViews.size()); i++)
                    if(partnerBoard.getPlayerScoringArea()[i] != null)
                        playerScoringViews.get(i).setImage(new Image(new FileInputStream(partnerBoard.getPlayerScoringArea()[i].getImageDir())));

            if (partnerBoard.getEnemyScoringArea() != null)
                for (int i = 0; (i < partnerBoard.getEnemyScoringArea().length && i < enemyScoringViews.size()); i++)
                    if(partnerBoard.getEnemyScoringArea()[i] != null)
                        enemyScoringViews.get(i).setImage(new Image(new FileInputStream(partnerBoard.getEnemyScoringArea()[i].getImageDir())));
        }
        catch (FileNotFoundException e) {
            printNewLine("Somehow, the visible nature of some cards have been wiped from existence.  The Khan is furious.");
        }
    }

    public void endScreen(int code){
        Dialog screen = new Dialog();
        String title = "Game over";
        String content = "The game is over";

        switch(code) {
            case 0: // win, unnamed
                Dialog getName = new TextInputDialog("What name do you give the Khan?");
                getName.setTitle("Your name?");
                getName.setHeaderText("The khan nods. \"Well done.  Challenger, I do not know your name.\"");

                Optional<String> name = getName.showAndWait();

                if(name.isPresent()){
                    record.setName(name.get());
                }

                title = "You win!";
                content = "\"Very well.  My congratulations to you, " + record.getName() + ".  " +
                        "You have earned one year for your village, and you are welcome to return to my table anytime.";
                break;
            case 1: // win, named
                title = "You win!";
                content = "The Khan shifts in their seat.  \"Once again, well done, " + record.getName() + ".  Again?\"";
                break;
            case 2: // loss, honorable
                title = "You lose.";
                content = "\"A shame,\" the Khan says as they stand up and stretch.  \"But, you played honorably.  " +
                        "Would you like to try again?\"";
                break;
            case 3: // loss, dishonorable
                title = "You died.";
                content = "The Khan finishes their winning turn and reaches for a massive battleaxe.  \"I do not abide " +
                        "cheaters at my table.  Hopefully, the next challenger your village sends has a better sense" +
                        " of honor.\"  Oh dear!  You are dead.";
                break;
            case 4: // loss, death
                title = "You died.";
                content = "The Khan wins by default, as you are no longer alive to play.";
                break;
            default:
                break;
        }

        content += "\n\n";
        if(record.getWinBalance() < 0) {
            content += "Your village owes " + (record.getWinBalance() * -1) + " years of service to the Khan.";
        }
        else if(record.getWinBalance() > 0) {
            content += "Your village will be ignored by the Khan for " + record.getWinBalance() + " years.";
        }
        else {
            content += "Your village has a neutral balance with the Khan.";
        }
        content += "\nYour win streak is " + record.getWinStreak() + ".";

        screen.setTitle(title);
        screen.setContentText(content);
        Integer selected = 0;

        ButtonType close = new ButtonType("Leave the table", ButtonBar.ButtonData.NO);
        ButtonType restart = new ButtonType("Play again", ButtonBar.ButtonData.YES);
        screen.getDialogPane().getButtonTypes().add(close);
        screen.getDialogPane().getButtonTypes().add(restart);

        screen.setResultConverter(new Callback<ButtonType, Integer>() {
            @Override
            public Integer call(ButtonType b) {
                if (b == close) return 0;
                else if (b == restart) return 1;

                return 0;
            }
        });

        Optional<Integer> result = screen.showAndWait();

        if(result.isPresent()){
            selected = result.get();
        }

        if (selected == 1){
            startGameButton.setVisible(false);
            partnerBoard.initializeCards();
        }
        else printNewLine("Press 'X' in the top right whenever you are ready to leave the table.");
    }
}
