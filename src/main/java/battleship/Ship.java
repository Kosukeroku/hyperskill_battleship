package battleship;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private int length;
    protected int requiredLength;
    private boolean horizontal;
    private List<PositionOnField> parts;

    public Ship(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {

        /*String regex = "^[A-J](10|[1-9])$";

        if (!firstCoordinate.matches(regex) || !secondCoordinate.matches(regex)) {
            throw new IllegalArgumentException("Error! Coordinate must contain only letters A-J and numbers 1-10");
        }

        int xToPassFirst, xToPassSecond;

        if (firstCoordinate.length() > 2) {
            xToPassFirst = Integer.parseInt(firstCoordinate.substring(1));
        } else {
            xToPassFirst = Character.getNumericValue(firstCoordinate.charAt(1));
        }

        if (secondCoordinate.length() > 2) {
            xToPassSecond = Integer.parseInt(secondCoordinate.substring(1));
        } else {
            xToPassSecond = Character.getNumericValue(secondCoordinate.charAt(1));
        }

        PositionOnField firstPos = new PositionOnField(xToPassFirst, firstCoordinate.charAt(0) - 64);
        PositionOnField secondPos = new PositionOnField(xToPassSecond, secondCoordinate.charAt(0) - 64);*/

        PositionOnField firstPos = PositionOnField.inputToPosition(firstCoordinate);
        PositionOnField secondPos = PositionOnField.inputToPosition(secondCoordinate);

        if (firstPos.getX() != secondPos.getX() && firstPos.getY() != secondPos.getY()) {
            throw new IllegalArgumentException("Error! Ship is not on the same line");
        }

        if (firstPos.getX() == secondPos.getX()) {
            this.horizontal = false;
            this.length = Math.abs(firstPos.getY() - secondPos.getY()) + 1;
        } else if (firstPos.getY() == secondPos.getY()) {
            this.horizontal = true;
            this.length = Math.abs(firstPos.getX() - secondPos.getX()) + 1;
        }

            parts = new ArrayList<>();


            if (this.horizontal) {
                int smallest = Math.min(firstPos.getX(), secondPos.getX());
                for (int i = smallest; i < length + smallest; i++) {
                    parts.add(new PositionOnField(i, firstPos.getY()));
                }
            } else {
                int smallest = Math.min(firstPos.getY(), secondPos.getY());
                for (int i = smallest; i < length + smallest; i++) {
                    parts.add(new PositionOnField(firstPos.getX(), i));
                }
            }

        }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRequiredLength(int requiredLength) {
        this.requiredLength = requiredLength;
    }


    public List<PositionOnField> getParts() {
        return parts;
    }

    public void printParts() {
        for (PositionOnField pos : parts) {
            System.out.print(pos + " ");
        }
    }

    public void printInfo() {
        System.out.println("Length: " + this.length);
        System.out.print("Parts: ");
        printParts();
    }
}
