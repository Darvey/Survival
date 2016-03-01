package sample;

/**
 * Item
 * peu aussi représenter un groupe d'items identiques du moment que l'attribut 'nbr' dépasse 1.
 */


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.Objects;

abstract class Item {



    //protected static Label txtItemOnclic = new Label();

    /** général */
    protected String name;
    protected boolean onMap;

    /** on map */
    protected SpriteSheet sprite;
    protected Tile tile;

    /** on inventory */
    protected Image thumbnail;
    protected String description;           // description de l'item
    protected float weight;                 // poid de l'item
    protected int nbr;                      // nombre de cet Item présent dans le groupe
    protected String type;                  // type de l'item (food / drink...)
    protected String family;                // famille de l'item (champignon / herbe / médicament / potion...)

    protected Inventory inventory;          // inventaire qui contient l'item
    protected int index;
    protected int gridPosX;
    protected int gridPosY;


    /**
     * default Constructor
     * @throws SlickException
     */
    public Item() throws SlickException{

    }


    /**
     * Constructor pour la  carte
     * @param name
     * @param tile
     * @throws SlickException
     */
    public Item(String name, Tile tile) throws SlickException{

        this();

        this.onMap = true;

        this.name = name;
        this.sprite = new SpriteSheet("img/tile/earth/object/"+name+".png", 32, 32);
        this.thumbnail = new Image("img/item/thumbnail/"+name+".png");
        this.tile = tile;

        /** ici charge les infos depuis le fichier xml */
        this.nbr = 1;
        this.name = name;
        this.weight = 12;
        if(Objects.equals(this.name, "mushroom")){
            this.description = "Champi!";
        }else {
            this.description = "Description en attente";
        }


    }

    public void render(int posX, int posY){

        this.sprite.draw(posX, posY);
    }

    public Position added(Inventory inventory){

        this.onMap = false;
        this.inventory = inventory;

        Position position;
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                if(this.inventory.getGrid()[i][j].isEmpty){
                    position = this.inventory.getGrid()[i][j].addItem(this);
                    this.gridPosX = position.getX();
                    this.gridPosY = position.getY();
                    return position;
                }
            }
        }
        this.gridPosX = -1;
        this.gridPosY = -1;
        return position = new Position(-1, -1);
    }





    /**
     * Method that returns the name, description, and the weight of an item as a string
     * @return String value
     */
    public String toStringItem()
    {
        return this.name+" : \n"+
                        this.description+" \n"+
                        "poids : "+ this.weight;
    }



    public boolean use(){
        type = this.getType();
        switch(type){
            case "weapon":
                System.out.println("Equippement de l'arme");
                return false;
            case "tool":
                System.out.println("Utilisation de l'outil");
                return false;
            case "consumable":
                System.out.println("Consommation de : "+this.name);
                return true;
                // fonction effet()
                //this.setNbr(this.getNbr()-1);
                //this.inventory.labelMap.get(this.getName()).setText(Integer.toString(this.getNbr()));
                //this.inventory.refreshItemList();
            case "junk":
                System.out.println("Putain ça sert à rien ce truc");
                return false;
            default:
                return false;
        }
    }

    // ********** SETTERS ********** //

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    // ********** GETTERS ********** //

    public String getType(){
        return this.type;
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public float getWeight() {
        return this.weight * this.nbr;
    }

    public int getNbr() {
        return this.nbr;
    }



}
