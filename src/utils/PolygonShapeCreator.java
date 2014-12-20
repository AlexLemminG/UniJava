package utils;

//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.InputMultiplexer;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.utils.Json;
//import com.lemming.seasong.basicobjects.CameraGObject;
//import com.lemming.seasong.basicobjects.World;
//import com.lemming.seasong.events.eventListeners.InputListener;
import geometry.P2d;
import geometry.PolygonShape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Lemming on 02.08.2014.
 */
public class PolygonShapeCreator
//        extends Game
{
//    PolygonShape polygonShape;
//    LinkedList<PolygonShape> savedPolygons = new LinkedList<PolygonShape>();
//    LinkedList<P2d> points = new LinkedList<P2d>();
//    InputMultiplexer input = new InputMultiplexer();
//    CameraGObject cameraGObject;
//    World world = new World();
//    ShapeRenderer shapeRenderer;
//
//    @Override
//    public void render() {
//        super.render();
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        cameraGObject.update(1f);
//
//        shapeRenderer.setProjectionMatrix(cameraGObject.getCamera().combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//        shapeRenderer.setColor(Color.DARK_GRAY);
//        for(PolygonShape pol : savedPolygons){
//            pol.render(shapeRenderer);
//        }
//
//        for(int i = -200; i <= 200; i++){
//            for(int j = -200; j <= 200; j++){
//                shapeRenderer.line(-10000, i * 50, 10000, i * 50);
//                shapeRenderer.line(j * 50, -10000, j * 50, 10000);
//
//            }
//        }
//        shapeRenderer.setColor(Color.GRAY);
//        shapeRenderer.line(-10000, 0, 10000, 0);
//        shapeRenderer.line(0, -10000, 0, 10000);
//
//
//
//
//
//
//
//
//        if(points.size() > 0){
//            shapeRenderer.setColor(Color.WHITE);
//            for (int i = 1; i < points.size(); i++) {
//                shapeRenderer.line(points.get(i - 1).toVector3(), points.get(i).toVector3());
//            }
//            shapeRenderer.setColor(Color.LIGHT_GRAY);
//            shapeRenderer.line(points.getLast().toVector3(), points.getFirst().toVector3());
//        }
//
//        shapeRenderer.end();
//
//
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        shapeRenderer.dispose();
//        sc.close();
//
//    }
//    Scanner sc;
//
//    @Override
//    public void create() {
//        sc = new Scanner(System.in);
//
//        final Json json = new Json();
//
//        shapeRenderer = new ShapeRenderer();
//        cameraGObject = new CameraGObject(world);
//        Gdx.input.setInputProcessor(input);
//        InputListener listener = new InputListener(){
//            @Override
//            public boolean keyDown(int keycode) {
//                if(keycode == Input.Keys.SPACE){
//                    points.add(new P2d(cameraGObject.getCamera().unproject(getMousePos().toVector3())));
//                }
//                if(keycode == Input.Keys.BACKSPACE){
//                    points.removeLast();
//                }
//                if(keycode == Input.Keys.ENTER){
//                    P2d[] p = new P2d[points.size()];
//                    points.toArray(p);
//                    polygonShape = new PolygonShape(p);
//
//                    String s = json.toJson(polygonShape);
//                    try {
//                        PrintWriter pw = new PrintWriter(new File("files/shapes/" + sc.nextLine() + ".shape"));
//                        pw.println(s);
//                        pw.close();
//                        savedPolygons.add(polygonShape);
//                        points = new LinkedList<P2d>();
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return super.keyDown(keycode);
//            }
//        };
//
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//        cameraGObject.resize(width, height);
//    }
}
