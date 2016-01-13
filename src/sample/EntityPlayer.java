package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

public class EntityPlayer extends Entity{


    protected String name;

    // Skills
    protected int valueCompFire;
    protected int valueCompBow;
    protected int valueCompGun;
    protected int valueCompCut;

    // Specs
    protected int agility;
    protected int strength;
    protected int constitution;
    protected int dexterity;
    protected int intelect;

    protected Inventory inv;

    // player position on map
    protected float posX;
    protected float posY;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;

    //-----move-----
    protected float acc;
    protected float accX;
    protected float accY;
    protected boolean pressedUp;
    protected boolean pressedDown;
    protected boolean pressedLeft;
    protected boolean pressedRight;



    //constantes
    protected int accLimit;
    protected float friction;
    //--------------

    /*
        Player Constructor.
        @param name nom du joueur
        @param a agility value
        @param s strength value
        @param c constitution value
        @param d exterity value
        @param i intelect value
     */
    public EntityPlayer(String name, int a, int s, int c, int d, int i) {

        this.name = name;

        this.agility = a;
        this.strength = s;
        this.constitution = c;
        this.dexterity = d;
        this.intelect = i;

        this.valueCompBow = 0;
        this.valueCompCut = 0;
        this.valueCompFire = 0;
        this.valueCompGun = 0;

        this.inv = new Inventory();

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/bernard.png"));
        image.setImage(imagePath);

        posX = 0f;
        posY = 0f;

        image.setTranslateY(posY);
        image.setTranslateX(posX);

        //move
        this.acc = 0.4f;
        this.accX = 0f;
        this.accY = 0f;

        this.pressedDown = false;
        this.pressedLeft = false;
        this.pressedRight = false;
        this.pressedUp = false;
        this.accLimit = 2;
        this.friction = 0.9f;


        // Debut test pour inventaire
        Item gk = new Item("goldKey_s");
        this.inv.addItem(gk);
        // Fin test pour inventaire

        new AnimationTimer(){
            public void handle(long arg0){
                moveto();
            }
        }.start();

    }
    /*
        Default Constructor
     */
    public EntityPlayer(){

    }



    /*
        create a backup of the player
        @param f file name to use for backup
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

    /*
        move the character in the direction given by parameter
     */
    public void move(int dir){

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
                return;
        }

    }

    public void moveto() {
        System.out.println("accX : "+ this.accX + "/ accY :" + this.accY);
        if (this.pressedUp) {
            this.accY -= this.acc;
        }
        if (this.pressedDown) {
            this.accY += this.acc;
        }
        if (this.pressedLeft) {
            this.accX -= this.acc;
        }
        if (this.pressedRight) {
            this.accX += this.acc;
        }

        accX *= friction;
        accY *= friction;

        if (this.accX > this.accLimit)
            this.accX = this.accLimit;
        if (this.accX < -this.accLimit)
            this.accX = -this.accLimit;
        if (this.accY > this.accLimit)
            this.accY = this.accLimit;
        if (this.accY < -this.accLimit)
            this.accY = -this.accLimit;

        accX = approximatelyZero(accX);
        accY = approximatelyZero(accY);

        this.posX += accX;
        this.image.setTranslateX(posX);

        this.posY += accY;
        this.image.setTranslateY(posY);
    }

    private float approximatelyZero(float f){

        float rF = f;
        if(rF > 0f && rF < 0.1f)
            rF = 0f;
        if(rF < 0f && rF > -0.1f)
            rF = 0f;

        return rF;
    }

    public void displayInventory(){
        this.inv.display();
    }

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
        return intelect;
    }

    public int getStrength() {
        return strength;
    }

    public ImageView getImage() {
        return image;
    }
}