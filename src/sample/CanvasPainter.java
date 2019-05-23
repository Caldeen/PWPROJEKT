package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CanvasPainter {
    private final Image shipImage=new Image("file:assets/ship.png");
    public CanvasPainter() {
    }

    public void drawFromPortStands(ArrayList<PortStand>portStands,GraphicsContext graphicsContext){
        for(PortStand portStand:portStands){
            int spacing=0;
            for(int i=0;i<portStand.getShipsToDraw();i++){
                    graphicsContext.drawImage(shipImage,portStand.getStartX(),portStand.getStartY()+95+spacing);
                    spacing+=shipImage.getHeight()+10;
            }
            graphicsContext.setFill(Color.ANTIQUEWHITE);
            graphicsContext.fillText("waiting"+portStand.getShipsToDraw()+"\nLoad per Time Step: "+portStand.getLoadPerTimestep()
                    ,portStand.getStartX(),15);
            portStand.drawPortStand();

        }
    }
    public void drawBackground(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.LIGHTSTEELBLUE);
        graphicsContext.fillRect(600,0,900,900);
        graphicsContext.setFill(Color.SADDLEBROWN);
        graphicsContext.fillRect(600,0,900,137);
    }

}


