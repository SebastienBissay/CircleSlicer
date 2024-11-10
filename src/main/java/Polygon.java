import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

import static parameters.Parameters.*;
import static processing.core.PConstants.CLOSE;

public class Polygon {
    ArrayList<PVector> points;

    Polygon() {
        points = new ArrayList<>();
    }

    private boolean isLeft(PVector p, PVector p1, PVector p2) {
        return (p2.y - p1.y) * p.x + (p1.x - p2.x) * p.y + (p2.x * p1.y - p1.x * p2.y) < 0;
    }

    private PVector intersection(PVector cp1, PVector cp2, PVector s, PVector e) {
        PVector dc = new PVector(cp1.x - cp2.x, cp1.y - cp2.y);
        PVector dp = new PVector(s.x - e.x, s.y - e.y);
        float n1 = cp1.x * cp2.y - cp1.y * cp2.x;
        float n2 = s.x * e.y - s.y * e.x;
        float n3 = 1 / (dc.x * dp.y - dc.y * dp.x);
        return new PVector((n1 * dp.x - n2 * dc.x) * n3, (n1 * dp.y - n2 * dc.y) * n3);
    }

    public Polygon clip(Polygon clipPolygon) {
        if (!clipPolygon.isCCW()) {
            clipPolygon.reCCW();
        }
        PVector cp1, cp2, s, e;
        Polygon newPolygon = new Polygon();
        Polygon inputPolygon = new Polygon();

        newPolygon.points.addAll(points);

        for (int j = 0; j < clipPolygon.points.size(); j++) {
            inputPolygon.points.clear();
            inputPolygon.points.addAll(newPolygon.points);
            newPolygon.points.clear();

            cp1 = clipPolygon.points.get(j);
            cp2 = clipPolygon.points.get((j < clipPolygon.points.size() - 1) ? (j + 1) : 0);

            for (int i = 0; i < inputPolygon.points.size(); i++) {
                s = inputPolygon.points.get(i);
                e = inputPolygon.points.get((i < inputPolygon.points.size() - 1) ? (i + 1) : 0);

                if (isLeft(s, cp1, cp2) && isLeft(e, cp1, cp2)) {
                    newPolygon.points.add(e);
                } else {
                    if (!isLeft(s, cp1, cp2) && isLeft(e, cp1, cp2)) {
                        newPolygon.points.add(intersection(cp1, cp2, s, e));
                        newPolygon.points.add(e);
                    } else {
                        if (isLeft(s, cp1, cp2) && !isLeft(e, cp1, cp2)) {
                            newPolygon.points.add(intersection(cp1, cp2, s, e));
                        }
                    }
                }
            }
        }
        return newPolygon;
    }

    private boolean isCCW() {
        if (points.size() < 3) {
            return true;
        }
        PVector p = points.get(0), q = points.get(1), r = points.get(2);
        return (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y) < 0;
    }

    private void reCCW() {
        ArrayList<PVector> newPoints = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            newPoints.add(points.get(i));
        }
        points = newPoints;
    }

    public void translate(PVector v) {
        for (PVector p : points) {
            p.add(v);
        }
    }

    public void draw(PApplet pApplet) {
        PShape s = pApplet.createShape();
        s.beginShape();
        if (POLYGON_NO_FILL) {
            s.noFill();
        } else {
            s.fill(POLYGON_COLOR.red(), POLYGON_COLOR.green(), POLYGON_COLOR.blue(), POLYGON_COLOR.alpha());
        }
        if (POLYGON_NO_STROKE) {
            s.noStroke();
        } else {
            s.stroke(POLYGON_COLOR.red(), POLYGON_COLOR.green(), POLYGON_COLOR.blue(), POLYGON_COLOR.alpha());
            s.strokeWeight(POLYGON_STROKEWEIGHT);
        }
        for (PVector p : points) {
            s.vertex(p.x, p.y);
        }
        s.endShape(CLOSE);
        pApplet.shape(s);
    }
}
