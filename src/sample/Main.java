package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application {

    protected EntityPlayer p;
    protected Monster m[];

    @Override
    public void start(Stage primaryStage) throws Exception{

        //creation du groupe
        Group root = new Group();

        Level L = new Level(15, 10);
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 10; j++){
                root.getChildren().add(L.getTile(i,j).getImage());
            }
        }
        /*System.out.println(L.getItem(3,3));
        root.getChildren().add(L.getItem(3,3).getImage());*/

        // Init primaryStage //
        Scene home = new Scene(root, 960, 640, true);

        // Creation joueur
        p = new EntityPlayer("Bernard",16,100,55,100,12,L); //s a d c i

        // Ajout joueur au group
        root.getChildren().add(p.getImage());
        root.getChildren().add(p.getImageWeapon());

        // init controles du joueur
        Controller cont = new Controller(home,p,L);

        //création d'un monstre
        Monster monster1 = new Monster("Albert la mouche", 200, 200);
        Monster monster2 = new Monster("Hector à babord", 400, 400);
        m = new Monster[2];
        m[0] = monster1;
        m[1] = monster2;

        // Ajout monstre au group
        root.getChildren().add(monster1.getImage());
        root.getChildren().add(monster2.getImage());

        //les monstres écoutent le joueur
        p.addListener(monster1);
        p.addListener(monster2);

        primaryStage.setTitle("Survival");
        primaryStage.setScene(home);
        primaryStage.show();

        new Thread() {
            public void run(){
                gameLoop();
            }
        }.start();


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void gameLoop(){

        boolean runFlag = true;

        double delta = 0.0166d; //60fps
        //double delta = 0.0333d; //30fps
        System.out.println(delta);
        //startup();

        // convert the time to seconds
        double nextTime = (double)System.nanoTime() / 1000000000.0;
        double maxTimeDiff = 0.5;
        int skippedFrames = 1;
        int maxSkippedFrames = 5;
        while(runFlag)
        {
            // convert the time to seconds
            double currTime = (double)System.nanoTime() / 1000000000.0;
            if((currTime - nextTime) > maxTimeDiff) nextTime = currTime;
            if(currTime >= nextTime)
            {
                // assign the time for the next update
                nextTime += delta;

                update();
                //System.out.println("update");


                if((currTime < nextTime) || (skippedFrames > maxSkippedFrames))
                {
                    display();
                    //System.out.println("draw");

                    skippedFrames = 1;
                }
                else
                {
                    skippedFrames++;
                }
            }
            else
            {
                // calculate the time to sleep
                int sleepTime = (int)(1000.0 * (nextTime - currTime));
                // sanity check
                if(sleepTime > 0)
                {
                    // sleep until the next update
                    try
                    {
                        Thread.sleep(sleepTime);
                    }
                    catch(InterruptedException e)
                    {
                        // do nothing
                    }
                }
            }





        }
    }
    private void update(){
        this.p.moveto();
        for(Monster monster : this.m){
            monster.behaviourMove();
        }
    }
    private void display(){
        this.p.display();
        for(Monster monster : this.m){
            monster.display();
        }

    }
}
