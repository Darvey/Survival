package sample;

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

        Level L = new Level(3,4);
        for(int i=0;i < 3;i++){
            for(int j =0;j< 4;j++){
                root.getChildren().add(L.getTile(i,j).getImage());
            }
        }

        ImageView img = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/shroom.png"));
        img.setImage(imagePath);

        img.setVisible(true);
        img.setTranslateY(64);
        img.setTranslateX(64);

        root.getChildren().add(img);
        img.setTranslateZ(0);

        // Init primaryStage //
        Scene home = new Scene(root, 600, 400, true);

        // Creation joueur
        EntityPlayer p = new EntityPlayer("Bernard",0,0,0,0,0);

        // Ajout joueur au group
        root.getChildren().add(p.getImage());

        // init controles du joueur
        Controller cont = new Controller(home,p);

        primaryStage.setTitle("Survival");
        primaryStage.setScene(home);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
