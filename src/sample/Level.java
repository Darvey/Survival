package sample;

/*
    Description de la classe :
 */
public class Level {

    private String name;
    private Tile[][] tilesMap;
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

        //matrice de Int
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                defInt[i][j] = 1;
            }
        }

        // t
        // Creation des Tiles avec la matrice de Int

        tilesMap = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tilesMap[i][j] = new Tile(64, 64, 64 * x, 64 * y, 1, defInt[i][j]);
            }
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

}
