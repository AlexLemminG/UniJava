package geometry;

/**
 * Created by Lemming on 26.07.2014.
 */
public class Distance {


    public static DistanceInf pointToPoint(P2d a, P2d b){
        double dist = (double)Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
        P2d wayToPushA = new P2d(b.x - a.x, b.y - a.y);
        return new DistanceInf(dist, wayToPushA);
    }

    public static DistanceInf pointToCircle(P2d p, Circle c){
        DistanceInf result = pointToPoint(p, c.getPos());
        if(result.distance == 0)
            return new DistanceInf(- c.radius, new P2d(0, c.radius));
        else {
            result.distance -= c.radius;

            result.wayToPushA.multiplyThis(result.distance / result.wayToPushA.length());
            return result;
        }
    }

    public static DistanceInf circleToCircle(Circle a, Circle b){
        DistanceInf result = pointToPoint(a.pos, b.pos);
        if(result.distance == 0)
            return new DistanceInf(- a.radius - b.radius, new P2d(0, a.radius + b.radius));
        else {
            result.distance -= a.radius + b.radius;

            result.wayToPushA.multiplyThis(result.distance / result.wayToPushA.length());
            return result;
        }
    }

    public static DistanceInf pointToSegment(P2d p, Segment s){
        double t;

        P2d diff = p.minus(s.start);
        double dLSq = diff.lengthSq();

        t = diff.dot(s.localDirection);

        if(t <= 0){
            t = 0;
        }else{
            if(t > s.lengthSq){
                t = 1;
                diff.dec(s.localDirection);
            }else{
                t /= s.lengthSq;
                diff = diff.dec(s.localDirection.multiply(t));
            }

        }



        return new DistanceInf(diff.length(), diff.inverseThis());
    }

    public static DistanceInf circleToSegment(Circle c, Segment s){
        DistanceInf result = pointToSegment(c.pos, s);
        if(result.distance == 0)
            return new DistanceInf(- c.radius, new P2d(0, c.radius));
        else {
            result.distance -= c.radius;

            result.wayToPushA.multiplyThis(result.distance / result.wayToPushA.length());
            return result;
        }
    }

    public static DistanceInf pointToPolygonShape(P2d p, PolygonShape ps){
        DistanceInf result = new DistanceInf(Double.POSITIVE_INFINITY, new P2d());
        DistanceInf t;
        P2d[] shape = ps.getWorldP2d();
        Segment segment = new Segment(0, 0, 0, 0);

        for(int i = 0; i < shape.length; i++){
            segment.set(shape[i], shape[(i + 1) % shape.length]);
            t = pointToSegment(p, segment);
            result = result.distance < t.distance ? result : t;
        }

        if(ps.contains(p)){
            result.distance = -result.distance;
        }
        return result;
    }

    public static DistanceInf circleToPolygonShape(Circle c, PolygonShape ps){
        DistanceInf result = pointToPolygonShape(c.getPos(), ps);
        if(result.distance == 0)
            return new DistanceInf(- c.radius, new P2d(0, c.radius));
        else {
            if(result.distance < 0)
                result.wayToPushA.increaseLengthThis(c.radius);
            else
                result.wayToPushA.increaseLengthThis(-c.radius);

            result.distance -= c.radius;
            return result;
        }

    }


    public static DistanceInf polygonShapeToPolygonShape(PolygonShape a, PolygonShape b){
        double resultDistance = Double.NEGATIVE_INFINITY;
        boolean aFound = false;
        boolean bFound = false;
        P2d globalPointOfA = null;
        P2d globalPointOfB = null;
        P2d separatingAxisNormal = null;

        for(int i = 0; i < a.size; i++){
            double distance;
            P2d normal = a.getGlobalNormal(i);

            P2d support = b.getSupportPoint(normal.inverse());

            distance = normal.dot(support.minus(a.getGlobalVertex(i)));

            if(distance > resultDistance){
                resultDistance = distance;
                bFound = true;
                aFound = false;
                separatingAxisNormal = normal;
                globalPointOfB = support;


                P2d a1 = a.getGlobalVertex(i);
                P2d a2 = a.getGlobalVertex(i + 1);
                P2d dir = a2.minus(a1).normalizeThis();
                globalPointOfA = a1.plus(dir.multiplyThis(dir.dot(support.minus(a1))));
            }
        }


        for(int i = 0; i < b.size; i++){
            double distance;
            P2d normal = b.getGlobalNormal(i);

            P2d support = a.getSupportPoint(normal.inverse());

            distance = normal.dot(support.minus(b.getGlobalVertex(i)));

            if(distance > resultDistance){
                resultDistance = distance;
                bFound = false;
                aFound = true;
                separatingAxisNormal = normal;
                globalPointOfA = support;

                P2d a1 = b.getGlobalVertex(i);
                P2d a2 = b.getGlobalVertex(i + 1);
                P2d dir = a2.minus(a1).normalizeThis();
                globalPointOfB = a1.plus(dir.multiplyThis(dir.dot(support.minus(a1))));
            }
        }

        if(aFound){
            return new DistanceInf(resultDistance, separatingAxisNormal, globalPointOfA, globalPointOfB);  // возможно с минусом
        }else{
            return new DistanceInf(resultDistance, separatingAxisNormal.inverseThis(), globalPointOfA, globalPointOfB);  // возможно без минуса
        }




    }





    public static DistanceInf shapeToShape(Shape a, Shape b) throws Exception {
        if(a instanceof P2d){
            if(b instanceof P2d)            return pointToPoint((P2d)a, (P2d)b);
            if(b instanceof Circle)         return pointToCircle((P2d)a, (Circle)b);
            if(b instanceof Segment)        return pointToSegment((P2d)a, (Segment)b);
            if(b instanceof PolygonShape)   return pointToPolygonShape((P2d)a, (PolygonShape)b);
            throw(new Exception("Wrong shape type"));
        }
        if(a instanceof Circle){
            if(b instanceof P2d)            return pointToCircle((P2d)b, (Circle)a).invertThis();
            if(b instanceof Circle)         return circleToCircle((Circle) a, (Circle) b);
            if(b instanceof Segment)        return circleToSegment((Circle) a, (Segment) b);
            if(b instanceof PolygonShape)   return circleToPolygonShape((Circle) a, (PolygonShape) b);
            throw(new Exception("Wrong shape type"));
        }
        if(a instanceof Segment){
            if(b instanceof P2d)            return pointToSegment((P2d) b, (Segment) a).invertThis();
            if(b instanceof Circle)         return circleToSegment((Circle) b, (Segment) a).invertThis();
            if(b instanceof Segment)        return new DistanceInf(0, P2d.ZERO);                                    //too lazy to implement
            if(b instanceof PolygonShape)   return new DistanceInf(0, P2d.ZERO);                                    //too lazy to implement
            throw(new Exception("Wrong shape type"));
        }
        if(a instanceof PolygonShape){
            if(b instanceof P2d)            return pointToPolygonShape((P2d) b, (PolygonShape) a).invertThis();
            if(b instanceof Circle)         return circleToPolygonShape((Circle) b, (PolygonShape) a).invertThis();
            if(b instanceof Segment)        return new DistanceInf(0, P2d.ZERO);                                    //too lazy to implement
            if(b instanceof PolygonShape)   return polygonShapeToPolygonShape((PolygonShape) a, (PolygonShape) b);                                  // NOT too lazy to implement
            throw(new Exception("Wrong shape type"));
        }
        throw(new Exception("Wrong shape type"));

    }


}
