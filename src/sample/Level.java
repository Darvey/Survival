package sample;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/*
    Description de la classe :
 */
public class Level {

    private String name;
    private Tile[][] tilesMap;
    private Item[][] itemMap;
    private int h;
    private int l;
    private Random random;

    //variables pour les collisions
    protected Line2D.Double lineUp;
    protected Line2D.Double lineDown;
    protected Line2D.Double lineLeft;
    protected Line2D.Double lineRight;
    protected Rectangle2D.Double collisionArea;

    /*
        Constructeur
        @param x ,largeur de la map
        @param y ,hauteur de la map
        @param filePath ,chemin du fichier qui contient la définition du lvl
     */
    public Level(int x,int y) {

        this.h=y;
        this.l=x;
        random = new Random();

        int defInt[][] = new int[x][y];
        itemMap = new Item[x][y];

        //matrice de Int
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                defInt[i][j] = i % 3 + 1;
            }
        }

        String levelTile[][] = createLevel();

        // Creation des Tiles avec la matrice de Int

        tilesMap = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //tilesMap[i][j] = new Tile(32, 32, 32 * i, 32 * j, 1, defInt[i][j]);
                tilesMap[i][j] = new Tile(32, 32, 32 * i, 32 * j, 1, levelTile[i][j]);
            }
        }

        // -------- DEBUT test interactiveOnrenemt ---------//
        /*
        itemMap[3][3] = new Item("shroom1", 1.2f, true, "consumable", null);
        itemMap[3][3].getImage().setTranslateX(32*3+12);
        itemMap[3][3].getImage().setTranslateY(32*3+10);
        */
        // -------- DEBUT test interactiveOnrenemt ---------//

    }

    public void action(EntityPlayer player)
    {
        int caseX = (int)(player.getImage().getTranslateX()+7)/32;
        int caseY = (int)(player.getImage().getTranslateY()+10)/32;
        System.out.println("posPlayer = "+caseX+" "+caseY);
        if(caseX == 3 && caseY == 3){
            itemMap[3][3].getImage().setTranslateX(0);
            itemMap[3][3].getImage().setTranslateY(0);
            itemMap[3][3].getImage().setVisible(false);
            player.inv.addItem(itemMap[3][3].getName(), itemMap[3][3].getWeight(), true ,itemMap[3][3].getType());
        }
    }

    public boolean[] collision(int nX,int nY, int offset)
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

        this.lineUp = new Line2D.Double(nX, nY-offset, nX+32, nY-offset);
        this.lineDown = new Line2D.Double(nX, nY+32+offset, nX+32, nY+32+offset);
        this.lineLeft = new Line2D.Double(nX-offset, nY, nX-offset, nY+32);
        this.lineRight = new Line2D.Double(nX+32+offset, nY, nX+32+offset, nY+32);

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

        int levelMatrice[][][] = new int[15][10][4];
        String levelTile[][] = new String[15][10];
        int a;
        int b;
        int c;
        int d;

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 10; j++) {
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

                }else if(i != 0 && j != 0) {

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

                }else if(i == 0 && j == 0){
                    a = this.random.nextInt(2);
                    b = this.random.nextInt(2);
                    c = this.random.nextInt(2);
                    d = this.random.nextInt(2);

                }else{

                    a = 9;
                    b = 9;
                    c = 9;
                    d = 9;
                }
                //System.out.println(a + "/" + b + "/" + c + "/" + d);
                levelMatrice[i][j][0] = a;
                levelMatrice[i][j][1] = b;
                levelMatrice[i][j][2] = c;
                levelMatrice[i][j][3] = d;
                levelTile[i][j] = ""+a+b+c+d;
                System.out.println(levelTile[i][j]);
            }
        }
        return levelTile;
        /*System.out.println(levelMatrice);

        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 1; j++) {
                if(levelMatrice[i][j][0] == 1){
                    System.out.print("#");
                }else{
                    System.out.print("-");
                }
                if(levelMatrice[i][j][1] == 1){
                    System.out.print("#");
                }else{
                    System.out.print("-");
                }
            }
        }
        System.out.println();
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 1; j++) {
                if(levelMatrice[i][j][2] == 1){
                    System.out.print("#");
                }else{
                    System.out.print("-");
                }
                if(levelMatrice[i][j][3] == 1){
                    System.out.print("#");
                }else{
                    System.out.print("-");
                }
            }
        }*/
    }

}
