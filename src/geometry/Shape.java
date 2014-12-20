package geometry;


import render.ShapeRenderer;

/**
 * Created by Lemming on 26.07.2014.
 */
public abstract class Shape{
    public abstract void render(ShapeRenderer sr);

    public abstract void setPos(P2d pos);
    public abstract P2d getPos();

    public abstract void setA(double angleRad);
    public abstract double getA();

    public abstract boolean contains(P2d p);

    public abstract boolean localContains(P2d localPos);

    public abstract AABB getAABB();
}
