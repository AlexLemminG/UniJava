package render;

import game.Game;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Alexander on 22.11.2014.
 */
public class Log {

    private static String s = "";
    private static LinkedList<String> log = new LinkedList<String>();
    public static void print(String s){
        System.out.println(s);
        Log.s = s;
        log.add(s);
        Game.game.screen.setTitle(s);
        Game.game.screen.add(new TextField(s));
    }
}
