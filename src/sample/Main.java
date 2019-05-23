package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
public static long timeStep=400;
public PortStand port1,port2,port3;
    private ArrayList<PortStand> portStands;
@Override
 public void init(){ }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Canvas canvas=new Canvas(1600,900);
        GraphicsContext graphicsContext=canvas.getGraphicsContext2D();
        CanvasPainter canvasPainter=new CanvasPainter();
        canvasPainter.drawBackground(graphicsContext);
        port1=new PortStand(new ArrayList<Ship>(),new ArrayList<Ship>(),30,graphicsContext,
                620,50);
        port2=new PortStand(new ArrayList<Ship>(),new ArrayList<Ship>(),50,graphicsContext,
                920,50);
        port3=new PortStand(new ArrayList<Ship>(),new ArrayList<Ship>(),70,graphicsContext,
                1220,50);
        portStands=new ArrayList<PortStand>();
        portStands.add(port1);
        portStands.add(port2);
        portStands.add(port3);

        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(5,5,5,5));
        gridPane.setHgap(3);
        gridPane.setVgap(3);

        Ship shipTemplate=new Ship(null,-1,-1,null);

        Slider newShipCargoSelection=new Slider(0,500,250);
        TextField name=new TextField();
        Button submit=new Button();

        sliderAndButtonCreation(gridPane,0,1,name,submit,"ShipCargo",
                12,newShipCargoSelection,false,10,shipTemplate,1);
        sliderAndButtonCreation(gridPane,0,3,new TextField(),new Button(),"ShipUnitsTouUnload",
                12,new Slider(0,500,250),false,10,shipTemplate,2);
        createShipNameSelection(gridPane,shipTemplate);
        createNewShipButton(gridPane,shipTemplate);
        createPortStandSelection(gridPane,shipTemplate,4);

        primaryStage.setTitle("Project");
        Group root=new Group();
        Scene scene=new Scene(root,1600,900);



        root.getChildren().add(canvas);
        root.getChildren().add(gridPane);

        primaryStage.setScene(scene);
        primaryStage.show();
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                canvasPainter.drawBackground(graphicsContext);
                canvasPainter.drawFromPortStands(portStands,graphicsContext);
            }
        },0,timeStep);
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void sliderAndButtonCreation(GridPane gridPane,int whereX,int whereY,TextField textField,Button button,String prompName,
                                         int prefColumnCount,Slider slider,boolean isRounded,int majorTickUnit,Ship shipT,int which){


        textField.setPromptText(prompName);
        textField.setPrefColumnCount(prefColumnCount);
        textField.getText();

        gridPane.add(textField,whereX,whereY);


        gridPane.add(slider,whereX,whereY+1);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(majorTickUnit);
        slider.setSnapToTicks(true);
        showSliderValue(slider,textField,isRounded);
        GridPane.setColumnSpan(slider,3);

        Label label=new Label();
        label.setPrefWidth(200);
        label.setText("Set (( "+ prompName+" " + "))");
        gridPane.add(label,whereX+2,whereY);

        gridPane.add(button,whereX+1,whereY);
        button.setText("Confirm");
        setActionOnButton(button,slider,label,prompName,which,shipT);

    }
    private void setActionOnButton(Button button,Slider slider,Label label,String promptName,int which,Ship shipT){
     button.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
             label.setText("Input: "+slider.getValue() +"( "+promptName+" )");
             switch (which){
                 case 1:
                     shipT.setCargoUnits((int)(slider.getValue()));
                     break;
                 case 2:
                     shipT.setUnitsToLoad((int)slider.getValue());
                     break;
                 case 4:
                     if(slider.getValue()==1)
                        shipT.setPortStand(port1);
                     if(slider.getValue()==2)
                         shipT.setPortStand(port2);
                     if(slider.getValue()==3)
                         shipT.setPortStand(port3);
                     break;
             }
         }
     });
    }
    private void showSliderValue(Slider slider,TextField textField,boolean isRounded){
     slider.setShowTickLabels(true);
     slider.setShowTickMarks(true);
     slider.valueProperty().addListener(new ChangeListener<Number>() {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             if(isRounded)
                 newValue=newValue.shortValue();
            textField.setText(new DecimalFormat(".##").format(newValue));

         }
     });
    }
    private void createNewShipButton(GridPane gridPane,Ship shipT){
     Button button=new Button("New Ship");
    Label label=new Label();
     button.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
             Ship newShip= shipT.createFromShipTemplate(shipT,label);
             if(newShip!=null){
                 shipT.getPortStand().decShipsToDraw();
                 newShip.start();
                 shipT.printLoad();
             }
         }
     });
     button.setPrefSize(100,100);
     gridPane.add(button,0,9);
     gridPane.add(label,0,10);

}
    private void createShipNameSelection(GridPane gridPane,Ship shipT){
        TextField textField=new TextField();
        textField.setPrefColumnCount(12);
        textField.setPromptText("ShipName");
        gridPane.add(textField,0,0);

        Label label=new Label();
        label.setPrefWidth(200);
        label.setText("Set (( ShipName ))");
        gridPane.add(label,2,0);
        Button button=new Button("Confirm");
        gridPane.add(button,1,0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shipT.setShipName(textField.getText());
                label.setText("input: "+textField.getText());
            }
        });

    }
    private void createPortStandSelection(GridPane gridPane,Ship shipT,int which){
        sliderAndButtonCreation(gridPane,0,7,new TextField(),new Button()," PortStand",12,new Slider(1,3,5),
                true,1,shipT,which);

    }

}
