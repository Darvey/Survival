package sample;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui gère l'inventaire, son affichage et
 * l'affichage des items/cellules qu'il contient
 *
 * ******* TODO *******
 * -drag and drop sur les items
 */
public class Inventory {

    /** image de l'inventaire vide */
    private Image image;

    /** image de la sélection */
    private Image selectedCellImage;

    /** est-ce qu'on affichage l'inventaire ou non */
    protected boolean isDisplayed;

    /** est-ce qu'un item est sélectionné ? */
    protected boolean isSelected;

    /** liste des items */
    private List<Item> itemList;
    private InventoryCell[][] grid;

    /** marge de gauche */
    private int OFFSET_X = 13;
    /** marge du haut */
    private int OFFSET_Y = 5;
    /** espace entre chaque case */
    private int PADDING = 5;

    /** position de l'inventaire */
    private int posX = 150;
    private int posY = 150;

    /** position de la selection */
    private int selectionPosX;
    private int selectionPosY;


    /**
     * default Constructor
     * @throws SlickException
     */
    public Inventory() throws SlickException{
        this(null);
    }


    /**
    * Constructor
    */
    public Inventory(EntityPlayer player) throws SlickException {

        this.image = new Image("img/emptyInventory.png");
        this.selectedCellImage = new Image("img/selectedCell.png");
        this.isDisplayed = false;
        this.isSelected = false;
        this.itemList = new ArrayList<>();

        initGrid();
    }


    /**
     * initialisation de la grille de l'inventaire
     */
    public void initGrid(){
        this.grid = new InventoryCell[4][2];

        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                if(i == 2 && j == 1) {
                    /** la case de 2:1 */
                    this.grid[i][j] = new InventoryCell(true, 64, 32, i, j, false);
                }else if(i == 3 && j == 1){
                    /** la case empiétée par celle de 2:1 */
                    this.grid[i][j] = new InventoryCell(true, 0, 0, i, j, true);
                }else{
                    /** les cases de 1:1 */
                    this.grid[i][j] = new InventoryCell(true, 32, 32, i, j, false);
                }

            }
        }
    }


    /**
     * renvoie la grille de l'inventaire
     * @return : tableau 2D de cellule de grille
     */
    public InventoryCell[][] getGrid(){

        return this.grid;
    }


    /**
     * rendu de l'inventaire et des items qu'il contient
     * @param g : slick graphics
     */
    public void render(Graphics g){

        if(this.isDisplayed) {

            /** on affichage l'inventaire */
            //this.image.draw(this.posX, this.posY);
            g.drawImage(this.image, this.posX, (float) this.posY);


            /** on affichage les items */
            if(itemList.size() > 0) {
                for (int i = 0; i < itemList.size(); i++){
                    itemList.get(i).thumbnail.draw(
                            this.posX + OFFSET_X + ((32 + PADDING) * itemList.get(i).gridPosX),
                            this.posY + OFFSET_Y + ((32 + PADDING) * itemList.get(i).gridPosY)
                    );
                }
            }

            if(this.isSelected){
                this.selectedCellImage.draw(
                        this.posX + OFFSET_X + ((32 + PADDING) * this.selectionPosX - 1),
                        this.posY + OFFSET_Y + ((32 + PADDING) * this.selectionPosY - 1)
                );
            }
        }else{
            /** enlève la selection de l'item */
            this.isSelected = false;
        }
    }


    /**
     * ajoute une item à l'inventaire
     * @param item : un item provenant de la carte (pour l'instant)
     */
    public void addItem(Item item) {

        /** on ajoute à la liste d'item */
        this.itemList.add(item);
        /** puis depuis Item pour initialiser sa position dans l'inventaire, l'enlever de la carte ... */
        item.added(this);
    }


    /**
     * affiche la description de l'item sur lequel on clique
     * @param x : position x de la souris
     * @param y : position y de la souris
     */
    public void onClick(int x, int y){

        /** si l'inventaire est bien affiché (ouvert) */
        if(this.isDisplayed) {

            /** calcul de la position dans la grille d'item par rapport à la position de la souris */
            int colIndex = ((x - this.posX - this.OFFSET_X) / (32 + this.PADDING));
            int modIndex = ((y - this.posY - this.OFFSET_Y) / (32 + this.PADDING)) % 4;
            int index = colIndex + (modIndex * 4);
            Item item = this.getItemByGridIndex(index);

            /** index dans la liste */
            int indexOnList = itemList.indexOf(item);

            /** TODO : ici il faudra vérifier si on clique bien dans l'inventaire (? < x < ? && ? < y < ?) */

            /** on évite les "outOfBound" */
            if ((indexOnList < itemList.size()) && (indexOnList >= 0)) {

                /** on affiche la description */
                System.out.println(this.getItemByGridIndex(index).getDescription());
                /** on signale que l'item est selectionné */
                this.isSelected = true;
                this.selectionPosX = colIndex;
                this.selectionPosY = modIndex;

            } else {

                System.out.println("Y-a rien à cette position");
            }
        }
    }


    /**
     * utilise ou non l'item sur lequel on double-clique
     * @param x : position x de la souris
     * @param y : position y de la souris
     */
    public void onDoubleClick(int x, int y){

        /** si l'inventaire est bien affiché (ouvert) */
        if(this.isDisplayed){

            /** calcul de la position dans la grille d'item par rapport à la position de la souris */
            int colIndex = ((x - this.posX - this.OFFSET_X) / (32 + this.PADDING));
            int modIndex = ((y - this.posY - this.OFFSET_Y) / (32 + this.PADDING)) % 4;
            int index = colIndex + (modIndex * 4);
            Item item = this.getItemByGridIndex(index);

            /** index dans la liste */
            int indexOnList = itemList.indexOf(item);

            /** TODO : ici il faudra vérifier si on clique bien dans l'inventaire (? < x < ? && ? < y < ?) */

            /** on évite les "outOfBound" */
            if( ( indexOnList < itemList.size() ) && ( indexOnList >= 0 ) && ( colIndex < 4 ) ) {

                /** on utilise l'item, et on a en retour le fait qu'il soit consumable ou non => remove */
                if(item.use()) {
                    /** enlève l'item de la liste */
                    itemList.remove(itemList.indexOf(item));
                    /** libère l'espace dans la grille */
                    this.getGrid()[item.gridPosX][item.gridPosY].isEmpty = true;
                    /** enlève la selection de l'item */
                    this.isSelected = false;
                }

            }else{

                System.out.println("Y-a rien à cette position");

            }
        }
    }


    /**
     * retour l'item en fonction de l'index de la grille : 0, 1, 2, 3 // 4, 5, 6, 7
     * @param index : index dans la grille
     * @return : item
     */
    public Item getItemByGridIndex(int index){

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++) {
                if (this.getGrid()[i][j].getIndex() == index) {
                    return this.getGrid()[i][j].getItem();
                }
            }
        }
        return null;
    }
}
