package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntityPlayer extends Entity{


    protected String name;

    // Compétences
    protected int valueCompFire;
    protected int valueCompBow;
    protected int valueCompGun;
    protected int valueCompCut;

    // Caractéristiques principales
    protected int agility;
    protected int strength;
    protected int constitution;
    protected int dexterity;
    protected int intelligence;

    //Caractéristiques secondaires
    //protected int health;
    //protected float moveSpeed;
    //protected int dodge;
    //protected int stealth;
    protected int endurance;
    protected int modAttackSpeedCacS;
    protected int modAttackSpeedCacB;
    protected int modAttackSpeedRange;
    protected int modDamageCacS;
    protected int modDamageCacB;
    protected int modDamageRange;
    protected int modPrecisionCacS;
    protected int modPrecisionCacB;
    protected int modPrecisionRange;
    protected int modPrecisionGun;
    protected int resistanceDisease;
    protected int resistancePoison;
    protected int resistanceTiredness;
    protected int resistancePsy;
    protected int identify;
    protected int learn;

    protected Inventory inv;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;


    //var de mouvements
    protected float acc;
    protected float accX;
    protected float accY;

    protected Direction direction;                                          // NOUVEAU
    protected int nextPosX;
    protected int nextPosY;

    protected boolean pressedUp;
    protected boolean pressedDown;
    protected boolean pressedLeft;
    protected boolean pressedRight;

    //constantes de mouvement
    protected float accLimit;
    protected float friction;
    //--------------

    //pour le calcul de fps
    protected final long ONE_SECOND = 1000000000;
    protected long currentTime = 0;
    protected long lastTime = 0;
    protected long fps = 0;
    protected long delta = 0;

    Level l;

    /*
        Player Constructor.
        @param String name nom du joueur
        @param int s strength value
        @param int a agility value
        @param itn d dexterity value
        @param int c constitution value
        @param int i intelligence value
     */
    public EntityPlayer(String name, int s, int a, int d, int c, int i,Level l) {

        this.name = name;
        this.l = l;

        //caractéristiques principales
        this.strength = s;
        this.agility = a;
        this.dexterity = d;
        this.constitution = c;
        this.intelligence = i;

        //compétences
        this.valueCompBow = 0;
        this.valueCompCut = 0;
        this.valueCompFire = 0;
        this.valueCompGun = 0;

        //inventaire
        this.inv = new Inventory(this);

        //image
        image = new ImageView();
        imagePath = new Image(Main.class.getResourceAsStream("../img/player/gilbert.png"));
        image.setImage(imagePath);

        this.posX = 0f;
        this.posY = 0f;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        // box collision


        //move
        this.accLimit = 6;
        this.friction = 0.5f;
        this.acc = 3f;
        this.accX = 0f;
        this.accY = 0f;

        this.direction = new Direction(0,0);

        this.pressedDown = false;
        this.pressedLeft = false;
        this.pressedRight = false;
        this.pressedUp = false;


        //caractéristiques secondaires
        calculateSecondarySpecs();

        // ----------------------------------------------------------------
        // Debut test pour inventaire
        this.inv.addItem("shroom1", 0.2f, true, "consumable");
        this.inv.addItem("shroom1", 0.2f, true, "consumable");
        this.inv.addItem("silverCoin", 0.2f, true, "junk");
        this.inv.addItem("shotgun", 5.7f, true, "weapon");
        this.inv.addItem("bronzeCoin", 0.1f, true, "tool");



        /*Item s1 = new Item("shroom1",0.2f,true); // 1 CHAMPI
        this.inv.addItem(s1);
        Item s2 = new Item("shroom1",0.3f,true);                         // 2 CHAMPI
        this.inv.addItem(s2);

        Item s3 = new Item("shroom1",0.1f,true);
        this.inv.addItem(s3);

        Item tabSilverCoin[] = new Item[100];                       // 100 pieces d'argent
        for(int cnt=0 ; cnt < 100 ; cnt++){
            tabSilverCoin[cnt] = new Item("silverCoin",0.05f,true);
            this.inv.addItem(tabSilverCoin[cnt]);
        }
        */
        //this.inv.setShortcut(1,s1);


        // ----------------------------------------------------------------
        // Fin test pour inventaire

        // ----- début test arme -----
        /*
        String name,
        float weight,
        int precision,
        int damage,
        int speed,
        int type,
        int strenghtNeeded,
        int dexterityNeeded,
        int inteligenceNeeded
         */
        /*ItemWeapon gun1= new ItemWeapon(
                "shotgun",
                5f,
                37,
                12,
                5,
                1,
                12,
                15,
                5
        );*/
        //this.inv.addItem(gun1);
        // ----- fin test arme -----

        lastTime = System.nanoTime();
        new AnimationTimer(){
            public void handle(long arg0){
                moveto();
            }
        }.start();

    }
    /**
     * Default Constructor
     */
    public EntityPlayer(){

    }

    // liste des écouteurs du déplacement du joueur
    private List<MoveListener> listeners = new ArrayList<MoveListener>();

    public void addListener(MoveListener toAdd) {
        listeners.add(toAdd);
    }



    /**
     * create a backup of the player
     * @param f file name to use for backup
     */
    public void save(String f){
        try {
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(f + ".psave"))));

            oos.writeObject(this); // objet à sauvegarder : this
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * move the character in the direction given by parameter
     * @param dir the direction for the movement
     */
    public void move(int dir){
        //récupération des touches appuyées et relachées
        //0, 1, 2, 3 : touche appuyées / 4, 5, 6, 7 : touche relachées

        for (MoveListener hl : listeners)
            hl.playerIsMoving(this.posX, this.posY);

        switch(dir){
            case 0 :
                this.pressedUp = true;
                this.direction.setY(-1);
                break;
            case 1 :
                this.pressedDown = true;
                this.direction.setY(1);
                break;
            case 2 :
                this.pressedLeft = true;
                this.direction.setX(-1);
                break;
            case 3 :
                this.pressedRight = true;
                this.direction.setX(1);
                break;
            case 4 :
                this.pressedUp = false;
                break;
            case 5 :
                this.pressedDown = false;
                break;
            case 6 :
                this.pressedLeft = false;
                break;
            case 7 :
                this.pressedRight = false;
                break;
            default :
                break;
        }

    }

    public void moveto() {

        //calcul du fps
        currentTime = System.nanoTime();
        fps++;
        delta += currentTime - lastTime;
        if(delta > ONE_SECOND*3) {
            //System.out.println("FPS :"+ fps);
            delta -= ONE_SECOND;
            fps = 0;
        }
        lastTime = currentTime;


        //application des accélerations en fonction des touches appuyées
        if (this.pressedUp) {
            this.accY -= this.moveSpeed;
        }
        if (this.pressedDown) {
            this.accY += this.moveSpeed;
        }
        if (this.pressedLeft) {
            this.accX -= this.moveSpeed;
        }
        if (this.pressedRight) {
            this.accX += this.moveSpeed;
        }

        //application de la friction (ex : 0.9 sur terre, 0.3 sur de la glace)
        accX *= this.friction;
        accY *= this.friction;

        this.accLimit = (float)(this.moveSpeed * this.friction * 10);
        //cap de l'accéleration
        if (this.accX > this.accLimit)
            this.accX = this.accLimit;
        if (this.accX < -this.accLimit)
            this.accX = -this.accLimit;
        if (this.accY > this.accLimit)
            this.accY = this.accLimit;
        if (this.accY < -this.accLimit)
            this.accY = -this.accLimit;

        //System.out.println("accX : "+this.accX+" / accLimit : "+this.accLimit+" / moveSpeed : "+this.moveSpeed);

        //arrondi à zero quand la valeur est très petite (ex : 0.000658 = 0)
        accX = approximatelyZero(accX);
        accY = approximatelyZero(accY);

        //déplacement du personnage en fonction de son accéleration (moveSpeed)

        this.nextPosX += accX;
        this.nextPosY += accY;

        if( l.collision(nextPosX,nextPosY) )  {
            nextPosX = (int)posX;
            nextPosY = (int)posY;
        }
        else
        {
            posX = nextPosX;
            posY = nextPosY;
            image.setTranslateX(posX);
            image.setTranslateY(posY);
        }


    }

    private float approximatelyZero(float f){

        float rF = f;
        if(rF > 0f && rF < 0.1f)
            rF = 0f;
        if(rF < 0f && rF > -0.1f)
            rF = 0f;

        return rF;
    }

    private void calculateSecondarySpecs(){
        /*
        str = force physique
        agi = agilité du corps
        dextérité = agilité des doigts, manipulation
        con = endurance / resistance du corps
        int = intelligence innée / force mental
        */



        //-----déplacement-----
        //vitesse de déplacement (0.22 => 0.55 (si con et agi à 100))
        this.moveSpeed = this.acc + ((float)this.constitution / 100) + (float)((float)this.agility / 62.5);

        //furtivité du personnage
        this.stealth = Math.round(10 + ((this.agility + 1)/ 2));
        //endurance
        this.endurance = Math.round(10 + ((this.constitution + 1)/ 2));

        //-----modificateur de défenses-----
        //vie
        this.health = 100 + this.constitution;
        //esquive
        this.dodge = Math.round(5 + (this.agility / 5));

        //-----modificateur d'attaque-----
        //vitesse d'attaque avec arme au corps à corps (dague)
        this.modAttackSpeedCacS = Math.round(this.agility / 5);
        //vitesse d'attaque avec arme au corps à corps (masse)
        this.modAttackSpeedCacB = Math.round((this.agility + this.strength) / 9);
        //vitesse d'attaque avec arme à distance (arc, fronde)
        this.modAttackSpeedRange = Math.round((this.dexterity + this.agility) / 10);
        //dégats avec les armes (sauf gun)
        this.modDamageCacS = Math.round(this.dexterity / 6);
        this.modDamageCacB = Math.round(this.strength / 6);
        this.modDamageRange = Math.round(this.strength / 8);
        //précision des armes
        this.modPrecisionCacS = Math.round((this.dexterity + this.agility) / 11);
        this.modPrecisionCacB = Math.round(this.agility / 9);
        this.modPrecisionRange = Math.round(this.dexterity / 8);
        this.modPrecisionGun = Math.round(this.dexterity / 8);

        //-----resistances-----
        //resistance à la maladie
        this.resistanceDisease = Math.round(15 + (this.constitution / 4));
        //resistance au maladie
        this.resistancePoison = Math.round(5 + (this.constitution / 6));
        //resistance à la fatigue
        this.resistanceTiredness = Math.round(20 + (this.constitution / 4));
        //resistance mentale
        this.resistancePsy = Math.round(10 + (this.intelligence / 3));

        //-----autre-----
        this.learn = Math.round(this.intelligence / 2);
        this.identify = Math.round(this.intelligence);
    }



    public void displayInventory(){
        this.inv.display();
    }

    public void addItem(Item item)
    {
        //this.inv.addItem(item);
    }

    // ----- GETTERS -----// 

    public Inventory getIv() {
        return inv;
    }

    public int getAgility() {
        return agility;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelect() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public ImageView getImage() {
        return image;
    }
}
