package physics;

import geometry.Distance;
import geometry.DistanceInf;
import geometry.P2d;

/**
 * Created by Lemming on 25.07.2014.
 */
public class CollisionInf {
    boolean collided;
    double percentPush = 1;

    PhysicsObj objA;
    PhysicsObj objB;

    P2d dPosA = new P2d();
    P2d dPosB = new P2d();

    double dAA;
    double dAB;

    P2d dVA = new P2d();
    P2d dVB = new P2d();

    P2d dAVA = new P2d();
    P2d dAVB = new P2d();

    public void solve(){


        if(collided) {
            if(!dPosA.equals(P2d.ZERO))
                objA.owner.setGlobalPos(objA.owner.getGlobalPos().plus(dPosA.multiplyThis(percentPush)));
            if(!dPosB.equals(P2d.ZERO))
                objB.owner.setGlobalPos(objB.owner.getGlobalPos().plus(dPosB.multiplyThis(percentPush)));

//            objA.owner.addEvent(new CollisionEvent(objB.owner));
//            objB.owner.addEvent(new CollisionEvent(objA.owner));
        }
    }


    public CollisionInf(PhysicsObj objA, PhysicsObj objB) throws Exception {
        if(objA.physicsType == PhysicsType.NOTPHYSICAL || objB.physicsType == PhysicsType.NOTPHYSICAL) return;
        this.objA = objA;
        this.objB = objB;

        if(!objA.getShape().getAABB().intersects(objB.getShape().getAABB()))
            return;
        DistanceInf distanceInf = Distance.shapeToShape(objA.getShape(), objB.getShape());
        if(distanceInf.distance >= 0) return;
        collided = true;

        if(objA.physicsType != objB.physicsType) return;


//        while(objA.isStatic && objA.owner.getParent() instanceof GObject && ((GObject) objA.owner.getParent()).getPhysicsObj().physicsType != PhysicsType.NOTPHYSICAL) {
//            objA = ((GObject) objA.owner.getParent()).getPhysicsObj();
//        }
//
//        while(objB.isStatic && objB.owner.getParent() instanceof GObject && ((GObject) objB.owner.getParent()).getPhysicsObj().physicsType != PhysicsType.NOTPHYSICAL) {
//            objB = ((GObject) objB.owner.getParent()).getPhysicsObj();
//        }

        if(objA.isStatic && objB.isStatic) return;

        if(objA.isStatic) {
            dPosB = distanceInf.wayToPushA.inverse();
            return;
        }
        if(objB.isStatic) {
            dPosA = distanceInf.wayToPushA;
            return;
        }

        double massSumInv;
        if(objA.getMass() > 0 || objB.getMass() > 0)
            massSumInv = 1f / (objA.getMass() + objB.getMass());
        else
            massSumInv = 1;

        dPosA.set(distanceInf.wayToPushA.multiply(objB.getMass() * massSumInv));
        dPosB.set(distanceInf.wayToPushA.inverseThis().multiply(objA.getMass() * massSumInv));
        this.objA = objA;
        this.objB = objB;

    }



}
