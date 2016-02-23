package sample;

/**
 * Class qui gère les collisions entre Entity et Level
 */
public class Collision {

    private Level level;
    private Entity entity;


    /**
     * default Constructor
     */
    public Collision(){
        this(null, null);
    }


    /**
     * Constructor
     * @param entity : entité pour qui on check les coliisions
     * @param level : niveau dans lequel on check les collisions
     */
    public Collision(Entity entity, Level level){
        this.level = level;
        this.entity = entity;
    }

    /**
     * Est-ce qu'il y a une collision en bas ?
     * @return : true si collision
     */
    private boolean collisionBot(){

        return (this.level.getTile(this.entity.posX + 16, this.entity.posY + 64).solid);
    }


    /**
     * Est-ce qu'il y a une collision en haut ?
     * @return : true si collision
     */
    private boolean collisionTop(){

        return (
                this.level.getTile(
                        this.entity.posX + 16,
                        this.entity.posY
                ).solid
                &&
                !this.level.getTile(
                        this.entity.posX + 16,
                        this.entity.posY
                ).platform
        );
    }


    /**
     * Est-ce qu'il y a une collision à gauche ?
     * @return : true si collision
     */
    private boolean collisionLeft(){

        return (this.level.getTile(this.entity.posX, this.entity.posY + 32).solid);
    }


    /**
     * Est-ce qu'il y a une collision à droite ?
     * @return : true si collision
     */
    private boolean collisionRight(){

        return (this.level.getTile(this.entity.posX + 32, this.entity.posY + 32).solid);
    }


    /**
     * définie l'entité
     * @param entity : l'entité
     */
    public void setEntity(Entity entity){

        this.entity = entity;
    }


    /**
     * définie le niveau
     * @param level : le niveau
     */
    public void setLevel(Level level){

        this.level = level;
    }


    /**
     * renvoie l'entité
     * @return : l'entité
     */
    public Entity getEntity(){

        return this.entity;
    }


    /**
     * renvoie le niveau
     * @return : le niveau
     */
    public Level getLevel(){

        return this.level;
    }
}
