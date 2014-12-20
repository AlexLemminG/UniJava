package geometry;

/**
 * Created by Lemming on 26.07.2014.
 */
public class DistanceInf {
    public double distance;
    public P2d wayToPushA;
    public P2d intersectionLocalP2dA;
    public P2d intersectionLocalP2dB;

    public DistanceInf(double distance, P2d wayToPushA) {
        this.distance = distance;
        this.wayToPushA = wayToPushA;
    }

    public DistanceInf(double distance, P2d wayToPushA, P2d intersectionLocalP2dA, P2d intersectionLocalP2dB) {
        this.distance = distance;
        this.wayToPushA = wayToPushA;
        this.intersectionLocalP2dA = intersectionLocalP2dA;
        this.intersectionLocalP2dB = intersectionLocalP2dB;
    }

    public DistanceInf invertThis(){
        wayToPushA.inverseThis();
        P2d temp = intersectionLocalP2dA;
        intersectionLocalP2dA = intersectionLocalP2dB;
        intersectionLocalP2dB = temp;
        return this;
    }
}
