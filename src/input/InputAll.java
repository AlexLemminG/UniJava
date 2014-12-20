package input;

import game.Game;
import geometry.P2d;
import render.Log;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * Created by Alexander on 19.11.2014.
 */
public class InputAll implements KeyListener, MouseListener{
    private HashMap<Integer, Boolean> isDown = new HashMap<Integer, Boolean>();
    private boolean[] isMouseDown = new boolean[6];
    private P2d[] lastPressedWorldP2d = new P2d[6];

    public void applyControl(){}


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        isDown.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isDown.put(e.getKeyCode(), false);
    }

    public boolean isKeyDown(int keycode){
        Boolean b = isDown.get(keycode);
        return (b==null)?false:b;
    }





    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMouseDown[e.getButton()] = true;
        lastPressedWorldP2d[e.getButton()] = Game.sr.projection.screenToWorldP2d(new P2d(e.getX(), e.getY()));
        Log.print(""+lastPressedWorldP2d[e.getButton()]);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMouseDown[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
