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
import java.awt.event.KeyEvent;
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
    private double timeSpeed = 1;


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

        world.update(0.05 * timeSpeed);
//        sr.projection.setScale(sr.projection.getScale() + 0.001);
//        sr.setPosition(a.getGlobalPos().minus(new P2d(300, 300)));
        sr.setDimentions(screen.getWidth(), screen.getHeight());
        sr.setPosition(a.getGlobalPos());
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

        sr.setDimentions(screen.getWidth(), screen.getHeight());
        sr.setGraphics(g);
        sr.setPosition(a.getGlobalPos());

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
        sr.projection.setScale(1);


        initSettings();


        world = new World();
        screen.panel.addMouseListener(new InputAll());


        PolygonShape shapeA = new PolygonShape(-40, 10, -40, -10, 0, 0);
        shapeA.setOriginPos(new P2d(-20,0));
        a = new GObject(world, shapeA);
        a.setLocalA(Math.PI / 2);
        a.setMass(10000);
        a.setGlobalPos(new P2d(190, 230));
        a.getPhysicsObj().setPhysicsType(PhysicsType.PHYSICAL);


        input = new PlayerControl(a){
            @Override
            public void applyControl() {
                super.applyControl();
                if(isKeyDown(KeyEvent.VK_E)){
                    timeSpeed *= 1.1;
                }
                if(isKeyDown(KeyEvent.VK_Q)){
                    timeSpeed /= 1.1;
                }
            }
        };
        screen.panel.addKeyListener(input);
    }


    public void initSettings(){
        settings = new HashMap<String, Object>();

        settings.put("render V", false);
    }
}
