package battleship;

public enum StateOfPosition {
    NO_SHIP("~"),
    SHIP("0"),
    HIT("X"),
    MISS("M"),
    LABEL("label");


    private String code;

    StateOfPosition(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
