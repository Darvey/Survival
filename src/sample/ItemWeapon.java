package sample;


public class ItemWeapon extends Item{

    private String name;
    private int precision;
    private int damage;
    private int speed;
    private int newAttr;
    private int type;
    private int strenghtNeeded;
    private int dexterityNeeded;
    private int inteligenceNeeded;

    /**
    * Constructor
    */
    public ItemWeapon(String name,
                      int precision,
                      int damage,
                      int speed,
                      int newAttr,
                      int type,
                      int strenghtNeeded,
                      int dexterityNeeded,
                      int inteligenceNeeded)
    {
      this.name = name;
      this.precision = precision;
      this.damage = damage;
      this.speed = speed;
      this.newAttr = newAttr;
      this.type = type;
      this.strenghtNeeded = strenghtNeeded;
      this.dexterityNeeded = dexterityNeeded;
      this.inteligenceNeeded = inteligenceNeeded;
    }





}
