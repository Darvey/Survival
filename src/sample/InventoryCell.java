package sample;

/**
 * Created by Administrateur on 29/02/2016.
 */
public class InventoryCell {

    protected boolean isEmpty;
    private int width;
    private int height;
    private int index;
    private int posX;
    private int posY;
    private static int size = 0;
    protected boolean isInvade;
    private Item item;


    /**
     *
     * @param isEmpty : la cellule est-elle occupée par un item ?
     * @param width : largeur de la cellule (32px ou 64px)
     * @param height : hauteur de la cellule (32px)
     * @param posX : posX dans la grille
     * @param posY : posY dans la grille
     * @param isInvade : est-ce une cellule englobée dans une autre cellule à largeur de 64px ? TODO : devra être calculé
     */
    public InventoryCell(boolean isEmpty, int width, int height, int posX, int posY, boolean isInvade){

        this.isEmpty = isEmpty;
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.isInvade = isInvade;
        this.index = this.size;
        this.size++;
    }


    /**
     * ajoute un item dans la cellule et reinitialise celle-ci
     * @param item : item à ajouter
     * @return : position à laquelle l'item a été ajouté
     */
    public Position addItem(Item item){

        Position position = new Position(this.posX, this.posY);
        this.item = item;
        this.isEmpty = false;
        return position;
    }


    /**
     * renvoie l'index de la cellule
     * @return l'index de la cellule
     */
    public int getIndex(){
        return this.index;
    }


    /**
     * renvoie l'item contenu dans la cellule
     * @return l'item contenu dans la cellule
     */
    public Item getItem(){
        return this.item;
    }


}
