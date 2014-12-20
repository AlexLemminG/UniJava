package basicobjects;

import geometry.P2d;
import geometry.PolygonShape;

/**
 * Created by Lemming on 21.07.2014.
 */
public class Path extends PolygonShape {
    double pathLength;
    boolean circled;
    double[] length;
    public Path(boolean circled, double... xy) {
        super(xy);
        length = new double[size];

        this.circled = circled;
        for(int i = 1; i < size; i++){
            length[i - 1] = local[i].minus(local[i-1]).length();
            pathLength += length[i - 1];
        }
        if(circled) {
            length[size - 1] = local[0].minus(local[size - 1]).length();
            pathLength += length[size - 1];
        }
    }

    public P2d getP2d(double t){
        double l = 0;
        int i = 0;
        if(t > 1)
            t = t % 1f;
        P2d result;
        t *= pathLength;
        for(i = 0; i < size && l + length[i] < t; i++)
            l += length[i];
        t = (t - l) / length[i];
        result = local[i].plus((local[(i + 1) % size].minus(local[i])).multiply(t));

        result = result.minus(origin).rotate(a).plus(pos);


        return result;
    }

    public boolean isCircled() {
        return circled;
    }

    public double getLength() {
        return pathLength;
    }
}
