package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.newdawn.slick.SlickException;

/**
 * Created by Administrateur on 24/01/2016.
 */



public class ItemTool extends Item {


    protected int strenghtNeeded;           // force nécessaire pour avoir la bonne description
    protected int dexterityNeeded;          // dexterité nécessaire pour avoir la bonne description
    protected int intelligenceNeeded;       // intelligence nécessaire pour avoir la bonne description

    public ItemTool(String name, Tile tile) throws SlickException{

        super(name, tile);

        this.type = "junk";
    }
}
