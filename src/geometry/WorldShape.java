package geometry;


import render.ShapeRenderer;

/**
 * Created by Lemming on 28.07.2014.
 */
public class WorldShape extends Shape{
    @Override
    public void render(ShapeRenderer sr) {

    }

    @Override
    public void setPos(P2d pos) {

    }

    @Override
    public P2d getPos() {
        return P2d.ZERO.copy();
    }

    @Override
    public void setA(double angleRad) {

    }

    @Override
    public double getA() {
        return 0;
    }

    @Override
    public boolean contains(P2d p) {
        return true;
    }

    @Override
    public AABB getAABB() {
        return new AABB(new P2d(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), new P2d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    @Override
    public boolean localContains(P2d localPos) {
        return true;
    }
}
