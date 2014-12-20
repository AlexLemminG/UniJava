package basicobjects;


import basicobjects.actions.Action;
import events.Event;
import events.eventListeners.EventListener;
import geometry.AABB;
import geometry.P2d;
import render.ShapeRenderer;

import java.util.LinkedList;
import java.util.ListIterator;

import static java.lang.Math.*;



/**
 * Created by Lemming on 20.07.2014.
 */
public class Cont implements Comparable<Cont>{
    private static double PI2 = Math.PI * 2;
    transient protected World world;
    transient protected Cont parent;
    protected boolean dirty = true;

    LinkedList<Cont> children = new LinkedList<Cont>();
    private LinkedList<Event> events = new LinkedList<Event>();
    private LinkedList<Action> actions = new LinkedList<Action>();
    private LinkedList<Action> justAddedActions = new LinkedList<Action>();
    private LinkedList<EventListener> eventListeners = new LinkedList<EventListener>();



    private static int nextID = 1;
    public final int ID;





    //TODO redo and copyto gobject
    public void update(double dt){
        dirty = true;
        for(Cont c : children){
            c.update(dt);
        }
    }


    public World getWorld() {
        return world;
    }


    private Cont(){
        ID = nextID++;
    }

    public void setParent(Cont parent) {
        if(this.parent == parent)
            return;
        if(this.parent == null) {
            this.parent = parent;
            world = parent.getWorld();
            parent.children.add(this);
            world.addToAllChildren(this);
        }else {
            if(parent.getWorld() != getWorld()) {
                world.removeFromAllChildren(this);
                world = parent.getWorld();
                world.addToAllChildren(this);
            }


            this.parent.children.remove(this);
            this.parent = parent;
            parent.children.add(this);



        }
    }

    public Cont(Cont parent) {
        this();
        setParent(parent);
    }




    public void addEvent(Event event) {
        events.add(event);
    }

    public void addAction(Action action){
        action.setOwner(this);
        justAddedActions.add(action);
    }

    public LinkedList<Event> collectThisAndChildrenEvents(){
        LinkedList<Event> result = new LinkedList<Event>();
        result.addAll(events);
        for(Cont child : children)
            result.addAll(child.collectThisAndChildrenEvents());
        return result;
    }

    public void removeThisAndChildrenEvents(){
        events.clear();
        for(Cont child : children){
            child.removeThisAndChildrenEvents();
        }
    }


    public void handleEvent(Event e){
        for(EventListener eventListener : eventListeners){
            eventListener.handleEvent(e);
        }
    }

    public void handleAllEvents(){
        while(!events.isEmpty()){
            handleEvent(events.getFirst());
            events.removeFirst();
        }
        for(Cont child : children){
            child.handleAllEvents();
        }
    }

    public void addEventListener(EventListener listener){
        listener.setOwner(this);
        eventListeners.add(listener);
    }


    public Cont getParent() {
        return parent;
    }






    public void addChild(Cont c){
        if(c != null){
            c.setParent(this);
        }

    }

    public void removeChild(Cont c){
        children.remove(c);
    }

    public LinkedList<Cont> getChildren(){
        return children;
    }
    public LinkedList<Cont> getAllChildren(){
        LinkedList<Cont> result = new LinkedList<Cont>(children);
        for(Cont c : children)
            result.addAll(c.getAllChildren());
        return result;
    }

    public void applyControl(){
        for(Cont c : children)
            c.applyControl();
    }

//    public void render(SpriteBatch batch){
//        for(Cont c : children)
//            c.render(batch);
//    }





    public boolean removeAction(Action actionToRemove) {
        return actions.remove(actionToRemove);
    }

    public LinkedList<Action> getAllThisAndChildrenActions(){
        LinkedList<Action> actions = new LinkedList<Action>(this.actions);
        for(Cont child : children)
            actions.addAll(child.getAllThisAndChildrenActions());
        return actions;
    }


    public void executeAllActions(double dt){
        actions.addAll(justAddedActions);
        justAddedActions.clear();
        ListIterator<Action> iterator = actions.listIterator();
        Action action;

        while(iterator.hasNext()){
            action = iterator.next();
            if(action.isDead())
                iterator.remove();
            else
                action.act(dt);
        }
    }

    public boolean hasParent(Cont parent){
        return this.parent != null && (this.parent == parent || this.parent.hasParent(parent));
    }




    public LinkedList<Cont> getAllThisAndChildren(Checker cheker) {
        LinkedList<Cont> result = new LinkedList<Cont>();
        if(cheker.check(this)){
            result.add(this);
        }
        for(Cont child : children){
            if(cheker.check(child))
                result.add(child);
            result.addAll(child.getAllThisAndChildren(cheker));
        }
        return result;
    }


    @Override
    public int compareTo(Cont o) {
        return Integer.compare(ID, o.ID);
    }

}
