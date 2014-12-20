package geometry;

/**
 * Created by Lemming on 27.07.2014.
 */
public class GeometryUtils {
    public static PolygonShape[] poligonToWallSegments(PolygonShape basis, double width, boolean circled){
        double r = width / 2;
        int size = basis.getSize();
        if(!circled)
            size--;
        PolygonShape[] result = new PolygonShape[size];

        double x1, x2;
        P2d normal = new P2d();
        P2d normal2 = new P2d();
        P2d normalR90 = new P2d();


        P2d a = new P2d() ,b = new P2d(), c = new P2d(), d = new P2d();
        Segment a1, a2;

        P2d[] basisVertex = basis.getWorldP2d();

        a1 = new Segment(basisVertex[basisVertex.length - 1], basisVertex[0]);
        a2 = new Segment(basisVertex[0], basisVertex[1]);


        int k = 0;


        if(!circled){
            x1 = r; x2 = 0;
            normal = a2.getNormal();
            a = basisVertex[k].plus(normal.multiplyThis(x1));
            b = basisVertex[k].minus(normal);
        }else{
            normal = a2.getNormal();
            normalR90 = normal.rotate90();
            normal2 = a1.getNormal();

            x1 = r;
            double t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            a = basisVertex[k].plus(normal.multiplyThis(x1).inc(normalR90.multiplyThis(x2)));
            b = basisVertex[k].minus(normal);
        }



        for(k = 1; k < size; k++){
            a1.set(a2);
            a2.set(basisVertex[k], basisVertex[(k + 1) % basisVertex.length]);

            normal = a2.getNormal();
            normalR90 = normal.rotate90();
            normal2 = a1.getNormal();

            x1 = r;
            double t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            c = basisVertex[k].plus(normal.multiplyThis(x1).inc(normalR90.multiplyThis(x2)));
            d = basisVertex[k].minus(normal);

            result[k - 1] = new PolygonShape(a, b, d, c);
            a = c;
            b = d;

        }

        if(!circled){
            x1 = r; x2 = 0;
            normal = a2.getNormal();
            c = basisVertex[k].plus(normal.multiplyThis(x1));
            d = basisVertex[k].minus(normal);
        }else{
            a1.set(a2);
            a2.set(basisVertex[0], basisVertex[1]);
            normal = a2.getNormal();
            normalR90 = normal.rotate90();
            normal2 = a1.getNormal();

            x1 = r;
            double t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            c = basisVertex[0].plus(normal.multiplyThis(x1).inc(normalR90.multiplyThis(x2)));
            d = basisVertex[0].minus(normal);
        }
        result[k - 1] = new PolygonShape(a, b, d, c);

        return result;


    }

    public static PolygonShape getPolygonWithoutPolygon(PolygonShape polygon, PolygonShape withous){
        P2d[] resultLocal = new P2d[polygon.local.length + withous.local.length + 2];

        for(int i = 0; i < polygon.local.length; i++)
            resultLocal[i] = polygon.local[i].copy();
        resultLocal[polygon.local.length] = polygon.local[0];

        for(int i = 0; i < withous.local.length; i++)
            resultLocal[i + polygon.local.length + 1] = withous.local[i];
        resultLocal[resultLocal.length - 1] = withous.local[0];
        return new PolygonShape(resultLocal);
    }

}
