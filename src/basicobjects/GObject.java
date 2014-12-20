package basicobjects;

import game.Game;
import geometry.AABB;
import physics.PhysicsObj;
import physics.PhysicsType;
import geometry.P2d;
import geometry.PolygonShape;
import geometry.Shape;
import render.ShapeRenderer;

import static java.lang.Math.PI;

/**
 * Created by Lemming on 21.07.2014.
 */
public class GObject extends Cont{
    private static double PI2 = Math.PI * 2;
    protected GObject parent;
    public int height = 0;
    private P2d globalPos = new P2d();
    private double globalA;

    protected P2d localPos = new P2d();
    protected double localA ;
    protected P2d localV = new P2d();
    protected P2d localVWanted = new P2d();
    protected double localAV;
    public P2d getLocalPos(){
        return localPos;
    }
    public double getLocalA() {
        return localA;
    }
    public double getGlobalA(){
        if(dirty)
            updateGlobalPosAndAngle();
        return globalA;

//        double result = (localA + parent.getGlobalA()) % PI2;
//        if(result > PI)
//            result -= PI2;
//        else
//            if(result < -PI)
//                result += PI2;
//        return result;
    }
    public P2d getGlobalPos(){
        if(dirty)
            updateGlobalPosAndAngle();
        return globalPos;

//        double a = parent.getGlobalA();
//        return parent.getGlobalPos().plus(localPos.rotate(a));
    }
    private void updateGlobalPosAndAngle(){
        double resultA = (localA + parent.getGlobalA()) % PI2;
        if(resultA > PI)
            resultA -= PI2;
        else
        if(resultA < -PI)
            resultA += PI2;
        globalA = resultA;

        double a = getParent().getGlobalA();
        P2d resultPos = parent.getGlobalPos().plus(localPos.rotate(a));
        globalPos = resultPos;



        dirty = false;
    }
    public void setGlobalPos(P2d pos) {
        setLocalPos(getParent().globalToLocalPos(pos));
    }
    public P2d localToGlobalPos(P2d local){
        double a = getParent().getGlobalA();
        return getParent().localToGlobalPos(local).plus(localPos.rotate(a));
    }
    public P2d globalToLocalPos(P2d global){
        P2d result = new P2d(global);
        globalToLocalPosRec(result);

        return result;

    }
    protected void globalToLocalPosRec(P2d p){
        getParent().globalToLocalPosRec(p);
        p.dec(localPos).rotateThis(-localA);
    }
    public P2d globalVOfLocalPos(P2d localPos){
        return getParent().globalVOfLocalPos(this.localPos).
                inc(localPos.multiply(localAV).rotateThis(PI / 2 + getGlobalA())).
                inc(localV.rotate(getParent().getGlobalA()));
    }
    public P2d getLocalV() {
        return localV;
    }
    public P2d getGlobalV(){
        return globalVOfLocalPos(P2d.ZERO);
    }
    public void setLocalV(P2d localV) {

        this.localV.set(localV);
    }
    public void setGlobalV(P2d globalV) {
        P2d result = globalV.minus(getParent().globalVOfLocalPos(localPos)).rotate(-getParent().getGlobalA());
        setLocalV(result);
    }
    public void setLocalAV(double localAV) {
        this.localAV = localAV;
    }

    public double getLocalAV() {
        return localAV;
    }
    public P2d localToGlobalVector(P2d localVector){
        return localVector.rotate(getGlobalA());
    }
    public P2d globalToLocalVector(P2d globalVector){
        return globalVector.rotate(-getGlobalA());
    }
    public AABB getAABB(){
        if(this instanceof GObject) {
            return ((GObject)this).getShape().getAABB();
        }else return getGlobalPos().getAABB();
    }
    public int getZ() {
        return getParent().getZ() + height;
    }
    public void setGlobalA(double a) {
        setLocalA(a - getParent().getGlobalA());
    }
    public void setLocalA(double localA) {
        dirty = true;

        double result = localA % PI2;
        if(result > PI)
            result -= PI2;
        if(result < -PI)
            result += PI2;
        this.localA = result;
    }
    public void setLocalPos(P2d pos){
        dirty = true;
        this.localPos = pos;
    }
    public void setParent(GObject parent) {
        if(getParent() == parent)
            return;
        if(getParent() == null) {
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


            P2d pos = getGlobalPos();
            double a = getGlobalA();

            this.parent.children.remove(this);
            this.parent = parent;
            parent.children.add(this);

            setGlobalPos(pos);
            setGlobalA(a);


        }
    }
    public GObject getParent() {
        return this.parent;
    }
    public PhysicsObj getPhysicsObj() {
        return physicsObj;
    }

    PhysicsObj physicsObj = new PhysicsObj(this, 1, PhysicsType.NOTPHYSICAL);


    public void setNotPhysical() {
        physicsObj.setPhysicsType(PhysicsType.NOTPHYSICAL);
    }

    public double getSelfMass() {
        if(physicsObj != null)
            return physicsObj.getSelfMass();
        else
            return 0;
    }


//    Sprite sprite;
    private Shape shape;

    public GObject(GObject parent, Shape shape) {
        this(parent);
        this.shape = shape;
    }

    public GObject(GObject parent){
        super(parent);
        setParent(parent);
        shape = new P2d();
    }

    public void setMass(double mass) {
        physicsObj.setMass(mass);
    }

    public Shape getShape(){
        shape.setA(getGlobalA());
        shape.setPos(getGlobalPos());
        return shape;
    }

//    public void setSprite(Sprite sprite){
//        this.sprite = sprite;
//    }
//    public Sprite getSprite(){
//        return sprite;
//    }


    private void setOrigin(double x, double y){
//        if(sprite != null)sprite.setOrigin(x, y);
        if(shape != null)
            if(shape instanceof PolygonShape)
                ((PolygonShape)shape).setOriginXY(x, y);

    }

//    public void render(SpriteBatch batch){
//        if(sprite == null) {
//            return;
//        }
//        sprite.setRotation(getGlobalA() * MathUtils.radiansToDegrees);
//        P2d pos = getGlobalPos().minus(new P2d(sprite.getOriginX(), sprite.getOriginY()));
//        sprite.setPosition(pos.x, pos.y);
//        sprite.draw(batch);
//
//
//        super.render(batch);
//    }

    public void update(double dt){
        super.update(dt);
        getLocalPos().inc(getLocalV().multiply(dt));
        setLocalA(getLocalA() + getLocalAV() * dt);
    }

    public void setShape(Shape shape) {

        this.shape = shape;

    }

    public void renderShape(ShapeRenderer sr) {
        double color = 1;
        sr.setColor(color, color, color, 0);
        getShape().render(sr);

        if((Boolean) Game.settings.get("render V")){
            getGlobalV().renderAt(sr, getGlobalPos());
        }
        for(Cont child : getChildren()){
            if(child instanceof GObject){
                ((GObject)child).renderShape(sr);
            }
        }
    }


    public boolean isAbsolutelyStatic() {
        return physicsObj.isStatic();
    }
}
