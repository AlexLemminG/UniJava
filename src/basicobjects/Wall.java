package basicobjects;

import physics.PhysicsType;
import geometry.GeometryUtils;
import geometry.PolygonShape;

/**
 * Created by Lemming on 27.07.2014.
 */
public class Wall extends GObject{
    public Wall(GObject parent, PolygonShape shape, double width, boolean circled) {
        super(parent);
        height = 0;
        WallSegment wallSegment;
        PolygonShape[] segmentsShape = GeometryUtils.poligonToWallSegments(shape, width, circled);
        for(int i = 0; i < segmentsShape.length; i++){
            wallSegment = new WallSegment(this, segmentsShape[i]);
            wallSegment.setMass(1);
            wallSegment.physicsObj.setIsStatic(true);
            wallSegment.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);
        }
    }




    private static class WallSegment extends GObject{

        public WallSegment(GObject parent, PolygonShape shape) {
            super(parent, shape);
        }
    }
}

