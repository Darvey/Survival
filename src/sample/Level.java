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
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                // Contruction d'un objet Tile en fonction de ce qui se trouve dans le
                // fichier text.
            }
        }

    }

}