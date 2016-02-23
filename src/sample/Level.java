package sample;


import org.newdawn.slick.SlickException;

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
public class Level {

    private int id;
    private String name;
    private int width;
    private int height;
    private String tileset;
    private Tile[][] tiles;

    private boolean hasLogicLayer;
    private boolean hasGraphicLayer;


    /**
     * default Constructor
     */
    public Level(){

        this("src/map/mapTest.xml");
    }


    /**
     * Constructor
     * @param refMap : url du fichier xml (src/map/tatati.xml)
     */
    public Level(String refMap){

        this.id = 0;
        this.name = refMap;
        this.hasLogicLayer = false;
        this.hasGraphicLayer = false;
        this.load(refMap);
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
            //File mapXmlFile = new File("src/map/mapTest.xml");
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
            //System.out.println("tileset : "+this.tileset);
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
            if(!hasLogicLayer || !hasGraphicLayer){
                throw new RuntimeException("Level don't have LogicMap AND GraphicMap");
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
                //System.out.println(i);
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
                    //System.out.println(tileIndex);
                    tileIndex++;
                }
            }
        }else{

            throw new ArrayIndexOutOfBoundsException("tile.length != (width * height)");
        }
    }

    /**
     * rendu graphique des tiles
     */
    public void render(){
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {

                /** on dessine les tiles */
                this.tiles[i][j].render();
            }
        }
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
}
