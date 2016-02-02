package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Controller {

    EntityPlayer player;
    Level level;

    public Controller(Scene home,EntityPlayer p,Level level){

        this.player = p;
        this.level = level;
        //Scene home = h;
        System.out.println("enterControllerClass");
        home.setOnKeyPressed(keyPressedListener); //clavier pressed
        home.setOnKeyReleased(keyReleasedListener); //clavier released
        //home.setOnKeyReleased(keyListener);
        home.setOnMousePressed(mouseListener); //souris
        home.setOnScroll(scrollListener); //molette souris (wheel)
        home.setOnMouseMoved(mouseMovedListener);

    }

    private final EventHandler<KeyEvent> keyPressedListener = new EventHandler<KeyEvent>() {

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
                    //System.out.println("Z");
                    player.updateControl(0);
                    break;
                case S :
                    //System.out.println("S");
                    player.updateControl(1);
                    break;
                case Q :
                    //System.out.println("Q");
                    player.updateControl(2);
                    break;
                case D :
                    //System.out.println("D");
                    player.updateControl(3);
                    break;
                case I :
                    player.displayInventory();
                    break;
                case DIGIT1:
                    player.inv.useShortcut(1);
                    break;
                case R :
                    level.action(player);
                    break;
                default:
                    break;
            }
        }
    };

    private final EventHandler<MouseEvent> mouseMovedListener = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent e) {
            player.mouseX = e.getX();
            player.mouseY = e.getY();
            //System.out.println("mouseX : "+e.getX());
        }
    };

    private final EventHandler<KeyEvent> keyReleasedListener = new EventHandler<KeyEvent>() {

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
                    //System.out.println("rZ");
                    player.updateControl(4);
                    break;
                case S :
                    //System.out.println("rS");
                    player.updateControl(5);
                    break;
                case Q :
                    //System.out.println("rQ");
                    player.updateControl(6);
                    break;
                case D :
                    //System.out.println("rD");
                    player.updateControl(7);
                break;
                default:
                    break;
            }
        }
    };

    private final EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>(){
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
                    if(e.getClickCount() == 2){
                        if(player.inv.isOpen){
                            System.out.println("Position double-clic gauche = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                        }
                    }else {
                        System.out.println("Position clic gauche = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    }
                    break;
                case SECONDARY :
                    System.out.println("Position clic droit = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    break;
                case MIDDLE :
                    System.out.println("Position clic molette = (x :" + e.getSceneX() + ", y :" + e.getSceneY() + ")");
                    break;
                default :
                    break;
            }
        }
    };

    private final EventHandler<ScrollEvent> scrollListener = e -> {
        //dans quel sens va la molette
        if(e.getDeltaY() > 0){
            System.out.println("Molette tournée vers l'avant");
        }else if(e.getDeltaY() < 0){
            System.out.println("Molette tournée vers l'arrière");
        }
    };



}



