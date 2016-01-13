package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyListener;

public class Controller {

    public Controller(Scene h){
        Scene home = h;
        System.out.println("enterControllerClass");
        home.setOnKeyPressed(keyListener);
    }

    final EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(javafx.scene.input.KeyEvent e){
            switch(e.getCode()) {
                case Z :
                    System.out.println("Z");
                    break;
                case Q :
                    System.out.println("Q");
                    break;
                case S :
                    System.out.println("S");
                    break;
                case D :
                    System.out.println("D");
                    break;
                default:
                    return;

            }
        }
    };



}



