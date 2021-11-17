package com.grupo3.proyectoprimerparcial;

import TDAs.CircularDoublyLinkedList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import partida.Partida;
import sopa_letras.Letra;
import sopa_letras.Sopa;

public class SecondaryController implements Initializable {

    
    @FXML
    private AnchorPane panelCentral;
    
    private GridPane gridpane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        
    }

    
    public void generarSopa(){
        //Creo mi gridpane que muestra mi sopa de letras
        gridpane=new GridPane();
        //Obtengo mi sopa de letras que lleva el control por detras del javafx
        Sopa sopaDeLetras = Partida.sopa;
        //Recorro mi sopa por fila y columna, uso for porque es esencial guardar las coordenadas (x,y) para el gridpana
        for(int y=0;y<sopaDeLetras.getSopa().size();y++){
            CircularDoublyLinkedList<Letra> fila=sopaDeLetras.getSopa().get(y); //Obtengo mi lista que hace de fila
            for(int x=0;x<fila.size();x++){
                int coordX=x;
                int coordY=y;
                Letra l=fila.get(x); //Obtengo mi elemento por columna
                Character c=l.getLetra(); //Extraigo el valor del objeto letra
                
                //tamaños dinámicos
                double size_fila = 500/fila.size();
                double size_columna = 500/sopaDeLetras.getSopa().size();
                double tamaño_letra = (size_fila >= size_columna)?size_columna/2:size_fila/2;
                
                StackPane pane = new StackPane();
                pane.setPrefSize(size_fila, size_columna);
                pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
                pane.setCursor(Cursor.HAND);
                
                Label contenedor=new Label(String.valueOf(c)); //Lo contengo en un label para mostrar
                contenedor.setMouseTransparent(true);
                
                contenedor.setStyle("-fx-font-family: 'Tahoma'; -fx-font-size: "+tamaño_letra+"px;");
                
                pane.getChildren().add(contenedor);
                StackPane.setAlignment(contenedor ,Pos.CENTER);
                
                pane.setOnMouseClicked(t->seleccionarLetra(l,coordX,coordY));
                
                pane.setOnMouseEntered(mouseEnteredButton);
                pane.setOnMouseExited(mouseExitedButton);
                pane.setOnMousePressed(mouseExitedButton);
                pane.setOnMouseReleased(mouseEnteredButton);
                
                gridpane.add(pane,x,y); //Agrego al gridpane el contener en la posicion X,Y
                
            }
            
        }
        panelCentral.getChildren().add(gridpane);
        
    }
    
    EventHandler<MouseEvent> mouseEnteredButton = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        p.setStyle(p.getStyle()+" -fx-background-color: #BFE1FF;");
        event.consume();
    };
    
    EventHandler<MouseEvent> mouseExitedButton = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        p.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        event.consume();
    };
    
    public void seleccionarLetra(Letra l,int posicionX, int posicionY){
        //Implementacion del click
    }
}