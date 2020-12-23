package Interfaces;

import Classes.Vector2d;

import java.awt.*;

public interface IMapElement {
    /**
     * standard 2D position of element
     * position param should const every class implements IMapElement
     */
    Vector2d getPosition();


    /**
     * Its
     *
     * @return true if elements will use move function to move, else should return false.
     */
    boolean isMovable();


    void addObserver(IPositionChangeObserver observer);

    Color toColor();


    void sign();
}