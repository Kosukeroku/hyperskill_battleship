package battleship;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private GameField gameField1;
    private GameField gameField2;

    public UserInterface(Scanner scanner, GameField gameField1, GameField gameField2) {
        this.scanner = scanner;
        this.gameField1 = gameField1;
        this.gameField2 = gameField2;
    }

    public void start() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        System.out.println("Player 1, place your ships on the game field!");
        this.gameField1.printField(false);

        placeAShip(AircraftCarrier.class, "Aircraft Carrier", 5, this.gameField1);
        placeAShip(Battleship.class, "Battleship", 4, this.gameField1);
        placeAShip(Submarine.class, "Submarine", 3, this.gameField1);
        placeAShip(Cruiser.class, "Cruiser", 3, this.gameField1);
        placeAShip(Destroyer.class, "Destroyer", 2, this.gameField1);

        passTurn();

        System.out.println("Player 2, place your ships on the game field!");
        this.gameField2.printField(false);

        placeAShip(AircraftCarrier.class, "Aircraft Carrier", 5, this.gameField2);
        placeAShip(Battleship.class, "Battleship", 4, this.gameField2);
        placeAShip(Submarine.class, "Submarine", 3, this.gameField2);
        placeAShip(Cruiser.class, "Cruiser", 3, this.gameField2);
        placeAShip(Destroyer.class, "Destroyer", 2, this.gameField2);

        passTurn();

        while (true) {
            this.gameField2.printField(true);
            this.gameField1.printField(false);


            System.out.println("Player 1, it's your turn!");
            System.out.print("> ");
            String input = scanner.next();
            this.takeAShot(input, this.gameField2);

            if (this.gameField2.getShipsOnField().isEmpty()) {
                System.out.println("You sank the last ship. You won. Congratulations, Player 1!");
                break;
            }

            passTurn();

            this.gameField1.printField(true);
            this.gameField2.printField(false);

            System.out.println("Player 2, it's your turn!");
            System.out.print("> ");
            input = scanner.next();
            this.takeAShot(input, this.gameField1);

            if (this.gameField2.getShipsOnField().isEmpty()) {
                System.out.println("You sank the last ship. You won. Congratulations, Player 1!");
                break;
            }
            
            passTurn();
        }


    }

    private void placeAShip(Class<? extends Ship> shipClass, String name, int length, GameField gameField) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean hasBeenPlaced = false;

        while (!hasBeenPlaced) {

            System.out.println("Enter the coordinates of the " + name + " (" + length + " cells): ");
            System.out.print("> ");

            String inputOne = scanner.next();
            String inputTwo = scanner.next();


            try {
                Ship inputAircraft = shipClass.getDeclaredConstructor(String.class, String.class).newInstance(inputOne, inputTwo);
                gameField.addShipToField(inputAircraft);
                gameField.printField(false);
                hasBeenPlaced = true;
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IllegalArgumentException) {
                    System.out.println(cause.getMessage()); 
                } else {
                    System.out.println("Unexpected error: " + cause.getMessage());
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error creating ship: " + e.getMessage());
            }
        }
    }


    private void takeAShot(String input, GameField gameField) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try {
            PositionOnField inputPosition = PositionOnField.inputToPosition(input);
            int x = inputPosition.getX();
            int y = inputPosition.getY();

            inputPosition = gameField.getCoordinates()[y][x]; 

            if (inputPosition.getState() == StateOfPosition.SHIP) {

                inputPosition.setState(StateOfPosition.HIT);

                Ship hitShip = null;
                for (Ship ship : gameField.getShipsOnField()) {
                    for (PositionOnField shipPart : ship.getParts()) {
                        if (shipPart.getX() == x && shipPart.getY() == y) {
                            hitShip = ship;
                            break;
                        }
                    }
                    if (hitShip != null) break;
                }

                boolean isSunk = false;
                if (hitShip != null) {
                    isSunk = true;
                    for (PositionOnField part : hitShip.getParts()) {
                        PositionOnField actualPart = gameField.getCoordinates()[part.getY()][part.getX()];
                        if (actualPart.getState() == StateOfPosition.SHIP) {
                            isSunk = false;
                            break;
                        }
                    }
                }

                if (hitShip != null && isSunk) {
                    System.out.println("You sank a ship!");
                    gameField.getShipsOnField().remove(hitShip);
                } else {
                    System.out.println("You hit a ship!");  
                }

            } else {
                if (inputPosition.getState() == StateOfPosition.HIT) {
                        System.out.println("You've already hit this!");
                    } else {
                        inputPosition.setState(StateOfPosition.MISS);
                        System.out.println("You missed!");
                    }
                }
            }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearScreen() {
        System.out.print(".\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n");
    }

    private void passTurn() {
        System.out.println("Press Enter to pass the move to another player!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearScreen();
    }
}
