package Model;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

/*controls the UI of the application
 this application uses JavaFX to provide the graphic
 that uses specific type of functions and parameters
 */
public class Main extends Application {
    Tile[][] tiles;
    Text turnText;
    Text warningText;
    Text wallsNum;
    PlayWithFriend playWithFriend;
    PlayWithAI playWithAI;
    PlayAIvsAI playAIvsAI;
    int x1, y1, x2, y2;
    int count = 0;
    Button friendButton, AIButton, AIvsAIButton;
    Button goAI;
    Boolean playingWithAI = false, playingAIvsAI = false;
    boolean a = false;


    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);
        root.setBackground(new Background(new BackgroundFill(Color.web("#EEDEC0"), CornerRadii.EMPTY, Insets.EMPTY)));

        /*
        buttons that user decides the mode of the game by
         */

        friendButton = new Button("PLAY WITH A FRIEND"); //to play multiplayer game
        friendButton.setPrefHeight(35);
        friendButton.setPrefWidth(140);
        friendButton.setTranslateX(360);
        friendButton.setTranslateY(230);
        root.getChildren().add(friendButton);

        AIButton = new Button("PLAY WITH AI"); // to play with AI
        AIButton.setPrefHeight(35);
        AIButton.setPrefWidth(140);
        AIButton.setTranslateX(360);
        AIButton.setTranslateY(270);
        root.getChildren().add(AIButton);

        AIvsAIButton = new Button("AI Vs. AI"); // to watch AI plays with AI
        AIvsAIButton.setPrefHeight(35);
        AIvsAIButton.setPrefWidth(140);
        AIvsAIButton.setTranslateX(360);
        AIvsAIButton.setTranslateY(310);
        root.getChildren().add(AIvsAIButton);

        goAI = new Button("GO AI");
        goAI.setPrefHeight(35);
        goAI.setPrefWidth(100);
        goAI.setTranslateX(610);
        goAI.setTranslateY(470);
        root.getChildren().add(goAI);
        goAI.setVisible(false);


        /*
        a set of texts that are displayed during the game
         */
        warningText = new Text("");
        turnText = new Text("your`s turn");
        wallsNum = new Text("number of walls");


        friendButton.setOnMouseClicked(mouseEvent -> {
            playWithFriend = new PlayWithFriend();
            init(root);
            startGame();

        });

        AIButton.setOnMouseClicked(mouseEvent -> {
            playWithAI = new PlayWithAI();
            playingWithAI = true;
            goAI.setVisible(true);
            init(root);
            startGame();
        });

        AIvsAIButton.setOnMouseClicked(mouseEvent -> {
            playAIvsAI = new PlayAIvsAI();
            playingAIvsAI = true;
            goAI.setVisible(true);
            init(root);
            startGame();
        });

        goAI.setOnMouseClicked(mouseEvent -> {
            if (playingWithAI) {

                if (playWithAI.turnA) {
                    AITurn();
                }
            } else if (playingAIvsAI) {
                AIvsAITurn();
            }
        });
        return root;
    }


    void init(Pane root) {
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        tiles = new Tile[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
        int x = 0, y = 0;
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                Tile tile = tiles[i][j];
                tile.setTranslateX(20 + x);
                tile.setTranslateY(35 + y);
                root.getChildren().add(tile);
                if (j % 2 == 0)
                    x += 50;
                else
                    x += 10;
            }
            x = 0;
            if (i % 2 == 0)
                y += 50;
            else
                y += 10;
        }

        turnText.setFont(Font.font(15));
        turnText.setTranslateX(580);
        turnText.setTranslateY(35);
        root.getChildren().add(turnText);

        warningText.setFont(Font.font(15));
        warningText.setTranslateX(580);
        warningText.setTranslateY(125);
        root.getChildren().add(warningText);

        wallsNum.setFont(Font.font(15));
        wallsNum.setTranslateX(580);
        wallsNum.setTranslateY(80);
        root.getChildren().add(wallsNum);

        tiles[0][8].setText("A");
        tiles[16][8].setText("B");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Quoridor");
        primaryStage.getIcons().add(new Image("/./Source/game_box.png"));
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void startGame() {
        if (playingWithAI) {
            playWithAI.rand();
            playWithAI.readGenes();
            showTurn();
            if (playWithAI.turnA) {
                AITurn();
            }
        } else if (playingAIvsAI) {
            playAIvsAI.rand();
            playAIvsAI.readGenes();
            showTurn();
        } else {
            playWithFriend.rand();
            showTurn();
        }

    }

    void showTurn() {
        if (playingWithAI) {
            if (playWithAI.turnB) {
                turnText.setText("It's B Turn");
                wallsNum.setText("Remaining Walls: " + playWithAI.wallB);
            } else {
                turnText.setText("It's AI Turn");
                wallsNum.setText("Remaining Walls: " + playWithAI.wallA);
            }
            // show board state?

        } else if (playingAIvsAI) {
            if (playAIvsAI.turnB) {
                turnText.setText("It's B Turn");
                wallsNum.setText("Remaining Walls: " + playAIvsAI.wallB);
            } else {
                turnText.setText("It's A Turn");
                wallsNum.setText("Remaining Walls: " + playAIvsAI.wallA);
            }

            //AIvsAITurn();

        } else {
            if (playWithFriend.turnB) {
                turnText.setText("It's B Turn");
                wallsNum.setText("Remaining Walls: " + playWithFriend.wallB);
            } else {
                turnText.setText("It's A Turn");
                wallsNum.setText("Remaining Walls: " + playWithFriend.wallA);
            }
            playWithFriend.showBoardState();
        }
    }

    private class Tile extends StackPane {
        Rectangle boarder;
        Text text;

        public Tile(int i, int j) {
            if (i % 2 == 1 && j % 2 == 1) {
                boarder = new Rectangle(10, 10);
                boarder.setFill(Color.gray(0.3));
            } else if (i % 2 == 1) {
                boarder = new Rectangle(50, 10);
                boarder.setFill(Color.gray(0.7));
            } else if (j % 2 == 1) {
                boarder = new Rectangle(10, 50);
                boarder.setFill(Color.gray(0.7));
            } else {
                boarder = new Rectangle(50, 50);
                boarder.setFill(Color.web("D86868"));
            }

            boarder.setStroke(Color.BLACK);

            text = new Text();
            getChildren().addAll(boarder, text);

            setOnMouseClicked(event -> {
                if (playingWithAI) {
                    AIPlayed(i, j);
                } else if (!playingAIvsAI) {
                    friendPlayed(i, j);
                }

            });
        }

        void setText(String s) {
            text.setText(s);
        }
    }

    void friendPlayed(int i, int j) {
        if ((i % 2 == 1 || j % 2 == 1) && (i % 2 == 0 || j % 2 == 0)) {
            count++;
            if (count == 1) {
                x1 = i;
                y1 = j;
                warningText.setText("Pick Second Wall");
            } else {
                x2 = i;
                y2 = j;
            }

            String warn = "";

            if (count == 2) {

                warn = playWithFriend.puttingWall(x1, y1, x2, y2);
                if (warn.equals("invalidWall") || warn.equals("wallExists") || warn.equals("noWalls,only move")) {
                    warningText.setText(warn);
                    count = 0;
                } else if (warn.equals("ok")) {
                    warningText.setText("Wall Placed");
                    tiles[x1][y1].boarder.setFill(Color.BLACK);
                    tiles[x2][y2].boarder.setFill(Color.BLACK);
                    count = 0;
                }
                showTurn();
            }
        } else if ((i % 2 == 0 && j % 2 == 0)) {
            int x, y;
            char c;
            if (playWithFriend.turnA) {
                x = playWithFriend.game.posA.x;
                y = playWithFriend.game.posA.y;
                c = 'A';
            } else {
                x = playWithFriend.game.posB.x;
                y = playWithFriend.game.posB.y;
                c = 'B';
            }
            String war = playWithFriend.move(i, j);
            if (war.equals("ok")) {
                tiles[x][y].text.setText("");
                tiles[i][j].text.setText(c + "");
                if (playWithFriend.goalState()) {
                    if (playWithFriend.turnA) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("PLAYER 'A' IS WINNER!");
                        alert.showAndWait();
                        exit();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("PLAYER 'B' IS WINNER!");
                        alert.showAndWait();
                        exit();
                    }
                } else {
                    playWithFriend.updateTurn();
                    showTurn();
                }
            } else if (war.equals("false")) {
                warningText.setText("invalid Cell, have another go!");
            }
        }
    }

    void AIPlayed(int i, int j) {

        if ((i % 2 == 1 || j % 2 == 1) && (i % 2 == 0 || j % 2 == 0) && playWithAI.turnB) {
            count++;
            if (count == 1) {
                x1 = i;
                y1 = j;
                warningText.setText("Pick Second Wall");
            } else {
                x2 = i;
                y2 = j;
            }
            String warn = "";

            if (count == 2) {

                warn = playWithAI.puttingWall(x1, y1, x2, y2);
                if (warn.equals("invalidWall") || warn.equals("wallExists") || warn.equals("noWalls,only move")) {
                    warningText.setText(warn);
                    count = 0;
                } else if (warn.equals("ok")) {
                    tiles[x1][y1].boarder.setFill(Color.BLACK);
                    tiles[x2][y2].boarder.setFill(Color.BLACK);
                    warningText.setText("Wall Placed");
                    count = 0;

                    playWithAI.updateTurn();
                    showTurn();
                    warningText.setText("click the button, wait for your opponent");
                }
            }
        } else if ((i % 2 == 0 && j % 2 == 0) && playWithAI.turnB) {
            int x, y;
            char c;
            x = playWithAI.game.posB.x;
            y = playWithAI.game.posB.y;
            c = 'B';

            String war = playWithAI.move(i, j);
            if (war.equals("ok")) {
                tiles[x][y].text.setText("");
                tiles[i][j].text.setText(c + "");

                showWinner();

                playWithAI.updateTurn();
                showTurn();
                warningText.setText("click the button, and wait for your opponent");
                //AITurn();


            } else if (war.equals("false")) {
                warningText.setText("invalid Cell, have another go!");
            }
        }
    }

    void showWinner() {
        if (playingWithAI) {
            if (playWithAI.goalState()) {
                if (playWithAI.turnA) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("PLAYER 'A' IS WINNER!");
                    alert.showAndWait();
                    exit();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("PLAYER 'B' IS WINNER!");
                    alert.showAndWait();
                    exit();
                }
            }
        } else if (playingAIvsAI) {
            if (playAIvsAI.goalState()) {
                if (playAIvsAI.turnA) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("PLAYER 'A' IS WINNER!");
                    alert.showAndWait();
                    exit();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("PLAYER 'B' IS WINNER!");
                    alert.showAndWait();
                    exit();
                }
            }
        }
    }

    void AITurn() {

        Object[] o = playWithAI.AITurn();
        if ((boolean) o[0]) {
            Point prev = (Point) o[1];
            Point now = (Point) o[2];
            tiles[prev.x][prev.y].setText("");
            tiles[now.x][now.y].setText("A");

            showWinner();

        } else {
            Point prev = (Point) o[1];
            Point now = (Point) o[2];
            tiles[prev.x][prev.y].boarder.setFill(Color.BLACK);
            tiles[now.x][now.y].boarder.setFill(Color.BLACK);
        }
        warningText.setText("");
        playWithAI.updateTurn();
        showTurn();
    }

    void AIvsAITurn() {

        playAIvsAI.setGene();
        Object[] o = playAIvsAI.AIvsAITurn();
        if ((boolean) o[0]) {
            Point prev = (Point) o[1];
            Point now = (Point) o[2];

            if (playAIvsAI.turnA) {
                tiles[prev.x][prev.y].setText("");
                tiles[now.x][now.y].setText("A");
            } else {
                tiles[prev.x][prev.y].setText("");
                tiles[now.x][now.y].setText("B");
            }

            showWinner();

        } else {
            Point prev = (Point) o[1];
            Point now = (Point) o[2];
            tiles[prev.x][prev.y].boarder.setFill(Color.BLACK);
            tiles[now.x][now.y].boarder.setFill(Color.BLACK);
        }
        warningText.setText("");
        playAIvsAI.updateTurn();
        showTurn();

    }
}