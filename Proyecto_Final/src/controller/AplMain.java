package controller;

import controller.Controller;
import view.MainMenuView;

public class AplMain {
    public static void main(String[] args) {
        Controller controller = new Controller();
        new MainMenuView(controller);
    }
}