package basicobjects.actions;

import basicobjects.Cont;

/**
 * Created by Lemming on 26.07.2014.
 */
public abstract class Action {
    public boolean isDead() {
        return dead;
    }

    boolean dead ;
    protected Cont owner;

    public abstract void act(double dt);

    public void setOwner(Cont owner){
        this.owner = owner;
    }
}
