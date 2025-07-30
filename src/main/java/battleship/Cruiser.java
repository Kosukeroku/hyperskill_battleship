package battleship;

public class Cruiser extends Ship {

    public Cruiser(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {
        super(firstCoordinate, secondCoordinate);
        this.requiredLength = 3;

        if (super.getLength() != this.requiredLength) {
            throw new IllegalArgumentException("Error! Wrong length of the Cruiser!");
        }
    }


}
