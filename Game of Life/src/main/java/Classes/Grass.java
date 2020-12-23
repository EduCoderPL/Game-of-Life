package Classes;

import Interfaces.IMapElement;
import Interfaces.IPositionChangeObserver;

import java.awt.*;

public class Grass implements IMapElement {

    Vector2d position;

    public Vector2d getPosition(){
        return this.position;
    }


    public Grass(Vector2d position){
        this.position = position;
    }


    public boolean isMovable(){
        return false;
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        return;
    }


    public Color toColor() {
        return new Color(25, 118, 0);
    }

    @Override
    public void sign() {
        return;
    }


}
