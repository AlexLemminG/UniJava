package basicobjects.actions;

import basicobjects.Cont;
import basicobjects.GObject;
import geometry.P2d;

/**
 * Created by Lemming on 29.07.2014.
 */
public abstract class SingleAction extends Action{

    public void act(double dt){
        act();
        dead = true;
    }

    public abstract void act();

    public static class AddAction extends SingleAction{
        Action action;

        @Override
        public void act() {
            owner.addAction(action);
        }

        public AddAction(Action action) {
            this.action = action;
        }
    }

    public static class SetParent extends SingleAction{
        Cont parent;

        public SetParent(Cont parent) {
            this.parent = parent;

            if(parent == null){
                try {
                    throw new Exception("parent is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void act() {
            owner.setParent(parent);
        }
    }

    public static class SetGlobalPos extends SingleAction{
        P2d pos;

        protected SetGlobalPos(P2d pos) {
            this.pos = pos;
            if(pos == null){
                try {
                    throw new Exception("parent is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void act() {
            GObject o = (GObject)owner;
            o.setGlobalPos(pos);
        }
    }
}
