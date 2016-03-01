package sample;


import org.newdawn.slick.*;


/**
 * Main Class
 *
 * ******* TODO *******
 * - déplacer la gestion des contrôles dans une classe Controle
 * qui utilise l'interface Slick des contrôles
 */
public class Main extends BasicGame {

    /** container du jeu */
    private GameContainer container;

    /** joueur */
    private EntityPlayer player;

    /** niveau */
    private Level level;

    /** quelques monstres de test */
    Monster monster1;
    Monster monster2;
    Monster monster3;

    Music music;

    //private Entity arrayEntities[];


    /**
     * main
     * @param args : default args
     * @throws SlickException
     */
    public static void main(String[] args) throws SlickException {

        AppGameContainer appGC;
        try {
            appGC = new AppGameContainer(new Main(), 640, 480, false); // 20tiles / 15tiles
            appGC.setTargetFrameRate(60);
            appGC.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    /**
     * default constructor
     */
    private Main() {
        super("Version 0.0.4");
    }


    /**
     * initialisation du jeu
     * @param container : container du jeu
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container) throws SlickException {

        this.container = container;

        /** initialisation du joueur */
        this.player = new EntityPlayer(
                "Bernard",  // nom
                0,         // force
                0,        // agilité
                0,         // dextérité
                0,        // constitution
                0          // intelligence
        );
        System.out.println(this.player.toString());

        /** initialisation du premier niveau */
        this.level = new Level("src/map/mapTest.xml");

        /** envoie de la carte au joueur */
        this.player.setLevel(this.level);
        this.player.setPosition(64, 64);

        this.monster1 = new Blob();
        this.monster1.setLevel(this.level);
        this.monster1.setPosition(300, 300);
        this.monster1.setPlayer(this.player);

        this.monster2 = new Blob();
        this.monster2.setLevel(this.level);
        this.monster2.setPosition(320, 64);
        this.monster2.setPlayer(this.player);

        this.monster3 = new Motha();
        this.monster3.setLevel(this.level);
        this.monster3.setPosition(0, 32);
        this.monster3.setPlayer(this.player);
        this.monster3.init();

        /** test musique => ça marche */
        //this.music = new Music("sound/music.ogg");
        //this.music.play();
    }


    /**
     * mise à jour du jeu
     * @param container : container du jeu
     * @param delta : delta pour la loop variable
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        this.player.update(delta);
        this.monster1.update(delta);
        this.monster2.update(delta);
        this.monster3.update(delta);


    }


    /**
     * rendu visuel du jeu
     * @param container : container du jeu
     * @param g : graphics slick
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        this.level.render();
        this.level.renderFront();
        this.player.render(g);
        this.monster1.render(g);
        this.monster2.render(g);
        this.monster3.render(g);

    }


    /**
     * récupération des boutons appuyés de la souris
     * @param button : 0 = gauche, 1 = droit, 2 = molette
     * @param x : coordonné x du click
     * @param y : coordonné y du click
     */
    @Override
    public void mousePressed(int button, int x, int y){

        switch(button){
            case 0:
                this.player.getWeapon().setPressedMouseLeft();
                if(this.player.inventory.isDisplayed){
                    this.player.inventory.onClick(x, y);
                }
                break;
            default:
                break;
        }
    }


    /**
     * récupération des boutons appuyés de la souris
     * @param button : 0 = gauche, 1 = droit, 2 = molette
     * @param x : coordonné x du click
     * @param y : coordonné y du click
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount){

        switch(button){
            case 0:
                //this.player.getWeapon().setPressedMouseLeft();
                if(this.player.inventory.isDisplayed && clickCount == 2){
                    this.player.inventory.onDoubleClick(x, y);
                }
                break;
            default:
                break;
        }
    }


    /**
     * récupération du bouton relaché de la souris
     * @param button
     * @param x
     * @param y
     */
    public void mouseReleased(int button, int x, int y){

        switch(button){
            case 0:
                this.player.getWeapon().setReleasedMouseLeft();
                break;
            default:
                break;
        }
    }


    /**
     * controle : récupération des touches appuyées
     * @param key : touche appuyée
     * @param c : char de la touche
     */
    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_Z:
                this.player.setPressedUp();
                break;
            case Input.KEY_S:
                this.player.setPressedDown();
                break;
            case Input.KEY_Q:
                this.player.setPressedLeft();
                break;
            case Input.KEY_D:
                this.player.setPressedRight();
                break;
            case Input.KEY_SPACE:
                this.player.setPressedJump();
                break;
            case Input.KEY_I:
                if(this.player.inventory.isDisplayed) {
                    this.player.inventory.isDisplayed = false;
                }else{
                    this.player.inventory.isDisplayed = true;
                }
                break;
            case Input.KEY_E:
                this.player.setPressedAction();
                break;
            default:
                break;
        }
    }


    /**
     * controle : récupération des touches relâchées
     * @param key : touche relâchée
     * @param c : char de la touche
     */
    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_Z:
                this.player.setReleasedUp();
                break;
            case Input.KEY_S:
                this.player.setReleasedDown();
                break;
            case Input.KEY_Q:
                this.player.setReleasedLeft();
                break;
            case Input.KEY_D:
                this.player.setReleasedRight();
                break;
            case Input.KEY_SPACE:
                this.player.setReleasedJump();
                break;
            case Input.KEY_E:
                this.player.setReleasedAction();
                break;
            default:
                break;
        }
    }


    /**
     * controle : déplacement de la souris
     * @param oldX : the old x position of the mouse
     * @param oldY : the old y position of the mouse
     * @param newX : the new x position of the mouse
     * @param newY : the new y position of the mouse
     */
    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY) {

        this.player.setMouse(oldX, oldY, newX, newY);
    }


    /**
     * controle : déplacement de la souris quand on a clické
     * @param oldX
     * @param oldY
     * @param newX
     * @param newY
     */
    @Override
    public void mouseDragged(int oldX, int oldY, int newX, int newY) {

        this.player.setMouse(oldX, oldY, newX, newY);
    }
}

    /*
    protected EntityPlayer player;
    protected Entity arrayEntities[];
    protected Level level;

    private long lastFpsTime;

    private int fps;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //creation du groupe
        Group root = new Group();

        int widthLevel = 15;
        int heigthLevel = 10;
        this.level = new Level(widthLevel, heigthLevel);
        for(int i = 0; i < this.level.width; i++){
            for(int j = 0; j < this.level.height; j++){
                root.getChildren().add(this.level.getTile(i,j).getImage());
            }
        }

        // Init primaryStage //
        Scene home = new Scene(root, 960, 640, true);


        arrayEntities = new Entity[3];
        // Creation joueur
        player = new EntityPlayer("Bernard",16,100,55,100,12,this.level, root, arrayEntities); //s a d c i

        // Ajout joueur au group
        root.getChildren().add(player.getImage());
        root.getChildren().add(player.getImageWeapon());

        // init controles du joueur
        new Controller(home, this.player, this.level);

        //création d'un monstre
        Monster monster1 = new Monster("Albert la mouche", 200, 200);
        Monster monster2 = new Monster("Hector à babord", 400, 400);


        arrayEntities[0] = player;
        arrayEntities[1] = monster1;
        arrayEntities[2] = monster2;


        //les monstres écoutent le joueur
        //player.addListener(monster1);
        //player.addListener(monster2);





    }
    */




