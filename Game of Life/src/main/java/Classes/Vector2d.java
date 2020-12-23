package Classes;

public class Vector2d {

    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

    public boolean follows(Vector2d other) {
        if (other == null) {
            return true;
        }

        return this.x >= other.x && this.y >= other.y;
    }


    public boolean precedes(Vector2d other) {
        if (other == null) {
            return false;
        }

        return this.x <= other.x && this.y <= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        if (other == null) {
            return this;
        }

        int newX = this.x > other.x ? this.x : other.x;
        int newY = this.y > other.y ? this.y : other.y;
        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other) {
        if (other == null) {
            return this;
        }

        int newX = this.x < other.x ? this.x : other.x;
        int newY = this.y < other.y ? this.y : other.y;
        return new Vector2d(newX, newY);
    }

    public Vector2d add(Vector2d other) {
        if (other == null) {
            return this;
        }

        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public Vector2d subtract(Vector2d other) {
        if (other == null) {
            return this;
        }

        int newX = this.x - other.x;
        int newY = this.y - other.y;
        return new Vector2d(newX, newY);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }

        Vector2d vector2d = (Vector2d) other;
        return this.x == vector2d.x && this.y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
    }


    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }
}
