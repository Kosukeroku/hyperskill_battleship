package battleship;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final int width = 11;
    private final int height = 11;

    private PositionOnField[][] coordinates;
    private List<Ship> shipsOnField;

    public GameField() {
        shipsOnField = new ArrayList<>();
        coordinates = new PositionOnField[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0) {
                    coordinates[x][y] = new PositionOnField(StateOfPosition.LABEL, x, y);
                } else {
                    coordinates[x][y] = new PositionOnField(StateOfPosition.NO_SHIP, x, y);
                }
            }

        }
    }

    public PositionOnField[][] getCoordinates() {
        return coordinates;
    }

    public List<Ship> getShipsOnField() {
        return shipsOnField;
    }

    public void printField(boolean fogEnabled) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0) { 
                    if (j == 0) {
                        System.out.print("  "); 
                    } else {
                        System.out.print(j + " ");
                    }
                } else if (j == 0) {
                    char c = (char) ('A' + i - 1);
                    System.out.print(c + " ");
                } else { 
                    if (fogEnabled) {
                        if (coordinates[i][j].getState() == StateOfPosition.SHIP) {
                            System.out.print(StateOfPosition.NO_SHIP.getCode() + " ");
                        } else {
                            System.out.print(coordinates[i][j].getState().getCode() + " ");
                        }
                    } else {
                        System.out.print(coordinates[i][j].getState().getCode() + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean hasAdjacent(Ship ship) {
        boolean isAdjacent = false;


        for (PositionOnField position : ship.getParts()) {
            int y = position.getX();
            int x = position.getY();



            if (coordinates[x][y].getState() != StateOfPosition.NO_SHIP) {
                isAdjacent = true;
            };

            if (x + 1 < width) {
                if (coordinates[x + 1][y].getState() != StateOfPosition.NO_SHIP) {
                    isAdjacent = true;
                }
            }

            if (coordinates[x - 1][y].getState() != StateOfPosition.NO_SHIP && x - 1 >= 1) {
                isAdjacent = true;
            }

            if (coordinates[x][y - 1].getState() != StateOfPosition.NO_SHIP && y - 1 >= 1) {
                isAdjacent = true;
            }

            if (y + 1 < height) {
                if (coordinates[x][y + 1].getState() != StateOfPosition.NO_SHIP) {
                    isAdjacent = true;
                }
            }

            if (coordinates[x - 1][y - 1].getState() != StateOfPosition.NO_SHIP && x - 1 >= 1 && y - 1 >= 1) {
                isAdjacent = true;
            }


            if (x + 1 < width && y + 1 < height) {
                if (coordinates[x + 1][y + 1].getState() != StateOfPosition.NO_SHIP ) {
                    isAdjacent = true;
                }
            }


            if (y + 1 < height) {
                if (coordinates[x - 1][y + 1].getState() != StateOfPosition.NO_SHIP && x - 1 >= 1) {
                    isAdjacent = true;
                }
            }

            if (x + 1 < width) {
                if (coordinates[x + 1][y - 1].getState() != StateOfPosition.NO_SHIP && y - 1 >= 1) {
                isAdjacent = true;
            }
        }



        }

        return isAdjacent;
    }

    public void addShipToField(Ship ship) throws IllegalArgumentException {

        if (hasAdjacent(ship)){
            throw new IllegalArgumentException("Error! You placed it too close to another one.");
        }

        shipsOnField.add(ship);

        for (PositionOnField position : ship.getParts()) {
            coordinates[position.getY()][position.getX()].setState(StateOfPosition.SHIP);
        }
    }
}


