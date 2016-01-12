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

        //creation du groupe
        Group root = new Group();

        Level L = new Level(3,4);
        for(int i=0;i < 3;i++){
            for(int j =0;j< 4;j++){
                root.getChildren().add(L.getTile(i,j).getImage());
            }
        }

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
