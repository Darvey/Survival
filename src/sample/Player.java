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
        this.agility = a;
        this.strength = s;
        this.constitution = c;
        this.dexterity = d;
        this.intelect = i;
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


}