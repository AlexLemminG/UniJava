package physics;

import basicobjects.Cont;
import basicobjects.GObject;
import game.Game;
import geometry.P2d;

import java.util.LinkedList;

/**
 * Created by Lemming on 30.07.2014.
 */
public class PhysicsUtils {
    public static Cont getHighestPlatformAt(GObject owner){
        P2d pos = owner.getGlobalPos();


        for(int z = owner.getZ(); z >= 0; z--) {
            LinkedList<GObject> allGObject = Game.world.getIntersection(pos.getAABB(), z);
            LinkedList<GObject> allPlatforms = new LinkedList<GObject>();

            for (Cont cont : allGObject) {
                if (cont instanceof GObject && ((GObject) cont).getPhysicsObj().getPhysicsType() == PhysicsType.PLATFORM)
                    allPlatforms.add((GObject) cont);
            }
            LinkedList<GObject> platformsAtPos = new LinkedList<GObject>();

            for (GObject platform : allPlatforms) {
                if ((platform).getShape().contains(pos)) {
                    platformsAtPos.add(platform);
                }
            }


            if(!platformsAtPos.isEmpty())
                return platformsAtPos.getFirst();
        }

        return Game.world;
    }
}
