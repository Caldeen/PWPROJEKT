package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

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
            graphicsContext.setFill(Color.ORANGERED);
            graphicsContext.fillOval(portStand.getStartX()+160,portStand.getStartY()-35,30,30);
            graphicsContext.setFill(Color.LIGHTGREEN);
           // if(portStand.getFinishPercentage()!=0)
             graphicsContext.fillArc(portStand.getStartX()+160,portStand.getStartY()-35,30,30,1,360-portStand.getFinishPercentage()*360
                    , ArcType.ROUND);
            System.out.println(portStand.getFinishPercentage());
        }

    }
    public void drawBackground(GraphicsContext graphicsContext){
        graphicsContext.setFill(Color.LIGHTSTEELBLUE);
        graphicsContext.fillRect(600,0,900,900);
        graphicsContext.setFill(Color.SADDLEBROWN);
        graphicsContext.fillRect(600,0,900,137);
    }

}


