package ruz0074;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author beh01
 */
public class Model {

    private ArrayList<ModelObject> objects = new ArrayList<>();
    private ModelObject rocket;
    private final int asteroidHeight = 81;
    private final int asteroidWidth = 99;


    public ArrayList<ModelObject> getObjects() {
        return this.objects;
    }

    public Model() {

    }

    public void setRocketSpeedVector(KeyCode keyCode) {
        Point2D currentSpeed = rocket.getSpeed();
        if (keyCode == KeyCode.UP) {
            rocket.setSpeed(new Point2D(currentSpeed.getX(), currentSpeed.getY() - 0.1));
        }
        if (keyCode == KeyCode.DOWN) {
            rocket.setSpeed(new Point2D(currentSpeed.getX(), currentSpeed.getY() + 0.1));
        }
        if (keyCode == KeyCode.LEFT) {
            rocket.setSpeed(new Point2D(currentSpeed.getX() - 0.1, currentSpeed.getY()));
        }
        if (keyCode == KeyCode.RIGHT) {
            rocket.setSpeed(new Point2D(currentSpeed.getX() + 0.1, currentSpeed.getY()));
        }
    }

    public synchronized void setCursor(double x, double y) {
        Point2D mousePoint = new Point2D(x, y);
        if (rocket != null) {
            rocket.setDirectionToPoint(mousePoint);
        }
    }

    public synchronized void initGame() {
        objects.clear();
        Point2D center = new Point2D(View.WIDTH / 2, View.HEIGHT / 2);
        rocket = new Rocket(center, new Point2D(100, 0));
        objects.add(rocket);

    }

    public synchronized void generateAsteroids() {
        Asteroid asteroid = new Asteroid(new Point2D(ThreadLocalRandom.current().nextDouble(0.0, 800.0), ThreadLocalRandom.current().nextDouble(0.0, 600.0)), new Point2D(60, 7));
        objects.add(asteroid);
    }

    public synchronized void fire(double x, double y) {
        if (rocket != null) {
            Point2D mousePoint = new Point2D(x, y);
            Point2D rocketPosition = rocket.getPosition();
            Point2D speed = mousePoint.subtract(rocketPosition);
            speed = speed.normalize();
            speed = speed.multiply(5);
            Point2D bulletStartPosition = rocketPosition.add(speed.multiply(7));
            Bullet bullet = new Bullet(bulletStartPosition, new Point2D(100, 0), speed);
            bullet.setDirectionToPoint(bulletStartPosition.add(speed.multiply(10)));
            objects.add(bullet);
            System.out.println("vyleti bullet");
        }
    }

    public synchronized ArrayList<ModelObject> checkAsteroids() {
        ArrayList<ModelObject> result = new ArrayList<>();

        for (ModelObject object : objects) {
            if (object instanceof Asteroid) {
                for (ModelObject object2 : objects) {
                    if (object2 instanceof Bullet) {
                        if ((object2.getPosition().getX() >= object.getPosition().getX() - asteroidWidth / 2) && (object2.getPosition().getX() <= object.getPosition().getX() + asteroidWidth / 2) && (object2.getPosition().getY() <= object.getPosition().getY() + asteroidHeight / 2) && (object2.getPosition().getY() >= object.getPosition().getY() - asteroidHeight / 2)) {
                            result.add(object);
                            result.add(object2);
                        }
                    }
                }
            }
        }
        return result;
    }

    public synchronized boolean checkAsteroidAndRocketCollision() {
        for (ModelObject object : objects) {
            if (object instanceof Asteroid) {
                for (ModelObject object2 : objects) {
                    if (object2 instanceof Rocket) {
                        if ((object2.getPosition().getX() >= object.getPosition().getX() - asteroidWidth / 2) && (object2.getPosition().getX() <= object.getPosition().getX() + asteroidWidth / 2) && (object2.getPosition().getY() <= object.getPosition().getY() + asteroidHeight / 2) && (object2.getPosition().getY() >= object.getPosition().getY() - asteroidHeight / 2)) {

                            return true;
                        }
                    }
                }
            }
        }
        return false; //pokud koliduje vraci true
    }
}
