package sample;

/*
    Description de la classe :
 */
public class Level {

    private String name;
    private Tile[][] tilesMap;

    /*
        Constructeur
        @param x ,largeur de la map
        @param y ,hauteur de la map
        @param filePath ,chemin du fichier qui contient la définition du lvl
     */
    public Level(int x,int y) {

        int defInt[][] = new int[x][y];

        //matrice de Int
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int selectedTile = (int)Math.random()*3;
                defInt[x][y] = selectedTile;
            }
        }

        // t
        // Creation des Tiles avec la matrice de Int
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tilesMap[x][y] = new Tile(64, 64, 64 * x, 64 * y, 1, defInt[x][y]);
            }
        }
    }
}
