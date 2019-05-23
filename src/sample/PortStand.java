package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class PortStand {
    private GraphicsContext graphicsContext;
    private float timeRemaining;
    private ArrayList<Ship> activeShips;
    private ArrayList<Ship> waitingShips;
    private int loadPerTimestep;
    private int startX;
    private int startY;
    private volatile int shipsToDraw=0;
    private  final Image portImage=new Image("file:assets/portStand.png");
    public PortStand( ArrayList<Ship> activeShips, ArrayList<Ship> waitingShips,int loadPerTimestep,
                      GraphicsContext graphicsContext,int startX,int startY) {
        this.activeShips = activeShips;
        this.waitingShips = waitingShips;
        this.loadPerTimestep = loadPerTimestep;
        this.graphicsContext=graphicsContext;
        this.startX=startX;
        this.startY=startY;
        drawPortStand();
    }

    public synchronized void dock(Ship ship){
        if(!waitingShips.contains(ship)){
            waitingShips.add(ship);
            System.out.println("Ile: "+waitingShips.size());
        }
        while(!ship.isDockable()||!waitingShips.contains(ship)){
            try{
                ship.wait();
            }catch (Exception e) {
            }
        }
        waitingShips.remove(ship);
        activeShips.add(ship);
        while(!ship.canLeave()){
            ship.load(loadPerTimestep);
            try{
                ship.sleep(Main.timeStep);
            }catch (Exception e){

            }
            ship.printLoad();
        }
        shipsToDraw--;
        activeShips.remove(ship);
        ship.setDockable(false);
        this.notifyAll();
    }

    public void decShipsToDraw() {
        shipsToDraw++;
    }
    public int getShipsToDraw() {
        return shipsToDraw;
    }
    public void drawPortStand(){
        graphicsContext.drawImage(portImage,startX,startY);
    }
    public int getStartX() {
        return startX;
    }

    public int getLoadPerTimestep() {
        return loadPerTimestep;
    }

    public int getStartY() {
        return startY;
    }
}
