package sample;

import java.io.*;

public class Player {


    private String name;

    // Stats
    private int pv;
    private int speed;

    // Skills
    private int valueCompFire;
    private int valueCompBow;
    private int valueCompGun;
    private int valueCompCut;

    // Specs
    private int agility;
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelect;

    /*
        Player Constructor.
        @param name nom du joueur
        @param a agility value
        @param s strength value
        @param c constitution value
        @param d exterity value
        @param i intelect value
     */
    public Player(String name, int a, int s, int c, int d, int i) {

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

    public int getPv() {
        return pv;
    }

    public int getSpeed() {
        return speed;
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
}