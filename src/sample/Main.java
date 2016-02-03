package sample;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    protected EntityPlayer player;
    protected Entity arrayEntities[];
    protected Level level;

    private long lastFpsTime;
    /** The current number of frames recorded */
    private int fps;

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
        //player.addListener(monster1);
        //player.addListener(monster2);

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

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        // keep looping round til the game ends
        while (runFlag)
        {
            //fermeture du thread à la fermeture du programme
            if(!pPrimaryStage.isShowing()){
                runFlag = false;
            }

            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            // update the frame counter
            lastFpsTime += updateLength;
            fps++;

            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= 1000000000)
            {
                System.out.println("(FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }

            // update the game logic
            update();

            // draw everyting
            display();

            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for
            // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
            try{
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            }catch(Exception e){

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
