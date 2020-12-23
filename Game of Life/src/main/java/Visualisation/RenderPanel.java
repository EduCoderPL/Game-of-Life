package Visualisation;

import Classes.Animal;
import Classes.Grass;
import World.SteppeAndJungleMap;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    public SteppeAndJungleMap map;
    public MapSimulation simulation;

    public RenderPanel(SteppeAndJungleMap map, MapSimulation simulation) {
        this.map = map;
        this.simulation = simulation;
    }
    public Animal clicked = null;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize((int) (simulation.frame.getWidth() * 0.6), simulation.frame.getHeight() - 38);
        this.setLocation((int) (0.4 * simulation.frame.getWidth()), 0);
        int width = this.getWidth();
        int height = this.getHeight(); //38 is toolbar size
        int widthScale = Math.round(width / map.width);
        int heightScale = height / map.height;

        //draw Steppe
        g.setColor(new Color(170, 224, 103));
        g.fillRect(0, 0, width, height);

        //draw Jungle
        g.setColor(new Color(0, 217, 9));
        g.fillRect(map.getJungleLowerLeft().getX() * widthScale,
                map.getJungleLowerLeft().getY() * heightScale,
                map.jungleWidth * widthScale,
                map.jungleHeight * heightScale);

        //draw Grass
        for (Grass grass : map.getGrass()) {
            g.setColor(grass.toColor());
            int y = map.toNoBoundedPosition(grass.getPosition()).getY() * heightScale;
            int x = map.toNoBoundedPosition(grass.getPosition()).getX() * widthScale;
            g.fillRect(x, y, widthScale, heightScale);
        }
        //draw Animals
        for (Animal a : map.getAnimals()) {
            g.setColor(a.toColor());
            int y = map.toNoBoundedPosition(a.getPosition()).getY() * heightScale;
            int x = map.toNoBoundedPosition(a.getPosition()).getX() * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }



        //draw Pause
        g.setColor(new Color(255, 3, 3, 127));
        g.setFont(new Font( "SansSerif", Font.BOLD, 18 ));
        g.drawString("Press SPACE to pause simulation", 10, 20);


    }




}