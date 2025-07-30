package battleship;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Scanner sc = new Scanner(System.in);
        GameField gameField1 = new GameField();
        GameField gameField2 = new GameField();

        UserInterface ui = new UserInterface(sc, gameField1, gameField2);

        ui.start();


    }
}