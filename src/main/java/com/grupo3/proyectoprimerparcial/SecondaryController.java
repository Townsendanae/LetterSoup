package com.grupo3.proyectoprimerparcial;

import TDAs.CircularDoublyLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sopa_letras.Letra;
import sopa_letras.Sopa;

public class SecondaryController implements Initializable {

    
    @FXML
    private VBox panelCentral;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        generarSopa(7,7);
        
    }

    
    public void generarSopa(int columnas, int filas){
        //Creo mi gridpane que muestra mi sopa de letras
        GridPane gridpane=new GridPane();
        //Creo mi sopa de letras que lleva el control por detras del javafx
        Sopa sopaDeLetras=new Sopa(columnas,filas);
        //Recorro mi sopa por fila y columna, uso for porque es esencial guardar las coordenadas (x,y) para el gridpana
        for(int y=0;y<filas;y++){
            CircularDoublyLinkedList<Letra> fila=sopaDeLetras.getSopa().get(y); //Obtengo mi lista que hace de fila
            for(int x=0;x<columnas;x++){
                int coordX=x;
                int coordY=y;
                Letra l=fila.get(x); //Obtengo mi elemento por columna
                Character c=l.getLetra(); //Extraigo el valor del objeto letra
                
                Label contenedor=new Label(String.valueOf(c)); //Lo contengo en un label para mostrar
                contenedor.setOnMouseClicked(t->seleccionarLetra(l,coordX,coordY));
                gridpane.add(contenedor,x,y); //Agrego al gridpane el contener en la posicion X,Y
                
            }
            
        }
        panelCentral.getChildren().add(gridpane);
        
    }
    
    public void seleccionarLetra(Letra l,int posicionX, int posicionY){
        //Implementacion del click
    }
}