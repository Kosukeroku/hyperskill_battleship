package battleship;

public class Submarine extends Ship {

    public Submarine(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {
        super(firstCoordinate, secondCoordinate);
        this.requiredLength = 3;

        if (super.getLength() != this.requiredLength) {
            throw new IllegalArgumentException("Error! Wrong length of the Submarine!");
        }
    }


}
