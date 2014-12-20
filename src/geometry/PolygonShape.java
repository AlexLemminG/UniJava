package geometry;

import render.ShapeRenderer;

/**
 * Created by Lemming on 21.07.2014.
 */
public class PolygonShape extends Shape{
    protected P2d[] world;
    protected int size;
    protected P2d pos = new P2d();
    protected P2d[] local;
    protected double a;
    protected P2d origin = new P2d();
    protected boolean dirty = true;

    public int getSize() {
        return size;
    }

    public PolygonShape(double... xy){
        size = xy.length / 2;
        local = new P2d[size];
        world = new P2d[size];
        for(int i = 0; i < size; i++){
            local[i] = new P2d(xy[i * 2], xy[i * 2  + 1]);
            world[i] = local[i].copy();
        }
        dirty = false;
    }

    public PolygonShape(P2d... vertex){
        size = vertex.length;
        local = new P2d[size];
        world = new P2d[size];
        for(int i = 0; i < vertex.length; i++){
            local[i] = vertex[i].copy();
            world[i] = vertex[i].copy();
        }
        dirty = false;
    }


    public P2d[] getWorldP2d(){
        if(!dirty) return world;
        dirty = false;

        for(int i = 0; i < size; i++){
            world[i] = pos.plus(local[i].minus(origin).rotate(a));
        }

        return world;
    }

    public void setPos(P2d pos){
        if(!pos.equals(this.pos)) {
            dirty = true;
            this.pos = pos;
        }
    }

    @Override
    public P2d getPos() {
        return pos;
    }

    public void setXY(double x, double y){
        if(!pos.equals(x, y)) {
            pos.x = x;
            pos.y = y;
            dirty = true;
        }
    }

    public void setA(double a){
        if(this.a != a) {
            this.a = a;
            dirty = true;
        }
    }

    @Override
    public double getA() {
        return a;
    }

    public void setOriginPos(P2d origin){
        if(!origin.equals(this.origin)) {
            this.origin = origin;
            dirty = true;
        }
    }

    public void setOriginXY(double x, double y){
        if(!origin.equals(x, y)) {
            origin.x = x;
            origin.y = y;
            dirty = true;
        }
    }

    public void render(ShapeRenderer shapeRenderer){
        getWorldP2d();
        shapeRenderer.polygon(this);
    }

    public boolean contains (P2d p) {
        getWorldP2d();
        int intersects = 0;
        double x = p.x;
        double y = p.y;

        for (int i = 0; i < size; i ++) {
            double x1 = world[i].x;
            double y1 = world[i].y;
            double x2 = world[(i + 1) % size].x;
            double y2 = world[(i + 1) % size].y;
            if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) && x < ((x2 - x1) / (y2 - y1) * (y - y1) + x1)) intersects++;

        }
        return (intersects & 1) == 1;
    }

    @Override
    public boolean localContains(P2d localPos) {
        int intersects = 0;
        double x = localPos.x + origin.x;
        double y = localPos.y + origin.y;

        for (int i = 0; i < size; i ++) {
            double x1 = local[i].x;
            double y1 = local[i].y;
            double x2 = local[(i + 1) % size].x;
            double y2 = local[(i + 1) % size].y;
            if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) && x <= ((x2 - x1) / (y2 - y1) * (y - y1) + x1)) intersects++;

        }
        return (intersects & 1) == 1;
    }

    @Override
    public AABB getAABB() {
        getWorldP2d();
        P2d max = world[0].copy();
        P2d min = world[0].copy();
        for(int i = 1; i < world.length; i++){
            if(min.x > world[i].x)
                min.x = world[i].x;
            if(min.y > world[i].y)
                min.y = world[i].y;

            if(max.x < world[i].x)
                max.x = world[i].x;
            if(max.y < world[i].y)
                max.y = world[i].y;
        }
        return new AABB(min, max);
    }

    public double[] toPolygon(){
        double[] result = new double[size * 2];
        getWorldP2d();
        for(int i = 0; i < world.length; i++){
            result[2 * i] = world[i].x;
            result[2 * i + 1] = world[i].y;
        }
        return result;
    }

    public P2d getGlobalVertex(int index){
        getWorldP2d();
        return world[index % size];
    }

    public P2d getGlobalNormal(int index){
        getWorldP2d();
        P2d a = world[(index) % size];
        P2d b = world[(index + 1) % size];
        return (a.minus(b).rotate90().normalizeThis());
    }


    protected P2d getSupportPoint(P2d direction){
        getWorldP2d();
        double bestProjection = Double.NEGATIVE_INFINITY;
        P2d bestVertex = null;

        for(int i = 0; i < size; i++){
            P2d v = getGlobalVertex(i);
            double proj = direction.dot(v);

            if(proj > bestProjection){
                bestProjection = proj;
                bestVertex = v;
            }
        }

        return bestVertex;
    }

}
