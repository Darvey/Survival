package sample;

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

    protected Inventory iv;

    // player position on map
    protected int posX;
    protected int posY;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;

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

        this.iv = new Inventory();

        image = new ImageView();
        Image imagePath = new Image(Main.class.getResourceAsStream("../img/shroom.png"));
        image.setImage(imagePath);

        posX = 0;
        posY = 0;

        image.setTranslateY(posY);
        image.setTranslateX(posX);
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
            case 0:
                this.posY -= 10;
                this.image.setTranslateY(posY);
                break;
            case 1:
                this.posX +=10;
                this.image.setTranslateX(posX);
                break;
            case 2:
                this.posY +=10;
                this.image.setTranslateY(posY);
                break;
            case 3:
                this.posX -=10;
                this.image.setTranslateX(posX);
                break;
            default:;
        }
    }

    public Inventory getIv() {
        return iv;
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