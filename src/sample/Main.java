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

        Group root = new Group();

        Scene home = new Scene(root,600,400);
        primaryStage.setTitle("Survival");
        primaryStage.setScene(home);
        primaryStage.show();

        Tile T = new Tile(64,64,100,100,1,2);

        root.getChildren().add(T.getImage());
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
