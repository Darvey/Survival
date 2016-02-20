package sample;


import org.newdawn.slick.*;


public class Main extends BasicGame {

    private GameContainer container;
    private EntityPlayer player;
    //private Entity arrayEntities[];
    private Level level;


    /**
     * main
     * @param args : default args
     * @throws SlickException
     */
    public static void main(String[] args) throws SlickException {

        AppGameContainer appGC;
        try {
            appGC = new AppGameContainer(new Main(), 640, 480, false); // 20t / 15t
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
        super("test");
    }


    /**
     * initialisation du jeu
     * @param container : container du jeu
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container) throws SlickException {

        /** container du jeu */
        this.container = container;

        /** initialisation du joueur */
        this.player = new EntityPlayer(
                "Bernard",  // nom
                16,         // force
                100,        // agilité
                55,         // dextérité
                100,        // constitution
                12          // intelligence
        );

        /** initialisation du premier niveau */
        this.level = new Level("src/map/mapTest.xml");

        /** envoie de la carte au joueur */
        this.player.setLevel(this.level);
    }


    /**
     * mise à jour du jeu
     * @param container : container du jeu
     * @param delta : delta pour la loop variable
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        System.out.println(delta);
        this.player.update(delta);
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
        this.player.render(g);
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
                System.out.println("tris");
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




