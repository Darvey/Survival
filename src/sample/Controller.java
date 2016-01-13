package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;


public class Controller {

    public Controller(Scene h){
        Scene home = h;
        System.out.println("enterControllerClass");
        home.setOnKeyPressed(keyListener); //clavier
        //home.setOnKeyReleased(keyListener);
        home.setOnMousePressed(mouseListener); //souris
        home.setOnScroll(scrollListener); //molette souris (wheel)
    }

    final EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {

        public void handle(KeyEvent e){
            /*
            en jeu :
            Z = déplacer player vers le haut
            Q = déplacer player vers la gauche
            S = déplacer player vers le bas
            D = déplacer player vers la droite
             */
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

    final EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>(){
        /*
        en jeu :
        clic gauche = tir principal
        clic droit = tir secondaire
        clic milieu = tir tertiaire
         */
        public void handle(MouseEvent e){
            //sur quel bouton de la souris on appuie ?
            switch(e.getButton()) {
                case PRIMARY :
                    System.out.println("Position clic gauche = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    break;
                case SECONDARY :
                    System.out.println("Position clic droit = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    break;
                case MIDDLE :
                    System.out.println("Position clic molette = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    break;
                default :
                    return;
            }
        }
    };

    final EventHandler<ScrollEvent> scrollListener = new EventHandler<ScrollEvent>(){
        /*
        en jeu :
        molette avant/arrière = sélection de l'arme
         */

        //détection molette de la souris
        public void handle(ScrollEvent e){
            //dans quel sens va la molette
            if(e.getDeltaY() > 0){
                System.out.println("Molette tournée vers l'avant");
            }else if(e.getDeltaY() < 0){
                System.out.println("Molette tournée vers l'arrière");
            }   
        }
    };



}



