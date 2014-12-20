package basicobjects;

import physics.PhysicsType;

/**
 * Created by Lemming on 29.07.2014.
 */
public abstract class Checker {

    public static boolean isOutOfParent(GObject child){
        if(child.parent instanceof World)
            return true;
        if((!(child instanceof GObject) || ((GObject)child).getPhysicsObj().getPhysicsType().equals(PhysicsType.NOTPHYSICAL)
                || !(child.parent instanceof GObject) || !((GObject)child.parent).getPhysicsObj().getPhysicsType().equals(PhysicsType.PLATFORM)))
            return false;
        else return  !((GObject)child.parent).getShape().localContains(child.getLocalPos());
    }

    public abstract boolean check(Cont owner);

    public static class IsPhysicalChecker extends Checker{
        @Override
        public boolean check(Cont owner) {
            return owner instanceof GObject && ((GObject) owner).getPhysicsObj().getPhysicsType() == PhysicsType.PHYSICAL;
        }
    }

    public static class IsPlatformChecker extends Checker{
        @Override
        public boolean check(Cont owner) {
            return owner instanceof World || (owner instanceof GObject && ((GObject) owner).getPhysicsObj().getPhysicsType() == PhysicsType.PLATFORM);
        }
    }
}
