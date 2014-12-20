package render;

import game.Game;
import geometry.P2d;
import geometry.PolygonShape;

import java.awt.*;

/**
 * Created by Alexander on 18.11.2014.
 */
public class ShapeRenderer {
    public ProjectionMatrix projection;
    private Color lineColor;
    Graphics2D g2d;


    public void line(P2d a, P2d b){
        P2d a2 = projection.multiply(a);
        P2d b2 = projection.multiply(b);
        g2d.drawLine((int)a2.x, (int)a2.y, (int)b2.x, (int)b2.y);
    }

    public void line(double x1, double y1, double x2, double y2){
        line(new P2d(x1, y1), new P2d(x2, y2));
    }


    public void circle(P2d center, double radius){
        P2d center2 = projection.multiply(center);
        radius *= projection.getScale();
        g2d.drawOval((int) (center2.x - radius), (int) (center2.y - radius), (int) (radius * 2), (int) (radius * 2));
    }

    public void circle(double centerX, double centerY, double radius){
        circle(new P2d(centerX, centerY), radius);
    }

    public void polygon(PolygonShape polygon){
        P2d[] poly = polygon.getWorldP2d();
        int[] xPoints = new int[poly.length];
        int[] yPoints = new int[poly.length];
        int n = poly.length;

        for(int i = 0; i < n; i++){
            P2d p = projection.multiply(poly[i]);
            xPoints[i] = (int)p.x;
            yPoints[i] = (int)p.y;
        }

        g2d.drawPolygon(xPoints, yPoints, n);

    }

    public ShapeRenderer(Graphics g2d) {
        this.g2d = (Graphics2D) g2d;
        projection = new ProjectionMatrix();
        lineColor = new Color(255, 255, 0);
        g2d.setColor(lineColor);
    }

    public void rect(double x1, double y1, double x2, double y2){
        polygon(new PolygonShape(x1, y1, x1, y2, x2, y2, x2, y1));
    }

    public void setGraphics(Graphics graphics) {
        this.g2d = (Graphics2D) graphics;
        g2d.setColor(lineColor);
    }

    public void setColor(double r, double g, double b, double a) {
        lineColor = new Color(0, 1, 0, 1f);
    }

    public void setPosition(P2d position) {
        projection.setPosition(position.minus(new P2d(0, -Game.game.screen.getHeight())));
        projection.setPosition(position.minus(new P2d(0, 0)));
    }
}
