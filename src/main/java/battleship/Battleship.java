package battleship;

public class Battleship extends Ship {

    public Battleship(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {
        super(firstCoordinate, secondCoordinate);
        this.requiredLength = 4;
        if (super.getLength() != this.requiredLength) {
            throw new IllegalArgumentException("Error! Wrong length of the Battleship!");
        }
    }
}
