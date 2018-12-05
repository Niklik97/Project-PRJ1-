/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruz0074;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * @author beh01
 */
public class TheGame extends Application {

    private Model model;
    private View view;
    private Controller controller;
    public static int score;

    public TheGame() {
        model = new Model();
    }

    @Override
    public void start(Stage primaryStage) {

        AnchorPane basePane = new AnchorPane();
        Button btnStart = new Button();
        btnStart.setText("Start game");
        Database database = new Database();
        Media sound = new Media(new File("src/music/Stargate.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        //database.dropAllRecords();
        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (controller.isRunning()) {
                    //action.stop();
                    controller.stop();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText("Ukončil jsi hru Asteroids, toho budeš litovat ^^");
                    StringBuilder s = new StringBuilder("Skore: " + score + "\n\n");
                    if (score > database.getBestScore()) {
                        System.out.println(score);
                        System.out.println(database.getBestScore());
                        s.append("překonal jsi rekord \n\n");

                    }
                    database.insertScore(score);

                    ArrayList<Integer> top10Scores = database.getTop10Scores();

                    for (int i = 0; i < top10Scores.size(); i++) {
                        s.append(i + 1).append(". ").append(top10Scores.get(i)).append("\n");
                    }

                    alert.setContentText(s.toString());
                    btnStart.setText("Start game");
                    alert.setOnHidden(hiddenevent->Platform.exit());
                    alert.show();
                } else {
                    model.initGame();
                    score = 0;
                    controller.start();

                    mediaPlayer.play();
                }
                btnStart.setText("Stop game");

            }
        });
        basePane.getChildren().add(btnStart);
        AnchorPane.setTopAnchor(btnStart, 0.0);
        AnchorPane.setLeftAnchor(btnStart, 0.0);
        AnchorPane.setRightAnchor(btnStart, 0.0);

        Pane root = new Pane();
        Canvas canvas = new Canvas(View.WIDTH, View.HEIGHT);
        root.getChildren().add(canvas);
        canvas.scaleXProperty().bind(root.widthProperty().multiply(1.0 / View.WIDTH));
        canvas.scaleYProperty().bind(root.heightProperty().multiply(1.0 / View.HEIGHT));
        canvas.translateXProperty().bind(root.widthProperty().subtract(View.WIDTH).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(View.HEIGHT).divide(2));

        view = new View(canvas.getGraphicsContext2D(), model);
        controller = new Controller(view, model);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                model.setCursor(event.getX(), event.getY());
                view.update();

            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                model.fire(event.getX(), event.getY());
            }
        });

        basePane.getChildren().add(root);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 30.0);

        Scene scene = new Scene(basePane, 800, 630);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(("file:src/image/rocket1.png")));

        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    Platform.exit();
                }
                model.setRocketSpeedVector(event.getCode());
               // primaryStage.setTitle("Asteroids - score: " + score);
                view.update();
            }
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
