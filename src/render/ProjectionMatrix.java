package render;

import game.Game;
import geometry.P2d;

/**
 * Created by Alexander on 18.11.2014.
 */
public class ProjectionMatrix {
    public static double defaultDx;
    public static double defaultDy;
    double angle;
    double scale;
    double dx;
    double dy;

    boolean dirty = true;

    double[][] matrix;

    public ProjectionMatrix(){
        matrix = new double[][]{{1, 0, 0},
                                {0, 1, 0}};
        angle = 0;
        scale = 1;
        dx = 0;
        dy = 0;
    }

    public P2d multiply(P2d a){
        update();
        return new P2d( matrix[0][0] * a.x + matrix[0][1] * a.y + matrix[0][2],
                        matrix[1][0] * a.x + matrix[1][1] * a.y + matrix[1][2] );
    }

    public P2d screenToWorldP2d(P2d screen){
        update();
        double scale2 = 1 / scale;
        scale2 *= scale2;

        double a = screen.x - matrix[0][2];
        double b = screen.y - matrix[1][2];

        return new P2d( matrix[0][0] * a + matrix[1][0] * b,
                matrix[0][1] * a + matrix[1][1] * b ).multiplyThis(scale2);
    }

    public void addPosition(P2d add){
        dx -= add.x;
        dy -= add.y;
        dirty = true;
    }

    public void setPosition(P2d pos){
        dx = -pos.x;
        dy = -pos.y;
        dirty = true;
    }

    public void addRotation(double add){
        angle -= add;
        dirty = true;
    }

    public void setRotation(double rotation){
        angle = -rotation;
        dirty = true;
    }

    public void update(){
        defaultDx = 0;
        defaultDy = -(Game.game.screen.getHeight() -1);

        matrix[0][0] = Math.cos(angle) * scale;
        matrix[0][1] = Math.sin(angle) * scale;
        matrix[1][0] = Math.sin(angle) * scale;
        matrix[1][1] = -Math.cos(angle) * scale;
        matrix[0][2] = (dx + defaultDx) * scale;
        matrix[1][2] = -(dy + defaultDy) * scale;
    }


    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
