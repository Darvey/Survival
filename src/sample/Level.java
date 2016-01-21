package sample;

/*
    Description de la classe :
 */
public class Level {

    private String name;
    private Tile[][] tilesMap;
    private Item[][] itemMap;
    private int h;
    private int l;

    /*
        Constructeur
        @param x ,largeur de la map
        @param y ,hauteur de la map
        @param filePath ,chemin du fichier qui contient la définition du lvl
     */
    public Level(int x,int y) {

        this.h=y;
        this.l=x;

        int defInt[][] = new int[x][y];
        itemMap = new Item[x][y];

        //matrice de Int
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                defInt[i][j] = i % 3 + 1;
            }
        }

        // Creation des Tiles avec la matrice de Int

        tilesMap = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tilesMap[i][j] = new Tile(32, 32, 32 * i, 32 * j, 1, defInt[i][j]);
            }
        }

        // -------- DEBUT test interactiveOnrenemt ---------//

        itemMap[3][3] = new Item("shroom1", 1.2f, true, "consumable", null);
        itemMap[3][3].getImage().setTranslateX(32*3+12);
        itemMap[3][3].getImage().setTranslateY(32*3+10);

        // -------- DEBUT test interactiveOnrenemt ---------//

    }

    public void action(EntityPlayer player)
    {
        int caseX = (int)player.getImage().getTranslateX()/32;
        int caseY = (int)player.getImage().getTranslateY()/32;
        System.out.println("posPlayer = "+caseX+" "+caseY);
        if(caseX == 3 && caseY == 3){
            itemMap[3][3].getImage().setTranslateX(0);
            itemMap[3][3].getImage().setTranslateY(0);
            itemMap[3][3].getImage().setVisible(false);
            player.inv.addItem(itemMap[3][3].getName(), itemMap[3][3].getWeight(), true ,itemMap[3][3].getType());
        }
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

}
