package ruz0074;

import javafx.geometry.Point2D;

public class Bullet extends ModelObject {

    Point2D speedVector;

    public Bullet(Point2D position, Point2D imageOffset, Point2D speedVector) {
        super(position, imageOffset);
        this.speedVector = speedVector;
    }

    @Override
    public void process() {
        move(speedVector.getX(), speedVector.getY());
    }

}

