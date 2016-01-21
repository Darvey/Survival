package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //creation du groupe
        Group root = new Group();

        Level L = new Level(15, 10);
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 10; j++){
                root.getChildren().add(L.getTile(i,j).getImage());
            }
        }
        System.out.println(L.getItem(3,3));
        root.getChildren().add(L.getItem(3,3).getImage());

        // Init primaryStage //
        Scene home = new Scene(root, 960, 640, true);

        // Creation joueur
        EntityPlayer p = new EntityPlayer("Bernard",16,100,55,100,12); //s a d c i

        // Ajout joueur au group
        root.getChildren().add(p.getImage());

        // init controles du joueur
        Controller cont = new Controller(home,p,L);

        //création d'un monstre
        Monster monster1 = new Monster("Albert la mouche", 200, 200);
        Monster monster2 = new Monster("Hector à babord", 400, 400);

        // Ajout monstre au group
        root.getChildren().add(monster1.getImage());
        root.getChildren().add(monster2.getImage());

        //les monstres écoutent le joueur
        p.addListener(monster1);
        p.addListener(monster2);

        primaryStage.setTitle("Survival");
        primaryStage.setScene(home);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
