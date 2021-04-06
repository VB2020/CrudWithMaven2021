package controller;

import view.ForConsole;
import view.MainMenu;

import java.util.Scanner;

public class MainController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            //Show view for main menu
            MainMenu.startM();
            String response = sc.next();
            switch (response) {
                case "1":
                    isExit = true;
                    RegionController.startM();
                    break;
                case "2":
                    UserController.startM();
                    isExit = true;
                    break;
                case "3":
                    isExit = true;
                    PostController.startM();
                    break;
                case "4":
                    isExit = true;
                    break;
                default:
                    System.out.println(ForConsole.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
        sc.close();
    }
}
