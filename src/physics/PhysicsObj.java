package physics;

import basicobjects.Cont;
import basicobjects.GObject;
import geometry.P2d;
import geometry.Shape;

import java.util.LinkedList;

/**
 * Created by Lemming on 25.07.2014.
 */
public class PhysicsObj {
    GObject owner;

    Shape shape;
    private double mass;
    P2d localV;
    P2d localAV;


    public boolean isStatic() {
        return isStatic;
    }

    boolean isStatic;

    public PhysicsType getPhysicsType() {
        return physicsType;
    }

    public void setPhysicsType(PhysicsType physicsType) {
        this.physicsType = physicsType;
    }

    PhysicsType physicsType;


    public PhysicsObj(GObject owner, double mass, PhysicsType physicsType) {
        this.owner = owner;
        this.setMass(mass);
        this.physicsType = physicsType;
    }

    public Shape getShape() {
        return owner.getShape();
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        double mass = this.mass;
        LinkedList<Cont> children = owner.getAllChildren();
        for(Cont gObject : children){
            if(gObject instanceof GObject){
                mass += ((GObject)gObject).getSelfMass();
            }
        }

        return mass;
    }

    public double getSelfMass() {
                                                                                                                                return mass;
    }

    public void setIsStatic(boolean b) {
        isStatic = b;
    }
}
