package sample;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

public class Ship extends Thread{
    private String name;
    private int cargoUnits;
    private int unitsToLoad;
    private boolean dockable=true;
    private PortStand portStand;

    public Ship(String name, int cargoUnits, int unitsToLoad, PortStand portStand) {
        this.name = name;
        this.portStand=portStand;
        this.cargoUnits = cargoUnits;
        this.unitsToLoad = unitsToLoad;
    }
    public Ship createFromShipTemplate(Ship shipT, Label label){
        Ship ship=new Ship(shipT.getShipName(),shipT.getCargoUnits(),shipT.getUnitsToLoad(),shipT.getPortStand());
        if(shipT.getShipName()==null||shipT.getShipName().length()==0||shipT.getCargoUnits()==-1||shipT.getUnitsToLoad()==-1||shipT.getPortStand()==null){
            label.setTextFill(Paint.valueOf("red"));
            label.setText("Podaj wszystkie wartości!");
            return null;
        }
        else{
            label.setText("");
            label.setTextFill(Paint.valueOf("green"));
            label.setText("Stworzono!");
            return ship;
        }

    }

    public boolean isDockable() {
        return dockable;
    }
    public void load(int value){

        if(cargoUnits>0){
            cargoUnits-=value;
            if(cargoUnits<0)
                cargoUnits=0;
        }else
        if(unitsToLoad>0){
            unitsToLoad-=value;
            if(unitsToLoad<0)
                unitsToLoad=0;
        }
    }
    public void setDockable(boolean dockable) {
        this.dockable = dockable;
    }
    public boolean canLeave(){
        return cargoUnits==0&&unitsToLoad==0;
    }
    public String getShipName() {
        return name;
    }
    public void printLoad(){
        System.out.println("Shipname: "+name+" cargo: "+cargoUnits+" unitsToLoad: "+unitsToLoad );
    }
    public int getCargoUnits() {
        return cargoUnits;
    }
    public int getUnitsToLoad() {
        return unitsToLoad;
    }
    @Override
    public void run(){
        while(dockable)
            portStand.dock(this);

    }



    public void setShipName(String name) {
        this.name = name;
    }

    public void setCargoUnits(int cargoUnits) {
        this.cargoUnits = cargoUnits;
    }

    public void setUnitsToLoad(int unitsToLoad) {
        this.unitsToLoad = unitsToLoad;
    }

    public PortStand getPortStand() {
        return portStand;
    }

    public void setPortStand(PortStand portStand) {
        this.portStand = portStand;
    }
}
