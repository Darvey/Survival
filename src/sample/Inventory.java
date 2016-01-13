package sample;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Inventory {

    private int maxSize;
    private TreeMap<String,List<Item>> itemList;
    private Scene invScene;
    private Stage invStage;
    private GridPane invGrid;

    public Inventory(){
        itemList = new TreeMap<String,List<Item>>();

        invGrid = new GridPane();
        invGrid.setPadding(new Insets(20,20,20,20));
        invGrid.setHgap(5);
        invGrid.setVgap(5);

        invScene = new Scene(invGrid,200,200);
        invStage = new Stage();
        invStage.setScene(invScene);
    }

    /*
        add an item into the invotory
        @param item to add
     */
    public void addItem(Item item){
        // Si l'objet est déjà présent dans l'inventaire
        if(itemList.containsKey(item.getName())){
            List<Item> l = itemList.get(item.getName());
            l.add(item);
        }else{
            List<Item> l = new ArrayList<>();
            l.add(item);
            itemList.put(item.name,l);
            // ajout de l'item à la grille
            invGrid.add(item.getImage(),1,1);
        }
    }

    public void deleteItem(Item item){
        List<Item> l = itemList.get(item.getName());
        l.remove(item);
    }

    /*
        returns the quantity of an item included in the inventory
     */
    public int getQuantity(Item item){
        return itemList.get(item.getName()).size();
    }

    public void display(){
        invStage.show();
    }

}
