import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class CircleSlicer extends PApplet {
    public static void main(String[] args) {
        PApplet.main(CircleSlicer.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noLoop();
    }

    @Override
    public void draw() {
        ArrayList<Polygon> polygons = new ArrayList<Polygon>();
        Polygon circle = new Polygon();
        for (int i = 0; i < CIRCLE_POINTS; i++) {
            circle.points.add(new PVector(CIRCLE_RADIUS * cos(TWO_PI * i / CIRCLE_POINTS),
                    CIRCLE_RADIUS * sin(TWO_PI * i / CIRCLE_POINTS)));
        }
        polygons.add(circle);

        translate(width / 2f, height / 2f);
        for (int i = 0; i < NUMBER_OF_SLICES; i++) {
            float angle1 = random(PI);
            float angle2 = random(PI);
            float distance = GAUSSIAN_MULTIPLIER * CIRCLE_RADIUS * randomGaussian();
            float offset = random(OFFSET_LOW, OFFSET_HIGH);

            Polygon clip1 = new Polygon(), clip2 = new Polygon();
            PVector point = PVector.fromAngle(angle1).setMag(distance);

            clip1.points.add(PVector.add(point, PVector.fromAngle(angle2).mult(2 * width)));
            clip1.points.add(PVector.add(clip1.points.get(0), PVector.fromAngle(angle2 + PI / 2).mult(2 * width)));
            clip1.points.add(PVector.add(clip1.points.get(1), PVector.fromAngle(angle2).mult(-2 * width)));
            clip1.points.add(PVector.add(point, PVector.fromAngle(angle2).mult(-2 * width)));

            clip2.points.add(PVector.add(point, PVector.fromAngle(angle2).mult(2 * width)));
            clip2.points.add(PVector.add(clip2.points.get(0), PVector.fromAngle(angle2 - PI / 2).mult(2 * width)));
            clip2.points.add(PVector.add(clip2.points.get(1), PVector.fromAngle(angle2).mult(-2 * width)));
            clip2.points.add(PVector.add(point, PVector.fromAngle(angle2).mult(-2 * width)));

            ArrayList<Polygon> newPolys = new ArrayList<Polygon>();
            for (Polygon polygon : polygons) {
                Polygon clipped = polygon.clip(clip1);
                if (clipped.points.size() > 2) {
                    clipped.translate(PVector.fromAngle(angle2 + PI / 2).mult(offset));
                    newPolys.add(clipped);
                }
                clipped = polygon.clip(clip2);
                if (clipped.points.size() > 2) {
                    clipped.translate(PVector.fromAngle(angle2 - PI / 2).mult(offset));
                    newPolys.add(clipped);
                }
            }
            polygons = newPolys;
        }

        for (Polygon polygon : polygons) {
            polygon.draw(this);
        }

        saveSketch(this);
    }
}
