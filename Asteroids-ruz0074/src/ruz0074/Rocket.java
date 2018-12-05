package ruz0074;

import javafx.geometry.Point2D;

public class Rocket extends ModelObject {

    public Rocket(Point2D position, Point2D imageOffset) {
        super(position, imageOffset);
    }

    @Override
    public void process() {
        move(super.getSpeed().getX(), super.getSpeed().getY());

    }

}
