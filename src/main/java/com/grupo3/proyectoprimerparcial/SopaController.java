package com.grupo3.proyectoprimerparcial;

import TDAs.CircularDoublyLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import partida.Jugador;
import partida.Partida;
import sopa_letras.Letra;
import sopa_letras.Sopa;

public class SopaController implements Initializable {

    
    @FXML
    private AnchorPane panelCentral;
    
    private GridPane gridpane;
    private Jugador jugadorActual;
    
    private boolean modoAgregarFila;
    private boolean modoEliminarFila;
    private boolean modoAgregarColumna;
    private boolean modoEliminarColumna;
    
    @FXML
    private VBox leftVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox playerTwoPane;
    @FXML
    private HBox vidasJ1;
    @FXML
    private HBox vidasJ2;
    @FXML
    private HBox topHBox;
    @FXML
    private HBox bottomHBox;
    @FXML
    private HBox buttons;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        if(Partida.jugadorDos == null) playerTwoPane.setVisible(false);
        jugadorActual = Partida.jugadorUno;
        
        vidasJ1.getChildren().forEach((n) -> n.setVisible(true) );
        vidasJ2.getChildren().forEach((n) -> n.setVisible(true) );
        
    }

    
    public void generarSopa(){
        //Limpio la Sopa Anterior
        panelCentral.getChildren().clear();
        leftVBox.getChildren().clear();
        rightVBox.getChildren().clear();
        topHBox.getChildren().clear();
        bottomHBox.getChildren().clear();
        
        //Creo mi gridpane que muestra mi sopa de letras
        gridpane=new GridPane();
        
        //Obtengo mi sopa de letras que lleva el control por detras del javafx
        Sopa sopaDeLetras = Partida.sopa;
        
        //tamaños dinámicos
        double width = 500/sopaDeLetras.getN_columnas();
        double height = 500/sopaDeLetras.getN_filas();
        double tamaño_letra = (width >= height)?height/2:width/2;
        
        //Recorro mi sopa por fila y columna, uso for porque es esencial guardar las coordenadas (x,y) para el gridpana
        for(int y=0;y<sopaDeLetras.getSopa().size();y++){
            
            CircularDoublyLinkedList<Letra> fila=sopaDeLetras.getSopa().get(y); //Obtengo mi lista que hace de fila
            
            //creación flechas para mover filas
            StackPane pane_2 = new StackPane();
            pane_2.setPrefHeight(height);
            pane_2.setCursor(Cursor.HAND);
            pane_2.setOpacity(0);
            pane_2.setUserData(y+1);
                
            Label flecha_izq = new Label("⮜");
            flecha_izq.setStyle("-fx-font-size: "+tamaño_letra+"px;");
            flecha_izq.setMouseTransparent(true);
            
            pane_2.getChildren().add(flecha_izq);
            StackPane.setAlignment(flecha_izq ,Pos.CENTER_RIGHT);
            
            leftVBox.getChildren().add(pane_2);
            
            pane_2.setOnMouseEntered(mouseEnteredArrow);
            pane_2.setOnMouseExited(mouseExitedArrow);
            pane_2.setOnMouseClicked(moveRowBackwards);
            
            StackPane pane_3 = new StackPane();
            pane_3.setPrefHeight(height);
            pane_3.setCursor(Cursor.HAND);
            pane_3.setOpacity(0);
            pane_3.setUserData(y+1);
                
            Label flecha_der = new Label("⮞");
            flecha_der.setStyle("-fx-font-size: "+tamaño_letra+"px;");
            flecha_der.setMouseTransparent(true);
            
            pane_3.getChildren().add(flecha_der);
            StackPane.setAlignment(flecha_der ,Pos.CENTER_RIGHT);
            
            rightVBox.getChildren().add(pane_3);
            
            pane_3.setOnMouseEntered(mouseEnteredArrow);
            pane_3.setOnMouseExited(mouseExitedArrow);
            pane_3.setOnMouseClicked(moveRowForward);
            
            for(int x=0;x<fila.size();x++){
                int coordX=x;
                int coordY=y;
                Letra l=fila.get(x); //Obtengo mi elemento por columna
                Character c=l.getLetra(); //Extraigo el valor del objeto letra
                
                //creación letras
                StackPane pane = new StackPane();
                pane.setPrefSize(width, height);
                pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
                pane.setCursor(Cursor.HAND);
                pane.setUserData(l);
                
                Label contenedor=new Label(String.valueOf(c)); //Lo contengo en un label para mostrar
                contenedor.setMouseTransparent(true);
                
                contenedor.setStyle("-fx-font-family: 'Tahoma'; -fx-font-size: "+tamaño_letra+"px;");
                
                pane.getChildren().add(contenedor);
                StackPane.setAlignment(contenedor ,Pos.CENTER);
                
                pane.setOnMouseClicked(t->seleccionarLetra(l,coordX,coordY));
                
                pane.setOnMouseEntered(mouseEnteredLetter);
                pane.setOnMouseExited(mouseExitedLetter);
                pane.setOnMousePressed(mouseExitedLetter);
                pane.setOnMouseReleased(mouseEnteredLetter);
                
                gridpane.add(pane,x,y); //Agrego al gridpane el contener en la posicion X,Y
                
            }
            
        }
        
        // Creación flechas para mover Columnas
        for(int i = 0; i < sopaDeLetras.getN_columnas(); i++){
            
            StackPane pane_4 = new StackPane();
            pane_4.setPrefSize(width, 30);
            pane_4.setCursor(Cursor.HAND);
            pane_4.setOpacity(0);
            pane_4.setUserData(i+1);
                
            Label flecha_aba = new Label("⮟");
            flecha_aba.setStyle("-fx-font-size: "+tamaño_letra*0.8+"px;");
            flecha_aba.setMouseTransparent(true);
            
            pane_4.getChildren().add(flecha_aba);
            StackPane.setAlignment(flecha_aba ,Pos.CENTER);
            
            bottomHBox.getChildren().add(pane_4);
            
            pane_4.setOnMouseEntered(mouseEnteredArrow);
            pane_4.setOnMouseExited(mouseExitedArrow);
            pane_4.setOnMouseClicked(moveColumnForward);
            
            StackPane pane_5 = new StackPane();
            pane_5.setPrefSize(width, 30);
            pane_5.setCursor(Cursor.HAND);
            pane_5.setOpacity(0);
            pane_5.setUserData(i+1);
                
            Label flecha_arr = new Label("⮝");
            flecha_arr.setStyle("-fx-font-size: "+tamaño_letra*0.8+"px;");
            flecha_arr.setMouseTransparent(true);
            
            pane_5.getChildren().add(flecha_arr);
            StackPane.setAlignment(flecha_arr ,Pos.CENTER);
            
            topHBox.getChildren().add(pane_5);
            
            pane_5.setOnMouseEntered(mouseEnteredArrow);
            pane_5.setOnMouseExited(mouseExitedArrow);
            pane_5.setOnMouseClicked(moveColumnBackwards);
        }
        
        panelCentral.getChildren().add(gridpane);
        
    }
    
    
    // EVENT HANDLERS
    
    EventHandler<MouseEvent> mouseEnteredLetter = (event) -> {
        
        StackPane p = (StackPane) event.getTarget();
        Letra l = (Letra) p.getUserData();
        int n_fila = l.getFila();
        int n_columna = l.getColumna();
        
        if(modoEliminarColumna || modoAgregarColumna){
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getColumna() == n_columna)
                    .forEach((Node n) -> n.setStyle(n.getStyle()+" -fx-background-color: #BFE1FF;"));
        }else if(modoEliminarFila || modoAgregarFila){
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getFila() == n_fila)
                    .forEach((Node n) -> n.setStyle(n.getStyle()+" -fx-background-color: #BFE1FF;"));
        }else{
            p.setStyle(p.getStyle()+" -fx-background-color: #BFE1FF;");
        }
        event.consume();
    };
    
    EventHandler<MouseEvent> mouseExitedLetter = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Letra l = (Letra) p.getUserData();
        int n_fila = l.getFila();
        int n_columna = l.getColumna();
        
        if(modoEliminarColumna || modoAgregarColumna){
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getColumna() == n_columna)
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        }else if(modoEliminarFila || modoAgregarFila){
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getFila() == n_fila)
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        }else{
            p.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        }
        
        event.consume();
    };
    
    EventHandler<MouseEvent> mouseEnteredArrow = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        p.setOpacity(1);
        event.consume();
    };
    
    EventHandler<MouseEvent> mouseExitedArrow = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        p.setOpacity(0);
        event.consume();
    };
    
    EventHandler<MouseEvent> moveRowForward = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Partida.sopa.avanzarFila((Integer) p.getUserData());
        refrescarSopa();
        event.consume();
    };
    
    EventHandler<MouseEvent> moveRowBackwards = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Partida.sopa.retrocederFila((Integer) p.getUserData());
        refrescarSopa();
        event.consume();
    };
    
    EventHandler<MouseEvent> moveColumnForward = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Partida.sopa.avanzarColumna((Integer) p.getUserData());
        refrescarSopa();
        event.consume();
    };
    
    EventHandler<MouseEvent> moveColumnBackwards = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Partida.sopa.retrocederColumna((Integer) p.getUserData());
        refrescarSopa();
        event.consume();
    };
    
    public void seleccionarLetra(Letra l,int posicionX, int posicionY){
        //Implementacion del click
    }
    
    private void refrescarSopa(){
        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
    }
    
    private void cambiarTurno(){
        jugadorActual = ((jugadorActual==Partida.jugadorUno)?Partida.jugadorDos:Partida.jugadorUno);
        comprobarModificaciones();
    }
    
    private void comprobarModificaciones(){
        if(jugadorActual.getModificaciones() == 0) buttons.setDisable(true);
        else buttons.setDisable(false);
    }

    private void quitarVidaJugadorActual(){
        if(jugadorActual == Partida.jugadorUno){
            vidasJ1.getChildren().remove(vidasJ1.getChildren().size()-1);
        }else{
            vidasJ2.getChildren().remove(vidasJ2.getChildren().size()-1);
        }
    }
    
    @FXML
    private void exitToMenu(ActionEvent event) throws IOException {
        App.setRoot("MainMenu");
    }

    @FXML
    private void agregarFila(ActionEvent event) {
        modoAgregarFila = !modoAgregarFila;
        modoEliminarFila = modoAgregarColumna = modoEliminarColumna = false;
    }

    @FXML
    private void eliminarFila(ActionEvent event) {
        modoEliminarFila = !modoEliminarFila;
        modoAgregarFila = modoAgregarColumna = modoEliminarColumna = false;
    }

    @FXML
    private void agregarColumna(ActionEvent event) {
        modoAgregarColumna = !modoAgregarColumna;
        modoAgregarFila = modoEliminarFila = modoEliminarColumna = false;
    }

    @FXML
    private void eliminarColumna(ActionEvent event) {
        modoEliminarColumna = !modoEliminarColumna;
        modoEliminarFila = modoAgregarFila = modoAgregarColumna = false;
    }
}