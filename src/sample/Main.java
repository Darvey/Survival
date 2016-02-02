package sample;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    protected EntityPlayer p;
    protected Monster m[];
    protected Level level;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //creation du groupe
        Group root = new Group();

        int widthLevel = 15;
        int heigthLevel = 10;
        this.level = new Level(widthLevel, heigthLevel);
        for(int i = 0; i < this.level.width; i++){
            for(int j = 0; j < this.level.height; j++){
                root.getChildren().add(this.level.getTile(i,j).getImage());
            }
        }
        /*System.out.println(L.getItem(3,3));
        root.getChildren().add(L.getItem(3,3).getImage());*/

        // Init primaryStage //
        Scene home = new Scene(root, 960, 640, true);

        // Creation joueur
        p = new EntityPlayer("Bernard",16,100,55,100,12,this.level); //s a d c i

        // Ajout joueur au group
        root.getChildren().add(p.getImage());
        //root.getChildren().add(p.getImageWeapon());
        //root.getChildren().add(p.getRight());
        //root.getChildren().add(p.getLeft());

        // init controles du joueur
        new Controller(home,p,this.level);

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

                gameLoop(primaryStage);

            }
        }.start();


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void gameLoop(Stage pPrimaryStage){

        boolean runFlag = true;

        double delta = 0.0166d; //60fps

        //startup();


        double nextTime = (double)System.nanoTime() / 1000000000.0;
        double maxTimeDiff = 0.5;
        int skippedFrames = 1;
        int maxSkippedFrames = 5;
        while(runFlag)
        {
            //fermeture du thread
            if(!pPrimaryStage.isShowing()){
                runFlag = false;
            }



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
        this.p.move(this.level);
        for(Monster monster : this.m){
            //monster.behaviourMove();
            monster.move(this.level);
        }
    }
    private void display(){
        this.p.display();
        for(Monster monster : this.m){
            monster.display();
        }

    }
}
