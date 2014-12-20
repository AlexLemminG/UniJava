package geometry;

import render.ShapeRenderer;

/**
 * Created by Lemming on 21.07.2014.
 */
public class P2d extends Shape{
    public static final P2d ZERO = new P2d();
    public double x, y;


    //  just for shape
//  begin
    @Override
    public void setPos(P2d pos) {
        set(pos);
    }

    @Override
    public P2d getPos() {
        return this;
    }

    @Override
    public void setA(double angleRad) {}

    @Override
    public double getA() {return 0;}

    @Override
    public boolean contains(P2d p) {
        return this.equals(p);
    }

    @Override
    public boolean localContains(P2d localPos) {
        return localPos.equals(ZERO);
    }

    @Override
    public AABB getAABB() {
        return new AABB(this, this);
    }

    //  end;
    
    public P2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public P2d() {
        x = 0;
        y = 0;
    }

    @Override
    public String toString() {
        return "P2d : [x = " + x + "; y = " + y + ";]";
    }

    public P2d(P2d p){
        x = p.x;
        y = p.y;
    }

    public double length(){
        return (double) Math.sqrt(x * x + y * y);
    }

    public P2d plus(P2d p){
        return new P2d(x + p.x, y + p.y);
    }

    public P2d minus(P2d p) {
        return new P2d(x - p.x, y - p.y);
    }

    public P2d multiply(double t){
        return new P2d(x * t, y * t);
    }

    public P2d rotate(double a) {
        double sin = Math.sin(a);
        double cos = Math.cos(a);
        return new P2d((double)(x * cos - y * sin),(double) (x * sin + y * cos));
    }
    public boolean equals(P2d p){
        return p.x == x && p.y == y;
    }
    public boolean equals(double x, double y){
        return this.x == x && this.y == y;
    }
    public P2d copy(){
        return new P2d(x, y);
    }

    public P2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public P2d set(P2d p){
        x = p.x;
        y = p.y;
        return this;
    }

    public P2d inc(P2d p){
        x += p.x;
        y += p.y;
        return this;
    }
    public P2d dec(P2d p){
        x -= p.x;
        y -= p.y;
        return this;
    }


    public P2d normalize(){
        double l = length();
        if(l == 0){
            return new P2d(this);
        }
        return new P2d(this.multiply(1 / l));
    }

    public static P2d atDirection(double angleRad) {

        return new P2d((double)Math.cos(angleRad), (double)Math.sin(angleRad));
    }

    public P2d rotateThis(double angleRad) {
        double sin = Math.sin(angleRad);
        double cos = Math.cos(angleRad);
        double tx = (double)(x * cos - y * sin);
        double ty = (double)(x * sin + y * cos);
        x = tx;
        y = ty;
        return this;
    }

    public void renderAt(ShapeRenderer sr, P2d start){
        sr.line(start.x, start.y, x + start.x, y + start.y);
    }

    public P2d multiplyThis(double a) {
        x *= a;
        y *= a;
        return this;
    }

    public P2d rotate90() {
        return new P2d(- y, x);
    }

    public double dot(P2d a){
        return x * a.x + y * a.y;
    }

    public double lengthSq() {
        return x * x + y * y;
    }

    @Override
    public void render(ShapeRenderer sr) {
        sr.circle(x, y, 1);
    }

    public P2d inverseThis() {
        return set(-x, -y);
    }

    public P2d normalizeThis() {
        double l = length();
        if(l == 0)
            return this;
        return this.multiplyThis(1 / l);
    }

    public double distanceSqTo(P2d pos) {
        return this.minus(pos).lengthSq();
    }

    public double distanceTo(P2d pos) {
        return this.minus(pos).length();
    }

    public P2d increaseLength(double increase){
        return this.normalize().multiplyThis(increase).inc(this);
    }

    public P2d increaseLengthThis(double increase){
        double length = length();
        return normalizeThis().multiplyThis(length + increase);
    }

    public P2d inverse() {
        return new P2d(-x, -y);
    }
}
