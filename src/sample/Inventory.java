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
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Inventory {

    private int maxItem;                                // nombre d'item maximum
    private int maxWeight;                              // poid maximum supporté
    private float weight;                               // poid total de l'inventaire

    private int nbrBoxOnX = 4;                          // nombre de cases en X
    private int nbrBoxOnY = 2;                          // nombre de cases en Y

    private HashMap<String,Item> itemMap;               // liste d'items
    private HashMap<String,Position> posMap;            // lie un item avec une position dans l'inventaire

    private boolean gridMat[][];                        // représente la présence d'un element sur la grille
    private GridPane grid;                              // gridPane pour le placement des items sur la scene
    private BorderPane bp;
    private Scene scene;                                // scene pour contenir les differents node
    private Stage stage;                                // fenetre de l'inventaire

    protected ImageView closeView;                      // node du bouton de fermeture
    protected Image closeImage;                         // image du bouton de fermeture
    
    /**
    * Constructor
    */
    public Inventory()
    {
        this.itemMap = new HashMap<String,Item>();
        this.posMap = new HashMap<String,Position>();

        this.setMaxSize(20);
        this.initGrid();

        this.bp = new BorderPane();
        this.bp.setPadding(new Insets(10,10,10,10));

        this.closeView = new ImageView();
        this.closeImage = new Image(Main.class.getResourceAsStream("../img/button.png"));
        this.closeView.setImage(closeImage);
        this.bp.setTop(closeView);
        this.bp.setCenter(grid);

        this.scene = new Scene(bp, 200, 200);
        this.stage = new Stage();
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setScene(scene);
    }

    /**
     * Display the Inventory on screen
     */
    public void display()
    {
        stage.show();
    }

    /**
     * add an item into the inventory
     * @param item to add
     */
    public void addItem(Item item) {
        // Si l'objet est déjà présent dans l'inventaire
        if (this.itemMap.containsKey(item.getName()))
        {
            // ajout du nombre d'item récupérés au nombre d'item déjà présent dans l'inventaire
            this.itemMap.get(item.getName()).setNbr(
                    itemMap.get(item.getName()).getNbr() + item.getNbr()
            );
            this.weight = this.weight + item.getWeight();
        }
        else
        {
            this.itemMap.put(item.name,item);
            // recherche une place dans l'inventaire
            Position p = searchFreeBox();
            // ajout dans le tableau d'association "item/Position"
            this.posMap.put(item.getName(),p);
            // ajout de l'item à la grille
            this.grid.add(item.getImage(),p.getY(),p.getX());
            this.weight = this.weight + item.getWeight();
        }
    }
    /**
     *    delete an item of the inventory
     *    @param item to delete
     */
    public void deleteItem(Item item)
    {
        this.itemMap.remove(item.getName());
    }
    /**
     *   @return the quantity of an item included in the inventory
     */
    public int getItemQuantity(Item item)
    {
        return this.itemMap.get(item.getName()).getNbr();
    }
    /**
     *   set the maximum size of the inventory
     */
    public void setMaxSize(int maxItem)
    {
        this.maxItem = maxItem;
    }
    /**
     *  @return postition de la première case libre dans l'inventaire
     */
    public Position searchFreeBox()
    {
        Position p = null;

        for (int i = 0; i < nbrBoxOnX; i++)
        {
            for (int j = 0; j < nbrBoxOnY; j++)
            {
                if (this.gridMat[i][j] == false)
                {
                    this.gridMat[i][j] = true;
                    return p = new Position(i,j);
                }
            }
        }
        return p;
    }

    public void initGrid()
    {
        this.grid = new GridPane();
        this.grid.setPadding(new Insets(32, 32, 32, 32));
        this.grid.setHgap(4);
        this.grid.setVgap(4);

        this.gridMat = new boolean[nbrBoxOnX][nbrBoxOnY];
        for (int i = 0; i < nbrBoxOnX; i++)
        {
            for (int j = 0; j < nbrBoxOnY; j++)
            {
                this.gridMat[i][j] = false;
            }
        }
    }
}
