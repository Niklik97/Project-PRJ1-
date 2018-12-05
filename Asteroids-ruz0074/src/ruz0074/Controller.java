package ruz0074;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;

import static ruz0074.TheGame.score;

/**
 * @author beh01
 */
public class Controller {

    private Timeline timer;
    private View view;
    private Model model;
    Database database = new Database();

    public Controller(View view, Model model) {
        timer = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<javafx.event.ActionEvent>() {

            public void handle(javafx.event.ActionEvent event) {
                HashSet<ModelObject> destroyedAsteroidsSet = new HashSet<>();
                synchronized (model) {
                    ArrayList<ModelObject> toDelete = new ArrayList<>();
                    for (ModelObject object : model.getObjects()) {
                        object.process();
                        if (model.checkAsteroidAndRocketCollision()) {
                            stop();
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
                            alert.setOnHidden(hiddenevent-> Platform.exit());
                            alert.show();
                            break;
                        }
                        if (object.isOutOfSpace()) {
                            if (object instanceof Rocket) {
                                object.loop();
                            } else {
                                toDelete.add(object);
                            }
                        } else {
                            ArrayList<ModelObject> destroyedAsteroids = model.checkAsteroids();
                            destroyedAsteroidsSet.addAll(destroyedAsteroids);
                            toDelete.addAll(destroyedAsteroids);
                        }
                    }
                    model.getObjects().removeAll(toDelete);
                }
                score += destroyedAsteroidsSet.size() / 2;
                System.out.println(score);
                view.update();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;
    }

    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.update();
        timer.play();
    }

}

