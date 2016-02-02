package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity {

    protected String name;

    // pour tester ( plus tard on utilisera des tableaux pour les animations... )
    protected ImageView image;
    protected Image imagePath;

    protected int health;
    protected float moveSpeed;
    protected int dodge;
    protected int stealth;
    protected String state;
    protected String facing;

    // position du joueur sur la carte
    protected int posX;
    protected int posY;
    protected int popX;
    protected int popY;
    private int prevPosX;
    private int prevPosY;
    protected float accX;
    protected float accY;
    protected float accLimit;
    protected float velX;
    protected float velY;
    protected float velLimit;
    private int velXInteger;
    private int velYInteger;

    // collider
    protected int colX;
    protected int colY;
    protected int colWidth;
    protected int colHeight;

    protected float friction;

    //animations
    private SpriteAnimation animationWalk;
    private SpriteAnimation animationIdle;

    Level level;

    public Entity(){

    }

    public void move(Level l){

        //récupération des collisions en fonction du level
        //à ajouter : envoyer la position du rectangle de collision + w/h
        boolean[] col = l.collision(posX+colX, posY+colY, colWidth, colHeight, 1);

        //à ajouter : récupération des collisions entre Entity

        //récupération de l'accélération
        float[] acc = getAcc(col);
        this.accX = acc[0];
        this.accY = acc[1];

        //récupération de l'état (WALK / IDLE...)
        this.state = getState();

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
            //il vaut mieux faire un while du coup
            while(posY > prevPosY + velYInteger){
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX+colX, posY+colY, colWidth, colHeight, 1);
                if(col[0]) {

                    break;
                }
                posY--;
            }
        }
        //déplacement vers le bas
        if(velY > 0) {
            while(posY < prevPosY + velYInteger){
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX+colX, posY+colY, colWidth, colHeight, 1);
                if(col[1]) {
                    break;
                }
                posY++;
            }
        }
        //déplacement vers la gauche
        if(velX < 0){
            while(posX > prevPosX + velXInteger){
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX+colX, posY+colY, colWidth, colHeight, 1);
                if(col[2]) {
                    break;
                }
                posX--;
            }
        }
        //déplacement vers la droite
        if(velX > 0) {
            while(posX < prevPosX + velXInteger) {
                /*
                ------- OPTIMISATION -------
                pour ne pas rechecker la map entière à chaque itération
                il faut qu'au check initial, au lieu de retourner false/true
                retourner l'ID de la tile à check pour ne faire le test que sur
                elle
                 */
                col = l.collision(posX+colX, posY+colY, colWidth, colHeight, 1);
                if(col[3]) {
                    break;
                }
                posX++;
            }
        }


        /* à déplacer
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
        */
    }



    public void display(){

        //déplacement du personnage
        image.setTranslateX(posX);
        image.setTranslateY(posY);

        if(this.facing.equals("RIGHT")) {
            image.setScaleX(1);
        }else if(this.facing.equals("LEFT")){
            image.setScaleX(-1);
        }


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



    private float approximatelyZero(float f){

        float rF = f;
        if(rF > 0f && rF < 0.1f)
            rF = 0f;
        if(rF < 0f && rF > -0.1f)
            rF = 0f;

        return rF;
    }


    /*
        ------- GETTERS -------
     */

    protected float[] getAcc(boolean[] col){

        //calcul de l'accélération

        // return de l'accélération
        float[] acc = new float[2];
        acc[0] = this.accX;
        acc[1] = this.accY;
        return acc;
    }

    public String getState(){
        return "";
    }

    public ImageView getImage() {

        return this.image;
    }
}
