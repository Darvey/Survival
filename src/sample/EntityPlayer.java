package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import java.awt.event.MouseMotionListener;
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

    //arme
    protected ImageView imageWeapon;
    protected Image imagePathWeapon;


    //var de mouvements
    protected float acc;
    protected float accX;
    protected float accY;
    protected float velX;
    protected float velY;
    protected int velXInteger;
    protected int velYInteger;

    protected Direction direction;                                          // NOUVEAU
    protected int nextPosX;
    protected int nextPosY;
    protected int prevPosX;
    protected int prevPosY;

    protected boolean pressedUp;
    protected boolean pressedDown;
    protected boolean pressedLeft;
    protected boolean pressedRight;

    //constantes de mouvement
    protected float accLimit;
    protected float velLimit;
    protected float friction;
    //--------------

    //pour le calcul de fps
    protected final long ONE_SECOND = 1000000000;
    protected long currentTime = 0;
    protected long lastTime = 0;
    protected long fps = 0;
    protected long delta = 0;

    Level l;

    //souris
    protected double mouseX;
    protected double mouseY;
    protected double mouseDeltaX;
    protected double mouseDeltaY;
    protected double lastAngle;
    protected double newAngle;

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
        imagePath = new Image(Main.class.getResourceAsStream("../img/collider_player.png"));
        image.setImage(imagePath);

        //image arme
        imageWeapon = new ImageView();
        imagePathWeapon = new Image(Main.class.getResourceAsStream("../img/item/shotgun.png"));
        imageWeapon.setImage(imagePathWeapon);

        this.posX = 0;
        this.posY = 0;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        //imageWeapon.setX(64);
        //imageWeapon.setY(16);
        this.lastAngle = 180;
        imageWeapon.setRotate(this.lastAngle);



        // box collision


        //move
        this.accLimit = 3;
        this.velLimit = 6;
        this.friction = 0.5f;               //friction du personnage (0.2 = boue/escalier, 0.5 = normal, 0.9 = glace)
        this.acc = 6f;
        this.accX = 0f;
        this.accY = 0f;
        this.velX = 0f;
        this.velY = 0f;

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
        this.inv.addItem("bronzeCoin", 0.1f, true, "tool");
        this.inv.addItem("shotgun", 5.7f, true, "weapon");



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

        lastTime = System.nanoTime();
        new AnimationTimer(){
            public void handle(long arg0){
                //moveto();
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

    public void mouseMoved(MouseEvent e){
        System.out.println("X : "+e.getX());
    }

    /**
     * move the character in the direction given by parameter
     * @param dir the direction for the movement
     */
    public void move(int dir){
        //récupération des touches appuyées et relachées
        //0, 1, 2, 3 : touche appuyées / 4, 5, 6, 7 : touche relachées
        System.out.println(posX);
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
        if(delta > ONE_SECOND) {
            System.out.println("FPS :"+ fps);
            delta -= ONE_SECOND;
            fps = 0;
        }
        lastTime = currentTime;

        //application des accélerations en fonction des touches appuyées
        /* ------- OPTIMISATION -------
        voir Level.collision
         */
        boolean[] col = l.collision(posX, posY, 1);
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

        //this.accLimit = (float)(this.moveSpeed * this.friction * 10);
        //this.acc = 1;

        //cap de l'accéleration
        if (this.accX > this.accLimit)
            this.accX = this.accLimit;
        if (this.accX < -this.accLimit)
            this.accX = -this.accLimit;
        if (this.accY > this.accLimit)
            this.accY = this.accLimit;
        if (this.accY < -this.accLimit)
            this.accY = -this.accLimit;

        velX += accX;
        velY += accY;

        accX = 0;
        accY = 0;

        //application de la friction
        velX *= this.friction;
        velY *= this.friction;

        //cap de la velocité
        if (this.velX > this.velLimit)
            this.velX = this.velLimit;
        if (this.velX < -this.velLimit)
            this.velX = -this.velLimit;
        if (this.velY > this.velLimit)
            this.velY = this.velLimit;
        if (this.velY < -this.velLimit)
            this.velY = -this.velLimit;



        //arrondi à zero quand la valeur est très petite (ex : 0.000658 = 0)
        velX = approximatelyZero(velX);
        velY = approximatelyZero(velY);


        velXInteger = Math.round(velX); // nombre de pixel pour le déplacement
        velYInteger = Math.round(velY);
        prevPosX = posX;
        prevPosY = posY;

        //déplacement vers le haut
        if(velY < 0){
            for (posY = posY; posY > prevPosY + velYInteger; posY--) {
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX, posY, 1);
                if(col[0]) {

                    break;
                }
            }
        }
        //déplacement vers le bas
        if(velY > 0) {
            for (posY = posY; posY < prevPosY + velYInteger; posY++) {
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX, posY, 1);
                if(col[1]) {
                    break;
                }
            }
        }
        //déplacement vers la gauche
        if(velX < 0){
            for (posX = posX; posX > prevPosX + velXInteger; posX--) {
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX, posY, 1);
                if(col[2]) {
                    break;
                }
            }
        }
        //déplacement vers la droite
        if(velX > 0) {
            for (posX = posX; posX < prevPosX + velXInteger; posX++) {
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX, posY, 1);
                if(col[3]) {
                    break;
                }
            }
        }


        //gestion de la souris
        this.mouseDeltaX = this.mouseX - this.posX;
        this.mouseDeltaY = this.mouseY - this.posY;
        double deg = Math.toDegrees(Math.atan2(this.mouseDeltaY, -this.mouseDeltaX));
        this.newAngle = lastAngle - deg;
        this.lastAngle = deg;
        System.out.println(newAngle);

    }

    public void display(){

        image.setTranslateX(posX);
        image.setTranslateY(posY);
        imageWeapon.setTranslateX(posX);
        imageWeapon.setTranslateY(posY);
        imageWeapon.getTransforms().add(new Rotate(this.newAngle, 50, 16));

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

    public ImageView getImageWeapon() {
        return imageWeapon;
    }
}
