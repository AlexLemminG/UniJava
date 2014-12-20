package geometry;

import render.ShapeRenderer;

/**
 * Created by Lemming on 31.07.2014.
 */
public class AABB extends Shape{
    //TODO доделать
    P2d min = new P2d(), max = new P2d();

    public AABB(P2d min, P2d max) {
        this.min = min;
        this.max = max;
    }

    public AABB(double x1, double y1, double x2, double y2){
        this.min.x = Math.min(x1, x2);
        this.min.y = Math.min(y1, y2);

        this.max.x = Math.max(x1, x2);
        this.max.y = Math.max(y1, y2);
    }
    public AABB(double x, double y, double radius){
        min.x = x - radius;
        min.y = y - radius;

        max.x = x + radius;
        max.y = y + radius;
    }
    public AABB(P2d pos, double radius){
        min.x = pos.x - radius;
        min.y = pos.y - radius;

        max.x = pos.x + radius;
        max.y = pos.y + radius;
    }

    public boolean intersects(AABB another){
        return min.x < another.max.x && min.y < another.max.y && another.min.x < max.x && another.min.y < max.y;
    }

    public AABB getAABB(){
        return this;
    }

    @Override
    public void render(ShapeRenderer sr) {
        sr.rect(min.x, min.y, max.x - min.x, max.y - min.y);
    }

    @Override
    public void setPos(P2d pos) {

    }

    @Override
    public P2d getPos() {
        return min;
    }

    public P2d getMin(){
        return min;
    }
    public P2d getMax(){
        return max;
    }
    public P2d getCenter(){
        return new P2d(max.plus(min).multiplyThis(0.5f));
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
        return p.x < max.x && p.x >= min.x && p.y < max.y && p.y >= min.y;
    }

    @Override
    public boolean localContains(P2d localPos) {
        return false;
    }

}
