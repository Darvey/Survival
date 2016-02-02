package sample;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    protected EntityPlayer player;
    protected Entity arrayEntities[];
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

        // Init primaryStage //
        Scene home = new Scene(root, 960, 640, true);

        // Creation joueur
        player = new EntityPlayer("Bernard",16,100,55,100,12,this.level, root); //s a d c i

        // Ajout joueur au group
        root.getChildren().add(player.getImage());
        root.getChildren().add(player.getImageWeapon());

        // init controles du joueur
        new Controller(home, this.player, this.level);

        //création d'un monstre
        Monster monster1 = new Monster("Albert la mouche", 200, 200);
        Monster monster2 = new Monster("Hector à babord", 400, 400);

        arrayEntities = new Entity[3];
        arrayEntities[0] = player;
        arrayEntities[1] = monster1;
        arrayEntities[2] = monster2;

        // Ajout monstre au group
        root.getChildren().add(monster1.getImage());
        root.getChildren().add(monster2.getImage());

        //les monstres écoutent le joueur
        player.addListener(monster1);
        player.addListener(monster2);

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

        //double delta = 0.0166d; //60fps
        double delta = 0.02d; //50fps

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

            if(true)
            if(currTime >= nextTime)
            {
                // assign the time for the next update
                nextTime += delta;

                update();
                //System.out.println("update");

                if(true)
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
        //this.player.move(this.level);
        for(Entity entity : this.arrayEntities){
            //monster.behaviourMove();
            entity.move(this.level);
        }
    }
    private void display(){
        //this.player.display();
        for(Entity entity : this.arrayEntities){
            entity.display();
        }

    }
}
