package sample;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntityPlayer extends Entity{


    // Compétences
    //private int valueCompFire;
    //private int valueCompBow;
    //private int valueCompGun;
    //private int valueCompCut;

    // Caractéristiques principales
    private int agility;
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;

    //Caractéristiques secondaires
    private int endurance;
    private int modAttackSpeedCacS;
    private int modAttackSpeedCacB;
    private int modAttackSpeedRange;
    private int modDamageCacS;
    private int modDamageCacB;
    private int modDamageRange;
    private int modPrecisionCacS;
    private int modPrecisionCacB;
    private int modPrecisionRange;
    private int modPrecisionGun;
    private int resistanceDisease;
    private int resistancePoison;
    private int resistanceTiredness;
    private int resistancePsy;
    private int identify;
    private int learn;

    protected Inventory inv;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    private SpriteAnimation animationWalk;
    private SpriteAnimation animationIdle;


    //arme affichée
    private ImageView imageWeapon;
    private Image imagePathWeapon;

    //touches
    private boolean pressedUp;
    private boolean pressedDown;
    private boolean pressedLeft;
    private boolean pressedRight;

    //pour le calcul de fps
    private final long ONE_SECOND = 1000000000;
    private long currentTime;
    private long lastTime;
    private long fps = 0;
    private long delta = 0;



    //souris
    protected double mouseX;
    protected double mouseY;
    private double mouseDeltaX;
    private double mouseDeltaY;
    private double weaponRotation;
    private int weaponPosX;
    private int weaponPosY;
    private int weaponScaleX;


    /*
        Default Constructor
     */
    public EntityPlayer(){

    }
    /**
     *   Player Constructor.
     *
     *   @param name nom du joueur
     *   @param s strength value
     *   @param a agility value
     *   @param d dexterity value
     *   @param c constitution value
     *   @param i intelligence value
     */
    public EntityPlayer(String name, int s, int a, int d, int c, int i,Level l) {


        //attributs principaux
        this.name = name;
        this.state = "IDLE";
        this.facing = "RIGHT";
        this.level = l;

        //caractéristiques principales
        this.strength = s;
        this.agility = a;
        this.dexterity = d;
        this.constitution = c;
        this.intelligence = i;

        //compétences
        //this.valueCompBow = 0;
        //this.valueCompCut = 0;
        //this.valueCompFire = 0;
        //this.valueCompGun = 0;

        //inventaire
        this.inv = new Inventory(this);

        //image et animation
        image = new ImageView();
        imagePath = new Image(Main.class.getResourceAsStream("../img/playerWalk.png"));
        image.setImage(imagePath);
        image.setViewport(new Rectangle2D(0, 0, 52, 89));

        animationWalk = new SpriteAnimation(image, Duration.millis(800), 8, 4, 0, 0, 52, 89);
        animationIdle = new SpriteAnimation(image, Duration.millis(800), 2, 4, 0, 178, 52, 89);

        animationWalk.setCycleCount(Animation.INDEFINITE);
        animationIdle.setCycleCount(Animation.INDEFINITE);

        //image arme
        imageWeapon = new ImageView();
        imagePathWeapon = new Image(Main.class.getResourceAsStream("../img/item/shotgun.png"));
        imageWeapon.setImage(imagePathWeapon);

        //position et initialisation position du perso
        this.posX = 0;
        this.posY = 0;

        //collider
        this.colX = 16;
        this.colY = 78;
        this.colWidth = 16;
        this.colHeight = 10;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        //position, angle et initialisation de l'arme
        imageWeapon.getTransforms().add(0, new Translate(0, 0));
        imageWeapon.getTransforms().add(1, new Scale(1, 1));
        imageWeapon.getTransforms().add(2, new Rotate(0, 0, 0));


        //variables pour le calcul du fps
        this.currentTime = System.nanoTime();
        this.lastTime = System.nanoTime();

        //move
        this.accLimit = 3;
        this.velLimit = 6;
        this.friction = 0.5f;               //friction du personnage (0.2 = boue/escalier, 0.5 = normal, 0.9 = glace)
        this.accX = 0f;
        this.accY = 0f;
        this.velX = 0f;
        this.velY = 0f;
        this.pressedDown = false;
        this.pressedLeft = false;
        this.pressedRight = false;
        this.pressedUp = false;

        //caractéristiques secondaires
        calculateSecondarySpecs();

        // Debut test pour inventaire
        this.inv.addItem("shroom1", 0.2f, true, "consumable");
        this.inv.addItem("shroom1", 0.2f, true, "consumable");
        this.inv.addItem("silverCoin", 0.2f, true, "junk");
        this.inv.addItem("bronzeCoin", 0.1f, true, "tool");
        this.inv.addItem("shotgun", 5.7f, true, "weapon");

        /*
        Item tabSilverCoin[] = new Item[100];                       // 100 pieces d'argent
        for(int cnt=0 ; cnt < 100 ; cnt++){
            tabSilverCoin[cnt] = new Item("silverCoin",0.05f,true);
            this.inv.addItem(tabSilverCoin[cnt]);
        }
        this.inv.setShortcut(1,s1);
        */
    }

    // liste des écouteurs du déplacement du joueur
    private final List<MoveListener> listeners = new ArrayList<>();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * move the character in the direction given by parameter
     * @param dir the direction for the movement
     */
    public void updateControl(int dir){

        for (MoveListener hl : listeners)
            hl.playerIsMoving(this.posX, this.posY);

        switch(dir){
            case 0 :
                this.pressedUp = true;
                break;
            case 1 :
                this.pressedDown = true;
                break;
            case 2 :
                this.pressedLeft = true;
                break;
            case 3 :
                this.pressedRight = true;
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

    @Override
    public void move(Level level) {

        getFps();

        super.move(level);

        //gestion de la souris
        this.mouseDeltaX = this.mouseX - this.posX;
        this.mouseDeltaY = this.mouseY - this.posY;
        weaponRotation = Math.toDegrees(Math.atan2(this.mouseDeltaY, -this.mouseDeltaX));

        //orientation gauche/droite
        if(mouseX > posX){
            this.facing = "RIGHT";
            weaponRotation += 180;
            weaponPosX = this.posX + 60;
            weaponScaleX = -1;
        }else{
            this.facing = "LEFT";
            weaponRotation *= -1;
            weaponPosX = this.posX - 8;
            weaponScaleX = 1;
        }
        weaponPosY = this.posY + 50;
    }

    @Override
    public void display(){

        super.display();

        //application des transformations sur l'arme
        imageWeapon.getTransforms().set(1, new Scale(weaponScaleX, 1));
        imageWeapon.getTransforms().set(0, new Translate(weaponPosX, weaponPosY));
        imageWeapon.getTransforms().set(2, new Rotate(weaponRotation, 43, 6));


        animation();
    }

    public void animation(){

        //on anime le perso en fonction de son état (idle = au repos, walk = marche)
        switch(this.state){
            case "IDLE":
                animationIdle.play();
                animationWalk.stop();
                break;
            case "WALK":
                animationWalk.play();
                animationIdle.stop();
                break;
            default:
                break;
        }
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
        //this.moveSpeed = this.acc + ((float)this.constitution / 100) + (float)((float)this.agility / 62.5);
        this.moveSpeed = 0.5f;
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

    /*
        ------- GETTERS -------
     */

    @Override
    protected float[] getAcc(boolean[] col){

        this.moveSpeed = 10;
        if (this.pressedUp) {
            if(col[0]){
                this.accY = 0;
            }else {
                this.accY -= this.moveSpeed;
            }
        }
        if (this.pressedDown) {
            if(col[1]){
                this.accY = 0;
            }else {
                this.accY += this.moveSpeed;
            }
        }
        if (this.pressedLeft) {
            if(col[2]){
                this.accX = 0;
            }else {
                this.accX -= this.moveSpeed;
            }
        }
        if (this.pressedRight) {
            if(col[3]){
                this.accX = 0;
            }else {
                this.accX += this.moveSpeed;
            }
        }
        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }

    @Override
    public String getState(){
        //statut du perso
        if(this.accX != 0 || this.accY != 0){

            this.state = "WALK";
        }else{
            this.state = "IDLE";
        }
        return this.state;
    }

    private void getFps(){

        this.currentTime = System.nanoTime();
        this.fps++;
        this.delta += (this.currentTime - this.lastTime);
        if(this.delta > this.ONE_SECOND) {
            System.out.println("FPS : "+this.fps);
            this.delta -= this.ONE_SECOND;
            this.fps = 0;
        }
        this.lastTime = this.currentTime;
    }

    public Inventory getIv() {

        return this.inv;
    }

    public ImageView getImageWeapon() {

        return this.imageWeapon;
    }

    public int getEndurance(){
        return this.endurance;
    }

    public int getModAttackSpeedCacS(){
        return modAttackSpeedCacS;
    }
    // ...






}
