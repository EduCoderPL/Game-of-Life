package Visualisation;

import Classes.Animal;
import Classes.Vector2d;
import World.SteppeAndJungleMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapSimulation implements ActionListener, KeyListener {
    public int delay;
    public SteppeAndJungleMap map;
    public int grassSpawnedInEachDay;
    public int startNumOfAnimals;


    public JFrame frame;
    public RenderPanel renderPanel;
    public PlotRenderPanel plotRenderPanel;
    public Timer timer;
    public boolean paused;

    public Animal signedAnimal = null;

    public MapSimulation(SteppeAndJungleMap map, int delay, int startNumOfAnimals, int grassSpawnedInEachDay){
        this.map = map;
        this.delay = delay;
        this.startNumOfAnimals = startNumOfAnimals;
        this.grassSpawnedInEachDay = grassSpawnedInEachDay;

        timer = new Timer(delay, this);

        frame = new JFrame("Evolution Simulator");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        renderPanel = new RenderPanel(map, this);
        renderPanel.setSize(new Dimension(1, 1));

        plotRenderPanel = new PlotRenderPanel(map, this);
        plotRenderPanel.setSize(1, 1);

        frame.add(renderPanel);
        frame.add(plotRenderPanel);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE)
                    paused = !paused;
                if(paused == true)
                    timer.stop();
                else
                    timer.start();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                Vector2d mousePosition = new Vector2d(
                        Math.round((e.getX() - renderPanel.getLocation().x) * renderPanel.map.width/renderPanel.getWidth()),
                        Math.round((e.getY() - renderPanel.getLocation().y - 38) * (renderPanel.map.height)/(renderPanel.getHeight() - 38)));


                if (signedAnimal != null) {
                    signedAnimal.sign();
                }
                try {
                    signedAnimal = (Animal) map.objectAt(mousePosition);
                    signedAnimal.sign();
                }
                catch (Exception xd){
                    signedAnimal = null;
                }

                plotRenderPanel.repaint();
                renderPanel.repaint();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        paused = false;

    }

    public void startSimulation() {

        for (int i = 0; i < startNumOfAnimals; i++) {
            map.addAnimalOnRandomField();
            map.placeAnimalToRandomFieldInJungle();
        }
        timer.start();

    }

    @Override
    //It will executed when timer finished counting
    public void actionPerformed(ActionEvent e) {

        plotRenderPanel.repaint();
        renderPanel.repaint();

        map.removeDeadAnimals();
        map.moveRandomAllAnimals();
        map.eating();
        map.nextDay();
        map.copulation();
        for (int i = 0; i < grassSpawnedInEachDay / 2; i++) {
            map.spawnGrass();
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE){
            paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}