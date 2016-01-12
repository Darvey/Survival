package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Cretation de la tile
        Tile T = new Tile(64,64,100,100,1,2);

        //creation du groupe
        Group root = new Group();

        //ajout de la tile à la scene
        root.getChildren().add(T.getImage());

        // Init primaryStage
        Scene home = new Scene(root,600,400);
        primaryStage.setTitle("Survival");
        primaryStage.setScene(home);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
