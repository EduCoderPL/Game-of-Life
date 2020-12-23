package Classes;

import Enums.MapDirection;
import Enums.MoveDirection;
import Interfaces.IMapElement;
import Interfaces.IPositionChangeObserver;
import Interfaces.IWorldMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection direction;
    private int startEnergy;
    private IWorldMap map;
    private Genes genes;
    public int energy;

    public boolean signed;
    public boolean checkedTopGenom;

    public ArrayList<IPositionChangeObserver> observerlist = new ArrayList<>();

    //statistics
    public int childrenCount;
    public int daysAlive;
    public LinkedList<Animal> kids = new LinkedList<>();

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Animal() {
        this.direction = MapDirection.NORTH;
        genes = new Genes();
        position = new Vector2d(2, 2);
        this.startEnergy = energy;
        this.daysAlive = 0;
        this.childrenCount = 0;
        this.signed = false;
        this.checkedTopGenom = false;
    }

    public Animal(IWorldMap map) {
        this();
        this.map = map;
        this.startEnergy = energy;
        this.daysAlive = 0;
        this.childrenCount = 0;
        this.signed = false;
        this.checkedTopGenom = false;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this(map);
        this.position = initialPosition;
        this.startEnergy = energy;
        this.daysAlive = 0;
        this.childrenCount = 0;
        this.signed = false;
        this.checkedTopGenom = false;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy) {
        this(map, initialPosition);
        this.energy = energy;
        this.startEnergy = energy;
        this.daysAlive = 0;
        this.childrenCount = 0;
        this.signed = false;
        this.checkedTopGenom = false;
    }

    public void sign(){
        signed = !signed;
    }

    public void checkedTopGenom(){
        checkedTopGenom = !checkedTopGenom;
    }

    public void move(MoveDirection d) {

        switch (d) {
            case LEFT:
                this.direction = this.direction.previous();
                return;
            case RIGHT:
                this.direction = this.direction.next();
                return;
            case FORWARD:
                if (map.canMoveTo(position.add(direction.toUnitVector()))) {
                    Vector2d old = new Vector2d(this.getPosition().getX(), this.getPosition().getY());
                    this.position = position.add(direction.toUnitVector());
                    this.positionChanged(old, this.position, this);
                }
                return;
            case BACKWARD:
                if (map.canMoveTo(position.subtract(direction.toUnitVector()))) {
                    Vector2d old = new Vector2d(this.getPosition().getX(), this.getPosition().getY());
                    position = modulo(position.subtract(direction.toUnitVector()));
                    this.positionChanged(old, this.position, this);
                }
                return;

        }
    }

    public Vector2d modulo(Vector2d old){
        return new Vector2d((old.getX() + map.width) % map.width, (old.getY() + map.height) % map.height);
    }

    private void positionChanged(Vector2d old, Vector2d n, Object a) {
        for (IPositionChangeObserver o : observerlist) {
            o.positionChanged(old, n, a);
        }
    }

    public void rotate(){
        Genes gen = new Genes();
        int rotations = gen.returnRandomGen();
        for (int i = 0; i<rotations; i++){
            this.move(MoveDirection.RIGHT);
        }
    }

    public Color toColor() {
        if(signed) return new Color(252, 0, 252);
        if(checkedTopGenom) return new Color(0, 63, 252);

        if(energy == 0) return new Color(5, 0, 0);
        if(energy < 0.2 * startEnergy) return new Color(76, 0, 0);
        if(energy < 1 * startEnergy) return new Color(141, 0, 0);
        if(energy < 2 * startEnergy) return new Color(191, 0, 0);
        if(energy < 4 * startEnergy) return new Color(255, 0, 0);
        return null;
    }

    public void changeEnergy(int i) {
        energy = energy + i;
    }

    public Animal copulation(Animal mother) {

        int childEnergy = (int) (0.25 * mother.energy) + (int) (this.energy * 0.25);
        mother.changeEnergy((int) -(0.25 * mother.energy));
        this.changeEnergy((int) -(this.energy * 0.25));

        Animal child = new Animal(map, mother.getPosition(), childEnergy);
        child.genes = new Genes(this.genes, mother.genes);

        return child;
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observerlist.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observerlist.remove(observer);
    }

    public int numberOfDescendant() {
        int result = 0;
        HashSet<Animal> animalSet = new HashSet<>();
        LinkedList<Animal> queue = (LinkedList) kids.clone();
        while (queue.size() != 0) {
            Animal a = queue.poll();
            animalSet.add(a);
            result += 1;
            for (int i = 0; i < a.kids.size(); i++) {
                if (!animalSet.contains(a.kids.get(i))) {
                    queue.add(a.kids.get(i));
                }
            }
        }
        return result;
    }

    public Genes getGenes() {
        return this.genes;
    }
}
