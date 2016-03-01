package sample;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * Class qui gère le niveau et les tiles qui le compose
 * crée une map à partir de fichiers xml
 * qui contiennent les différentes informations
 * de la carte et les tiles sur plusieurs niveaux
 * (logic pour les collisions et graphic pour
 * l'affichage).
 */
public class Level implements TileBasedMap {

    /** identifiant de la carte */
    private int id;
    private String name;

    /** dimension de la carte */
    private int width;
    private int height;
    private int widthInTiles;
    private int heightInTiles;

    /** élément pour la création graphique de la carte */
    private String tileset;
    private Tile[][] tiles;
    private Image background;
    private Image grass;

    /** calques de la carte */
    private boolean hasLogicLayer;
    private boolean hasGraphicLayer;


    /**
     * default Constructor
     */
    public Level() throws SlickException{

        this("src/map/mapTest.xml");
    }


    /**
     * Constructor
     * @param refMap : url du fichier xml (src/map/tatati.xml)
     */
    public Level(String refMap) throws SlickException{

        this.id = 0;
        this.name = refMap;
        this.hasLogicLayer = false;
        this.hasGraphicLayer = false;
        this.load(refMap);
        this.background = new Image("img/background/backgroundSky.png");
        //this.grass = new Image("img/tile/earth/objectGrass.png");
    }


    /**
     * charge le fichier xml, le parse et envoie les résultats
     * dans les fonctions de création des layers
     * @param refMap : url du fichier xml (src/map/tatata.xml)
     */
    private void load(String refMap){

        System.out.println("load");
        try {
            /** on charge le fichier xml donné en paramètre */
            File mapXmlFile = new File(refMap);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(mapXmlFile);

            /** initialisation des propriétés de la map */
            this.id = Integer.parseInt(doc.getDocumentElement().getAttribute("id"));
            this.name = doc.getDocumentElement().getAttribute("name");
            this.width = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            this.height = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
            this.tileset = doc.getDocumentElement().getAttribute("tileset");

            /** initialisation du tableau qui contiendra les tiles */
            this.tiles = new Tile[this.width][this.height];

            /** on récupère la liste des noeuds "layer" */
            NodeList layers = doc.getElementsByTagName("layer");

            /** pour chaque "layer" : logique et graphique */
            for(int i = 0; i < layers.getLength(); i++){

                Node layer = layers.item(i);
                if(layer.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) layer;

                    /** si le layer est logic on crée la map logic */
                    if(Objects.equals(element.getAttribute("name"), "logic")){

                        this.mappingLogicLayer(element.getTextContent());
                        this.hasLogicLayer = true;
                    }

                    /** si le layer est graphic on crée la map graphic */
                    if (Objects.equals(element.getAttribute("name"), "graphic")) {
                        this.mappingGraphicLayer(element.getTextContent());
                        this.hasGraphicLayer = true;
                    }
                }
            }

            /** on vérifie que la carte a bien un calque logique et graphiue */
            if(!hasLogicLayer || !hasGraphicLayer){
                throw new RuntimeException("Level don't have LogicMap AND GraphicMap");
            }


            /** **********************************
             * chargement de la liste des objets *
             ********************************** */

            /** on récupère la liste des objets */
            NodeList items = doc.getElementsByTagName("item");

            /** pour chaque "layer" : logique et graphique */
            for(int i = 0; i < items.getLength(); i++){

                Node item = items.item(i);

                if(item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;

                    try {
                        getTile(
                                Integer.parseInt(element.getAttribute("posX")) * 32,
                                Integer.parseInt(element.getAttribute("posY")) * 32
                        ).addItem(element.getAttribute("name"), element.getAttribute("type"));
                    }catch(SlickException e){
                        e.printStackTrace();
                    }
                }
            }

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }
    }


    /**
     * crée le layer logic de la map utilisé pour
     * les collisions (si le data est "1", la tile
     * est "solid"
     * @param data : "content" du noeud layer logic du fichier xml
     */
    private void mappingLogicLayer(String data){

        /** tableau contenant un int pour chaque tile */
        String[] dataTiles = data.split(",");

        /** si le nombre de tile correspond à la hauteur et à la largeur de la map */
        if(dataTiles.length == (this.width * this.height)) {

            /** utilisation d'un autre index car on parcours un tableau 1D */
            int tileIndex = 0;

            for (int i = 0; i < this.height; i++) {

                for (int j = 0; j < this.width; j++) {

                    /** création de la tile (data, posX, posY) */
                    try {
                        Tile tile = new Tile(dataTiles[tileIndex], j, i, tileIndex);
                        this.tiles[j][i] = tile;
                    }catch(SlickException e){
                        e.printStackTrace();
                    }

                    tileIndex++;
                }
            }
        }else{

            /** première vérification des dimensions de la carte */
            throw new ArrayIndexOutOfBoundsException("tile.length != (width * height)");
        }
    }


    /**
     * crée le layer graphic de la map
     * data renvoie aux images utilisées pour les tiles
     * l'association se fera via un tileset (
     * ex : tile/underground/1, 2, 3... .png
     * ex : tile/plateau/1, 2, 3... .png
     * le tileset sera contenu dans le fichier xml
     * @param data : liste des id des tile
     */
    private void mappingGraphicLayer(String data){

        /** tableau contenant un int pour chaque tile */
        String[] dataTiles = data.split(",");

        /** si le nombre de tile correspond à la hauteur et à la largeur de la map */
        if(dataTiles.length == (this.width * this.height)) {

            /** utilisation d'un autre index car on parcours un tableau 1D */
            int tileIndex = 0;

            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {

                    /** création de la partie graphique de la tile */
                    this.tiles[j][i].initGraphic(dataTiles[tileIndex], this.tileset);
                    tileIndex++;
                }
            }
        }else{
            /** première vérification des dimensions de la carte */
            throw new ArrayIndexOutOfBoundsException("tile.length != (width * height)");
        }
    }


    /**
     * rendu graphique des tiles
     */
    public void render(){

        /** background de la map */
        this.background.draw(0, 0);

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {

                /** on dessine les tiles */
                this.tiles[i][j].render();
            }
        }
    }


    /**
     * Test des ornements
     */
    public void renderFront(){

        //this.grass.draw(32, 352);
        //this.grass.draw(64, 352);

        //this.grass.draw(224, 288);
        //this.grass.draw(256, 288);
    }


    /**
     * renvoie une tile
     * @param posX : position X en pixel
     * @param posY : position Y en pixel
     * @return : la tile qui est à la position posX / posY
     */
    public Tile getTile(int posX, int posY){

        return this.tiles[posX / 32][posY / 32];
    }


    /**
     * renvoie le tableau des tiles
     * @return : le tableau des tiles
     */
    public Tile[][] getTiles(){

        return this.tiles;
    }


    /**
     * renvoie la largeur du level en pixel
     * @return : la largeur du level en pixel
     */
    public int getWidthInPixel(){

        return this.width * 32;
    }


    /**
     * renvoie la hauteur du level en pixel
     * @return : la hauteur du level en pixel
     */
    public int getHeightInPixel(){

        return this.height * 32;
    }


    /** ******* TEST DE PATHFINDING ******* */

    @Override
    public boolean blocked(PathFindingContext context, int x, int y) {
        Tile[][] tile = this.getTiles();
        if (tile[x][y].solid) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public float getCost(PathFindingContext arg0, int arg1, int arg2) {
        return 0;
    }

    @Override
    public int getWidthInTiles() {
        return this.width;
    }

    @Override
    public int getHeightInTiles(){
        return this.height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {
    }

    /** ******* FIN TEST DE PATHFINDING ******* */
}
