package sample;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class Inventory {

    protected EntityPlayer player;
    protected boolean isOpen = false;                   // est-ce que l'inventaire est ouvert ou non
    private int maxItem;                                // nombre d'item maximum
    private int maxWeight;                              // poid maximum supporté
    private float weight;                               // poid total de l'inventaire

    private int nbrBoxOnX = 4;                          // nombre de cases en X
    private int nbrBoxOnY = 2;                          // nombre de cases en Y

    private ImageView boxMap[];                         // Images des box vides
    private Label infosItem;                           // Label pour stocker la description d'un item

    private HashMap<String,Item> itemMap;               // liste d'items
    private HashMap<String,Position> posMap;            // lie un item avec une position dans l'inventaire
    protected HashMap<String,Label> labelMap;             // liste des labels contenant le nombre de chaque item

    private Group group;

    private boolean gridMat[][];                        // représente la présence d'un element sur la grille
    private Scene scene;                                // scene pour contenir les differents node
    private Stage stage;                                // fenetre de l'inventaire

    private ImageView closeView;                      // node du bouton de fermeture
    private Image closeImage;                         // image du bouton de fermeture
    private final Font font;                          // pixel police

    private Color fontColor = Color.rgb(242,242,242,0.8);
    private Color sceneColor = Color.rgb(178,148,112);

    private Item shortcuts[];                         // tableau de racourcis

    /**
    * Constructor
    */
    public Inventory(EntityPlayer pPlayer) {

        this.player = pPlayer;
        this.maxItem = 4*2;
        this.setMaxSize(20);
        font = Font.loadFont(Inventory.class.getResource("slkscr.ttf").toExternalForm(), 11);

        shortcuts = new Item[10];

        this.itemMap = new HashMap<String, Item>();
        this.posMap = new HashMap<String, Position>();
        this.labelMap = new HashMap<String,Label>();

        this.group = new Group();

        this.initGrid();


        // BOUTON DE FERMETURE
        this.closeView = new ImageView();
        this.closeImage = new Image(Main.class.getResourceAsStream("../img/item/closeButton.png"));
        this.closeView.setImage(closeImage);
        this.closeView.setTranslateX(2);
        this.closeView.setTranslateY(2);
        this.group.getChildren().add(closeView);

        // PARTIE DESCRIPTION D'ITEM
        this.infosItem = Item.getTxtItemOnclic();
        this.infosItem.setTranslateX(10);
        this.infosItem.setTranslateY(120);
        this.infosItem.setFont(font);
        this.infosItem.setTextFill(fontColor);

        this.group.getChildren().add(infosItem);


        // SCENE ET STAGE
        this.scene = new Scene(group, 152, 200, sceneColor);
        this.stage = new Stage();
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setScene(scene);

        this.setBtnAction();

        System.out.println(player.name);
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
            this.isOpen = false;
            stage.close();
            System.out.println("CLOSE INVENTAIRE");
        }else
        {
            this.isOpen = true;
            System.out.println("OPEN INVENTAIRE");
            stage.show();
        }
    }

    /**
     * add an item into the inventory
     * @param pName name
     * @param pWeigth weigth
     * @param pName name
     */
    //public void addItem(Item item) {
    public void addItem(String pName, float pWeigth, boolean pHaveThumbnail, String pType) {
        Item item = new Item(pName, pWeigth, pHaveThumbnail, pType, this);
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
            // ajout item dans liste
            this.itemMap.put(item.name,item);

            // ajout label dans liste
            this.labelMap.put(item.name,new Label(Integer.toString(item.getNbr())));
            this.labelMap.get(item.getName()).setFont(font);
            this.labelMap.get(item.getName()).setTextFill(fontColor);

            // recherche une place dans l'inventaire
            Position p = searchFreeBox();

            // ajout dans le tableau d'association "item/Position"
            this.posMap.put(item.getName(),p);

            // ajout de la miniature et son label au pane
            itemMap.get(item.getName()).getThumbnail().setTranslateX(p.getX()*37+4);
            itemMap.get(item.getName()).getThumbnail().setTranslateY(p.getY()*37+4+20);
            labelMap.get(item.getName()).setTranslateX(p.getX()*37+5);
            labelMap.get(item.getName()).setTranslateY(p.getY()*37+4+20);

            this.group.getChildren().add(itemMap.get(item.getName()).getThumbnail());
            this.group.getChildren().add(labelMap.get(item.getName()));

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
                // supprime l'item de la liste
                this.itemMap.get(entry.getKey()).getThumbnail().setVisible(false);
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

        for (int y = 0; y < nbrBoxOnY; y++)
        {
            for (int x = 0; x < nbrBoxOnX; x++)
            {
                if (this.gridMat[x][y] == false)
                {
                    this.gridMat[x][y] = true;
                    return p = new Position(x,y);
                }
            }
        }
        return p;
    }

    public void initGrid()
    {

        int cnt = 0;
        this.boxMap = new ImageView[maxItem];

        this.gridMat = new boolean[nbrBoxOnX][nbrBoxOnY];
        for (int i = 0; i < nbrBoxOnX; i++)
        {
            for (int j = 0; j < nbrBoxOnY; j++)
            {
                this.gridMat[i][j] = false;
                boxMap[cnt] = new ImageView();
                boxMap[cnt].setImage(new Image(Main.class.getResourceAsStream("../img/item/emptyItemBox.png")));

                boxMap[cnt].setTranslateX(i*37+2);
                boxMap[cnt].setTranslateY(j*37+2+20);
                group.getChildren().add(boxMap[cnt]);
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
        labelMap.get(shortcuts[n].getName()).setText(Integer.toString(shortcuts[n].getNbr()));
        refreshItemList();
    }
}
