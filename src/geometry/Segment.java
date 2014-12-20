package geometry;

import render.ShapeRenderer;

/**
 * Created by Lemming on 26.07.2014.
 */
public class Segment extends Shape{
    P2d start;
    P2d localDirection;
    double lengthSq;
    double a;

    public Segment(double x1, double y1, double x2, double y2) {
        start = new P2d(x1, y1);
        localDirection = new P2d(x2 - x1, y2 - y1);
        lengthSq = localDirection.lengthSq();
    }

    public Segment(P2d p1, P2d p2) {
        this(p1.x, p1.y, p2.x, p2.y);
    }

    public P2d getNormal(){
        return getGlobalDirection().rotate90().normalize();
    }

    private P2d getGlobalDirection() {
        return localDirection.rotate(a);
    }

    public Segment set(double x1, double y1, double x2, double y2){
        start = new P2d(x1, y1);
        localDirection = new P2d(x2 - x1, y2 - y1);
        lengthSq = localDirection.lengthSq();
        return this;
    }

    public Segment set(P2d p1, P2d p2){
        return set(p1.x, p1.y, p2.x, p2.y);
    }

    @Override
    public void render(ShapeRenderer sr) {
        P2d global = getGlobalDirection();
        sr.line(start.x, start.y, start.x + global.x, start.y + global.y);
    }


    @Override
    public void setPos(P2d pos) {
        start.set(pos);
    }

    @Override
    public P2d getPos() {
        return start;
    }

    @Override
    public void setA(double angleRad) {
        a = angleRad % (Math.PI * 2);
    }

    @Override
    public double getA() {
        return a;
    }

    @Override
    public boolean contains(P2d p) {
        P2d global = getGlobalDirection();
        return p.minus(start).dot(global)/ global.length() == p.minus(start).length();
    }

    @Override
    public boolean localContains(P2d localPos) {
        return localPos.dot(localDirection) == localPos.length() * localDirection.length();
    }

    @Override
    public AABB getAABB() {
        P2d p = getGlobalDirection().inc(getPos());
        return new AABB(p.x, p.y, start.x, start.y);
    }

    public P2d getLocalDirection() {
        return localDirection;
    }

    public void set(Segment a2) {
        start = a2.start.copy();
        localDirection = a2.localDirection.copy();
        lengthSq = a2.lengthSq;
        a = a2.a;
    }
}
