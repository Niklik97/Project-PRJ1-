package ruz0074;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.geometry.Point2D;

/**
 * @author beh01
 */
public abstract class ModelObject {


    private Point2D position;
    double direction;
    private final Point2D imageOffset;
    private Point2D speed = new Point2D(0, 0);
    private final int loopOffset = 5;
    private final int rocketHeight = 121;
    private final int rocketWidth = 111;


    public ModelObject(Point2D position, Point2D imageOffset) {
        this.position = new Point2D(position.getX(), position.getY());
        this.imageOffset = imageOffset;
    }


    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getDirection() {
        return direction;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setDirectionToPoint(Point2D direction) {
        Point2D imageRotation = position.add(imageOffset);
        double angle = position.angle(direction, imageRotation);
        if (direction.getY() < position.getY()) {
            angle = -angle;
        }
        this.direction = angle;
    }

    public boolean isOutOfSpace() {
        if (this.position.getX() < 0 || this.position.getX() > View.WIDTH) {
            return true;

        }
        if (this.position.getY() < 0 || this.position.getY() > View.HEIGHT) {
            return true;
        }
        return false;
    }

    public void loop() {
        System.out.println(getPosition().getX());
        System.out.println(getPosition().getY());

        if (getPosition().getX() >= View.WIDTH - rocketWidth / 2) {//vleti pravo
            System.out.println("pravy okraj");
            setPosition(new Point2D(loopOffset + rocketWidth / 2, getPosition().getY()));
        }
        if (getPosition().getX() <= rocketWidth / 2) {
            setPosition(new Point2D(View.WIDTH - rocketWidth / 2 - loopOffset, getY()));
        }
        if (getPosition().getY() <= rocketHeight / 2) {//vleti na vrch
            setPosition(new Point2D(getX(), View.HEIGHT - rocketHeight / 2 - loopOffset));
        }
        if (getPosition().getY() >= View.HEIGHT - rocketHeight / 2) {//vleti dolu
            setPosition(new Point2D(getX(), loopOffset + rocketHeight / 2));
        }

    }

    protected void move(double offsetX, double offsetY) {
        position = new Point2D(getX() + offsetX, getY() + offsetY);
    }

    public Point2D getSpeed() {
        return speed;
    }

    public void setSpeed(Point2D speed) {
        this.speed = speed;
    }

    public abstract void process();
}


