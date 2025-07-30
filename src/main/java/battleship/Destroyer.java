package battleship;

public class Destroyer extends Ship {

    public Destroyer(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {
        super(firstCoordinate, secondCoordinate);
        this.requiredLength = 2;

        if (super.getLength() != this.requiredLength) {
            throw new IllegalArgumentException("Error! Wrong length of the Destroyer!");
        }
    }


}
