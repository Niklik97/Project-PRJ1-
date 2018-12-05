package ruz0074;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

/**
 * @author beh01
 */
public class View {

    private int iteratorAsteroidu = 0;

    public final static int HEIGHT = 600;
    public final static int WIDTH = 800;

    private final GraphicsContext context;
    private final Image background, rocket, bullet, asteroid;


    private final Model model;

    View(GraphicsContext context, Model model) {
        background = new Image("file:src/image/background2.jpeg");
        rocket = new Image("file:src/image/raketa.png");
        //rocketDimensions = new Point2D(rocket.getWidth(), rocket.getHeight());
        bullet = new Image("file:src/image/laser_saber.png");
        asteroid = new Image("file:src/image/asteroid.png");
        this.context = context;
        this.model = model;
        update();
    }

    private void drawImage(Image image, Point2D point) {
        context.drawImage(image, point.getX() - image.getWidth() / 2, point.getY() - image.getHeight() / 2);

    }

    private void rotate(double angle, Point2D center) {
        Rotate r = new Rotate(angle, center.getX(), center.getY());
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public void update() {
        iteratorAsteroidu++;
        context.drawImage(background, 0, 0, WIDTH, HEIGHT);
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        if (iteratorAsteroidu % 100 == 0) {
            model.generateAsteroids();
        }
        synchronized (model) {
            for (ModelObject object : model.getObjects()) {
                context.save();
                rotate(object.getDirection(), object.getPosition());

                if (object instanceof Rocket) {

                    drawImage(rocket, object.getPosition());
                } else if (object instanceof Bullet) {

                    drawImage(bullet, object.getPosition());
                } else if (object instanceof Asteroid) {
                    drawImage(asteroid, object.getPosition());
                }
                context.restore();
            }
        }
    }

}
