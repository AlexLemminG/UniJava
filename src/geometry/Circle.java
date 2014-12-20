package geometry;


import render.ShapeRenderer;

/**
 * Created by Lemming on 26.07.2014.
 */
public class Circle extends Shape{
    P2d pos = new P2d();
    double radius;
    double radiusSq;
    double a;

    public Circle(P2d pos, double radius) {
        this.pos.set(pos);
        this.radius = radius;
        radiusSq = radius * radius;
    }

    @Override
    public boolean contains(P2d p) {
        return (p.minus(pos).lengthSq() < radiusSq);
    }

    @Override
    public boolean localContains(P2d localPos) {
        return localPos.lengthSq() < radiusSq;
    }

    @Override
    public AABB getAABB() {
        return new AABB(pos, radius);
    }

    public void setPos(P2d pos) {

        this.pos = pos;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        radiusSq = radius * radius;
    }

    public P2d getPos() {
        return pos;
    }

    @Override
    public void setA(double angleRad) {
        a = angleRad;
    }

    @Override
    public double getA() {
        return a;
    }

    public double getRadius() {
        return radius;
    }


    @Override
    public void render(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, radius);
    }


}
