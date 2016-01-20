package sample;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class Inventory {

    private int maxItem;                                // nombre d'item maximum
    private int maxWeight;                              // poid maximum supporté
    private float weight;                               // poid total de l'inventaire

    private int nbrBoxOnX = 4;                          // nombre de cases en X
    private int nbrBoxOnY = 2;                          // nombre de cases en Y

    private HashMap<String,Item> itemMap;               // liste d'items
    private HashMap<String,Position> posMap;            // lie un item avec une position dans l'inventaire
    private HashMap<String,Label> labelMap;             // liste des labels contenant le nombre de chaque item

    private boolean gridMat[][];                        // représente la présence d'un element sur la grille
    private GridPane grid;                              // gridPane pour le placement des items sur la scene
    private BorderPane bp;
    private Scene scene;                                // scene pour contenir les differents node
    private Stage stage;                                // fenetre de l'inventaire

    protected ImageView closeView;                      // node du bouton de fermeture
    protected Image closeImage;                         // image du bouton de fermeture
    protected final Font font;                          // pixel police

    protected Item shortcuts[];                         // tableau de racourcis

    /**
    * Constructor
    */
    public Inventory() {

        font = Font.loadFont(Inventory.class.getResource("slkscr.ttf").toExternalForm(), 20);
        shortcuts = new Item[10];

        this.itemMap = new HashMap<String, Item>();
        this.posMap = new HashMap<String, Position>();
        this.labelMap = new HashMap<String,Label>();

        this.setMaxSize(20);
        this.initGrid();

        this.bp = new BorderPane();
        this.bp.setPadding(new Insets(10, 10, 10, 10));

        this.closeView = new ImageView();
        this.closeImage = new Image(Main.class.getResourceAsStream("../img/button.png"));
        this.closeView.setImage(closeImage);
        this.bp.setTop(closeView);
        this.bp.setCenter(grid);

        this.scene = new Scene(bp, 200, 200);
        this.stage = new Stage();
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setScene(scene);

        this.setBtnAction();

        // ------- DEBUT test affichage nombre d'item ---------//

        // final Font f = Font.loadFont(Inventory.class.getResource("slkscr.ttf").toExternalForm(), 20);
        // this.labelMap.put("key",new Label("22"));
        // this.labelMap.get("key").setFont(f);
        // this.bp.setBottom(labelMap.get("key"));


        // ------- FIN test affichage nombre d'item ---------//


    }

    /**
     *  Défini l'action sur les différents boutons de l'inventaire
     */
    public void setBtnAction(){
        closeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                display();
            }
        });
    }
    /**
     * Display the Inventory on screen
     */
    public void display()
    {
        System.out.print(stage.isShowing());
        if(stage.isShowing())
        {
            stage.close();
            System.out.println("CLOSE INVENTAIRE");
        }else
        {
            System.out.println("OPEN INVENTAIRE");
            stage.show();
        }
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
            // modification label
            this.labelMap.get(item.getName()).setText(Integer.toString(
                    itemMap.get(item.getName()).getNbr()));
            this.weight = this.weight + item.getWeight();
        }
        else
        {
            this.itemMap.put(item.name,item);
            // ajout label
            this.labelMap.put(item.name,new Label(Integer.toString(item.getNbr())));
            this.labelMap.get(item.getName()).setFont(font);
            // recherche une place dans l'inventaire
            Position p = searchFreeBox();
            // ajout dans le tableau d'association "item/Position"
            this.posMap.put(item.getName(),p);
            // ajout de l'item à la grille
            this.grid.add(item.getImage(),p.getX(),p.getY());
            this.grid.add(labelMap.get(item.getName()),p.getX(),p.getY());
            this.weight = this.weight + item.getWeight();
        }
    }
    /**
     * Supprime de la liste les items dont l'attribut "nbr" est à 0
     */
    public void refreshItemList()
    {
        for(Map.Entry<String,Item> entry : itemMap.entrySet())
        {
            if(entry.getValue().getNbr() == 0)
            {
                // supprime l'item
                this.itemMap.remove(entry.getKey());
                // supprime le label
                this.labelMap.get(entry.getKey()).setVisible(false);
                this.labelMap.remove(entry.getKey());
                // libère la place sur la grille d'affichage
                int x = posMap.get(entry.getKey()).getX();
                int y = posMap.get(entry.getKey()).getY();
                this.gridMat[x][y] = false;
                // supprime sa position
                this.posMap.remove(entry.getKey());
            }
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

    /**
     * Set a shortcut
     * @param n represent the number of [0-9] of the shortcuts
     * @param item the item for add on the shortcut
     */
    public void setShortcut(int n,Item item){
        shortcuts[n] = itemMap.get(item.getName());
        System.out.println(shortcuts[n].getName());
    }

    public void useShortcut(int n){
        shortcuts[n].setNbr(shortcuts[n].getNbr()-1);
        System.out.println("item "+itemMap.get(shortcuts[n].getName()).getName()+" =" +itemMap.get(shortcuts[n].getName()).getNbr());
        refreshItemList();
    }
}
