package basicobjects;

import geometry.P2d;
import geometry.PolygonShape;

/**
 * Created by Lemming on 30.07.2014.
 */
public class CameraGObject
//        extends GObject
{
//    OrthographicCamera camera;
//
//    public OrthographicCamera getCamera() {
//        return camera;
//    }
//
//    public void setCamera(OrthographicCamera camera) {
//        this.camera = camera;
//    }
//
//    @Override
//    public void update(float dt) {
//        super.update(dt);
//        camera.position.set(getGlobalPos().toVector3());
//        camera.up.set(P2d.atDirection(getGlobalA()).rotate90().toVector3());
//        camera.update();
//    }
//
//    public CameraGObject(GObject parent) {
//        super(parent);
//        camera = new OrthographicCamera();
//        setShape(new PolygonShape(0, 0, 0, 20, -30, 20, -30, -20, 30, -20, 30, 20, 0, 20));
//        setNotPhysical();
//        addEventListener(new InputListener(){
//            @Override
//            public boolean scrolled(int amount) {
//                camera.zoom *= (1 + amount / 10f);
//                return super.scrolled(amount);
//            }
//        });
//    }
//
//    public void resize(int width, int height){
//        camera.viewportWidth = width;
//        camera.viewportHeight = height;
//        setShape(new PolygonShape(-camera.viewportWidth / 2, -camera.viewportHeight / 2,
//                -camera.viewportWidth / 2, camera.viewportHeight / 2,
//                camera.viewportWidth / 2, camera.viewportHeight / 2,
//                camera.viewportWidth / 2, -camera.viewportHeight / 2));
//    }
}
