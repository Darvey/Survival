package sample;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 *   Description de la classe :
 *
 */

public class Level {

    // ********** ATTRIBUTES ********** //

    protected final String name;
    private final Tile[][] tilesMap;
    private final Item[][] itemMap;
    private int h;
    private int l;
    protected final int width;
    protected final int height;
    private final Random random;

    //variables pour les collisions
    protected Line2D.Double lineUp;
    protected Line2D.Double lineDown;
    protected Line2D.Double lineLeft;
    protected Line2D.Double lineRight;
    protected Rectangle2D.Double collisionArea;

    // ********** CONSTRUCTORS ********** //

    /**
     * Constructeur
     *
     * @param pWidth    largeur de la map
     * @param pHeight   hauteur de la map
     */
    public Level(int pWidth,int pHeight) {

        this.name = "Premier niveau";

        this.width = pWidth;
        this.height = pHeight;
        random = new Random();

        int defInt[][] = new int[this.width][this.height];
        itemMap = new Item[this.width][this.height];

        //matrice de Int
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                defInt[i][j] = i % 3 + 1;
            }
        }

        String levelTile[][] = createLevel();

        // Creation des Tiles avec la matrice de Int

        tilesMap = new Tile[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                //tilesMap[i][j] = new Tile(32, 32, 32 * i, 32 * j, 1, defInt[i][j]);
                tilesMap[i][j] = new Tile(32, 32, 32 * i, 32 * j, 1, levelTile[i][j]);
            }
        }
    }

    /**
     * This methods is launched each time the 'R' key is pressed
     *
     * @param player    the player who performed the action
     */
    public void action(EntityPlayer player)
    {
        int caseX = (int)(player.getImage().getTranslateX()+7)/32;
        int caseY = (int)(player.getImage().getTranslateY()+10)/32;
        if(caseX == 3 && caseY == 3){
            itemMap[3][3].getImage().setTranslateX(0);
            itemMap[3][3].getImage().setTranslateY(0);
            itemMap[3][3].getImage().setVisible(false);
            player.inv.addItem(itemMap[3][3].getName(), itemMap[3][3].getWeight(), true ,itemMap[3][3].getType());
        }
    }

    public boolean[] collision(int nX,int nY, int nWidth, int nHeight, int offset)
    {
        /* ------- OPTIMISATION -------
        pour l'instant on check tous les objets (tile pour l'instant) de la map,
        il faudrait tester uniquement les cases à proximité du joueur
        donc redéfinir un tableau tileArray avec seulement la case sur laquelle on est
        et celles qui entourent le perso
        tilesMap[(int) nX /32][ (int) nY / 32]
        */
        boolean col[] = new boolean[4];
        boolean colUp = false;
        boolean colDown = false;
        boolean colLeft = false;
        boolean colRight = false;

        this.lineUp = new Line2D.Double(nX, nY-offset, nX+nWidth, nY-offset);
        this.lineDown = new Line2D.Double(nX, nY+nHeight+offset, nX+nWidth, nY+nHeight+offset);
        this.lineLeft = new Line2D.Double(nX-offset, nY, nX-offset, nY+nHeight);
        this.lineRight = new Line2D.Double(nX+nWidth+offset, nY, nX+nWidth+offset, nY+nHeight);

        for(Tile tileArray[] : tilesMap){
            for(Tile elem : tileArray){
                if(elem.solid){
                    this.collisionArea = new Rectangle2D.Double(elem.posX+1, elem.posY+1, 30, 30);
                    if(this.lineUp.intersects(this.collisionArea)){
                        colUp = true;
                    }
                    if(this.lineDown.intersects(this.collisionArea)){
                        colDown = true;
                    }
                    if(this.lineLeft.intersects(this.collisionArea)){
                        colLeft = true;
                    }
                    if(this.lineRight.intersects(this.collisionArea)){
                        colRight = true;
                    }
                }
            }
        }
        col[0] = colUp;
        col[1] = colDown;
        col[2] = colLeft;
        col[3] = colRight;

        return col;
    }

    public int getH() {
        return h;
    }

    public int getL() {
        return l;
    }

    public Tile getTile(int x,int y){
        return tilesMap[x][y];
    }

    public Item getItem(int x,int y){
        return itemMap[x][y];
    }

    public String[][] createLevel(){

        int levelMatrice[][][] = new int[this.width][this.height][4];
        String levelTile[][] = new String[this.width][this.height];
        int a;
        int b;
        int c;
        int d;

        for(int i = 0; i < this.width; i++) {
            for(int j = 0; j < this.height; j++) {
                if(i != 0 && j == 0){

                    if(levelMatrice[i-1][j][1] == 1){
                        a = 1;
                    }else{
                        a = 0;
                    }

                    b = this.random.nextInt(2);

                    if(levelMatrice[i-1][j][3] == 1){
                        c = 1;
                    }else{
                        c = 0;
                    }

                    d = this.random.nextInt(2);

                }else if(i == 0 && j != 0){

                    if(levelMatrice[i][j-1][2] == 1){
                        a = 1;
                    }else{
                        a = 0;
                    }

                    if(levelMatrice[i][j-1][3] == 1){
                        b = 1;
                    }else{
                        b = 0;
                    }

                    c = this.random.nextInt(2);
                    d = this.random.nextInt(2);

                }else if(i != 0) {

                    if(levelMatrice[i-1][j][1] == 1){
                        a = 1;
                    }else{
                        a = 0;
                    }

                    if(levelMatrice[i][j-1][3] == 1){
                        b = 1;
                    }else{
                        b = 0;
                    }

                    if(levelMatrice[i-1][j][3] == 1){
                        c = 1;
                    }else{
                        c = 0;
                    }

                    d = this.random.nextInt(2);

                }else{ //  Simplify 'i == 0 && j == 0'
                    a = this.random.nextInt(2);
                    b = this.random.nextInt(2);
                    c = this.random.nextInt(2);
                    d = this.random.nextInt(2);

                }

                levelMatrice[i][j][0] = a;
                levelMatrice[i][j][1] = b;
                levelMatrice[i][j][2] = c;
                levelMatrice[i][j][3] = d;
                levelTile[i][j] = ""+a+b+c+d;
                //System.out.println(levelTile[i][j]);
            }
        }
        return levelTile;
    }
}
