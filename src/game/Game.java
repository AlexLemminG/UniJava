package game;

import basicobjects.GObject;
import basicobjects.Wall;
import basicobjects.World;
import geometry.Circle;
import geometry.P2d;
import geometry.PolygonShape;
import input.InputAll;
import input.PlayerControl;
import physics.PhysicsType;
import screens.BasicScreen;
import render.ShapeRenderer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

/**
 * Created by Alexander on 18.11.2014.
 */
public class Game implements Runnable{
    public static Game game;
    public static World world;
    public static HashMap<String, Object> settings;
    public static ShapeRenderer sr;
    public BasicScreen screen;
    public static InputAll input;
    GObject a;


    public static void main(String... args){
        game = new Game();
        new Thread(game).start();
    }

    public Game(){

    }

    @Override
    public void run() {
        init();

        while(true){
            update();


            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
//        sr.projection.addPosition(new P2d(1, 0));
//        sr.projection.addRotation(0.01);
        input.applyControl();
        world.update(0.05);
//        sr.projection.setScale(sr.projection.getScale() + 0.001);

        render();

    }

    public void render(){
        BufferStrategy bs = screen.panel.getBufferStrategy();
        if(bs == null)
        {
            screen.panel.createBufferStrategy(3);
            screen.panel.requestFocus();
            return;
        }


        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());

        sr.setGraphics(g);

        renderWorld();

        g.dispose();
        bs.show();
    }

    private void renderWorld() {
        world.renderShape(sr);
    }



    public void init(){
        screen = new BasicScreen();
        sr = new ShapeRenderer(screen.panel.getGraphics());
        sr.setPosition(new P2d(-300, -300));
        sr.projection.setScale(1);



        initSettings();


        world = new World();
        screen.panel.addMouseListener(new InputAll());
        for(int i = 0; i < 0; i++) {
            GObject o = new GObject(world);
            o.setGlobalPos(new P2d(100 + Math.random() * 200 ,100 +  Math.random() * 200 ));
            o.setMass(10);
            o.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);
//            triangle.setShape(new PolygonShape(-10, -10, 10, -10, 0, 10));
            o.setShape(new Circle(P2d.ZERO, 10));
            o.setLocalV(new P2d((Math.random() - .5) * 100, (Math.random() - .5) * 100));
        }

//        Wall wall = new Wall(world, new PolygonShape(   -200, -200,
//                                            -200, 200,
//                                            200, 200,
//                                            200, -200)
//         , 5,  true);
//        wall.setLocalPos(new P2d(200, 200));


        GObject o = new GObject(world);
        o.setGlobalPos(new P2d(700, 500));
        o.setMass(100);
        o.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);
        o.setShape(new Circle(P2d.ZERO, 10));
        o.setLocalAV(Math.random());


        for(int i = 0; i < 3; i++){
            o = new GObject(o);
            o.setGlobalPos(new P2d(800 + i * 100, 500 ));
            o.setMass(1000);
            o.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);
            o.setShape(new Circle(P2d.ZERO, 10));
            o.setLocalAV(Math.random());
        }


        PolygonShape shapeA = new PolygonShape(-100, 10, -100, -10, 0, 0);
        PolygonShape shapeB = new PolygonShape(-100, 10, -100, -10, 0, 0);
        a = new GObject(world, shapeA);
        GObject b = new GObject(world, shapeB);
        a.setLocalA(Math.PI / 2);
        a.setMass(10000);

        a.setGlobalPos(new P2d(190, 230));
        b.setGlobalPos(new P2d(200, 200));
        a.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);
        b.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);


        input = new PlayerControl(a);
        screen.panel.addKeyListener(input);
    }


    public void initSettings(){
        settings = new HashMap<String, Object>();

        settings.put("render V", false);
    }
}
