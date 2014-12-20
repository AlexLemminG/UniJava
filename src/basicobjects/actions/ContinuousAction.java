package basicobjects.actions;

import basicobjects.Checker;
import basicobjects.Cont;
import basicobjects.GObject;
import physics.PhysicsUtils;
import geometry.P2d;

/**
 * Created by Lemming on 29.07.2014.
 */
public abstract class ContinuousAction extends Action{
    private static double PI2 = Math.PI;
    private static double PI = Math.PI;

    public abstract void act(double dt);

    public static class MovingAction extends ContinuousAction{

        @Override
        public void act(double dt) {
            GObject o = (GObject)owner;
            o.getLocalPos().inc(o.getLocalV().multiply(dt));
            o.setLocalA(o.getLocalA() + o.getLocalAV() * dt);
        }
    }

    public static class WantedGlobalAngleToReal extends ContinuousAction{
        double maxSpeed = 1000f;
        double wantedAngle = 0;

        public WantedGlobalAngleToReal(double wantedAngle) {
            this.wantedAngle = wantedAngle;
        }

        @Override
        public void act(double dt) {
            GObject o = (GObject)owner;
            double da = (wantedAngle - o.getGlobalA()) % PI2;
            if(da > PI)
                da = PI2 - da;
            else if(da < -PI)
                da = PI2 + da;

            if(Math.abs(da) < 0.00000001f) {
                dead = true;
                return;
            }


            da = Math.min(Math.abs(da) / dt, maxSpeed) * Math.signum(da);
            o.setLocalAV(da);

        }
    }

    public static class WantedGlobalPosToReal extends ContinuousAction{
        double maxSpeed = 100;
        P2d wantedGlobalPos;

        public WantedGlobalPosToReal(P2d wantedGlobalPos) {
            this.wantedGlobalPos = wantedGlobalPos;
        }

        @Override
        public void act(double dt) {
            GObject o = (GObject)owner;
            double distance;
            if((distance = o.getGlobalPos().distanceTo(wantedGlobalPos)) > 0.0001f){
                o.setGlobalV(wantedGlobalPos.minus(o.getGlobalPos()).normalizeThis().multiplyThis(Math.min(maxSpeed, distance / dt)));
            }else{
                o.setLocalV(P2d.ZERO);
                dead = true;
            }
        }
    }

    public static class FollowingAction extends ContinuousAction{
        private Cont followed;
        private WantedGlobalPosToReal wantedGlobalPosToReal;
        private WantedGlobalAngleToReal wantedGlobalAngleToReal;
        boolean added = false;


        public FollowingAction(Cont followed) {
            this.followed = followed;
            wantedGlobalPosToReal = new WantedGlobalPosToReal(P2d.ZERO);
            wantedGlobalAngleToReal = new WantedGlobalAngleToReal(0);
        }

        @Override
        public void act(double dt) {
            GObject o = (GObject)owner;
            GObject f = (GObject)followed;

            if(!added) {
                o.addAction(wantedGlobalPosToReal);
                o.addAction(wantedGlobalAngleToReal);
                added = true;
            }
            wantedGlobalPosToReal.dead = false;
            wantedGlobalAngleToReal.dead = false;
            wantedGlobalPosToReal.wantedGlobalPos = f.getGlobalPos();
            wantedGlobalAngleToReal.wantedAngle = f.getGlobalA();
        }
    }

    public static class CanChangePlatformAction extends ContinuousAction{

        @Override
        public void act(double dt) {
            GObject o = (GObject)owner;
            if(Checker.isOutOfParent(o))
                owner.addAction(new SingleAction.SetParent(PhysicsUtils.getHighestPlatformAt(o)));
        }
    }

}
