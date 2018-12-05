package ruz0074;

/**
 * @author beh01
 */
import javafx.geometry.Point2D;

import java.util.concurrent.ThreadLocalRandom;


public class Asteroid extends ModelObject {

    public Asteroid(Point2D position, Point2D imageOffset) {
        super(position, imageOffset);
        super.setSpeed(new Point2D(ThreadLocalRandom.current().nextDouble(-1, 2), ThreadLocalRandom.current().nextDouble(-1, 2)));
    }

    @Override
    public void process() {
        move(super.getSpeed().getX(), super.getSpeed().getY());

    }
}
