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
                // Unwrap the actual exception from the reflection call
                Throwable cause = e.getCause();
                if (cause instanceof IllegalArgumentException) {
                    System.out.println(cause.getMessage()); // Show your custom error
                } else {
                    System.out.println("Unexpected error: " + cause.getMessage());
                }
            } catch (IllegalArgumentException e) {
                // Handle other argument issues (e.g., from addShipToField)
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error creating ship: " + e.getMessage());
            }
        }
    }

    /*private void takeAShot(String input) {
        try {
            PositionOnField inputPosition = PositionOnField.inputToPosition(input);
            int x = inputPosition.getX();
            int y = inputPosition.getY();

            // Use actual grid position object
            PositionOnField target = this.gameField.getCoordinates()[y][x];

            if (target.getState() == StateOfPosition.SHIP) {
                target.setState(StateOfPosition.HIT); // Update actual grid position

                // Find ship containing this EXACT grid position object
                Ship hitShip = null;
                for (Ship ship : this.gameField.getShipsOnField()) {
                    // Relies on .equals() working with coordinates
                    if (ship.getParts().contains(target)) {
                        hitShip = ship;
                        break;
                    }
                }

                // Check if sunk (using actual grid states)
                boolean isSunk = true;
                if (hitShip != null) {
                    for (PositionOnField part : hitShip.getParts()) {
                        // Verify: part should be same object as grid position
                        if (part.getState() == StateOfPosition.SHIP) {
                            isSunk = false;
                            break;
                        }
                    }
                }

                // Single message logic
                if (isSunk && hitShip != null) {
                    System.out.println("You sank a ship!");
                    this.gameField.getShipsOnField().remove(hitShip);
                } else {
                    System.out.println("You hit a ship!"); // Only if not sunk
                }
            } else {
                target.setState(StateOfPosition.MISS);
                System.out.println("You missed!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }*/



    private void takeAShot(String input, GameField gameField) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try {
            PositionOnField inputPosition = PositionOnField.inputToPosition(input);
            int x = inputPosition.getX();
            int y = inputPosition.getY();

            inputPosition = gameField.getCoordinates()[y][x];  // Get actual game field position

            if (inputPosition.getState() == StateOfPosition.SHIP) {
                // FIRST: Update the position state
                inputPosition.setState(StateOfPosition.HIT);

                // SECOND: Find the specific ship that was hit
                Ship hitShip = null;
                for (Ship ship : gameField.getShipsOnField()) {
                    for (PositionOnField shipPart : ship.getParts()) {
                        // Compare positions by coordinates, not by object reference
                        if (shipPart.getX() == x && shipPart.getY() == y) {
                            hitShip = ship;
                            break;
                        }
                    }
                    if (hitShip != null) break;
                }

                // THIRD: Check if this hit sank the ship
                boolean isSunk = false;
                if (hitShip != null) {
                    isSunk = true;
                    for (PositionOnField part : hitShip.getParts()) {
                        // Always check state from game field, not ship's copy
                        PositionOnField actualPart = gameField.getCoordinates()[part.getY()][part.getX()];
                        if (actualPart.getState() == StateOfPosition.SHIP) {
                            isSunk = false;
                            break;
                        }
                    }
                }

                // FOURTH: Print appropriate message (ONLY ONE)
                if (hitShip != null && isSunk) {
                    System.out.println("You sank a ship!");
                    gameField.getShipsOnField().remove(hitShip);
                } else {
                    System.out.println("You hit a ship!");  // Only if not sunk
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
