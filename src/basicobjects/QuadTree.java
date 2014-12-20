package basicobjects;

import physics.PhysicsType;
import geometry.AABB;
import geometry.P2d;
import render.ShapeRenderer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Lemming on 31.07.2014.
 */
public class QuadTree {
    public static int maxZ = 1000;

    Cell[] basicCells = new Cell[maxZ];

    public QuadTree(AABB aabb){

        for(int i = 0; i < maxZ; i++)
            basicCells[i] = new Cell(aabb, null);
    }

    public void add(GObject child){

        basicCells[child.getZ()].add(child);
    }

    public boolean remove(GObject child){

        return basicCells[child.getZ()].remove(child);
    }

    public LinkedList<GObject> getIntersection(AABB intersector, int z){

        return basicCells[z].getIntersection(intersector);
    }

    public void removeAllNotAbsolutelyStatic(){
        for(int i = 0; i < maxZ; i++) {
            basicCells[i].removeAllNotAbsolutelyStatic();
            basicCells[i].update();
        }
    }

    public LinkedList<GObjectPair> getPossibleCollisions(){
        LinkedList<GObjectPair> result = new LinkedList<GObjectPair>();
        for(int i = 0; i < maxZ; i++)
            result.addAll(basicCells[i].getPossibleCollisions());

        Collections.sort(result);
        ListIterator<GObjectPair> iterator = result.listIterator();

        GObjectPair prev = null;
        if(iterator.hasNext())
            prev = iterator.next();
        GObjectPair curr;
        while(iterator.hasNext()){
            curr = iterator.next();

            if(curr.compareTo(prev) == 0)
                iterator.remove();

            prev = curr;

        }

        return result;
    }

    public void addAllNotAbsolutelyStatic(LinkedList<Cont> children) {
        for(Cont child : children){
            if(child instanceof GObject) {
                if (!((GObject)child).isAbsolutelyStatic())
                    basicCells[((GObject)child).getZ()].add((GObject)child);
            }
        }
    }

    public int size(){
        int result = 0;
        for(int i = 0; i < maxZ; i++)
            result += basicCells[i].children.size();
        return result;
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        for(int i = 0; i < maxZ; i++)
            basicCells[i].renderShape(shapeRenderer);
    }


    private static class Cell{
        static int maxDepth = 10;
        static int maxChildrentSize = 10;

        AABB aabb;
        P2d center;
        int depth;
        boolean splitted;
        Cell parent;
        Cell a, b;
        Cell c, d;
        LinkedList<GObject> children;

        public Cell(AABB aabb, Cell parent){
            this.aabb = aabb;
            center = aabb.getCenter();
            this.parent = parent;
            if(parent != null)
                depth = parent.depth + 1;
            else
                depth = 0;
            children = new LinkedList<GObject>();

        }

        public boolean canSplit(){
            return depth < maxDepth;
        }

        public boolean mustSplit(){
            return !splitted && canSplit() && children.size() > maxChildrentSize;
        }

        public void add(GObject child){

            children.add(child);

            if(splitted){
                LinkedList<Cell> cells = getMustContainSubCells(child.getAABB());
                for(Cell cell : cells)
                    cell.add(child);
            }


            if(mustSplit())
                split();



        }

        public boolean remove(GObject child){
            if(!splitted)
                return children.remove(child);

            LinkedList<Cell> cells = getMustContainSubCells(child.getAABB());
            for(Cell cell : cells){
                cell.remove(child);
            }
            return children.remove(child);

        }

        public LinkedList<Cell> getMustContainSubCells(AABB childAABB){
            LinkedList<Cell> result = new LinkedList<Cell>();
            P2d min = childAABB.getMin();
            P2d max = childAABB.getMax();

            if(min.x >= aabb.getMax().x || min.y >= aabb.getMax().y || max.x <= aabb.getMin().x || max.y <= aabb.getMin().y)
                return result;

            if(!splitted){
                return result;
            }else{
                if(min.x < center.x){
                    if(min.y < center.y)
                        result.add(c);
                    if(max.y >= center.y)
                        result.add(a);
                }
                if(max.x > center.x){
                    if(min.y < center.y)
                        result.add(d);
                    if(max.y >= center.y)
                        result.add(b);
                }
            }
            return result;

        }

        public void split(){
            splitted = true;
            P2d max = aabb.getMax();
            P2d min = aabb.getMin();

            AABB aBox = new AABB(min.x, center.y, center.x, max.y);
            AABB bBox = new AABB(center.x, center.y, max.x, max.y);
            AABB cBox = new AABB(min.x, min.y, center.x, center.y);
            AABB dBox = new AABB(center.x, min.y, max.x, center.y);

            a = new Cell(aBox, this);
            b = new Cell(bBox, this);
            c = new Cell(cBox, this);
            d = new Cell(dBox, this);

            for(GObject child : children) {
                LinkedList<Cell> cells = getMustContainSubCells(child.getAABB());
                for(Cell cell : cells)
                    cell.add(child);
            }


        }

        public LinkedList<GObject> getIntersection(AABB intersector){
            LinkedList<GObject> result = new LinkedList<GObject>();
            P2d min = intersector.getMin();
            P2d max = intersector.getMax();

            if(min.x >= aabb.getMax().x || min.y >= aabb.getMax().y || max.x <= aabb.getMin().x || max.y <= aabb.getMin().y)
                return result;

            if(!splitted){
                result.addAll(children);
            }else{
                if(min.x < center.x){
                    if(min.y < center.y)
                        result.addAll(c.getIntersection(intersector));
                    if(max.y >= center.y)
                        result.addAll(a.getIntersection(intersector));
                }
                if(max.x > center.x){
                    if(min.y < center.y)
                        result.addAll(d.getIntersection(intersector));
                    if(max.y >= center.y)
                        result.addAll(b.getIntersection(intersector));
                }
            }
            return result;


        }

        public void update(){
            if(splitted && children.size() < maxChildrentSize){
                splitted = false;
                a = null;
                b = null;
                c = null;
                d = null;
            }
        }

        public void removeAllNotAbsolutelyStatic() {
            ListIterator<GObject> iterator = children.listIterator();
            while(iterator.hasNext()){
                if(!iterator.next().isAbsolutelyStatic())
                    iterator.remove();
            }
            if(splitted){
                a.removeAllNotAbsolutelyStatic();
                b.removeAllNotAbsolutelyStatic();
                c.removeAllNotAbsolutelyStatic();
                d.removeAllNotAbsolutelyStatic();
            }
        }

        public LinkedList<GObjectPair> getPossibleCollisions() {
            LinkedList<GObjectPair> result = new LinkedList<GObjectPair>();
            GObject GObjectA, GObjectB;
            if(!splitted){
                ListIterator<GObject> iterator1 = children.listIterator();
                ListIterator<GObject> iterator2;

                while(iterator1.hasNext()){
                    if((GObjectA = iterator1.next()) instanceof GObject && ((GObject)GObjectA).getPhysicsObj().getPhysicsType() != PhysicsType.NOTPHYSICAL){
                        iterator2 = children.listIterator(iterator1.nextIndex());
                        while(iterator2.hasNext()){
                            if((GObjectB = iterator2.next()) instanceof GObject && ((GObject)GObjectB).getPhysicsObj().getPhysicsType() != PhysicsType.NOTPHYSICAL){
                                result.add(new GObjectPair((GObject)GObjectA, (GObject)GObjectB));
                            }
                        }
                    }
                }
            }else{
                result.addAll(a.getPossibleCollisions());
                result.addAll(b.getPossibleCollisions());
                result.addAll(c.getPossibleCollisions());
                result.addAll(d.getPossibleCollisions());
            }
            return result;
        }


        public void renderShape(ShapeRenderer sr){
            if(splitted){
                a.renderShape(sr);
                b.renderShape(sr);
                c.renderShape(sr);
                d.renderShape(sr);
            }else{
                aabb.render(sr);
            }

        }
    }

}
