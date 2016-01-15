package sample;


import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Inventory {

    /*
        gridTab : martrice de boolean. true pour la présence d'un item
        permet de placer les items sur la grille.
     */
    private int maxSize;
    private int maxWeight;
    private int weight;

    private int nbrBoxOnX = 4;
    private int nbrBoxOnY = 2;

    private TreeMap<String,List<Item>> itemMap;
    private TreeMap<String,Position> assocMap;
    private boolean gridTab[][];

    private Scene scene;
    private Stage stage;
    private GridPane grid;

    protected ImageView closeView;
    protected Image closeImage;

    public Inventory() {

        itemMap = new TreeMap<String,List<Item>>();
        assocMap = new TreeMap<String,Position>();

        this.setMaxSize(20);
        this.initGrid();

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,10,10,10));

        closeView = new ImageView();
        closeImage = new Image(Main.class.getResourceAsStream("../img/button.png"));
        closeView.setImage(closeImage);

        bp.setTop(closeView);

        bp.setCenter(grid);

        scene = new Scene(bp, 200, 200);
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
    }

    /*
        Display the Inventory on screen
     */
    public void display(){
        stage.show();
    }

    /*
        add an item into the inventory
        @param item to add
     */
    public void addItem(Item item) {
        // Si l'objet est déjà présent dans l'inventaire
        if (itemMap.containsKey(item.getName())) {
            List<Item> l = itemMap.get(item.getName());
            l.add(item);
        } else {
            List<Item> l = new ArrayList<>();
            l.add(item);
            itemMap.put(item.name, l);
            // recherche une place dans l'inventaire
            Position p = searchFreeBox();
            // ajout dans le tableau d'association "item/Position"
            assocMap.put(item.getName(),p);
            // ajout de l'item à la grille
            grid.add(l.get(0).getImage(),p.getY(),p.getX());
        }
    }

    /*
        delete an item of the inventory
        @param item to delete
     */
    public void deleteItem(Item item) {
        List<Item> l = itemMap.get(item.getName());
        l.remove(item);
    }
    /*
        returns the quantity of an item included in the inventory
     */
    public int getItemQuantity(Item item) {
        return itemMap.get(item.getName()).size();
    }

    /*
        set the maximum size of the inventory
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /*
        Fonction qui renvoit la Position de la première Case
        de libre dans l'inventaire
     */
    public Position searchFreeBox() {

        Position p = null;

        for (int i = 0; i < nbrBoxOnX; i++) {
            for (int j = 0; j < nbrBoxOnY; j++) {
                if (gridTab[i][j] == false) {
                    gridTab[i][j] = true;
                    return p = new Position(i,j);
                }
            }
        }
        return p;
    }

    public void initGrid(){

        grid = new GridPane();
        grid.setPadding(new Insets(32, 32, 32, 32));
        grid.setHgap(4);
        grid.setVgap(4);

        gridTab = new boolean[nbrBoxOnX][nbrBoxOnY];

        for (int i = 0; i < nbrBoxOnX; i++) {
            for (int j = 0; j < nbrBoxOnY; j++) {
                gridTab[i][j] = false;
            }
        }
    }
}