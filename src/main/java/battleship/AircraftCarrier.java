package battleship;

public class AircraftCarrier extends Ship {

    public AircraftCarrier(String firstCoordinate, String secondCoordinate) throws IllegalArgumentException {
        super(firstCoordinate, secondCoordinate);
        this.requiredLength = 5;

        if (super.getLength() != this.requiredLength) {
            throw new IllegalArgumentException("Error! Wrong length of the Aircraft Carrier!");
        }
    }


}
