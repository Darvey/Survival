package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

//import java.util.UUID;


public class BulletTmp {

    private final ImageView image;
    public final Image imagePath_0;
    public final Image imagePath_90;
    protected double posX;
    protected double posY;
    protected double direction;
    protected boolean visibility;
    private final double vel;
    private String id;
    protected Translate translate;


    public BulletTmp(int pPosX, int pPosY, double pDirection, boolean pVisibility){
        //this.id = UUID.randomUUID().toString();

        this.image = new ImageView();
        this.imagePath_0 = new Image(Main.class.getResourceAsStream("../img/bullet.png"));
        this.imagePath_90 = new Image(Main.class.getResourceAsStream("../img/bullet.png"));
        image.setImage(imagePath_0);
        double spread = Math.random() * 2 - 1;
        this.posX = pPosX;
        this.posY = pPosY;
        this.direction = pDirection + (spread / 10);
        this.vel = 20 + (Math.random()*2-1);
        this.image.setVisible(pVisibility);
    }

    public void update(){

        this.posX += Math.cos(this.direction) * this.vel;
        this.posY += Math.sin(this.direction) * this.vel;


        if (this.image.getTransforms().isEmpty()) {
            if(this.translate == null) {
                this.translate = new Translate(this.posX, this.posY);
                translate.setX(this.posX);
                translate.setY(this.posY);
                this.image.getTransforms().add(0, this.translate);
            }
        } else {
            translate.setX(this.posX);
            translate.setY(this.posY);
            this.image.getTransforms().set(0, translate);
        }
    }

    public void display(){
        //System.out.println(this.number);
        if(this.translate != null) {
            if (this.image.getTransforms().isEmpty()) {
                this.translate.setX(this.posX);
                this.translate.setY(this.posY);
                this.image.getTransforms().add(0, this.translate);
            } else {
                this.translate.setX(this.posX);
                this.translate.setY(this.posY);
                this.image.getTransforms().set(0, this.translate);
            }
        }else{
            this.translate = new Translate(this.posX, this.posY);
            this.image.getTransforms().add(0, this.translate);
        }
    }

    public ImageView getImage(){
        return this.image;
    }

    public String getId(){
        return this.id;
    }


}
