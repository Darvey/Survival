package sample;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


/**
 * Class weapon
 * gère l'arme et son affichage ainsi
 * que les balles envoyées par celle-ci
 */
public class Weapon {

    /** identifiants de l'arme */
    private String id;
    private String name;

    /** éléments et caractéristiques graphiques */
    private SpriteSheet sprite;
    private double rotation;
    private String facing;

    /** position et dimension */
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int offsetCenterX;
    private int offsetCenterY;

    /** contrôle : clic gauche */
    private boolean pressedMouseLeft;

    /** caractéristiques de l'arme */
    private int attackSpeedBase;
    private int attackSpeed;
    private float spreadBase;
    private float spread;
    private int nBullet;

    /** compteur utilisé pour la vitesse d'attaque */
    private int timeAttack;

    /** joueur qui a l'arme équipé */
    EntityPlayer player;

    /** liste des balles et iterateur pour les suppressions propres */
    private List<Bullet> bulletList;
    private Iterator<Bullet> iterator;


    /**
     * default Constructor
     */
    public Weapon() throws SlickException{

        this(null);
    }


    /**
     * Constructor
     * @param player : joueur qui a l'arme
     * @throws SlickException
     */
    public Weapon(EntityPlayer player) throws SlickException{

        this.id = UUID.randomUUID().toString();
        this.name = "shotgun";

        this.sprite = new SpriteSheet("img/item/shotgun.png", 32, 32);
        this.sprite.setCenterOfRotation(24, 5);
        this.rotation = 0d;
        this.facing = "RIGHT";

        this.width = 67;
        this.height = 12;
        this.offsetCenterX = 24;
        this.offsetCenterY = 5;

        this.pressedMouseLeft = false;

        this.timeAttack = 0;
        this.attackSpeedBase = 60;
        this.nBullet = 8;

        this.player = player;

        this.bulletList = new ArrayList<>();
        this.iterator = this.bulletList.iterator();

        /** calcul des caractéristiques de l'arme en fonction des modificateurs du joueur */
        this.calculateFeatures();
    }


    /**
     * tir de l'arme
     * @param deltaX : pour la direction de la balle
     * @param deltaY : pour la direction de la balle
     */
    private void fire(double deltaX, double deltaY){

        /** si le tir est prêt (compteur à zéro) */
        if(this.timeAttack == 0){

            this.timeAttack = this.attackSpeed;

            for(int i = 0; i < nBullet; i++) {
                try {
                    Bullet bullet = new Bullet(this, deltaX, deltaY);
                    bulletList.add(bullet);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * rechargement de l'arme
     */
    private void reload(){

    }


    /**
     * mise à jour de l'arme
     * @param posX : position X du joueur
     * @param posY : position Y du joueur
     * @param mouseX : position X de la souris
     * @param mouseY : position Y de la souris
     */
    public void update(int posX, int posY, double mouseX, double mouseY){

        this.posX = posX;
        this.posY = posY;

        double deltaX = mouseX - this.posX - (this.player.width / 2);
        double deltaY = mouseY - this.posY - (this.player.height / 2);
        this.rotation = Math.toDegrees(Math.atan2(deltaY, deltaX));

        if(mouseX > posX + (this.player.width / 2)){
            this.facing = "RIGHT";
        }else{
            this.facing = "LEFT";
        }

        /** décrémentation du compteur pour savoir si le tir est en délai */
        if(this.timeAttack > 0){
            this.timeAttack--;
        }

        /** tir */
        if(this.pressedMouseLeft){
            this.fire(deltaX, deltaY);
        }



        /** update des balles et suppression */
        this.iterator = this.bulletList.iterator();
        while(iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.update();
            if(bullet.posX < 0 || bullet.posX > 640 || bullet.posY < 0 || bullet.posY > 480) {
                iterator.remove();
            }
        }
    }


    /**
     * rendu graphique de l'arme
     * @param g : graphics slick
     */
    public void render(Graphics g){

        Image image;

        if(this.facing == "RIGHT") {
            image = this.sprite.getFlippedCopy(false, false);
            image.setCenterOfRotation(this.offsetCenterX, this.offsetCenterY);
            this.posX += (this.player.width / 2) - this.offsetCenterX;
        }else{
            image = this.sprite.getFlippedCopy(true, false);
            this.rotation += 180;
            image.setCenterOfRotation(this.width - this.offsetCenterX, this.offsetCenterY);
            this.posX += (this.player.width / 2) - (this.width - this.offsetCenterX);
        }
        this.posY += (this.player.height / 2) - this.offsetCenterY;

        image.setFilter(1); // FILTER_LINEAR
        image.setRotation((float) this.rotation);
        image.draw(this.posX, this.posY);

        /** update des balles */
        for(Bullet bullet : bulletList){
            bullet.render();
        }
    }


    /**
     * calcul des caractéristiques de l'arme par rapport au modificateur du joueur
     */
    private void calculateFeatures(){

        this.attackSpeed = this.attackSpeedBase;
        this.spread = this.spreadBase;
    }


    /**
     * est-ce qu'on click ?
     */
    public void setPressedMouseLeft(){
        this.pressedMouseLeft = true;
    }


    /**
     * est-ce qu'on relâche le click ?
     */
    public void setReleasedMouseLeft(){
        this.pressedMouseLeft = false;
    }


    /**
     * renvoie la position X de l'arme
     * @return : position X de l'arme
     */
    public int getPosX(){

        return this.posX + (this.player.width / 2);
    }


    /**
     * renvoie la position Y de l'arme
     * @return : la position Y de l'arme
     */
    public int getPosY(){

        return this.posY + (this.player.height / 2);
    }


    /**
     * renvoie la liste des balles
     * @return
     */
    public List<Bullet> getBulletList(){

        return this.bulletList;
    }
}
