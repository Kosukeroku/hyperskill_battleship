package battleship;

import java.util.Objects;

public class PositionOnField {
    private StateOfPosition state;
    //private StateOfPosition stateOnFog;
    private int x;
    private int y;


    public PositionOnField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PositionOnField(StateOfPosition state, int x, int y) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public void setState(StateOfPosition state) {
        this.state = state;
    }

    public StateOfPosition getState() {
        return state;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static PositionOnField inputToPosition(String inputCoordinate) throws IllegalArgumentException {

        String regex = "^[A-J](10|[1-9])$";

        if (!inputCoordinate.matches(regex)) {
            throw new IllegalArgumentException("Error! Coordinate must contain only letters A-J and numbers 1-10");
        }

        int xToPass;

        if (inputCoordinate.length() > 2) {
            xToPass = Integer.parseInt(inputCoordinate.substring(1));
        } else {
            xToPass = Character.getNumericValue(inputCoordinate.charAt(1));
        }

        return new PositionOnField(xToPass, inputCoordinate.charAt(0) - 64);
    }

    @Override
    public String toString() {
        return (char) (this.y + 64) + "" + this.x;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PositionOnField that = (PositionOnField) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
