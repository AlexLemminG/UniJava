package screens;

import render.ProjectionMatrix;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 18.11.2014.
 */
public class BasicScreen extends JFrame{
    public BasicCanvas panel;
    public BasicScreen() throws HeadlessException {
        super();
        panel = new BasicCanvas();
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setSize(500, 500);
        setFocusable(true);
        requestFocus();


        setVisible(true);


    }


    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
//        ProjectionMatrix.defaultDx = width;
        ProjectionMatrix.defaultDy = -height;
//        System.out.print(getContentPane().getSize());
    }
}
