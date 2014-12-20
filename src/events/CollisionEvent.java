package events;

import basicobjects.GObject;

/**
 * Created by Lemming on 28.07.2014.
 */
public class CollisionEvent extends Event{
    public GObject collided;
    public CollisionEvent(GObject collided) {
        this.collided = collided;
    }
}
