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
    public Level(int x,int y,String filePath){

        tilesMap = new Tile[x][y];

        //matrice
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                tilesMap[x][y] = 1;
            }
        }

        //affichages des images
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                Tile T = new Tile(64,64,64*x,64*y,1,tilesMap[x][y]);
            }
        }

    }

}
