package input;

import basicobjects.GObject;
import geometry.P2d;

import java.awt.event.KeyEvent;

/**
 * Created by Alexander on 20.11.2014.
 */
public class PlayerControl extends InputAll {
    int leftButton = KeyEvent.VK_A;
    int rightButton = KeyEvent.VK_D;
    int upButton = KeyEvent.VK_W;
    int downButton = KeyEvent.VK_S;

    private P2d addedLocalV = new P2d();
    private GObject player;

    public PlayerControl(GObject player){
        this.player = player;
    }

    @Override
    public void applyControl(){
        player.setLocalV(player.getLocalV().minus(addedLocalV));

        P2d wantedLocalV = new P2d();


        if(isKeyDown(leftButton)){
            wantedLocalV.inc(new P2d(-1, 0));
        }
        if(isKeyDown(rightButton)){
            wantedLocalV.inc(new P2d(1, 0));
        }
        if(isKeyDown(upButton)){
            wantedLocalV.inc(new P2d(0, 1));
        }
        if(isKeyDown(downButton)){
            wantedLocalV.inc(new P2d(0, -1));
        }
        wantedLocalV.normalizeThis().multiplyThis(100);
        addedLocalV = player.getParent().globalToLocalVector(wantedLocalV);
        player.setLocalV(player.getLocalV().plus(addedLocalV));
    }
}
