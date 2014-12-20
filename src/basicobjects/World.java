package basicobjects;

import physics.CollisionInf;
import physics.PhysicsType;
import geometry.AABB;
import geometry.P2d;
import geometry.WorldShape;

import java.util.LinkedList;

import static java.lang.Math.*;

/**
 * Created by Lemming on 21.07.2014.
 */
public class World extends GObject{
    public QuadTree quadTree;
    public int numberOfPositionIterations = 10;


    public LinkedList<Cont> getAllChildren() {
        return allChildren;
    }

    public void addToAllChildren(Cont child){
        allChildren.add(child);
//        quadTree.add(child);
        child.world = this;
    }
    public void removeFromAllChildren(Cont child) {
        child.world = null;
        allChildren.remove(child);
        if(child instanceof GObject)
            quadTree.remove((GObject)child);

    }

    private LinkedList<Cont> allChildren = new LinkedList<Cont>();



    @Override
    public World getWorld() {
        return this;
    }


    @Override
    public void setGlobalPos(P2d pos) {
        this.localPos = pos;
    }

    @Override
    public void setParent(Cont parent) {
        if(parent == null)
            return;
        else
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public P2d globalVOfLocalPos(P2d localPos) {
        return localPos.rotate(PI / 2).multiply(localAV).inc(getLocalV());
    }

    public LinkedList<GObject> getIntersection(AABB intersector, int z) {
        return quadTree.getIntersection(intersector, z);
    }

        @Override
    public P2d getGlobalV() {
        return getLocalV();
    }

    @Override
    public void setLocalV(P2d localV) {
        super.setLocalV(localV);
    }

    @Override
    public void setGlobalV(P2d globalV) {
        super.setLocalV(globalV);
    }

    @Override
    protected void globalToLocalPosRec(P2d p) {

    }

    @Override
    public P2d globalToLocalPos(P2d global) {
        return global;
    }

    @Override
    public P2d localToGlobalPos(P2d local) {
        return local;
    }

    public World() {
        super(null);
        quadTree = new QuadTree(new AABB(-1500, -1500, 1500, 1500));
        setShape(new WorldShape());
        physicsObj.setPhysicsType(PhysicsType.PLATFORM);
    }

    @Override
    public double getGlobalA() {
        return localA;
    }

    @Override
    public P2d getGlobalPos(){
        return getLocalPos();
    }

    public LinkedList<CollisionInf> getCollisionInf() throws Exception {
        LinkedList<GObjectPair> possibleCollisions = quadTree.getPossibleCollisions();

        LinkedList<CollisionInf> result = new LinkedList<CollisionInf>();

        for(GObjectPair pair : possibleCollisions){
            result.add(new CollisionInf(pair.a.getPhysicsObj(), pair.b.getPhysicsObj()));
        }

        return result;
    }

    @Override
    public void update(double dt) {

//        handleAllEvents();
//        removeThisAndChildrenEvents();
        LinkedList<Cont> children = getAllChildren();


        double deltaTime = dt / numberOfPositionIterations;
        for (int i = 0; i < numberOfPositionIterations; i++) {
            quadTree.removeAllNotAbsolutelyStatic();
            super.update(deltaTime);
            quadTree.addAllNotAbsolutelyStatic(children);
            for(Cont child : children)
                child.executeAllActions(deltaTime);

            try {
                LinkedList<CollisionInf> collisions = getCollisionInf();
                for (CollisionInf c : collisions) {
                    c.solve();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getZ() {
        return 0;
    }

    public LinkedList<Cont> getIntersection(AABB aabb) {
        LinkedList<Cont> result = new LinkedList<Cont>();
        for(int z = 0; z < QuadTree.maxZ; z++){
            result.addAll(quadTree.getIntersection(aabb, z));
        }
        return result;
    }
}
